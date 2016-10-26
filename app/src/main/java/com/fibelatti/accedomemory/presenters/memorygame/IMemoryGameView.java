package com.fibelatti.accedomemory.presenters.memorygame;

import com.fibelatti.accedomemory.models.Card;

import java.util.List;

public interface IMemoryGameView {
    void onGameChanged(List<Card> cardList);

    void onCurrentScoreChanged(int currentScore);

    void onRound(List<Card> cardList);

    void onNewHighScore(int rank, int score);

    void onGameFinished(int rank, int score);
}
