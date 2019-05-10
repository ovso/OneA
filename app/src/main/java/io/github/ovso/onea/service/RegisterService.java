package io.github.ovso.onea.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.extrainfo.ExtraInfoActivity;
import io.github.ovso.onea.utils.BataTime;
import io.github.ovso.onea.utils.BataTimeCallback;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisterService extends Service {

  private RemoteViews remoteViews;
  private NotificationManager notificationManager;
  private final static int NOTI_ID = 1;
  private Notification notification;
  private String email;
  private AtomicInteger time = new AtomicInteger(0);
  private BataTime bataTime;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    time.set(intent.getIntExtra("time", 0));
    email = intent.getStringExtra("email");
    bataTime = new BataTime(time.get() * 1000, 1000);
    startForegroundService();
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  void startForegroundService() {
    notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
    remoteViews = new RemoteViews(getPackageName(), R.layout.notification_service);
    refreshNotification(String.format(getString(R.string.extra_info_dialog_msg), time.get()));
    bataTime.start(bataTimeCallback);

    Intent notificationIntent = new Intent(this, ExtraInfoActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    NotificationCompat.Builder builder;
    if (Build.VERSION.SDK_INT >= 26) {
      String CHANNEL_ID = "register_service_channel";
      NotificationChannel channel = new NotificationChannel(
          CHANNEL_ID,
          "Register Service Channel",
          NotificationManager.IMPORTANCE_DEFAULT
      );
      notificationManager.createNotificationChannel(channel);

      builder = new NotificationCompat.Builder(this, CHANNEL_ID);
    } else {
      builder = new NotificationCompat.Builder(this);
    }
    notification = builder
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContent(remoteViews)
        .setContentIntent(pendingIntent).build();

    startForeground(NOTI_ID, notification);
  }

  private void refreshNotification(String msg) {
    new Handler(Looper.getMainLooper()).post(() -> {
      remoteViews.setTextViewText(R.id.textview_notification_time, msg);
      notificationManager.notify(NOTI_ID, notification);
    });
  }

  private BataTimeCallback bataTimeCallback = new BataTimeCallback() {
    @Override public void onUpdate(int elapsed) {
      refreshNotification(
          String.format(getString(R.string.extra_info_dialog_msg), time.getAndDecrement())
      );
    }

    @Override public void onComplete() {
      refreshNotification(
          String.format(getString(R.string.extra_info_dialog_msg_complete), email)
      );
    }
  };
}