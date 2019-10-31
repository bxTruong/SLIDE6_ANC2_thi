package com.example.slide6_anc2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.slide6_anc2.ReaderHelper.*;

public class Dao {
    private ReaderHelper readerHelper;

    public Dao(Context context) {
        readerHelper = new ReaderHelper(context);
    }

    // Truy Vấn Hóa Đơn --------------------------------------------------------------------------------------------------------------------

    public long inserMap(Map map) {
        SQLiteDatabase sqLiteDatabase = readerHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, map.getName());
        contentValues.put(COLUMN_LATITUTE, map.getLatitute());
        contentValues.put(COLUMN_LONGITUTE, map.getLongitute());

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public List<Map> getAllMap() {
        List<Map> mapList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = readerHelper.getWritableDatabase();
        String SELECT = " SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT, null);
        if (cursor.moveToFirst()) {
            do {
                Map map = new Map();
                map.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                map.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                map.setLongitute(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUTE)));
                map.setLatitute(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUTE)));
                mapList.add(map);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return mapList;
    }

    public int updateBook(Map map) {
        SQLiteDatabase sqLiteDatabase = readerHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME, map.getName());
        contentValues.put(COLUMN_LATITUTE, map.getLatitute());
        contentValues.put(COLUMN_LONGITUTE, map.getLongitute());

        //Update theo Id
        int update = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[]{String.valueOf(map.getId())});
        sqLiteDatabase.close();
        return update;
    }

    public long deleteBill(int id) {
        SQLiteDatabase sqLiteDatabase = readerHelper.getWritableDatabase();
        long delete = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        return delete;
    }
}
