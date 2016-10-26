package com.fibelatti.accedomemory;

import com.fibelatti.accedomemory.helpers.GameHelper;
import com.fibelatti.accedomemory.helpers.IGameHelper;
import com.fibelatti.accedomemory.models.Card;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameHelperTest {
    @Test
    public void gameHasSixteenCardsAndEightPairs() throws Exception {
        IGameHelper gameHelper = GameHelper.getInstance();
        List<Card> currentGame = gameHelper.createGame();
        HashMap<Integer, Integer> pairs = new HashMap<>();

        assertEquals(16, currentGame.size());

        for (Card card : currentGame) {
            int key = card.getDrawableId();
            int currentValue = pairs.get(key) == null ? 0 : pairs.get(key);

            pairs.put(key, currentValue + 1);
        }

        boolean valid = true;

        for (Integer value : pairs.values()) {
            if (value != 2) valid = false;
        }

        assertEquals(true, valid);
    }

    @Test
    public void allCardsAreFaceDownOnNewGame() throws Exception {
        IGameHelper gameHelper = GameHelper.getInstance();
        List<Card> currentGame = gameHelper.createGame();

        boolean valid = true;

        for (Card card : currentGame) {
            if (card.isFaceUp()) valid = false;
        }

        assertEquals(true, valid);
    }
}