package io.github.ovso.onea.ui.market;

import android.content.Intent;

public interface MarketPresenter {

  void onCreate();

  void onDestroy();

  void onMarketCheckedChange(int checkedId);

  void onConfirmClick();

  void onEmailTextChanged(String text);

  void onResume();

  void onActivityResult(int requestCode, Intent data);

  void onRestart();

  interface View {

    void addRadioButton(int viewId, String text);

    void setupRadioGroup();

    void enableConfirmButton(boolean enable);

    void setupUserEmail(String userEmail);

    void navigateToExtra();

    void navigateToChooseAccount();
  }
}
