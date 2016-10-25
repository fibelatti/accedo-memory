package com.fibelatti.accedomemory.presenters.memorygame;

import android.content.Context;

import com.fibelatti.accedomemory.helpers.GameHelper;

public class MemoryGamePresenter
        implements IMemoryGamePresenter {

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
}
