package org.moosetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Text;

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
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Count Integer, String Text, Latitude Double, Longitude Double, Timestamp Date )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String count, String description, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, count);
        contentValues.put(Col_3, description);
        contentValues.put(Col_4, latitude);
        contentValues.put(Col_5, longitude);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
