package com.example.jaj;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class trainingController {


    ContentValues values = new ContentValues();

    @SuppressLint("Range")
    public String getInstructor(String tid, Context context){
        String str = "";
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);

        try{
            String query="select * from TrainingInstructor where TrainingId=?";
            Cursor cursor = db.rawQuery(query, new String[]{tid});


            while (cursor.moveToNext()) {
                String insId = cursor.getString(cursor.getColumnIndex("InstructorId"));
                String query1 = "SELECT InsFirstName || ' ' || InsLastName AS name FROM Instructor WHERE InstructorId=?";
                Cursor instructor = db.rawQuery(query1, new String[]{insId});



                while (instructor.moveToNext()) {

                    str = instructor.getString(instructor.getColumnIndex("name"));

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return str;
    }
    @SuppressLint("Range")
    public List<TrainingModel> getOngoingTraining(String empId, Context context) {

        List<TrainingModel> getAll = new ArrayList<>();
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);

        try {
            String query = "SELECT TrainingId FROM BookTraining WHERE EmployeeId=?";
            Cursor cursor = db.rawQuery(query, new String[]{new SessionController(context).getEmpId()});

            while (cursor.moveToNext()) {
                String startDate = new DateConverter().bookDateandtime(cursor.getInt(cursor.getColumnIndex("TrainingId")) + "", context);
                String dateDb = new DateConverter().completedTrainingDate(cursor.getInt(cursor.getColumnIndex("TrainingId")) + "");

                boolean check = new DateConverter().isBanExpired(startDate, dateDb);

                if (check) {
                    String trainingId = cursor.getString(cursor.getColumnIndex("TrainingId"));

                    // Fetch ongoing training details from the Training table
                    Cursor trainingCursor = db.rawQuery(
                            "SELECT * FROM Training WHERE TrainingId=? AND TrainingArchived='False' " +
                                    "ORDER BY TrainingDate DESC, TrainingStart DESC",
                            new String[]{trainingId}
                    );

                    if (trainingCursor.moveToFirst()) {
                        getAll.add(new TrainingModel(
                                trainingCursor.getInt(trainingCursor.getColumnIndex("TrainingId")),
                                trainingCursor.getInt(trainingCursor.getColumnIndex("CourseId")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingName")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDate")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingStart")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingEnd")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingLocation")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingArchived")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDescription"))
                        ));
                    }

                    trainingCursor.close(); // Close the trainingCursor after use
                }
            }

            cursor.close(); // Close the main cursor after use

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getAll;
    }

    @SuppressLint("Range")
    public List<TrainingModel> getCompletedTraining(String empId, Context context) {

        List<TrainingModel> getAll = new ArrayList<>();
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);

        try {
            // Fetch TrainingId from the BookTraining table
            Cursor cursor = db.rawQuery("SELECT TrainingId FROM BookTraining WHERE EmployeeId=?", new String[]{empId});

            while (cursor.moveToNext()) {

                String startDate = new DateConverter().bookDateandtime(cursor.getInt(cursor.getColumnIndex("TrainingId")) + "", context);
                String dateDb = new DateConverter().completedTrainingDate(cursor.getInt(cursor.getColumnIndex("TrainingId")) + "");

                boolean check = new DateConverter().isCompleted(startDate, dateDb);


                if (check) {
                    int trainingId = cursor.getInt(cursor.getColumnIndex("TrainingId"));

                    // Fetch training details based on TrainingId
                    Cursor trainingCursor = db.rawQuery("SELECT * FROM Training WHERE TrainingId=?", new String[]{String.valueOf(trainingId)});

                    if (trainingCursor.moveToFirst()) {
                        getAll.add(new TrainingModel(
                                trainingCursor.getInt(trainingCursor.getColumnIndex("TrainingId")),
                                trainingCursor.getInt(trainingCursor.getColumnIndex("CourseId")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingName")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDate")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingStart")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingEnd")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingLocation")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingArchived")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDescription"))
                        ));
                    }

                    trainingCursor.close(); // Close the trainingCursor after use
                }
            }

            cursor.close(); // Close the main cursor after use

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getAll;
    }

    @SuppressLint("Range")
    public List<TrainingModel>getUpcoming(String empId, Context context){
        List<TrainingModel>getAll = new ArrayList<>();

        try{

            SQLiteDatabase db = SQLiteConnection.getDatabase(context);
            String query="SELECT TrainingId FROM BookTraining WHERE EmployeeId=?";
            Cursor cursor = db.rawQuery(query, new String[]{empId});




            while(cursor.moveToNext()){
               @SuppressLint("Range")
                int trainingId = cursor.getInt(cursor.getColumnIndex("TrainingId"));
                String dateDb = new DateConverter().bookDateandtime(trainingId+"", context);
                boolean check = new DateConverter().isBanExpired(dateDb);

                if(!check) {

                    String trainingQuery = "select * from Training where TrainingId=? and TrainingArchived='False' order by TrainingDate desc";
                    Cursor trainingCursor = db.rawQuery(trainingQuery, new String[]{String.valueOf(trainingId)});


                    while (trainingCursor.moveToNext()) {
                        getAll.add(new TrainingModel(
                                trainingCursor.getInt(trainingCursor.getColumnIndex("TrainingId")),
                                trainingCursor.getInt(trainingCursor.getColumnIndex("CourseId")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingName")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDate")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingStart")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingEnd")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingLocation")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingArchived")),
                                trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDescription"))));
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return getAll;

    }
    @SuppressLint("Range")
    public List<TrainingModel>getAll(String courseId, Context context){
        List<TrainingModel>getAll = new ArrayList<>();
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        try{
            Cursor trainingCursor = db.rawQuery("select * from Training where CourseId=? and TrainingArchived != 'True'", new String[]{courseId});



            while(trainingCursor.moveToNext()){
                getAll.add(new TrainingModel(
                        trainingCursor.getInt(trainingCursor.getColumnIndex("TrainingId")),
                        trainingCursor.getInt(trainingCursor.getColumnIndex("CourseId")),
                        trainingCursor.getString(trainingCursor.getColumnIndex("TrainingName")),
                        trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDate")),
                        trainingCursor.getString(trainingCursor.getColumnIndex("TrainingStart")),
                        trainingCursor.getString(trainingCursor.getColumnIndex("TrainingEnd")),
                        trainingCursor.getString(trainingCursor.getColumnIndex("TrainingLocation")),
                        trainingCursor.getString(trainingCursor.getColumnIndex("TrainingArchived")),
                        trainingCursor.getString(trainingCursor.getColumnIndex("TrainingDescription"))));
            }



        }catch (Exception e){
            e.printStackTrace();
        }


        return getAll;
    }

    @SuppressLint("Range")
    public String notificationMsg(String tid, Context context){
        String msg = "";
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Cursor cursor = db.rawQuery("select * from Training where TrainingId=? and TrainingArchived='False'", new String[]{tid});
        try{


            while(cursor.moveToNext()){
                msg = "Upcoming Training:\n" + cursor.getString(cursor.getColumnIndex("TrainingName")) + "\nTrainingDate: " + new DateConverter().date(cursor.getString(cursor.getColumnIndex("TrainingDate")))  + "\nTime: " + new DateConverter().time(cursor.getString(cursor.getColumnIndex("TrainingStart")))  + " - " + new DateConverter().time(cursor.getString(cursor.getColumnIndex("TrainingEnd"))) ;

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return msg;
    }

    public String book(String empId, String tid, String date, String startTime, String close){
        String msg = "";
        Connection con = ConnectionProvider.connect();
        System.out.println("TID: " + tid);
        try{
            boolean checkDuplicates = false;




            //scanning first if the user has already booked a training

            PreparedStatement scanTraining = con.prepareStatement("select TrainingId from BookTraining where EmployeeId=? and TrainingId=?");
            scanTraining.setString(1,empId);
            scanTraining.setString(2,tid);
            ResultSet readTraining = scanTraining.executeQuery();

            while(readTraining.next()){
                checkDuplicates = true;
            }

            if(checkDuplicates){
                msg = "Error-Duplicate";
            }else{
                PreparedStatement ps = con.prepareStatement("Insert into BookTraining (BookTrainingArchived,EmployeeId,TrainingId,BookTrainingDate,notify,startTime, TrainingDate) VALUES (?,?,?,?,?,?,?)");
                ps.setString(1, "False");
                ps.setString(2, empId);
                ps.setString(3, tid);
                ps.setString(4, new DateConverter().dateNow());
                ps.setString(5, "False");
                ps.setString(6, startTime);
                ps.setString(7, date);

                int k = ps.executeUpdate();

                if(k > 0){
                    msg = "Success";
                }else{
                    msg = "Error-Create";
                }
                ps.close();
            }
            scanTraining.close();
            readTraining.close();

            if(close.equals("Yes")){
                con.close();
            }
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return msg;
    }



}
