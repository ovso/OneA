package io.github.ovso.onea.ui.market;

public interface MarketPresenter {

  void onCreate();

  void onDestroy();

  interface View {

    void addRadioButton(int viewId, String text);

    void setupRadioGroup();
  }
}
