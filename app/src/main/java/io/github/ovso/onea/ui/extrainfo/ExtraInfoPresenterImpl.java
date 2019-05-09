package io.github.ovso.onea.ui.extrainfo;

import io.github.ovso.onea.App;
import io.github.ovso.onea.data.HeaderInfo;
import io.github.ovso.onea.ui.base.DisposablePresenter;

public class ExtraInfoPresenterImpl extends DisposablePresenter implements ExtraInfoPresenter {

  private final View view;

  public ExtraInfoPresenterImpl(ExtraInfoPresenter.View view) {
    this.view = view;
  }

  @Override public void onPause() {
    clearDisposable();
  }

  @Override public void onResume() {
    addDisposable(
        App.getInstance().getRxBus().toObservable().subscribe(o -> {
          if (o instanceof HeaderInfo) {
            HeaderInfo header = (HeaderInfo) o;
            view.setupHeader(header);
          }
        })
    );
  }
}
