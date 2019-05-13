package io.github.ovso.onea.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.pixplicity.easyprefs.library.Prefs;
import io.github.ovso.onea.utils.Consts;
import timber.log.Timber;

public class TestReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    switch (checkAction(intent)) {

      case DATA:
        saveData(intent);
        break;
      case MODE:
        saveMode(intent);
        break;
      case UNKNOWN:
        Timber.d("Unsuppored action");
        break;
    }
  }

  private void saveMode(Intent intent) {
    boolean mode = intent.getBooleanExtra(Consts.BR_KEY_MODE, false);
    Prefs.putBoolean(Consts.PREFS_KEY_MODE, mode);
    Timber.d("mode = %s", mode);
  }

  private void saveData(Intent intent) {
    String email = intent.getStringExtra(Consts.BR_KEY_EMAIL);
    int operator = intent.getIntExtra(Consts.BR_KEY_OPERATOR, 0);
    Prefs.putInt(Consts.PREFS_KEY_OPERATOR, operator);
    Prefs.putString(Consts.PREFS_KEY_EMAIL, email);
    Timber.d("data email = %s", email);
    Timber.d("data operator = %s", operator);
  }

  private Action checkAction(Intent intent) {
    String action = TextUtils.isEmpty(intent.getAction()) ? "" : intent.getAction();
    switch (action) {
      case Consts.BR_ACTION_DATA:
        return Action.DATA;
      case Consts.BR_ACTION_MODE:
        return Action.MODE;
      default:
        return Action.UNKNOWN;
    }
  }

  private enum Action {
    DATA, MODE, UNKNOWN
  }
}
