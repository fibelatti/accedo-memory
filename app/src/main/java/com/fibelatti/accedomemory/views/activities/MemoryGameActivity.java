package com.fibelatti.accedomemory.views.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.helpers.AlertDialogHelper;
import com.fibelatti.accedomemory.models.Card;
import com.fibelatti.accedomemory.presenters.memorygame.IMemoryGamePresenter;
import com.fibelatti.accedomemory.presenters.memorygame.IMemoryGameView;
import com.fibelatti.accedomemory.presenters.memorygame.MemoryGamePresenter;
import com.fibelatti.accedomemory.utils.ConfigurationUtils;
import com.fibelatti.accedomemory.views.adapters.MemoryGameAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryGameActivity extends AppCompatActivity
        implements IMemoryGameView {
    private Context context;
    private IMemoryGamePresenter presenter;
    private MemoryGameAdapter adapter;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout layout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        setUpPresenter();
        setUpLayout();
        setUpRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
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
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        recyclerView.setLayoutManager(new GridLayoutManager(this, ConfigurationUtils.getColumnsBasedOnTypeAndOrientation(context)));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(IMemoryGamePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onNewGame(List<Card> cardList) {
        adapter.setCardList(cardList);
    }

    private void setUpPresenter() {
        MemoryGamePresenter.createPresenter(context, this);
    }

    private void setUpLayout() {
        setContentView(R.layout.activity_memory_game);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);
    }

    private void setUpRecyclerView() {
        adapter = new MemoryGameAdapter(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, ConfigurationUtils.getColumnsBasedOnTypeAndOrientation(context)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    private void newGame() {
        AlertDialogHelper dialogHelper = new AlertDialogHelper(this);
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
