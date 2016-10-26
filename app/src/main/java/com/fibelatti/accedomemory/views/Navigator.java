package com.fibelatti.accedomemory.views;

import android.app.Activity;
import android.content.Intent;

import com.fibelatti.accedomemory.views.activities.HighScoreActivity;

public class Navigator {
    Activity activity;

    public Navigator(Activity activity) {
        this.activity = activity;
    }

    public void goToHighScores() {
        Intent intent = new Intent(activity, HighScoreActivity.class);
        activity.startActivity(intent);
    }
}
