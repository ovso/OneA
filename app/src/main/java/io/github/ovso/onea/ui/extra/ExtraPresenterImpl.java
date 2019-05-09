package io.github.ovso.onea.ui.extra;

import android.content.res.Resources;
import io.github.ovso.onea.App;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.HeaderInfo;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import io.github.ovso.onea.ui.utils.MarketType;
import io.github.ovso.onea.ui.utils.SimOperator;
import java.util.Arrays;
import java.util.List;

public class ExtraPresenterImpl extends DisposablePresenter implements ExtraPresenter {

  private final View view;
  private List<String> items;
  private HeaderInfo header;

  ExtraPresenterImpl(ExtraPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate() {
  }

  @Override public void onItemClick(int position) {
    sendEvent();
    view.navigateToExtraInfo();
  }

  private void sendEvent() {
    App.getInstance().getRxBus().send(
        header
    );
  }

  @Override public void onPause() {
    clearDisposable();
  }

  @Override public void onResume() {
    addDisposable(
        App.getInstance().getRxBus().toObservable().subscribe(o -> {
          if ((o instanceof HeaderInfo)) {
            header = (HeaderInfo) o;
            view.setupHeader(header);
            items = getItems(header.getOperatorType());
            view.setupRecyclerView(header.getOperatorType(), items);
          }
        })
    );
  }

  private List<String> getItems(SimOperator.Type type) {
    Resources resources = App.getInstance().getResources();
    if (type == SimOperator.Type.UNKNOWN) {
      return Arrays.asList(resources.getStringArray(R.array.extras_na));
    } else {
      return Arrays.asList(resources.getStringArray(R.array.extras));
    }
  }
}