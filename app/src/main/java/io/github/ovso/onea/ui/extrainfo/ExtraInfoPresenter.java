package io.github.ovso.onea.ui.extrainfo;

import io.github.ovso.onea.data.HeaderInfo;

public interface ExtraInfoPresenter {

  void onPause();

  void onResume();

  interface View {

    void setupHeader(HeaderInfo header);
  }
}
