package com.example.jaj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteConnection {

    private static MyDatabaseHelper dbHelper;
    private static SQLiteDatabase database;

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            dbHelper = new MyDatabaseHelper(context);
            database = dbHelper.getWritableDatabase();
        }
        return database;
    }

    public static void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}

