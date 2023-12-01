package com.example.jaj;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.xml.transform.Result;

public class TableUpdater {

    private MyDatabaseHelper dbHelper;
    ContentValues values = new ContentValues();


    private void deleteAllData(Context context, String tableName) {
        // Gets the data repository in write mode
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);

        // Delete all rows from the table
        int rowsDeleted = db.delete(tableName, null, null);

        if (rowsDeleted > 0) {
            // Successful deletion

        } else {
            // No rows were deleted

        }
    }


    public void readUpdateBookTraining(Context context){

        Connection con = new ConnectionProvider().connect();
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        deleteAllData(context, "BookTraining");


        try {
            PreparedStatement ps = con.prepareStatement("select * from BookTraining");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){


                values.put("BookTrainingArchived", rs.getInt("BookTrainingArchived") == 1 ? "True" : (rs.getInt("BookTrainingArchived") == 0 ? "False" : "DefaultValue"));
                values.put("EmployeeId", rs.getString("EmployeeId"));
                values.put("TrainingId", rs.getString("TrainingId"));
                values.put("BookTrainingDate", rs.getString("BookTrainingDate"));
                values.put("notify", rs.getString("notify"));
                values.put("TrainingDate", rs.getString("TrainingDate"));


                // Mapping 1 to 'True' and 0 to 'False' for the Archived column


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("BookTraining", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

            rs.close();
            ps.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readCourses(Context context){


        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();
        deleteAllData(context, "Course");

        try {
            PreparedStatement ps = con.prepareStatement("select * from Course");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){


                values.put("CourseId", rs.getString("CourseId"));
                values.put("CourseArchived", rs.getInt("CourseArchived") == 1 ? "True" : (rs.getInt("CourseArchived") == 0 ? "False" : "DefaultValue"));
                values.put("CourseName", rs.getString("CourseName"));
                values.put("CourseDescription", rs.getString("CourseDescription"));
                values.put("CourseStatus", rs.getString("CourseStatus"));
                // Assuming Image is of BLOB type, you may need to handle it accordingly
                byte[] imageBytes = rs.getBytes("CourseImage");
                values.put("CourseImage", imageBytes);

                byte[] imageBytes1 = rs.getBytes("PptFileContent");
                values.put("PptFileContent", imageBytes1);



                // Mapping 1 to 'True' and 0 to 'False' for the Archived column


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("Course", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

            rs.close();
            ps.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readCourseInstructor(Context context){


        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();
        deleteAllData(context, "CourseInstructor");

        try {
            PreparedStatement ps = con.prepareStatement("select * from CourseInstructor");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){


                values.put("CourseInstructorId", rs.getString("CourseInstructorId"));
                values.put("CourseInstructorArchived", rs.getInt("CourseInstructorArchived") == 1 ? "True" : (rs.getInt("CourseInstructorArchived") == 0 ? "False" : "DefaultValue"));
                values.put("CourseId", rs.getString("CourseId"));
                values.put("InstructorId", rs.getString("InstructorId"));



                // Mapping 1 to 'True' and 0 to 'False' for the Archived column


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("CourseInstructor", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

            rs.close();
            ps.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void readTraining(Context context){


        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();
        deleteAllData(context, "Training");

        try {
            PreparedStatement ps = con.prepareStatement("select * from Training");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){


                values.put("TrainingId", rs.getString("TrainingId"));
                values.put("TrainingArchived", rs.getInt("TrainingArchived") == 1 ? "True" : (rs.getInt("TrainingArchived") == 0 ? "False" : "DefaultValue"));
                values.put("CourseId", rs.getString("CourseId"));
                values.put("TrainingName", rs.getString("TrainingName"));
                values.put("TrainingDate", rs.getString("TrainingDate"));
                values.put("TrainingStart", rs.getString("TrainingStart"));
                values.put("TrainingEnd", rs.getString("TrainingEnd"));
                values.put("TrainingLocation", rs.getString("TrainingLocation"));
                values.put("TrainingDescription", rs.getString("TrainingDescription"));





                // Mapping 1 to 'True' and 0 to 'False' for the Archived column


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("Training", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

            rs.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readTrainingInstructor(Context context){
        deleteAllData(context, "TrainingInstructor");

        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();


        try {
            PreparedStatement ps = con.prepareStatement("select * from TrainingInstructor");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){


                values.put("TrainingInstructorId", rs.getString("TrainingInstructorId"));
                values.put("TrainingInstructorArchived", rs.getInt("TrainingInstructorArchived") == 1 ? "True" : (rs.getInt("TrainingInstructorArchived") == 0 ? "False" : "DefaultValue"));
                values.put("TrainingId", rs.getString("TrainingId"));
                values.put("InstructorId", rs.getString("InstructorId"));






                // Mapping 1 to 'True' and 0 to 'False' for the Archived column


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("TrainingInstructor", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

            rs.close();
            ps.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readInstructors(Context context){
        deleteAllData(context, "TrainingInstructor");

        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();


        try {
            PreparedStatement ps = con.prepareStatement("select * from Instructor");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){


                values.put("InstructorId", rs.getString("InstructorId"));
                values.put("InsArchived", rs.getInt("InsArchived") == 1 ? "True" : (rs.getInt("InsArchived") == 0 ? "False" : "DefaultValue"));
                values.put("InsFirstName", rs.getString("InsFirstName"));
                values.put("InsLastName", rs.getString("InsLastName"));
                values.put("InsEmail", rs.getString("InsEmail"));
                values.put("InsPassword", rs.getString("InsPassword"));
                values.put("InsAddress", rs.getString("InsAddress"));
                values.put("InsContactNumber", rs.getString("InsContactNumber"));
                values.put("InsGender", rs.getString("TrainingId"));
                values.put("InstructorId", rs.getString("InstructorId"));
                values.put("TrainingId", rs.getString("TrainingId"));
                values.put("InstructorId", rs.getString("InstructorId"));
                values.put("TrainingId", rs.getString("TrainingId"));
                values.put("InstructorId", rs.getString("InstructorId"));
                values.put("TrainingId", rs.getString("TrainingId"));
                values.put("InstructorId", rs.getString("InstructorId"));
                values.put("TrainingId", rs.getString("TrainingId"));
                values.put("InstructorId", rs.getString("InstructorId"));







                // Mapping 1 to 'True' and 0 to 'False' for the Archived column


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("TrainingInstructor", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

            rs.close();
            ps.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void readEnrollment(Context context){


        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();
        deleteAllData(context, "Enrollment");

        try {
            PreparedStatement ps = con.prepareStatement("select * from Enrollment");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                values.put("EnrollmentId", rs.getString("EnrollmentId"));
                values.put("EnrollmentArchived", rs.getInt("EnrollmentArchived") == 1 ? "True" : (rs.getInt("EnrollmentArchived") == 0 ? "False" : "DefaultValue"));
                values.put("EmployeeId", rs.getString("EmployeeId"));
                values.put("CourseId", rs.getString("CourseId"));
                values.put("EnrollmentDate", rs.getString("EnrollmentDate"));




                // Mapping 1 to 'True' and 0 to 'False' for the Archived column


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("Enrollment", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

            rs.close();
            ps.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void readUpdateLogin(Context context){


        SQLiteDatabase db = SQLiteConnection.getDatabase(context);


        Connection con = new ConnectionProvider().connect();
        deleteAllData(context, "Employee");
        try {
            PreparedStatement ps = con.prepareStatement("select * from Employee");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                values.put("EmployeeId", rs.getString("EmployeeId"));
                values.put("FirstName", rs.getString("FirstName"));
                values.put("LastName", rs.getString("LastName"));
                values.put("Email", rs.getString("Email"));
                values.put("Password", rs.getString("Password"));
                values.put("JobTitle", rs.getString("JobTitle"));
                values.put("Address", rs.getString("Address"));
                values.put("ContactNumber", rs.getString("ContactNumber"));
                values.put("Gender", rs.getString("Gender"));
                values.put("DateOfBirth", rs.getString("DateOfBirth"));

                // Mapping 1 to 'True' and 0 to 'False' for the Archived column

                values.put("Archived", rs.getInt("Archived") == 1 ? "True" : (rs.getInt("Archived") == 0 ? "False" : "DefaultValue"));


                // Assuming Image is of BLOB type, you may need to handle it accordingly
                byte[] imageBytes = rs.getBytes("Image");
                values.put("Image", imageBytes);


                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("Employee", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void readInstructor(Context context){


        SQLiteDatabase db = SQLiteConnection.getDatabase(context);


        Connection con = new ConnectionProvider().connect();
        deleteAllData(context, "Instructor");
        try {
            PreparedStatement ps = con.prepareStatement("select * from Instructor");

            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                values.put("InstructorId", rs.getString("InstructorId"));
                values.put("InsFirstName", rs.getString("InsFirstName"));
                values.put("InsLastName", rs.getString("InsLastName"));
                values.put("InsEmail", rs.getString("InsEmail"));
                values.put("InsPassword", rs.getString("InsPassword"));

                values.put("InsAddress", rs.getString("InsAddress"));
                values.put("InsContactNumber", rs.getString("InsContactNumber"));
                values.put("InsGender", rs.getString("InsGender"));
                values.put("InsDateOfBirth", rs.getString("InsDateOfBirth"));
                values.put("InsJobTitle", rs.getString("InsJobTitle"));


                // Mapping 1 to 'True' and 0 to 'False' for the Archived column

                values.put("InsArchived", rs.getInt("InsArchived") == 1 ? "True" : (rs.getInt("InsArchived") == 0 ? "False" : "DefaultValue"));
                values.put("IsAdmin", rs.getInt("IsAdmin") == 1 ? "True" : (rs.getInt("IsAdmin") == 0 ? "False" : "DefaultValue"));

                // Assuming Image is of BLOB type, you may need to handle it accordingly
                byte[] imageBytes = rs.getBytes("InsImage");
                values.put("InsImage", imageBytes);



                // Insert the new row, returning the primary key value of the new row
                long newRowId = db.insert("Instructor", null, values);

                if (newRowId != -1) {
                    // Successful insert

                } else {
                    // Failed insert

                }



            }
            rs.close();
            ps.close();
            con.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @SuppressLint("Range")
    public void insertIntoNotificationTable(Context context, String close) {
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();

        Cursor cursor = null;
        try {
            String query = "SELECT * FROM notification WHERE email=?";
            cursor = db.rawQuery(query, new String[]{new SessionController(context).getUserEmail()});

            while (cursor.moveToNext()) {




                // Check if the cursor contains data before proceeding
                if (cursor.getCount() > 0) {
                    PreparedStatement ps = con.prepareStatement("insert into notification ( email, message) values (?,?)");


                    ps.setString(1, new SessionController(context).getUserEmail());
                    ps.setString(2, cursor.getString(cursor.getColumnIndex("message")));

                    int k = ps.executeUpdate();
                    // Handle the result if needed
                } else {
                    System.out.println("No data in the cursor.");
                }
            }
            if(close.equals("Yes")) {
                con.close();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor to release resources
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
    @SuppressLint("Range")
    public void updateBookTrainingInfo(Context context, String close) {
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        Connection con = new ConnectionProvider().connect();

        Cursor cursor = null;
        try {
            String query = "SELECT * FROM BookTraining WHERE EmployeeId=?";
            cursor = db.rawQuery(query, new String[]{new SessionController(context).getEmpId()});

            while (cursor.moveToNext()) {
                String notify = cursor.getString(cursor.getColumnIndex("Notify"));
                String trainingId = cursor.getString(cursor.getColumnIndex("TrainingId"));

                System.out.println("Training ID: " + trainingId);

                // Check if the cursor contains data before proceeding
                if (cursor.getCount() > 0) {
                    PreparedStatement ps = con.prepareStatement("UPDATE BookTraining SET notify=? WHERE TrainingId=? AND EmployeeId=?");

                    ps.setString(1, notify);
                    ps.setString(2, trainingId);
                    ps.setString(3, new SessionController(context).getEmpId());

                    int k = ps.executeUpdate();
                    // Handle the result if needed
                } else {
                    System.out.println("No data in the cursor.");
                }
            }
            if(close.equals("Yes")) {
                con.close();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the cursor to release resources
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }



}
