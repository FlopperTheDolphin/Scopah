package com.example.scopah.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class LocalDataDB implements LocalData {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private String[] columns = {DatabaseHelper.COLUMN_ID,
                                DatabaseHelper.COLUMN_MATCH_ID,
                                DatabaseHelper.COLUMN_NAME,
                                DatabaseHelper.COLUMN_COLOR,
                                DatabaseHelper.COLUMN_SCORE,
                                DatabaseHelper.COLUMN_COMPLETED};

    @Override
    public void open() {
        if(dbHelper==null) {
            dbHelper = new DatabaseHelper(AppContext.getAppContext());
        }
        database = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public ArrayList<MatchData> getAllData() {
        ArrayList<MatchData> matches = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns,
                null, null, null, null,
                DatabaseHelper.COLUMN_MATCH_ID);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(1);
            matches.add(cursorToData(cursor, id));
        }

        return matches;
    }

    @Override
    public MatchData insertData(MatchData data) {
        ArrayList<ContentValues> values = dbHelper.dataToValues(data);

        for (ContentValues v : values) {
            database.insert(DatabaseHelper.TABLE_NAME, null, v);
        }

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, DatabaseHelper.COLUMN_MATCH_ID + " = ?",
                new String[] {"" + data.getId()}, null, null, null);

        cursor.moveToFirst();
        MatchData ret = cursorToData(cursor, data.getId());
        cursor.close();
        return ret;
    }

    private MatchData cursorToData(Cursor cursor, long id) {
        boolean completed;
        ArrayList<PlayerData> players = new ArrayList<>();

        completed = cursor.getInt(5) == 0;

        while (!cursor.isAfterLast() && cursor.getLong(1) == id) {
            PlayerData p = new PlayerData(cursor.getString(2),
                                            cursor.getString(3),
                                            cursor.getInt(4));

            players.add(p);
            cursor.moveToNext();
        }

        return new MatchData(players, completed, id);
    }
}
