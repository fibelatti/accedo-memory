package com.fibelatti.accedomemory.helpers;

import com.fibelatti.accedomemory.models.Card;

import java.util.List;

public interface IGameHelperListener {
    void onCurrentScoreChanged(int currentScore);

    void onRound(List<Card> cardList);

    void onNewHighScore(int rank, int score);

    void onGameFinished(int rank, int score);
}
