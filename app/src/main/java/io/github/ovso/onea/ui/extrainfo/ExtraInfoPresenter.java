package io.github.ovso.onea.ui.extrainfo;

import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.ui.utils.SimOperator;

public interface ExtraInfoPresenter {

  void onPause();

  void onResume();

  interface View {

    void setupHeader(RxBusHeaderInfo header);

    void setupExtraInfo(String extra);

    void hideRegisterButton();

    void showRegisterButton();

    void changeLayoutGravityForRegisterButton(SimOperator.Type operatorType);
  }
}
