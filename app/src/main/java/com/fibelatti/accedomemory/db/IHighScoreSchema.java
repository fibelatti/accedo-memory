package com.fibelatti.accedomemory.db;

public interface IHighScoreSchema {
    String HIGH_SCORE_TABLE = "high_scores";
    String HIGH_SCORE_COLUMN_ID = "_id";
    String HIGH_SCORE_COLUMN_PLAYER_NAME = "player_name";
    String HIGH_SCORE_COLUMN_PLAYER_SCORE = "player_score";
    String HIGH_SCORE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + HIGH_SCORE_TABLE
            + " ("
            + HIGH_SCORE_COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HIGH_SCORE_COLUMN_PLAYER_NAME
            + " TEXT NOT NULL, "
            + HIGH_SCORE_COLUMN_PLAYER_SCORE
            + " INTEGER"
            + ")";

    String HIGH_SCORE_TABLE_DROP = "DROP TABLE IF EXISTS " + HIGH_SCORE_TABLE;

    String[] HIGH_SCORE_COLUMNS = new String[]{
            HIGH_SCORE_COLUMN_ID,
            HIGH_SCORE_COLUMN_PLAYER_NAME,
            HIGH_SCORE_COLUMN_PLAYER_SCORE};
}
