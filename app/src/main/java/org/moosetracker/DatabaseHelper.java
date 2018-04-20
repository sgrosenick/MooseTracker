package org.moosetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MooseReports.db";
    public static final String TABLE_NAME = "Reports";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "Count";
    public static final String Col_3 = "Description";
    public static final String Col_4 = "Latitude";
    public static final String Col_5 = "Longitude";
    public static final String Col_6 = "Timestamp";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Count Text, Description Text, Latitude Double, Longitude Double, Timestamp Date )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
