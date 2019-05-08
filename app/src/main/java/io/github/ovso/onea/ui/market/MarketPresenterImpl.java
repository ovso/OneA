package io.github.ovso.onea.ui.market;

import androidx.lifecycle.LifecycleOwner;
import io.github.ovso.onea.App;
import io.github.ovso.onea.data.db.AppDatabase;
import io.github.ovso.onea.data.db.model.AppEntity;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import io.github.ovso.onea.ui.utils.MarketType;
import io.reactivex.Observable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.Setter;

public class MarketPresenterImpl extends DisposablePresenter implements MarketPresenter {

  private final View view;
  private final AppDatabase appDb;
  private final LifecycleOwner lifecycleOwner;
  private AtomicInteger itemId;

  MarketPresenterImpl(MarketPresenter.View $view, LifecycleOwner lifecycleOwner) {
    this.view = $view;
    this.lifecycleOwner = lifecycleOwner;
    appDb = App.getInstance().getAppDb();
    itemId = new AtomicInteger(-1);
  }

  @Override public void onCreate() {
    view.setupRadioGroup();
    addDisposable(
        Observable.fromArray(MarketType.values())
            .map(this::handleMarketInfo)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(this::consumer)
    );
  }

  private MarketInfo handleMarketInfo(MarketType marketType) {
    List<AppEntity> entityByMarket = appDb.appsDao().getEntityByMarket(marketType.getName());
    MarketInfo marketInfo = new MarketInfo();
    marketInfo.name = marketType.getName();
    marketInfo.count = entityByMarket.size();
    marketInfo.tsize = appDb.appsDao().getApkSizeSumByMarket(marketType.getName());
    return marketInfo;
  }

  //앱 마켓 이름, 단말에 설치된 앱 수, 설치된 앱들의 apk tsize 합
  private void consumer(MarketInfo marketInfo) {
    int viewId = itemId.incrementAndGet();
    String text = String.format("%s, %s개, %sKB",
        marketInfo.name, marketInfo.count, (marketInfo.tsize / 1024));
    view.addRadioButton(viewId, text);
  }

  @Override public void onDestroy() {
    clearDisposable();
  }

  @Getter @Setter public static class MarketInfo {
    private String name;
    private int count;
    private long tsize;
  }
}
