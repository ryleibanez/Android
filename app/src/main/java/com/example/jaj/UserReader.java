package com.example.jaj;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.ListenableWorker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

public class UserReader extends Service {
    ContentValues values = new ContentValues();

    private int notificationIdCounter = 3;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    private boolean foreground = false;

    @SuppressLint({"NewApi", "WrongConstant"})
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Perform the email reading task here
        // Replace this with your actual email reading logic

    if(!foreground) {
        Notification notification1 = createForegroundNotification("Running");
        startForeground(1, notification1);
        foreground = true;
    }


        String msg = "You are running";
        boolean checker = readUserEmail(getApplicationContext());
        if(checker) {


        }

        // Schedule the next reading after 1 minute
        scheduleNextEmailReading();

        // Return START_STICKY to ensure the service is restarted if it's killed by the system
        return START_NOT_STICKY;
    }
    private void stopForegroundService() {
        stopForeground(true);
        stopSelf();
    }

    public void stopRepeatingAlarm() {
        Intent intent = new Intent(this, UserReader.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    @SuppressLint("Range")
    private boolean readUserEmail(Context context) {
        boolean check = false;
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);

        try{
            String query="select TrainingId from BookTraining where EmployeeId=? and notify='False'";
            Cursor cursor = db.rawQuery(query, new String[]{new SessionController(getApplicationContext()).getEmpId()});
            String msg = "";




            while(cursor.moveToNext()){

                @SuppressLint("Range")
                String dateDb = new DateConverter().bookDateandtime(cursor.getInt(cursor.getColumnIndex("TrainingId"))+"", context);
                String []splitter = dateDb.split(" ");
                boolean oneWeekCheck = false;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    oneWeekCheck = new DateConverter().checkNotification(splitter[0]);
                }

                if(oneWeekCheck){
                    System.out.println("One Week!!!");

                     msg = new trainingController().notificationMsg(cursor.getInt(cursor.getColumnIndex("TrainingId"))+"", getApplicationContext()) + "\n\n";
                    createNotif(msg);

                    values.put("notify", "True");

                    // Define the selection criteria
                    String selection = "TrainingId=? and EmployeeId=?";
                    String[] selectionArgs = {String.valueOf(cursor.getInt(cursor.getColumnIndex("TrainingId"))),new SessionController(getApplicationContext()).getEmpId()};


                    int rowsUpdated = db.update("BookTraining", values, selection, selectionArgs);


                    if(rowsUpdated > 0){
                        ContentValues values = new ContentValues();
                        values.put("email", new SessionController(getApplicationContext()).getUserEmail());
                        values.put("message", msg);

                        long newRowId = db.insert("notification", null, values);


                        new NotificationUpdater(getApplicationContext()).execute();
                        new TableUpdater().updateBookTrainingInfo(getApplicationContext(), "Yes");


                    }



                }


            }



        }catch (Exception e){
            e.printStackTrace();
        }





        return check;
    }

    // Declare a counter
    private Notification createNotif(String msg){
        // Create a notification channel (required for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "JAJ-Notification",
                    "JAJ Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "JAJ-Notification")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("JAJ")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg)) // Set style for multiline text
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        // Create a pending intent to open the app when the notification is tapped
        Intent notificationIntent = new Intent(this, LoginActivity.class); // Replace with your main activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // Build the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        int notificationId = notificationIdCounter++;

        notificationManager.notify(notificationId, builder.build());
        // Use a different notification ID (1) to distinguish it from other notifications.

        return builder.build();
    }
    private Notification createForegroundNotification(String msg) {
        Random random = new Random();

        // Generate a random integer between 0 (inclusive) and 100 (exclusive)


        // Create a notification channel (required for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "JAJ-Controller",
                    "JAJ",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        // Create the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "JAJ-Controller")
                .setSmallIcon(R.drawable.logo)

                .setContentTitle("JAJ")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Create a pending intent to open the app when the notification is tapped
        Intent notificationIntent = new Intent(this, MainActivity.class); // Replace with your main activity
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        // Build the notification
        return builder.build();
    }
    private void updateScheduleNotification() {
        // Create a notification for the schedule update
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Schedule Updated")
                .setContentText("Your schedule has been updated.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(3, builder.build()); // Use a different notification ID (1) to distinguish it from the original notification.
    }
    public void scheduleNextEmailReading() {
        // Set up an AlarmManager to trigger the service every 1 minute
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, UserReader.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        long intervalMillis = 5 * 60 * 1000; // 5 minutes

        long triggerAtMillis = SystemClock.elapsedRealtime() + intervalMillis;

        alarmManager.set(AlarmManager.ELAPSED_REALTIME, triggerAtMillis, pendingIntent);

    }

    private void stopNextEmailReading() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, UserReader.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        // Additional cleanup or resource release can be done here
    }
}

