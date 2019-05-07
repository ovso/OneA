package io.github.ovso.onea.ui.splash;

import androidx.lifecycle.LifecycleObserver;

public interface SplashPresenter extends LifecycleObserver {

  void onCreate();

  void onDestroy();

  interface View {

    void navigateToMarket();
  }
}