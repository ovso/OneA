package io.github.ovso.onea.ui.extrainfo;

import android.os.CountDownTimer;
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
import timber.log.Timber;

public class ExtraInfoPresenterImpl extends DisposablePresenter implements ExtraInfoPresenter {
  private final static int MAX_SECOND = 15;
  public final static int TICK_SECOND = 1000;
  private final static String TAG = "ExtraInfoPresenterImpl";
  private final View view;
  private CountDownTimer timer;
  private AtomicInteger time = new AtomicInteger(MAX_SECOND);
  private RxBusExtraInfo info;
  private TimerStatus timerStatus = TimerStatus.FINISH;
  private BataTime bataTime;

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
    startTimer2();
  }

  private void startTimer2() {
    time.set(MAX_SECOND);
    bataTime = new BataTime(1000 * MAX_SECOND, TICK_SECOND);
    bataTime.start(new BataTimeCallback() {
      @Override public void onUpdate(int elapsed) {
        Timber.d("onUpdate = %s = ", time.getAndDecrement());
      }

      @Override public void onComplete() {
        Timber.d("onComplete");
      }
    });
  }

  private void startTimer() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    timerStatus = TimerStatus.START;
    time.set(MAX_SECOND);
    timer = new CountDownTimer(1000 * MAX_SECOND, 1000) {
      @Override public void onTick(long millisUntilFinished) {
        timerStatus = TimerStatus.TICK;
        String msg = String.format(
            App.getInstance().getString(R.string.extra_info_dialog_msg),
            time.get()
        );
        view.changeDialogMessage(msg);
        time.decrementAndGet();
      }

      @Override public void onFinish() {
        Timber.d("onFinish = %s", time.decrementAndGet());
        String msg = String.format(
            App.getInstance().getString(R.string.extra_info_dialog_msg_complete),
            info.getHeaderInfo().getEmail()
        );
        view.changeDialogMessage(msg);
        timerStatus = TimerStatus.FINISH;
      }
    };
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
