package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseQRCode extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DataQRCode.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "textQRTb";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT = "textQR";
    public DatabaseQRCode(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TEXT + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addText(String textQR) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXT, textQR);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<String> getAllQRCodes() {
        ArrayList<String> retQRCodes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int index = cursor.getColumnIndex(COLUMN_TEXT);
                String textQR = cursor.getString(index);
                retQRCodes.add(textQR);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return retQRCodes;
    }

    public void delAllQRCodes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
