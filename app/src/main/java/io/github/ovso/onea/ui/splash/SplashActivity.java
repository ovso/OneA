package io.github.ovso.onea.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.base.BaseActivity;
import io.github.ovso.onea.ui.market.MarketActivity;
import io.github.ovso.onea.ui.utils.AppsInfoProvider;

public class SplashActivity extends BaseActivity implements SplashPresenter.View {
  private SplashPresenter presenter = new SplashPresenterImpl(provideArguments());

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    presenter.onCreate();
  }

  private SplashArguments provideArguments() {
    return new SplashArguments.Builder()
        .setAppsInfoProvider(new AppsInfoProvider(this))
        .setView(this)
        .Build();
  }

  @Override protected int getLayoutResId() {
    return R.layout.activity_splash;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override public void navigateToMarket() {
    startActivity(new Intent(this, MarketActivity.class));
  }
}