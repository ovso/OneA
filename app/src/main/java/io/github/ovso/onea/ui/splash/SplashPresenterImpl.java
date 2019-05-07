package io.github.ovso.onea.ui.splash;

import android.content.pm.ResolveInfo;
import io.github.ovso.onea.App;
import io.github.ovso.onea.data.db.dao.Apps;
import io.github.ovso.onea.data.db.model.AppsEntity;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import io.github.ovso.onea.ui.utils.AppsInfoProvider;
import io.reactivex.Single;
import timber.log.Timber;

class SplashPresenterImpl extends DisposablePresenter implements SplashPresenter {

  private final View view;
  private final AppsInfoProvider appsInfoProvider;
  private final Apps apps;

  SplashPresenterImpl(SplashArguments args) {
    view = args.getView();
    appsInfoProvider = args.getAppsInfoProvider();
    apps = App.getInstance().getAppDb().appsDao();
  }

  @Override public void onCreate() {
    addDisposable(
        Single.fromCallable(this::handleAppsInfo)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.ui())
            .subscribe(a -> view.navigateToMarket(), Timber::e)
    );
  }

  private Apps handleAppsInfo() {
    for (ResolveInfo resolveInfo : appsInfoProvider.getInstallApps()) {
      final AppsEntity entity = new AppsEntity();
      String pkg = appsInfoProvider.getPkgName(resolveInfo);
      entity.pkg = pkg;
      entity.market = appsInfoProvider.getFromInstalledMarket(pkg);
      entity.version = appsInfoProvider.getAppVersion(pkg);
      entity.lastUpdateTime = appsInfoProvider.getLastUpdateTime(pkg);
      entity.firstInstallTime = appsInfoProvider.getFirstInstallTime(pkg);
      entity.apkPath = appsInfoProvider.getApkPath(resolveInfo);
      entity.apkSize = appsInfoProvider.getApkSize(resolveInfo);
      apps.insert(entity);
    }
    return apps;
  }

  @Override public void onDestroy() {

  }
}