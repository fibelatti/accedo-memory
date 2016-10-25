package com.fibelatti.accedomemory.helpers;

import android.os.Handler;

import com.fibelatti.accedomemory.Constants;
import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.models.Card;
import com.fibelatti.accedomemory.models.MatchResult;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameHelper {
    private static final Object syncLock = new Object();
    private static GameHelper instance;

    private List<IGameHelperListener> listeners = new ArrayList<>();

    private List<Card> currentGame = new ArrayList<>(16);
    private int currentScore = 0;
    private boolean isMatched = false;
    private Card firstCard, secondCard;

    private Handler handler = new Handler();

    private GameHelper() {
        BusHelper.getInstance().getBus().register(this);
    }

    public static GameHelper getInstance() {
        if (instance == null) {
            synchronized (syncLock) {
                if (instance == null)
                    instance = new GameHelper();
            }
        }
        return instance;
    }

    public void addListener(IGameHelperListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IGameHelperListener listener) {
        this.listeners.remove(listener);
    }

    public List<Card> createGame() {
        currentGame = new ArrayList<>(16);
        List<Integer> images = new ArrayList<>(16);

        for (int i = 0; i < 2; i++) {
            images.add(R.drawable.card_cc);
            images.add(R.drawable.card_cloud);
            images.add(R.drawable.card_console);
            images.add(R.drawable.card_multiscreen);
            images.add(R.drawable.card_remote);
            images.add(R.drawable.card_tablet);
            images.add(R.drawable.card_tv);
            images.add(R.drawable.card_vr);
        }

        Collections.shuffle(images);

        for (int index : images) {
            currentGame.add(new Card(index));
        }

        return currentGame;
    }

    @Subscribe
    public void cardFlipped(Card currentCard) {
        if (firstCard == null) {
            firstCard = currentCard;
        } else if (secondCard == null) {
            secondCard = currentCard;
            isMatched = firstCard.getDrawableId() == secondCard.getDrawableId();
            currentScore += isMatched ? Constants.SCORE_SUCCESS : Constants.SCORE_FAILURE;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    BusHelper.getInstance().getBus().post(new MatchResult(isMatched));
                    notifyCurrentScoreChanged();

                    isMatched = false;
                    firstCard = null;
                    secondCard = null;
                }
            };

            handler.postDelayed(runnable, isMatched ? 0 : Constants.ROUND_DELAY);
        }
    }

    private void notifyCurrentScoreChanged() {
        for (IGameHelperListener listener : listeners) {
            listener.onCurrentScoreChanged(currentScore);
        }
    }
}
