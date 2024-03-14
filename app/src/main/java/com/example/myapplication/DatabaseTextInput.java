package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseTextInput extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Data.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "textTb";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEXT = "text";

    public DatabaseTextInput(Context context) {
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

    // Метод для добавления текста в базу данных
    public void addText(int id, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_TEXT, text);
        db.replace(TABLE_NAME, null, values);
        db.close();
    }
    public String getText(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String text = null;

        Cursor cursor = db.query(TABLE_NAME,
                new String[] { COLUMN_TEXT },
                COLUMN_ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            text = cursor.getString(0);
            cursor.close();
        }

        db.close();
        return text;
    }
}
