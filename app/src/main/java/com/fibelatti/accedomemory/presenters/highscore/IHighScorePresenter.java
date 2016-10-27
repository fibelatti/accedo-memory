package com.fibelatti.accedomemory.presenters.highscore;

public interface IHighScorePresenter {
    void onCreate();

    void onPause();

    void onResume();

    void onDestroy();

    void fetchData();

    boolean saveNewHighScore(String name, int score);
}
