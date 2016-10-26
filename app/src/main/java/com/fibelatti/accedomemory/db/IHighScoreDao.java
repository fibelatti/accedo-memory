package com.fibelatti.accedomemory.db;

import com.fibelatti.accedomemory.models.HighScore;

import java.util.List;

public interface IHighScoreDao {
    HighScore fetchHighScoreById(long highScoreId);

    List<HighScore> fetchAllHighScores();

    List<HighScore> fetchAllHighScoresHigherThan(int score);

    List<HighScore> fetchTopHighScores(int limit);

    boolean saveHighScore(HighScore highScore);
}
