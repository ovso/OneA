package io.github.ovso.onea.data.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public final class RxBus {
  private BehaviorSubject<Object> bsBus = BehaviorSubject.create();
  private PublishSubject<Object> psBus = PublishSubject.create();

  public void sendBs(Object o) {
    bsBus.onNext(o);
  }

  public Observable<Object> toBsObservable() {
    return bsBus;
  }

  public void sendPs(Object o) {
    psBus.onNext(o);
  }

  public Observable<Object> toPsObservable() {
    return psBus;
  }
}