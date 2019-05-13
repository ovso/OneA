package io.github.ovso.onea.ui.market;

import android.text.TextUtils;
import com.pixplicity.easyprefs.library.Prefs;
import io.github.ovso.onea.App;
import io.github.ovso.onea.data.rx.dto.RxBusHeaderInfo;
import io.github.ovso.onea.data.db.AppDatabase;
import io.github.ovso.onea.data.db.model.AppEntity;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import io.github.ovso.onea.utils.MarketType;
import io.github.ovso.onea.utils.SimOperator;
import io.github.ovso.onea.utils.UserAccountFetcher;
import io.github.ovso.onea.utils.Consts;
import io.reactivex.Observable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;

public class MarketPresenterImpl extends DisposablePresenter implements MarketPresenter {

  private final View view;
  private final AppDatabase appDb;
  private AtomicInteger marketButtonId;
  private int checkedMarketId;
  private String emailText = "";

  MarketPresenterImpl(View view) {
    this.view = view;
    appDb = App.getInstance().getAppDb();
    marketButtonId = new AtomicInteger(-1);
  }

  @Override public void onCreate() {
    emailText = getEmail();
    view.setupUserEmail(emailText);
    view.enableConfirmButton(!TextUtils.isEmpty(emailText));
    reqMarkets();
  }

  @Override public void onResume() {
    emailText = getEmail();
    view.setupUserEmail(emailText);
  }

  private String getEmail() {
    if (isTestMode()) {
      return Prefs.getString(
          Consts.PREFS_KEY_EMAIL, UserAccountFetcher.getEmail(App.getInstance()));
    } else {
      return UserAccountFetcher.getEmail(App.getInstance());
    }
  }

  private boolean isTestMode() {
    return Prefs.getBoolean(Consts.PREFS_KEY_MODE, false);
  }

  private void reqMarkets() {
    addDisposable(
        Observable.fromArray(MarketType.values())
            .map(this::getMarketInfo)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(
                this::consumerForReqMarkets,
                Timber::e,
                this::completeForReqMarkets)
    );
  }

  private void completeForReqMarkets() {
    view.setupRadioGroup();
  }

  private MarketInfo getMarketInfo(MarketType marketType) {
    List<AppEntity> entityByMarket = appDb.appsDao().getEntityByMarket(marketType.getName());
    MarketInfo marketInfo = new MarketInfo();
    marketInfo.name = marketType.getName();
    marketInfo.count = entityByMarket.size();
    marketInfo.tsize = appDb.appsDao().getApkSizeSumByMarket(marketType.getName());
    return marketInfo;
  }

  private void consumerForReqMarkets(MarketInfo marketInfo) {
    String text = String.format("%s, %s개, %sKB",
        marketInfo.name, marketInfo.count, (marketInfo.tsize / 1024));
    view.addRadioButton(marketButtonId.incrementAndGet(), text);
  }

  @Override public void onDestroy() {
    clearDisposable();
  }

  @Override public void onMarketCheckedChange(int checkedId) {
    checkedMarketId = checkedId;
  }

  @Override public void onConfirmClick() {
    sendEvent();
    view.navigateToExtra();
  }

  private void sendEvent() {
    SimOperator.Type type = SimOperator.toType(SimOperator.getOperator(App.getInstance()));
    //SimOperator.Type type = SimOperator.Type.SKT;
    App.getInstance().getRxBus().send(
        RxBusHeaderInfo.builder()
            .email(emailText)
            .operatorType(type)
            .marketType(MarketType.values()[checkedMarketId])
            .build()
    );
  }

  @Override public void onEmailTextChanged(String email) {
    emailText = email;
    view.enableConfirmButton(UserAccountFetcher.isValidEmail(email));
  }

  @Getter @Setter private static class MarketInfo {
    private String name;
    private int count;
    private long tsize;
  }
}
