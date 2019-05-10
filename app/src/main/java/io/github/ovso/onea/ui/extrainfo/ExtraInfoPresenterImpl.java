package io.github.ovso.onea.ui.extrainfo;

import android.os.Handler;
import android.os.Looper;
import io.github.ovso.onea.App;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.RxBus;
import io.github.ovso.onea.data.rx.dto.RxBusExtraInfo;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import io.github.ovso.onea.utils.BataTime;
import io.github.ovso.onea.utils.BataTimeCallback;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ExtraInfoPresenterImpl extends DisposablePresenter implements ExtraInfoPresenter {
  private final static int MAX_SECOND = 15;
  public final static int TICK_SECOND = 1000;
  private final static String TAG = "ExtraInfoPresenterImpl";
  private final View view;
  private AtomicInteger time = new AtomicInteger(MAX_SECOND);
  private RxBusExtraInfo info;
  private TimerStatus timerStatus = TimerStatus.FINISH;
  private BataTime timer = new BataTime(1000 * MAX_SECOND, TICK_SECOND);

  ExtraInfoPresenterImpl(ExtraInfoPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate() {
    toRxBusObservable();
  }

  @Override public void onDestroy() {
    clearDisposable();
  }

  @Override public void onRegisterClick() {
    view.showRegisterDialog();
    startTimer();
  }

  private void startTimer() {
    timer.start(bataTimeCallback);
  }

  private BataTimeCallback bataTimeCallback = new BataTimeCallback() {
    @Override public void onUpdate(int elapsed) {
      new Handler(Looper.getMainLooper()).post(() -> {
        timerStatus = TimerStatus.TICK;
        String msg = App.getInstance().getString(R.string.extra_info_dialog_msg);
        view.changeDialogMessage(String.format(msg, time.getAndDecrement()));
      });
    }

    @Override public void onComplete() {
      new Handler(Looper.getMainLooper()).post(() -> {
        String msg = App.getInstance().getString(R.string.extra_info_dialog_msg_complete);
        view.changeDialogMessage(String.format(msg, info.getHeaderInfo().getEmail()));
        timerStatus = TimerStatus.FINISH;
      });
    }
  };

  @Override public void onDialogCloseClick() {
    switch (timerStatus) {
      case TICK:
        // 노티
        view.startForegroundService(time.get());
        stopTimber();
        break;
      case FINISH:
        //앱 종료
        break;
    }
  }

  private void stopTimber() {
    if (timer != null) {
      timer.stop();
    }
  }

  @Override public void onDialogCancelClick() {
    stopTimber();
    timerStatus = TimerStatus.FINISH;
    time.set(MAX_SECOND);
  }

  private void toRxBusObservable() {
    RxBus rxBus = App.getInstance().getRxBus();
    addDisposable(
        rxBus.toObservable().subscribe(o -> {
          if (o instanceof RxBusExtraInfo) {
            info = ((RxBusExtraInfo) o);
            view.setupHeader(info.getHeaderInfo());
            view.setupExtraInfo(info.getService());
            view.changeLayoutGravityForRegisterButton(info.getHeaderInfo().getOperatorType());
          }
        })
    );
  }

  @AllArgsConstructor @Getter enum TimerStatus {
    TICK, FINISH
  }
}
