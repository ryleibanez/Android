package com.example.jaj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseController {



    @SuppressLint("Range")
    public List<Course> getAllCourses(String empId, Context context) {
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);
        List<Course> getAll = new ArrayList<>();
        String query = "select CourseId from Enrollment where EmployeeId=?";
        try {
            Cursor cursor = db.rawQuery(query, new String[]{empId});

            // get the enrolled courses based on the empId
            while (cursor.moveToNext()) {
                System.out.println("Emp ID :" + empId);

                // get the course info from the course table
                int courseId = cursor.getInt(cursor.getColumnIndex("CourseId"));
                Cursor courses = db.rawQuery("select * from Course where CourseId=? and CourseStatus='Open'", new String[]{String.valueOf(courseId)});

                // check if the cursor has results
                if (courses.moveToFirst()) {
                    // putting the info in a list
                    do {
                        getAll.add(new Course(
                                courses.getInt(courses.getColumnIndex("CourseId")),
                                courses.getString(courses.getColumnIndex("CourseName")),
                                courses.getString(courses.getColumnIndex("CourseDescription")),
                                courses.getString(courses.getColumnIndex("CourseStatus")),
                                courses.getString(courses.getColumnIndex("CourseArchived"))
                        ));
                    } while (courses.moveToNext());
                }

                // close the inner cursor
                courses.close();
            }

            // close the outer cursor
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return getAll;
    }

}
