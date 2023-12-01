package com.example.jaj;
import android.app.Notification;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyNotificationWorker extends Worker {
    public MyNotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams
    ) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        showNotification();
        return Result.success();
    }
    private void showNotification() {
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "your_notification_channel_id")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Task Completed")
                .setContentText("Your background task is complete.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, notification);
    }
}
