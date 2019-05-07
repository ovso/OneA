package io.github.ovso.onea.ui.base;

import io.github.ovso.onea.data.rx.SchedulersFacade;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DisposablePresenter {
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  protected SchedulersFacade schedulers = new SchedulersFacade();

  protected void addDisposable(Disposable d) {
    compositeDisposable.add(d);
  }

  protected void clearDisposable() {
    compositeDisposable.clear();
  }
}
