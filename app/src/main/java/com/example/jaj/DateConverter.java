package com.example.jaj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.xml.transform.Result;

public class DateConverter {


    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean checkNotification(String endBanDate) {
        boolean check = false;
        try {
            // Define the format of the date string (with LocalDate field)
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            }

            // Check for null before parsing
            if (endBanDate != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // Parse the endDateTime string to a LocalDate object
                LocalDate endDate = LocalDate.parse(endBanDate, formatter);

                // Get the current date
                LocalDate currentDate = LocalDate.now();

                // Calculate the date that is 1 week away from the current date
                LocalDate oneWeekAwayFromCurrentDate = currentDate.plusWeeks(1);

                // Check if the date one week away from the current date is equal to the endBanDate
                check = oneWeekAwayFromCurrentDate.isEqual(endDate);
                System.out.println("End Day: " + endDate);
                System.out.println("One Week Away: " + oneWeekAwayFromCurrentDate);

            }
        } catch (DateTimeParseException | NullPointerException e) {
            // Handle parsing or null pointer exception as needed
            e.printStackTrace();
        }

        return check;



    }
    public boolean isBanExpired(String endBanDate) {
        boolean check = false;
        try {
            // Define the format of the date-time string
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }

            // Parse the endDateTime string to a LocalDateTime object
            LocalDateTime endDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                endDate = LocalDateTime.parse(endBanDate, formatter);
            }

            // Get the current date and time
            LocalDateTime currentDateTime = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDateTime = LocalDateTime.now();
            }

            // Compare the two date-time objects
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                check = currentDateTime.compareTo(endDate) > 0;
            }
        } catch (IllegalArgumentException e) {
            // If there's an exception (e.g., invalid date format), handle it as needed
            e.printStackTrace();

        }

        return check;
    }

    public boolean isCompleted(String startBanDate, String endBanDate) {
        boolean check = false;
        try {
            // Define the format of the date-time string
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }

            // Parse the startDateTime and endDateTime strings to LocalDateTime objects
            LocalDateTime startDate = null;
            LocalDateTime endDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startDate = LocalDateTime.parse(startBanDate, formatter);
                endDate = LocalDateTime.parse(endBanDate, formatter);
            }

            // Get the current date and time
            LocalDateTime currentDateTime = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDateTime = LocalDateTime.now();
            }

            // Check if the current date and time is between start and end dates
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                check = currentDateTime.isAfter(endDate);
            }
        } catch (IllegalArgumentException e) {
            // If there's an exception (e.g., invalid date format), handle it as needed
            e.printStackTrace();
        }

        return check;
    }


    public boolean isBanExpired(String startBanDate, String endBanDate) {
        boolean check = false;
        try {
            // Define the format of the date-time string
            DateTimeFormatter formatter = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            }

            // Parse the startDateTime and endDateTime strings to LocalDateTime objects
            LocalDateTime startDate = null;
            LocalDateTime endDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startDate = LocalDateTime.parse(startBanDate, formatter);
                endDate = LocalDateTime.parse(endBanDate, formatter);
            }

            // Get the current date and time
            LocalDateTime currentDateTime = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentDateTime = LocalDateTime.now();
            }

            // Check if the current date and time is between start and end dates
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                check = currentDateTime.isAfter(startDate) && currentDateTime.isBefore(endDate);
            }
        } catch (IllegalArgumentException e) {
            // If there's an exception (e.g., invalid date format), handle it as needed
            e.printStackTrace();
        }

        return check;
    }

    @SuppressLint("Range")
    public String bookDateandtime(String tid, Context context){
        String date = "";
        try{

            SQLiteDatabase db = SQLiteConnection.getDatabase(context);
            String query = "SELECT TrainingDate || ' ' || TrainingStart AS date FROM Training WHERE TrainingId=?";
            Cursor cursor = db.rawQuery(query, new String[]{tid});




            while(cursor.moveToNext()){
                date = cursor.getString(cursor.getColumnIndex("date"));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    @SuppressLint("Range")
    public String completedTrainingDate(String tid){
        String date = "";
        SQLiteDatabase db = SQLiteConnection.getDatabase(this.context);
        try{
            String query = "SELECT TrainingDate || ' ' || TrainingEnd AS date FROM Training WHERE TrainingId=?";
            Cursor cursor = db.rawQuery(query, new String[]{tid});

            while(cursor.moveToNext()){
                date = cursor.getString(cursor.getColumnIndex("date"));
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public String dateNow(){
        String date = "";

        // Get the current date and time
        LocalDateTime currentDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now();
        }

        // Define the formatter for the output
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }

        // Format the current date and time
        String formattedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDateTime = currentDateTime.format(formatter);
        }

        date = formattedDateTime;

        return date;
    }


    public String dateandtime(){
        String date = "";

        // Get the current date and time
        LocalDateTime currentDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime = LocalDateTime.now();
        }

        // Define the formatter for the output
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }

        // Format the current date and time
        String formattedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDateTime = currentDateTime.format(formatter);
        }

        date = formattedDateTime;

        return date;
    }
    public String time (String time){
        String newTime = "";

        // Define the formatter for the input time
        SimpleDateFormat inputFormatter = new SimpleDateFormat("HH:mm");

        try {
            // Parse the input time string
            Date timeInput = inputFormatter.parse(time);

            // Define the formatter for the desired output format (12-hour time with AM/PM)
            SimpleDateFormat outputFormatter = new SimpleDateFormat("hh:mm a");

            // Format the time into the desired output format
            String regularTime = outputFormatter.format(timeInput);

            newTime = regularTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newTime;

    }
    public String date(String date){
        String newDate= "";



        // Define the formatter for the input date
        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the input date string
            Date dateInput = inputFormatter.parse(date);

            // Define the formatter for the desired output format
            SimpleDateFormat outputFormatter = new SimpleDateFormat("MMMM-dd-yyyy");

            // Format the date into the desired output format
            String outputDate = outputFormatter.format(dateInput);

          newDate = outputDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return newDate;
    }
}
