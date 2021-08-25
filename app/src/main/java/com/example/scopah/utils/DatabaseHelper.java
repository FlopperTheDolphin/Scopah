package com.example.scopah.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String SCOPAH_DB_NAME = "Scopah.db";
    public static final int SCOPAH_DB_VERSION = 3;
    public static final String TABLE_NAME = "GAMES";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MATCH_ID = "MATCH_ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_COLOR = "COLOR";
    public static final String COLUMN_SCORE = "SCORE";
    public static final String COLUMN_COMPLETED = "COMPLETED";

    private static final String QUERY_CREATE_DB = "CREATE TABLE " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MATCH_ID + " INTEGER NOT NULL, " +
            COLUMN_NAME + " TEXT NOT NULL, " +
            COLUMN_COLOR + " TEXT NOT NULL, " +
            COLUMN_SCORE + " INTEGER NOT NULL, " +
            COLUMN_COMPLETED + " INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, SCOPAH_DB_NAME, null, SCOPAH_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
            database.beginTransaction();
            database.execSQL(QUERY_CREATE_DB);
            database.endTransaction();
    }

    public ArrayList<ContentValues> dataToValues(MatchData data) {
        ArrayList<PlayerData> players = data.getPlayers();
        ArrayList<ContentValues> values = new ArrayList<>();

        for (PlayerData p : players) {
            ContentValues v = new ContentValues();
            v.put(COLUMN_MATCH_ID, data.getId());
            v.put(COLUMN_NAME, p.getName());
            v.put(COLUMN_COLOR, p.getColor());
            v.put(COLUMN_SCORE, p.getScore());
            v.put(COLUMN_COMPLETED, data.isCompleted());

            values.add(v);
        }

        return values;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // do nothing
        return;
    }
}
