package io.github.ovso.onea.ui.extrainfo;

import io.github.ovso.onea.App;
import io.github.ovso.onea.data.rx.RxBus;
import io.github.ovso.onea.data.rx.dto.RxBusExtraInfo;
import io.github.ovso.onea.ui.base.DisposablePresenter;

public class ExtraInfoPresenterImpl extends DisposablePresenter implements ExtraInfoPresenter {

  private final View view;

  ExtraInfoPresenterImpl(ExtraInfoPresenter.View view) {
    this.view = view;
  }

  @Override public void onPause() {
    clearDisposable();
  }

  @Override public void onResume() {
    toRxBusObservable();
  }

  private void toRxBusObservable() {
    RxBus rxBus = App.getInstance().getRxBus();
    addDisposable(
        rxBus.toObservable().subscribe(o -> {
          if (o instanceof RxBusExtraInfo) {
            RxBusExtraInfo info = (RxBusExtraInfo) o;
            view.setupHeader(info.getHeaderInfo());
            view.setupExtraInfo(info.getService());
            view.changeLayoutGravityForRegisterButton(info.getHeaderInfo().getOperatorType());
          }
        })
    );
  }
}
