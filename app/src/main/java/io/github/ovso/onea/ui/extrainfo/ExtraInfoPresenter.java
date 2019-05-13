package io.github.ovso.onea.ui.extrainfo;

import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.utils.SimOperator;

public interface ExtraInfoPresenter {

  void onRegisterClick();

  void onDialogCloseClick();

  void onDialogCancelClick();

  void onCreate();

  void onDestroy();

  interface View {

    void setupHeader(RxBusHeaderInfo header);

    void setupExtraInfo(String extra);

    void hideRegisterButton();

    void showRegisterButton();

    void changeLayoutGravityForRegisterButton(SimOperator.Type operatorType);

    void showRegisterDialog();

    void changeDialogMessage(String msg);

    void startForegroundService(int time, String email);

    void exitApp();
  }
}
