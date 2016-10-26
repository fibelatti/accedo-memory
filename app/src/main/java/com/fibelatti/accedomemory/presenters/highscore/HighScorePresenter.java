package com.fibelatti.accedomemory.presenters.highscore;

import android.content.Context;

public class HighScorePresenter
    implements IHighScorePresenter {

    private Context context;
    private IHighScoreView view;

    private HighScorePresenter(Context context, IHighScoreView view) {
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
        this.onCreate();
    }

    public static HighScorePresenter createPresenter(Context context, IHighScoreView view) {
        return new HighScorePresenter(context, view);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
}
