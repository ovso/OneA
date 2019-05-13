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
import io.github.ovso.onea.utils.SimOperator;
import io.github.ovso.onea.utils.UserAccountFetcher;
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
    addDisposable(
        App.getInstance().getRxBus().toObservable().subscribe(o -> {
          if ((o instanceof RxBusHeaderInfo)) {
            header = (RxBusHeaderInfo) o;
            updateScreen();
          }
        })
    );
  }

  private void updateScreen() {
    view.setupHeader(header);
    items.clear();
    items.addAll(getItems(header.getOperatorType()));
    view.setupRecyclerView(header.getOperatorType(), items);
  }

  @Override public void onItemClick(int position) {
    sendEvent(position);
    view.navigateToExtraInfo();
  }

  private void sendEvent(int position) {
    RxBus rxBus = App.getInstance().getRxBus();
    rxBus.send(RxBusExtraInfo.builder().headerInfo(header).extraServiceName(items.get(position)).build());
  }

  @Override public void onPause() {
  }

  private boolean isTestMode() {
    return Prefs.getBoolean(Consts.PREFS_KEY_MODE, false);
  }

  @Override public void onResume() {
    if (isTestMode()) {
      int operatorIndex =
          Prefs.getInt(Consts.PREFS_KEY_OPERATOR, SimOperator.Type.UNKNOWN.ordinal());
      String email =
          Prefs.getString(Consts.PREFS_KEY_EMAIL, UserAccountFetcher.getEmail(App.getInstance()));
      header.setEmail(email);
      header.setOperatorType(SimOperator.Type.values()[operatorIndex]);
      updateScreen();
    }
  }

  @Override public void onDestroy() {
    clearDisposable();
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