package com.example.scopah.utils;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class ScopahContentResolver implements LocalData {
    private String[] columns = {DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_MATCH_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_COLOR,
            DatabaseHelper.COLUMN_SCORE,
            DatabaseHelper.COLUMN_COMPLETED};

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public ArrayList<MatchData> getAllData(boolean completed) {
        ArrayList<MatchData> matches = new ArrayList<>();

        Cursor cursor = AppContext.getAppContext().getContentResolver().query(
                DatabaseHelper.CONTENT_URI,
                columns,
                DatabaseHelper.COLUMN_COMPLETED + " = ?",
                new String[] {"" + (completed ? 1 : 0)},
                DatabaseHelper.COLUMN_MATCH_ID + " DESC");

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(1);
            matches.add(cursorToData(cursor, id));
        }

        return matches;
    }

    @Override
    public MatchData insertData(MatchData data) {
        ArrayList<ContentValues> values = dataToValues(data);

        for (ContentValues v : values) {
            AppContext.getAppContext().getContentResolver().insert(DatabaseHelper.CONTENT_URI, v);
        }

        Cursor cursor = AppContext.getAppContext().getContentResolver().query(
                DatabaseHelper.CONTENT_URI,
                columns,
                DatabaseHelper.COLUMN_MATCH_ID + " = ?",
                new String[] {"" + data.getId()},
                null);

        cursor.moveToFirst();
        MatchData ret = cursorToData(cursor, data.getId());
        cursor.close();
        return ret;
    }

    @Override
    public int deleteData(long id) {
        return AppContext.getAppContext().getContentResolver().delete(
                DatabaseHelper.CONTENT_URI,
                DatabaseHelper.COLUMN_MATCH_ID + " = ?",
                new String[] {"" + id});
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

    private ArrayList<ContentValues> dataToValues(MatchData data) {
        ArrayList<PlayerData> players = data.getPlayers();
        ArrayList<ContentValues> values = new ArrayList<>();

        for (PlayerData p : players) {
            ContentValues v = new ContentValues();
            v.put(DatabaseHelper.COLUMN_MATCH_ID, data.getId());
            v.put(DatabaseHelper.COLUMN_NAME, p.getName());
            v.put(DatabaseHelper.COLUMN_COLOR, p.getColor());
            v.put(DatabaseHelper.COLUMN_SCORE, p.getScore());
            v.put(DatabaseHelper.COLUMN_COMPLETED, data.isCompleted());

            values.add(v);
        }

        return values;
    }
}
