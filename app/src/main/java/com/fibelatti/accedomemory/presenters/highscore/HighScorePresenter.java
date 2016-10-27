package com.fibelatti.accedomemory.presenters.highscore;

import android.content.Context;

import com.fibelatti.accedomemory.Constants;
import com.fibelatti.accedomemory.db.Database;
import com.fibelatti.accedomemory.models.HighScore;

public class HighScorePresenter
    implements IHighScorePresenter {

    private Context context;
    private IHighScoreView view;

    private HighScorePresenter(Context context, IHighScoreView view) {
        this.context = context;
        this.view = view;
    }

    public static HighScorePresenter createPresenter(Context context, IHighScoreView view) {
        return new HighScorePresenter(context, view);
    }

    @Override
    public void onCreate() {
        fetchData();
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
    }

    @Override
    public void fetchData() {
        if (view != null) view.onDataFetched(Database.highScoreDao.fetchTopHighScores(Constants.HIGH_SCORE_QUANTITY));
    }

    @Override
    public boolean saveNewHighScore(String name, int score) {
        return Database.highScoreDao.saveHighScore(new HighScore(name, score));
    }

}
