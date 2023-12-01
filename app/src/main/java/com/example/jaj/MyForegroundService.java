package com.example.jaj;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;

public class MyForegroundService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Handle the background task here, such as sending notifications.
        sendNotification();

        return START_STICKY;
    }

    private void sendNotification() {
        // Create a notification.
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, "YourChannelId")
                .setContentTitle("My Background Service")
                .setContentText("Running...")
                .setSmallIcon(R.drawable.logo)
                .build();

        // Show the notification.
        startForeground(1, notification);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "YourChannelId",
                    "YourChannelName",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}