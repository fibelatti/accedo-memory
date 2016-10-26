package com.fibelatti.accedomemory.helpers;

import android.os.Handler;

import com.fibelatti.accedomemory.Constants;
import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.db.Database;
import com.fibelatti.accedomemory.models.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameHelper implements IGameHelper {
    private static final Object syncLock = new Object();
    private static IGameHelper instance;

    private List<IGameHelperListener> listeners = new ArrayList<>();
    private List<IGameHelperResultListener> resultListeners = new ArrayList<>();

    private List<Card> currentGame;
    private int currentScore;
    private int currentMatches;
    private int cardsClicked;
    private boolean isMatched;
    private int firstCardIndex;
    private int secondCardIndex;

    private Handler handler = new Handler();

    private GameHelper() {
    }

    public static IGameHelper getInstance() {
        if (instance == null) {
            synchronized (syncLock) {
                if (instance == null)
                    instance = new GameHelper();
            }
        }
        return instance;
    }

    @Override
    public void addListener(IGameHelperListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IGameHelperListener listener) {
        listeners.remove(listener);
    }

    @Override
    public List<Card> getCurrentGame() {
        return currentGame != null ? currentGame : createGame();
    }

    @Override
    public int getCurrentScore() {
        return currentScore;
    }

    @Override
    public List<Card> createGame() {
        currentGame = new ArrayList<>(16);

        initVariables();

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

    @Override
    public boolean addResultListener(IGameHelperResultListener listener, int index) {
        cardsClicked++;
        
        if (cardsClicked <= 2 && index != -1 && currentGame.get(index).isFaceDown()) {
            resultListeners.add(listener);

            if (firstCardIndex == -1) {
                firstCardIndex = index;
                currentGame.get(index).setStatusFaceUp();
            } else if (secondCardIndex == -1) {
                secondCardIndex = index;
                currentGame.get(index).setStatusFaceUp();
                isMatched = currentGame.get(firstCardIndex).getDrawableId() == currentGame.get(secondCardIndex).getDrawableId();

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        notifyMatchResult(isMatched);

                        setCurrentScore(currentScore + (isMatched ? Constants.SCORE_SUCCESS : Constants.SCORE_FAILURE));

                        if (isMatched) {
                            currentMatches++;
                            currentGame.get(firstCardIndex).setStatusMatched();
                            currentGame.get(secondCardIndex).setStatusMatched();
                            checkGameState();
                        } else {
                            currentGame.get(firstCardIndex).setStatusFaceDown();
                            currentGame.get(secondCardIndex).setStatusFaceDown();
                        }

                        isMatched = false;
                        firstCardIndex = -1;
                        secondCardIndex = -1;
                    }
                };

                handler.postDelayed(runnable, isMatched ? 0 : Constants.ROUND_DELAY);
            }

            return true;
        }
        
        return false;
    }

    private void initVariables() {
        resultListeners.clear();
        setCurrentScore(0);
        currentMatches = 0;
        cardsClicked = 0;
        isMatched = false;
        firstCardIndex = -1;
        secondCardIndex = -1;
    }

    private void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
        notifyCurrentScoreChanged();
    }

    private void checkGameState() {
        if (currentMatches == Constants.PAIRS_QUANTITY) {
            final int currentScoreRank = Database.highScoreDao.fetchAllHighScoresHigherThan(currentScore).size() + 1;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (currentScoreRank <= Constants.HIGH_SCORE_QUANTITY) {
                        notifyNewHighScore(currentScoreRank);
                    } else {
                        notifyGameFinished(currentScoreRank);
                    }
                }
            }, Constants.ROUND_DELAY);
        }
    }

    private void notifyCurrentScoreChanged() {
        for (IGameHelperListener listener : listeners) {
            listener.onCurrentScoreChanged(currentScore);
        }
    }

    private void notifyNewHighScore(int rank) {
        for (IGameHelperListener listener : listeners) {
            listener.onNewHighScore(rank, currentScore);
        }
    }

    private void notifyGameFinished(int rank) {
        for (IGameHelperListener listener : listeners) {
            listener.onGameFinished(rank, currentScore);
        }
    }

    private void notifyMatchResult(boolean isMatched) {
        for (IGameHelperResultListener listener : resultListeners) {
            listener.onResult(isMatched);
        }

        cardsClicked = 0;
        resultListeners.clear();
    }
}
