package com.fibelatti.accedomemory.views.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.helpers.AlertDialogHelper;
import com.fibelatti.accedomemory.models.Card;
import com.fibelatti.accedomemory.presenters.memorygame.IMemoryGamePresenter;
import com.fibelatti.accedomemory.presenters.memorygame.IMemoryGameView;
import com.fibelatti.accedomemory.presenters.memorygame.MemoryGamePresenter;
import com.fibelatti.accedomemory.utils.ConfigurationUtils;
import com.fibelatti.accedomemory.views.Navigator;
import com.fibelatti.accedomemory.views.adapters.MemoryGameAdapter;
import com.fibelatti.accedomemory.views.fragments.HighScoreInputFragment;
import com.fibelatti.accedomemory.views.fragments.IHighScoreInputFragmentListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryGameActivity extends AppCompatActivity
        implements IMemoryGameView {
    private Context context;
    private IMemoryGamePresenter presenter;
    private MemoryGameAdapter adapter;
    private AlertDialogHelper dialogHelper;
    private Navigator navigator;

    //region View bindings
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout layout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.text_score)
    TextView textScore;
    @BindView(R.id.grid_view)
    GridView gridView;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        setUpLayout();
        setUpContentView();
        setUpPresenter();

        dialogHelper = new AlertDialogHelper(this);
        navigator = new Navigator(this);

        presenter.onCreate();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_memory_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_game:
                newGame();
                return true;
            case R.id.action_high_scores:
                navigator.goToHighScores();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameChanged(List<Card> cardList) {
        if (adapter != null) adapter.setCardList(cardList);
    }

    @Override
    public void onCurrentScoreChanged(int currentScore) {
        textScore.setText(getString(R.string.memory_game_text_score, currentScore));
    }

    @Override
    public void onRound(List<Card> cardList) {
        if (adapter != null) adapter.setCardList(cardList);
    }

    @Override
    public void onNewHighScore(int rank, int score) {
        presenter.newGame();
        navigator.goToHighScores(true, rank, score);
    }

    @Override
    public void onGameFinished(int rank, int score) {
        presenter.newGame();

        dialogHelper.createOkOnlyDialog(
                getString(R.string.memory_game_dialog_title_game_finished),
                getString(R.string.memory_game_dialog_text_game_finished, score, rank),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        navigator.goToHighScores();
                    }
                });
    }

    private void setUpPresenter() {
        this.presenter = MemoryGamePresenter.createPresenter(context, this);
    }

    private void setUpLayout() {
        setContentView(R.layout.activity_memory_game);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);

        textScore.setText(getString(R.string.memory_game_text_score, 0));
    }

    private void setUpContentView() {
        adapter = new MemoryGameAdapter(this);

        gridView.setNumColumns(ConfigurationUtils.getColumnsBasedOnTypeAndOrientation(context));
        gridView.setAdapter(adapter);
    }


    private void newGame() {
        dialogHelper.createYesNoDialog(
                getString(R.string.memory_game_dialog_title_new_game),
                getString(R.string.memory_game_dialog_text_new_game),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.newGame();
                    }
                },
                null);
    }
}
