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
    Timber.d("onReceive()");

    if (checkAction(intent) && checkData(intent)) {
      String email = intent.getStringExtra(Consts.BR_KEY_EMAIL);
      int operator = intent.getIntExtra(Consts.BR_KEY_OPERATOR, 0);
      Timber.d("email = %s, operator = %s", email, operator);
      Prefs.putInt(Consts.PREFS_KEY_OPERATOR, operator);
      Prefs.putString(Consts.PREFS_KEY_EMAIL, email);
    }
  }

  private boolean checkData(Intent intent) {
    String email = intent.getStringExtra(Consts.BR_KEY_EMAIL);
    return !TextUtils.isEmpty(email);
  }

  private boolean checkAction(Intent intent) {
    String action = intent.getAction();
    return !TextUtils.isEmpty(action) && action.equals(Consts.BR_ACTION_NAME_A);
  }
}
