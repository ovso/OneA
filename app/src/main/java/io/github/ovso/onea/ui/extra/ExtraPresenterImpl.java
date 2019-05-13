package io.github.ovso.onea.ui.extra;

import android.content.res.Resources;
import com.pixplicity.easyprefs.library.Prefs;
import io.github.ovso.onea.App;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.dto.RxBusExtraInfo;
import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.data.rx.RxBus;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import io.github.ovso.onea.utils.Consts;
import io.github.ovso.onea.utils.MarketType;
import io.github.ovso.onea.utils.SimOperator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtraPresenterImpl extends DisposablePresenter implements ExtraPresenter {

  private final View view;
  private List<String> items = new ArrayList<>();
  private RxBusHeaderInfo header;

  ExtraPresenterImpl(ExtraPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate() {
  }

  @Override public void onItemClick(int position) {
    sendEvent(position);
    view.navigateToExtraInfo();
  }

  private void sendEvent(int position) {
    RxBus rxBus = App.getInstance().getRxBus();
    rxBus.send(RxBusExtraInfo.builder().headerInfo(header).service(items.get(position)).build());
  }

  @Override public void onPause() {
    clearDisposable();
  }

  private int getSimOperatorIndex() {
    if (isTestMode()) {
      return Prefs.getInt(Consts.PREFS_KEY_OPERATOR, SimOperator.Type.UNKNOWN.ordinal());
    } else {
      return MarketType.ONE_STORE.ordinal();
    }
  }

  private boolean isTestMode() {
    return Prefs.getBoolean(Consts.PREFS_KEY_MODE, false);
  }

  @Override public void onResume() {
    addDisposable(
        App.getInstance().getRxBus().toObservable().subscribe(o -> {
          if ((o instanceof RxBusHeaderInfo)) {
            header = (RxBusHeaderInfo) o;
            view.setupHeader(header);
            items.clear();
            items.addAll(getItems(header.getOperatorType()));
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