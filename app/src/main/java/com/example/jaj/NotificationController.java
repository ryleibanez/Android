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

public class NotificationController {


    @SuppressLint("Range")
    public List<NotificationModel> getAll(String email, Context context){
        List<NotificationModel> getAll = new ArrayList<>();
        SQLiteDatabase db = SQLiteConnection.getDatabase(context);

        try{
            String query="select * from notification where email=?";
            Cursor cursor = db.rawQuery(query, new String[]{email});


            while (cursor.moveToNext()){


                getAll.add(new NotificationModel(cursor.getInt(cursor.getColumnIndex("NotificationId")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("message"))));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return getAll;

    }
}
