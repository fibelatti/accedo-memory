package com.fibelatti.accedomemory.presenters.highscore;

import com.fibelatti.accedomemory.models.HighScore;

import java.util.List;

public interface IHighScoreView {
    void onDataFetched(List<HighScore> highScoreList);
}
