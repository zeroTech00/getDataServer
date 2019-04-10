package com.example.zero.getdataserver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "dataSensor";
    private static final String COL1 = "ID";
    private static final String COL2 = "ID_server";
    private static final String COL3 = "sensor1";
    private static final String COL4 = "sensor2";
    private static final String COL5 = "sensor3";
    private static final String COL6 = "sensor4";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2 + " TEXT, " +
                    COL3 + " TEXT, " +
                    COL4 + " TEXT, " +
                    COL5 + " TEXT, " +
                    COL6 + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String ID_server, String sensor1, String sensor2, String sensor3, String sensor4) {

        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, ID_server); //add idServer
        contentValues.put(COL3, sensor1); //add sensor1
        contentValues.put(COL4, sensor2); //add sensor2
        contentValues.put(COL5, sensor3); //add sensor3
        contentValues.put(COL6, sensor4); //add sensor4

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
