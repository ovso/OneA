package io.github.ovso.onea.data.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersFacade {

  public Scheduler io() {
    return Schedulers.io();
  }

  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}
