package io.github.ovso.onea.ui.extrainfo;

import android.os.CountDownTimer;
import io.github.ovso.onea.App;
import io.github.ovso.onea.R;
import io.github.ovso.onea.data.rx.RxBus;
import io.github.ovso.onea.data.rx.dto.RxBusExtraInfo;
import io.github.ovso.onea.ui.base.DisposablePresenter;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ExtraInfoPresenterImpl extends DisposablePresenter implements ExtraInfoPresenter {
  private final static int MAX_SECOND = 15;
  private final static String TAG = "ExtraInfoPresenterImpl";
  private final View view;
  private CountDownTimer timer;
  private AtomicInteger time = new AtomicInteger(MAX_SECOND);
  private RxBusExtraInfo info;
  private TimerStatus timerStatus = TimerStatus.FINISH;

  ExtraInfoPresenterImpl(ExtraInfoPresenter.View view) {
    this.view = view;
  }

  @Override public void onCreate() {
    toRxBusObservable();
    timer = new CountDownTimer(1000 * MAX_SECOND, 1000) {
      @Override public void onTick(long millisUntilFinished) {
        Timber.d("onTick(%s)", time.get());
        String msg = String.format(
            App.getInstance().getString(R.string.extra_info_dialog_msg),
            time.get()
        );
        view.changeDialogMessage(msg);

        time.decrementAndGet();
        timerStatus = TimerStatus.TICK;
      }

      @Override public void onFinish() {
        Timber.d("onFinish = %s", time.get());
        String msg = String.format(
            App.getInstance().getString(R.string.extra_info_dialog_msg_complete),
            info.getHeaderInfo().getEmail()
        );
        view.changeDialogMessage(msg);
        timerStatus = TimerStatus.FINISH;
      }
    };
  }

  @Override public void onDestroy() {
    clearDisposable();
  }

  @Override public void onRegisterClick() {
    view.showRegisterDialog();
    startTimer();
  }

  private void startTimer() {
    if (timer != null) {
      time.set(15);
      timer.start();
      timerStatus = TimerStatus.START;
    }
  }

  @Override public void onDialogCloseClick() {
    switch (timerStatus) {

      case START:
        break;
      case TICK:
        // 노티
        view.startForegroundService(time.get());
        timer.cancel();
        break;
      case FINISH:
        //앱 종료
        break;
    }
  }

  private void cancelTimer() {
    if (timer != null) {
      timer.cancel();
    }
  }

  @Override public void onDialogCancelClick() {
    cancelTimer();
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
    START, TICK, FINISH
  }
}
