package io.github.ovso.onea.ui.splash;

import android.util.Log;
import io.github.ovso.onea.data.rx.SchedulersFacade;

class SplashPresenterImpl implements SplashPresenter {

  private final SchedulersFacade schedulers;
  private final View view;

  SplashPresenterImpl(SplashArguments args) {
    view = args.getView();
    schedulers = args.getSchedulers();
  }

  @Override public void onCreate() {
    Log.d("Splash", "onCreate!!!");
  }
}