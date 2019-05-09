package io.github.ovso.onea.ui.market;

import android.text.TextUtils;
import io.github.ovso.onea.App;
import io.github.ovso.onea.data.HeaderInfo;
import io.github.ovso.onea.data.db.AppDatabase;
import io.github.ovso.onea.data.db.model.AppEntity;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import io.github.ovso.onea.ui.utils.MarketType;
import io.github.ovso.onea.ui.utils.SimOperator;
import io.github.ovso.onea.ui.utils.UserAccountFetcher;
import io.reactivex.Observable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;
import timber.log.Timber;

public class MarketPresenterImpl extends DisposablePresenter implements MarketPresenter {

  private final View view;
  private final AppDatabase appDb;
  private AtomicInteger itemId;
  private int checkedMarketIndex;
  private String emailText = "";

  MarketPresenterImpl(MarketPresenter.View view) {
    this.view = view;
    appDb = App.getInstance().getAppDb();
    itemId = new AtomicInteger(-1);
  }

  @Override public void onCreate() {
    String email = UserAccountFetcher.getEmail(App.getInstance());
    view.setupUserEmail(email);
    view.enableConfirmButton(!TextUtils.isEmpty(email));
    reqMarkets();
  }

  private void reqMarkets() {
    addDisposable(
        Observable.fromArray(MarketType.values())
            .map(this::handleMarketInfo)
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

  private MarketInfo handleMarketInfo(MarketType marketType) {
    List<AppEntity> entityByMarket = appDb.appsDao().getEntityByMarket(marketType.getName());
    MarketInfo marketInfo = new MarketInfo();
    marketInfo.name = marketType.getName();
    marketInfo.count = entityByMarket.size();
    marketInfo.tsize = appDb.appsDao().getApkSizeSumByMarket(marketType.getName());
    return marketInfo;
  }

  private void consumerForReqMarkets(MarketInfo marketInfo) {
    int viewId = itemId.incrementAndGet();
    String text = String.format("%s, %s개, %sKB",
        marketInfo.name, marketInfo.count, (marketInfo.tsize / 1024));
    view.addRadioButton(viewId, text);
  }

  @Override public void onDestroy() {
    clearDisposable();
  }

  @Override public void onMarketCheckedChange(int checkedId) {
    checkedMarketIndex = checkedId;
  }

  @Override public void onConfirmClick() {
    sendEvent();
    view.navigateToExtra();
  }

  private void sendEvent() {
    SimOperator.Type type = SimOperator.toType(SimOperator.getOperator(App.getInstance()));
    App.getInstance().getRxBus().send(
        HeaderInfo.builder()
            .email(emailText)
            .operatorType(type)
            .marketType(MarketType.values()[checkedMarketIndex])
            .build()
    );
  }

  @Override public void onEmailTextChanged(String email) {
    emailText = email;
    view.enableConfirmButton(UserAccountFetcher.isValidEmail(email));
  }

  @Getter @Setter public static class MarketInfo {
    private String name;
    private int count;
    private long tsize;
  }
}
