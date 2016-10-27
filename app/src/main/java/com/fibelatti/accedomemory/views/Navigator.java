package com.fibelatti.accedomemory.views;

import android.app.Activity;
import android.content.Intent;

import com.fibelatti.accedomemory.Constants;
import com.fibelatti.accedomemory.views.activities.HighScoreActivity;

public class Navigator {
    Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public void goToHighScores() {
        goToHighScores(false, 0, 0);
    }

    public void goToHighScores(boolean shouldShowInputDialog, int rank, int score) {
        Intent intent = new Intent(activity, HighScoreActivity.class);

        if (shouldShowInputDialog) {
            intent.putExtra(Constants.INTENT_EXTRA_SHOULD_SHOW_DIALOG, shouldShowInputDialog);
            intent.putExtra(Constants.INTENT_EXTRA_RANK, rank);
            intent.putExtra(Constants.INTENT_EXTRA_SCORE, score);
        }

        activity.startActivity(intent);
    }
}
