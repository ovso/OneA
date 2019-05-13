package io.github.ovso.onea.ui.splash;

import io.github.ovso.onea.ui.base.IBuilder;
import io.github.ovso.onea.utils.AppsInfoProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter class SplashArguments {

  private final SplashPresenter.View view;
  private final AppsInfoProvider appsInfoProvider;

  private SplashArguments(Builder builder) {
    view = builder.view;
    appsInfoProvider = builder.appsInfoProvider;
  }

  @Setter @Accessors(chain = true) public static class Builder
      implements IBuilder<SplashArguments> {
    private SplashPresenter.View view;
    private AppsInfoProvider appsInfoProvider;

    @Override public SplashArguments Build() {
      return new SplashArguments(this);
    }
  }
}