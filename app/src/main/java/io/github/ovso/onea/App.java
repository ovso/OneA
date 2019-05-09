package io.github.ovso.onea;

import android.app.Application;
import com.facebook.stetho.Stetho;
import io.github.ovso.onea.data.db.AppDatabase;
import io.github.ovso.onea.data.rx.RxBus;
import lombok.Getter;
import timber.log.Timber;

public class App extends Application {
  @Getter private static App instance;
  @Getter private AppDatabase appDb;
  @Getter private RxBus rxBus;

  @Override public void onCreate() {
    super.onCreate();
    instance = this;
    rxBus = new RxBus();
    appDb = AppDatabase.getInstance(this);
    Stetho.initializeWithDefaults(this);
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }
}