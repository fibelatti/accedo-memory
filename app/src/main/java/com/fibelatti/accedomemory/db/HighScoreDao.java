package com.fibelatti.accedomemory.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.crashlytics.android.Crashlytics;
import com.fibelatti.accedomemory.models.HighScore;

import java.util.ArrayList;
import java.util.List;

public class HighScoreDao extends DbContentProvider
        implements IHighScoreSchema, IHighScoreDao {

    private Cursor cursor;
    private ContentValues initialValues;

    public HighScoreDao(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public HighScore fetchHighScoreById(long highScoreId) {
        final String selectionArgs[] = {String.valueOf(highScoreId)};
        final String selection = HIGH_SCORE_COLUMN_ID + " = ?";
        HighScore highScore = new HighScore();
        cursor = super.query(HIGH_SCORE_TABLE, HIGH_SCORE_COLUMNS, selection,
                selectionArgs, HIGH_SCORE_COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                highScore = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return highScore;
    }

    @Override
    public List<HighScore> fetchAllHighScores() {
        final String order = HIGH_SCORE_COLUMN_PLAYER_SCORE + " DESC";

        List<HighScore> highScoreList = new ArrayList<>();
        cursor = super.query(HIGH_SCORE_TABLE, HIGH_SCORE_COLUMNS, null, null, order);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                HighScore highScore = cursorToEntity(cursor);
                highScoreList.add(highScore);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return highScoreList;
    }

    @Override
    public List<HighScore> fetchTopHighScores(int amount) {
        final String order = HIGH_SCORE_COLUMN_PLAYER_SCORE + " DESC";

        List<HighScore> highScoreList = new ArrayList<>();
        cursor = super.query(HIGH_SCORE_TABLE, HIGH_SCORE_COLUMNS, null, null, order, String.valueOf(amount));

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                HighScore highScore = cursorToEntity(cursor);
                highScoreList.add(highScore);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return highScoreList;
    }

    @Override
    public boolean saveHighScore(HighScore highScore) {
        setContentValue(highScore);
        try {
            return super.insert(HIGH_SCORE_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException e) {
            Crashlytics.logException(e);
            return false;
        }
    }

    protected HighScore cursorToEntity(Cursor cursor) {
        HighScore highScore = new HighScore();

        int idIndex;
        int playerNameIndex;
        int playerScoreIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(HIGH_SCORE_COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(HIGH_SCORE_COLUMN_ID);
                highScore.setId(cursor.getLong(idIndex));
            }
            if (cursor.getColumnIndex(HIGH_SCORE_COLUMN_PLAYER_NAME) != -1) {
                playerNameIndex = cursor.getColumnIndexOrThrow(
                        HIGH_SCORE_COLUMN_PLAYER_NAME);
                highScore.setPlayerName(cursor.getString(playerNameIndex));
            }
            if (cursor.getColumnIndex(HIGH_SCORE_COLUMN_PLAYER_SCORE) != -1) {
                playerScoreIndex = cursor.getColumnIndexOrThrow(
                        HIGH_SCORE_COLUMN_PLAYER_SCORE);
                highScore.setPlayerScore(cursor.getInt(playerScoreIndex));
            }
        }
        return highScore;
    }

    private void setContentValue(HighScore highScore) {
        initialValues = new ContentValues();
        initialValues.put(HIGH_SCORE_COLUMN_PLAYER_NAME, highScore.getPlayerName());
        initialValues.put(HIGH_SCORE_COLUMN_PLAYER_SCORE, highScore.getPlayerScore());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }
}
