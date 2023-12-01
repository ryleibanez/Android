package com.example.jaj;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class UserController {



    public byte[] profileImage(String email, Context context) {
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        byte[] imgData = null;

        try {
            Cursor cursor = db.rawQuery("SELECT Image FROM Employee WHERE Email=?", new String[]{email});

            if (cursor.moveToNext()) {
                // Retrieve the blob data as a byte array
                imgData = cursor.getBlob(cursor.getColumnIndex("Image"));
            }

            // Close the cursor to release resources
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imgData;
    }



    public boolean updateInfo(String fname, String lname, String email, String address, String phone, String dob, String gender, Context context){
        boolean check = false;
        Connection con = ConnectionProvider.connect();
        ContentValues values = new ContentValues();
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);



        try{

            PreparedStatement ps = con.prepareStatement("update Employee set FirstName=?,LastName=?,Address=?,ContactNumber=?,Gender=?,DateOfBirth=? where email=?");

            ps.setString(1, fname);
            ps.setString(2, lname);

            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setString(6, dob);
            ps.setString(5, gender);
            ps.setString(7, email);

            int k = ps.executeUpdate();

            if(k>0){
                check = true;
                values.put("FirstName", fname);
                values.put("LastName", lname);
                values.put("Address", address);
                values.put("ContactNumber", phone);
                values.put("Gender", gender);
                values.put("DateOfBirth", dob);

                // Define the selection criteria
                String selection = "EmployeeId=?";
                String[] selectionArgs = {new SessionController(context).getEmpId()};


                int rowsUpdated = db.update("Employee", values, selection, selectionArgs);
            }

            ps.close();
        }catch (Exception err){
            err.printStackTrace();
        }

        return check;

    }
    @SuppressLint("Range")
    public List<User>getInfo(String email, Context context){
        List<User>getAll = new ArrayList<>();
        // Get the current date

        SQLiteDatabase db = SQLiteConnection.getDatabase(context);

        try{
            String query = "select * from Employee where Email=? and Archived='False'";
            Cursor cursor = db.rawQuery(query, new String[]{email});



            while(cursor.moveToNext()){
                Period age = null; // Initialize the Period

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    try {
                        LocalDate currentDate = LocalDate.now();

                        // Assuming rs.getString("DateOfBirth") returns a valid date string in the format "YYYY-MM-DD"
                        @SuppressLint("Range") LocalDate birthdate = LocalDate.parse(cursor.getString(cursor.getColumnIndex("DateOfBirth")));

                        // Calculate the age
                        age = Period.between(birthdate, currentDate);
                    } catch (DateTimeParseException e) {
                        // Handle parsing exception, e.g., invalid date format
                        e.printStackTrace();
                    }
                }

// Assuming getAll is a list to store User objects
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getAll.add(new User(
                            cursor.getInt(cursor.getColumnIndex("EmployeeId")),
                            cursor.getString(cursor.getColumnIndex("FirstName")),
                            cursor.getString(cursor.getColumnIndex("LastName")),
                            cursor.getString(cursor.getColumnIndex("Email")),
                            cursor.getString(cursor.getColumnIndex("Password")),
                            cursor.getString(cursor.getColumnIndex("JobTitle")),
                            cursor.getString(cursor.getColumnIndex("Address")),
                            cursor.getString(cursor.getColumnIndex("ContactNumber")),
                            cursor.getString(cursor.getColumnIndex("Gender")),
                            cursor.getString(cursor.getColumnIndex("DateOfBirth")),
                            cursor.getString(cursor.getColumnIndex("Archived")),
                            "",
                            (age != null) ? String.valueOf(age.getYears()) : "N/A"
                    ));
                }


            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return getAll;
    }
    @SuppressLint("Range")
    public boolean Login(String email, String password, Context context) {
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        boolean check = false;

        try {
            String encryptedPw = "";

            // Get the encrypted password from the Employee table
            String queryPassword = "SELECT Password FROM Employee WHERE Email=? AND Archived='False'";
            Cursor cursorPassword = db.rawQuery(queryPassword, new String[]{email});

            if (cursorPassword.moveToNext()) {
                encryptedPw = cursorPassword.getString(cursorPassword.getColumnIndex("Password"));
            }

            cursorPassword.close();

            // Decrypt the password using your decryption logic (AesHelper.decrypt in your case)
            String decrypted = AesHelper.decrypt(encryptedPw);
            Log.d("MainActivity", "Decrypted: " + decrypted);

            // Check if the entered password matches the decrypted password
            if (password.equals(decrypted)) {
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }


    public String getEmpId(String email){
        String getInfo = "";
        Connection con = ConnectionProvider.connect();
        try{

            PreparedStatement ps = con.prepareStatement("select EmployeeID from Employee where email=?");
            ps.setString(1,email);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                getInfo = rs.getString("EmployeeID");
            }

            rs.close();
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        return getInfo;
    }

    public List<User> getUsers(){
        List<User> getAll = new ArrayList<>();
        Connection con = ConnectionProvider.connect();
        try{

            String query = "select * from Employee";
            PreparedStatement ps = con.prepareStatement(query);


            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                getAll.add(new User(rs.getInt("EmployeeId"),rs.getString("FirstName"),rs.getString("LastName"),rs.getString("Email"),rs.getString("Password"),rs.getString("JobTitle"),rs.getString("Address"),rs.getString("ContactNumber"),rs.getString("Gender"),rs.getString("DateOfBirth"),rs.getString("Archived"),"",""));
            }
            ps.close();
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        return getAll;
    }
}
