package com.example.slide6_anc2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReaderHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MAP_manager";

    //TABLE HÓA ĐƠN
    public static final String TABLE_NAME = "Map";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LONGITUTE = "longitute";
    public static final String COLUMN_LATITUTE = "latitute";

    final String CREATE_BILL = " CREATE TABLE Map ( id INTEGER PRIMARY KEY , name STRING , longitute DOUBLE , latitute DOUBLE ) ";


    public ReaderHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BILL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
