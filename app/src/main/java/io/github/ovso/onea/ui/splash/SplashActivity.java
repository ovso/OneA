package io.github.ovso.onea.ui.splash;

import android.os.Bundle;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.SchedulersFacade;
import io.github.ovso.onea.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity implements SplashPresenter.View {

  private SplashPresenter presenter = new SplashPresenterImpl(provideArguments());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLifecycle().addObserver(presenter);
  }

  private SplashArguments provideArguments() {
    return new SplashArguments.Builder().setSchedulers(new SchedulersFacade())
        .setView(this)
        .Build();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_splash;
  }
}