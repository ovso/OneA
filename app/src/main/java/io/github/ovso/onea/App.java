package io.github.ovso.onea;

import android.app.Application;
import android.content.ContextWrapper;
import android.content.IntentFilter;
import com.facebook.stetho.Stetho;
import com.pixplicity.easyprefs.library.Prefs;
import io.github.ovso.onea.data.db.AppDatabase;
import io.github.ovso.onea.data.rx.RxBus;
import io.github.ovso.onea.receiver.TestReceiver;
import io.github.ovso.onea.utils.Consts;
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
    setupTimber();
    setupPrefs();
    registerTestReceiver();
  }

  private void registerTestReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addCategory("android.intent.category.DEFAULT");
    intentFilter.addAction(Consts.BR_ACTION_DATA);
    intentFilter.addAction(Consts.BR_ACTION_MODE);
    registerReceiver(new TestReceiver(), intentFilter);
  }

  private void setupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  private void setupPrefs() {
    new Prefs.Builder()
        .setContext(this)
        .setMode(ContextWrapper.MODE_PRIVATE)
        .setPrefsName(getPackageName())
        .setUseDefaultSharedPreference(true)
        .build();
  }
}