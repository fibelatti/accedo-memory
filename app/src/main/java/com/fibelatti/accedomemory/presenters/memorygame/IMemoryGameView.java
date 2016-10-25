package com.fibelatti.accedomemory.presenters.memorygame;

import com.fibelatti.accedomemory.models.Card;

import java.util.List;

public interface IMemoryGameView {
    void setPresenter(IMemoryGamePresenter presenter);

    void onNewGame(List<Card> cardList);
}
