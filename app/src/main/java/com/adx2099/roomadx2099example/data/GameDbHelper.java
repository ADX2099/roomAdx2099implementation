package com.adx2099.roomadx2099example.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adx2099.roomadx2099example.data.GameContract.GameEntry;

import androidx.annotation.Nullable;

public class GameDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "gameDb.db";
    private static final int VERSION = 1;

    public GameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + GameEntry.TABLE_NAME + " (" +
                GameEntry._ID                + " INTEGER PRIMARY KEY, " +
                GameEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                GameEntry.COLUMN_OWNED    + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME);
        onCreate(db);
    }
}
