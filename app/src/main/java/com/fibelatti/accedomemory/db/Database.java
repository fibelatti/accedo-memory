package com.fibelatti.accedomemory.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {
    private static final String TAG = Database.class.getSimpleName();
    private static final String DATABASE_NAME = "com.fibelatti.accedomemory.db";
    private static final int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private final Context context;

    public static IHighScoreDao highScoreDao;

    public Database open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase mDb = dbHelper.getWritableDatabase();

        highScoreDao = new HighScoreDao(mDb);

        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public Database(Context context) {
        this.context = context;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(IHighScoreSchema.HIGH_SCORE_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion);

            // Should only destroy old data if really necessary
            //db.execSQL(IHighScoreSchema.HIGH_SCORE_TABLE_DROP);

            onCreate(db);
        }
    }

}