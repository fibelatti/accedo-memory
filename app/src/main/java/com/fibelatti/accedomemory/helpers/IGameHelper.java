package com.fibelatti.accedomemory.helpers;

import com.fibelatti.accedomemory.models.Card;

import java.util.List;

public interface IGameHelper {
    void addListener(IGameHelperListener listener);

    void removeListener(IGameHelperListener listener);

    List<Card> getCurrentGame();

    int getCurrentScore();

    List<Card> createGame();

    boolean checkCard(int index);
}
