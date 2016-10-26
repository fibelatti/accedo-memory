package com.fibelatti.accedomemory.presenters.memorygame;

import android.content.Context;

import com.fibelatti.accedomemory.db.Database;
import com.fibelatti.accedomemory.helpers.GameHelper;
import com.fibelatti.accedomemory.helpers.IGameHelper;
import com.fibelatti.accedomemory.helpers.IGameHelperListener;
import com.fibelatti.accedomemory.models.Card;
import com.fibelatti.accedomemory.models.HighScore;

import java.util.List;

public class MemoryGamePresenter
        implements IMemoryGamePresenter,
        IGameHelperListener {

    private Context context;
    private IMemoryGameView view;
    private IGameHelper gameHelper;

    private MemoryGamePresenter(Context context, IMemoryGameView view) {
        this.context = context;
        this.view = view;
        this.gameHelper = GameHelper.getInstance();
    }

    public static MemoryGamePresenter createPresenter(Context context, IMemoryGameView view) {
        return new MemoryGamePresenter(context, view);
    }

    @Override
    public void onCreate() {
        GameHelper.getInstance().addListener(this);

        if (view != null) view.onGameChanged(gameHelper.getCurrentGame());
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        GameHelper.getInstance().removeListener(this);

        this.context = null;
        this.view = null;
        this.gameHelper = null;
    }

    @Override
    public void newGame() {
        if (view != null) view.onGameChanged(gameHelper.createGame());
    }

    @Override
    public boolean saveNewHighScore(String name) {
        return Database.highScoreDao.saveHighScore(new HighScore(name, gameHelper.getCurrentScore()));
    }

    @Override
    public void onCurrentScoreChanged(int currentScore) {
        if (view != null) view.onCurrentScoreChanged(currentScore);
    }

    @Override
    public void onRound(List<Card> cardList) {
        if (view != null) view.onRound(cardList);
    }

    @Override
    public void onNewHighScore(int rank, int score) {
        if (view != null) view.onNewHighScore(rank, score);
    }

    @Override
    public void onGameFinished(int rank, int score) {
        if (view != null) view.onGameFinished(rank, score);
    }
}
