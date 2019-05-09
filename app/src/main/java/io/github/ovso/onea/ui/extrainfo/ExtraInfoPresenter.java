package io.github.ovso.onea.ui.extrainfo;

import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;

public interface ExtraInfoPresenter {

  void onPause();

  void onResume();

  interface View {

    void setupHeader(RxBusHeaderInfo header);

    void setupExtraInfo(String extra);
  }
}
