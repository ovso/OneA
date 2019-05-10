package io.github.ovso.onea.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import io.github.ovso.onea.R;
import io.github.ovso.onea.ui.extrainfo.ExtraInfoActivity;
import timber.log.Timber;

public class RegisterService extends Service {

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    int time = intent.getIntExtra("time", 0);
    Timber.d("time = %s", time);
    startForegroundService(time);
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  void startForegroundService(int time) {
    Intent notificationIntent = new Intent(this, ExtraInfoActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

    RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_service);
    NotificationCompat.Builder builder;
    if (Build.VERSION.SDK_INT >= 26) {
      String CHANNEL_ID = "register_service_channel";
      NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
          "Register Service Channel",
          NotificationManager.IMPORTANCE_DEFAULT);

      ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
          .createNotificationChannel(channel);

      builder = new NotificationCompat.Builder(this, CHANNEL_ID);
    } else {
      builder = new NotificationCompat.Builder(this);
    }
    builder.setSmallIcon(R.mipmap.ic_launcher)
        .setContent(remoteViews)
        .setContentIntent(pendingIntent);

    startForeground(1, builder.build());
  }
}
