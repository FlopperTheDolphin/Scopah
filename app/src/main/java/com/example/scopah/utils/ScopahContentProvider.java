package com.example.scopah.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScopahContentProvider extends ContentProvider {
    private DatabaseHelper dbHelper;
    private UriMatcher matcher;
    private final static int DIR_INDICATOR = 1;
    private final static int ITEM_INDICATOR = 2;

    @Override
    public boolean onCreate() {
        if (dbHelper == null)
            dbHelper = new DatabaseHelper(getContext());

        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(DatabaseHelper.AUTHORITY, DatabaseHelper.PATH, DIR_INDICATOR);
        matcher.addURI(DatabaseHelper.AUTHORITY, DatabaseHelper.PATH + "/#", ITEM_INDICATOR);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int UriMatcherCode = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        String itemID = null;
        String whereClause = null;

        switch (UriMatcherCode) {
            case DIR_INDICATOR:
                cursor = db.query(DatabaseHelper.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ITEM_INDICATOR:
                itemID = uri.getPathSegments().get(1);
                whereClause = DatabaseHelper.COLUMN_ID + " = " + itemID;

                if (selection != null) {
                    whereClause += " AND (" + selection + ")";
                }
                cursor = db.query(DatabaseHelper.TABLE_NAME, projection, whereClause,
                        selectionArgs, null, null, sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
            case DIR_INDICATOR:
                return DatabaseHelper.MIME_TYPE_DIR;
            case ITEM_INDICATOR:
                return DatabaseHelper.MIME_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("The URI " + uri + "is unknown for this Content Provider");
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int UriMatcherCode = matcher.match(uri);
        if (UriMatcherCode == DIR_INDICATOR) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long newItemId = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);

            if (newItemId > 0)
                return ContentUris.withAppendedId(DatabaseHelper.CONTENT_URI, newItemId);

        } else {
            throw new IllegalArgumentException("The URI " + uri + " used for the INSERT operation is not valid in this Content Provider");
        }
        return null;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int UriMatcherCode = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteNumber = 0;
        String itemID = null;
        String whereClause = null;

        switch (UriMatcherCode) {
            case DIR_INDICATOR:
                deleteNumber = db.delete(DatabaseHelper.TABLE_NAME,selection,selectionArgs);
                break;
            case ITEM_INDICATOR:
                itemID = uri.getPathSegments().get(1);
                whereClause = DatabaseHelper.COLUMN_ID + " = " + itemID;

                if(selection != null) {
                    whereClause += " AND (" + selection + ")";
                }
                deleteNumber = db.delete(DatabaseHelper.TABLE_NAME,whereClause,selectionArgs);
                break;
        }
        return deleteNumber;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final int UriMatcherCode = matcher.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedItems = 0;
        String itemID = null;
        String whereClause = null;

        switch (UriMatcherCode) {
            case DIR_INDICATOR:
                updatedItems = db.update(DatabaseHelper.TABLE_NAME,values,selection,selectionArgs);
                break;
            case ITEM_INDICATOR:
                itemID = uri.getPathSegments().get(1);
                whereClause = DatabaseHelper.COLUMN_ID + " = " + itemID;

                if(selection != null) {
                    whereClause += " AND (" + selection + ")";
                }
                updatedItems = db.update(DatabaseHelper.TABLE_NAME,values,whereClause,selectionArgs);
                break;
        }
        return updatedItems;
    }
}
