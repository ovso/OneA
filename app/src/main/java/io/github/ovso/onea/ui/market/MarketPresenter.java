package io.github.ovso.onea.ui.market;

public interface MarketPresenter {

  void onCreate();

  void onDestroy();

  void onMarketCheckedChange(int checkedId);

  void onConfirmClick();

  void onEmailTextChanged(String text);

  interface View {

    void addRadioButton(int viewId, String text);

    void setupRadioGroup();

    void enableConfirmButton(boolean enable);

    void setupUserEmail(String userEmail);
  }
}
