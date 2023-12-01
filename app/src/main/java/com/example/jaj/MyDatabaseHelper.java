package com.example.jaj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jaj.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your tables here
        String employee = new CreateTables().createEmployeeTable();
        db.execSQL(employee);

        String createEnrollment = new CreateTables().createEnrollment();
        db.execSQL(createEnrollment);

        String createInstructorTable = new CreateTables().createInstructorTable();
        db.execSQL(createInstructorTable);

        String createTrainingTable = new CreateTables().createTrainingTable();
        db.execSQL(createTrainingTable);

        String createTrainingInstructorTable = new CreateTables().createTrainingInstructorTable();
        db.execSQL(createTrainingInstructorTable);

        String createCourseTable = new CreateTables().createCourseTable();
        db.execSQL(createCourseTable);

        String createCourseInstructor = new CreateTables().createCourseInstructor();
        db.execSQL(createCourseInstructor);

        String createBookTrainingTable = new CreateTables().createBookTrainingTable();
        db.execSQL(createBookTrainingTable);

        String createSQLiteNotificationTableQuery = new CreateTables().createSQLiteNotificationTableQuery();
        db.execSQL(createSQLiteNotificationTableQuery);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade the database if needed
        // This method will be called when the database version is increased in your application code
    }
}

