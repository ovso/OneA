package io.github.ovso.onea.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.extrainfo.ExtraInfoActivity;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.Timber;

public class RegisterService extends Service {

  private RemoteViews remoteViews;
  private NotificationManager notificationManager;
  private final static int NOTI_ID = 1;
  private Notification notification;
  private String email;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    int time = intent.getIntExtra("time", 0);
    email = intent.getStringExtra("email");
    Timber.d("time = %s", time);
    startForegroundService(time);
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  private AtomicInteger time = new AtomicInteger(0);

  void startForegroundService(int $time) {
    time.set($time);
    Intent notificationIntent = new Intent(this, ExtraInfoActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    remoteViews = new RemoteViews(getPackageName(), R.layout.notification_service);

    remoteViews.setTextViewText(
        R.id.textview_notification_time,
        String.format(getString(R.string.extra_info_dialog_msg), $time)
    );
    notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
    NotificationCompat.Builder builder;
    if (Build.VERSION.SDK_INT >= 26) {
      String CHANNEL_ID = "register_service_channel";
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
          "Register Service Channel",
          NotificationManager.IMPORTANCE_DEFAULT);

      notificationManager.createNotificationChannel(channel);

      builder = new NotificationCompat.Builder(this, CHANNEL_ID);
    } else {
      builder = new NotificationCompat.Builder(this);
    }
    builder.setSmallIcon(R.mipmap.ic_launcher)
        .setContent(remoteViews)
        .setContentIntent(pendingIntent);
    notification = builder.build();
    startForeground(NOTI_ID, notification);

    new CountDownTimer($time * 1000, 1000) {
      @Override public void onTick(long millisUntilFinished) {
        Timber.d("onTick = %s", time.get());
        remoteViews.setTextViewText(
            R.id.textview_notification_time,
            String.format(getString(R.string.extra_info_dialog_msg), time.get())
        );
        notificationManager.notify(NOTI_ID, notification);
        time.decrementAndGet();
      }

      @Override public void onFinish() {
        Timber.d("onFinish");
        remoteViews.setTextViewText(
            R.id.textview_notification_time,
            String.format(getString(R.string.extra_info_dialog_msg_complete), email)
        );
        notificationManager.notify(NOTI_ID, notification);
      }
    }.start();
  }
}
