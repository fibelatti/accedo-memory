package com.fibelatti.accedomemory.helpers;

public interface IGameHelperListener {
    void onCurrentScoreChanged(int currentScore);

    void onNewHighScore(int rank, int score);

    void onGameFinished(int rank, int score);
}
