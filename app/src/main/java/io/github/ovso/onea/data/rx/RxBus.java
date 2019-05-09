package io.github.ovso.onea.data.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public final class RxBus {
  private BehaviorSubject<Object> bus = BehaviorSubject.create();

  public void send(Object o) {
    bus.onNext(o);
  }

  public Observable<Object> toObservable() {
    return bus;
  }

  public boolean hasObservable() {
    return bus.hasObservers();
  }
}