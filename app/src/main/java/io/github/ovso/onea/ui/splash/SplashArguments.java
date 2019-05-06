package io.github.ovso.onea.ui.splash;

import io.github.ovso.onea.data.rx.SchedulersFacade;
import io.github.ovso.onea.ui.base.IBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter class SplashArguments {

  private final SplashPresenter.View view;
  private final SchedulersFacade schedulers;

  private SplashArguments(Builder builder) {
    view = builder.view;
    schedulers = builder.schedulers;
  }

  @Setter @Accessors(chain = true) public static class Builder
      implements IBuilder<SplashArguments> {
    private SplashPresenter.View view;
    private SchedulersFacade schedulers;

    @Override public SplashArguments Build() {
      return new SplashArguments(this);
    }
  }
}
