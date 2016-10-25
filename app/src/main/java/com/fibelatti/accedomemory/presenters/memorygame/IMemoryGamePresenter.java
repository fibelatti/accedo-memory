package com.fibelatti.accedomemory.presenters.memorygame;

public interface IMemoryGamePresenter {
    void onCreate();

    void onPause();

    void onResume();

    void onDestroy();

    void newGame();
}
