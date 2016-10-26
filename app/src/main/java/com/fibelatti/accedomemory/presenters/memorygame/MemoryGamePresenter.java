package com.fibelatti.accedomemory.presenters.memorygame;

import android.content.Context;

import com.fibelatti.accedomemory.db.Database;
import com.fibelatti.accedomemory.helpers.GameHelper;
import com.fibelatti.accedomemory.helpers.IGameHelperListener;
import com.fibelatti.accedomemory.models.HighScore;

public class MemoryGamePresenter
        implements IMemoryGamePresenter,
        IGameHelperListener {

    private Context context;
    private IMemoryGameView view;
    private GameHelper gameHelper;

    private MemoryGamePresenter(Context context, IMemoryGameView view) {
        this.context = context;
        this.view = view;
        this.gameHelper = GameHelper.getInstance();
        this.onCreate();
    }

    public static MemoryGamePresenter createPresenter(Context context, IMemoryGameView view) {
        return new MemoryGamePresenter(context, view);
    }

    @Override
    public void onCreate() {
        GameHelper.getInstance().addListener(this);

        view.onGameChanged(gameHelper.getCurrentGame());
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        this.context = null;
        this.view = null;
        this.gameHelper = null;
    }

    @Override
    public void newGame() {
        view.onGameChanged(gameHelper.createGame());
    }

    @Override
    public boolean saveNewHighScore(String name) {
        return Database.highScoreDao.saveHighScore(new HighScore(name, gameHelper.getCurrentScore()));
    }

    @Override
    public void onCurrentScoreChanged(int currentScore) {
        view.onCurrentScoreChanged(currentScore);
    }

    @Override
    public void onNewHighScore(int rank, int score) {
        view.onNewHighScore(rank, score);
    }

    @Override
    public void onGameFinished(int rank, int score) {
        view.onGameFinished(rank, score);
    }
}
