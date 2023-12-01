package com.example.jaj;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.core.app.NotificationCompat;

import java.util.Random;

public class MyJobService extends JobService {

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // Check for new email and send a notification if needed.
            boolean newEmailAvailable = checkForNewEmail();
            if (newEmailAvailable) {
                sendNotification();
            }

            // Indicate whether the job is finished or should be rescheduled.
            boolean reschedule = true; // Set to true if the job should be rescheduled.

            // Return true if the work is ongoing and should continue.
            jobFinished((JobParameters) msg.obj, reschedule);
            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters params) {
        // This method is called when the job starts.

        // Perform the background task on a separate thread using a Handler.
        Message message = mHandler.obtainMessage();
        message.obj = params;
        mHandler.sendMessage(message);

        // Return true to indicate that the job is ongoing.
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // This method is called if the job is interrupted or canceled.

        // Return true if you want to reschedule the job.
        return true;
    }

    private boolean checkForNewEmail() {

        return true;
    }

    private Notification sendNotification() {
        Random random = new Random();

        // Generate a random integer between 0 (inclusive) and 100 (exclusive)
        int channelID = 1;
        String email = readUserEmail();
        // Create a notification channel (required for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelID+"",
                    "JAJ",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID+"")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("JAJ")
                .setContentText("You are running " + email)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create a pending intent to open the app when the notification is tapped
        Intent notificationIntent = new Intent(this, MainActivity.class); // Replace with your main activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // Build the notification
        return builder.build();
    }

    private String readUserEmail() {

        return "ryleibanez@gmail.com";
    }
}

