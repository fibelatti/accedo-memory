package com.fibelatti.accedomemory.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.fibelatti.accedomemory.Constants;
import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.models.HighScore;
import com.fibelatti.accedomemory.presenters.highscore.HighScorePresenter;
import com.fibelatti.accedomemory.presenters.highscore.IHighScorePresenter;
import com.fibelatti.accedomemory.presenters.highscore.IHighScoreView;
import com.fibelatti.accedomemory.views.adapters.HighScoreAdapter;
import com.fibelatti.accedomemory.views.fragments.HighScoreInputFragment;
import com.fibelatti.accedomemory.views.fragments.IHighScoreInputFragmentListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighScoreActivity extends AppCompatActivity
        implements IHighScoreView, IHighScoreInputFragmentListener {
    private Context context;
    private IHighScorePresenter presenter;
    private HighScoreAdapter adapter;

    //region View bindings
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout layout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list_view)
    ListView listView;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        setUpLayout();
        setUpContentView();
        setUpPresenter();

        presenter.onCreate();

        fetchDataFromIntent();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataFetched(List<HighScore> highScoreList) {
        if (adapter != null) adapter.setHighScoreList(highScoreList);
    }

    @Override
    public void onHighScore(String name, int score) {
        presenter.saveNewHighScore(name, score);
        presenter.fetchData();
    }

    private void setUpPresenter() {
        this.presenter = HighScorePresenter.createPresenter(context, this);
    }

    private void setUpLayout() {
        setContentView(R.layout.activity_high_score);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpContentView() {
        adapter = new HighScoreAdapter(this);
        listView.setAdapter(adapter);
    }

    private void fetchDataFromIntent() {
        if (getIntent().hasExtra(Constants.INTENT_EXTRA_SHOULD_SHOW_DIALOG)) {
            boolean shouldShowDialog = (Boolean) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_SHOULD_SHOW_DIALOG);
            int rank  = (Integer) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_RANK);
            int score = (Integer) getIntent().getSerializableExtra(Constants.INTENT_EXTRA_SCORE);

            if (shouldShowDialog) {
                DialogFragment inputHighScoreFragment = HighScoreInputFragment.newInstance(score, rank);
                inputHighScoreFragment.show(getSupportFragmentManager(), HighScoreInputFragment.TAG);
            }

            getIntent().removeExtra(Constants.INTENT_EXTRA_SHOULD_SHOW_DIALOG);
            getIntent().removeExtra(Constants.INTENT_EXTRA_RANK);
            getIntent().removeExtra(Constants.INTENT_EXTRA_SCORE);
        }
    }
}
