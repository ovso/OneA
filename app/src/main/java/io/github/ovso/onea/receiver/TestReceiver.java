package io.github.ovso.onea.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import io.github.ovso.onea.utils.Consts;
import timber.log.Timber;

public class TestReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    Timber.d("onReceive()");

    String action = intent.getAction();
    if (!TextUtils.isEmpty(action) && action.equals(Consts.BR_ACTION_NAME_A)) {
      String email = intent.getStringExtra(Consts.BR_KEY_EMAIL);
      int operator = intent.getIntExtra(Consts.BR_KEY_OPERATOR, -1);
      Timber.d("email = $s, operator = %s", email, operator);
    }
  }
}
