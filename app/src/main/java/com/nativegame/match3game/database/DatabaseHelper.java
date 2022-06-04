package com.nativegame.match3game.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Oscar Liang on 2022/02/23
 */

/**
 * DatabaseHelper can access SQLite database where we
 * store structural data such as level star, otherwise
 * use SharePreference.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "star.db";

    // Database Column
    private static final String TABLE_NAME = "STAR_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_STAR = "STAR";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // This method will call only when the app first init
        String statement = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STAR + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This method will call when new version release
        // if (oldVersion == 1 && newVersion == 2) ...
    }

    public boolean insertLevelStar(int star) {
        // Get writable database as we want to write data
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        // "ID" will be inserted automatically.
        values.put(COLUMN_STAR, star);

        // Insert row
        long id = db.insert(TABLE_NAME, null, values);

        // Close db connection
        db.close();

        if (id == -1) {
            return false;
        }
        return true;
    }

    public boolean updateLevelStar(int levelID, int star) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STAR, star);

        // Updating row
        int id = db.update(TABLE_NAME, values,
                COLUMN_ID + " = ? ",
                new String[]{String.valueOf(levelID)});

        if (id == -1) {
            return false;
        }
        return true;
    }

    public int getLevelStar(int levelID) {
        // Get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_STAR},
                COLUMN_ID + " =? ",
                new String[]{String.valueOf(levelID)},
                null, null, null, null);

        int star = -1;
        if (cursor.moveToFirst()) {
            star = cursor.getInt(0);
        }

        // close the db connection
        cursor.close();
        db.close();

        return star;
    }

    public ArrayList<Integer> getAllLevelStar() {
        ArrayList<Integer> stars = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                stars.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        // Close db connection
        cursor.close();
        db.close();

        return stars;
    }

}
