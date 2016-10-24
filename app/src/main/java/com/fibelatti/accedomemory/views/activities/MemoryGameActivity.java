package com.fibelatti.accedomemory.views.activities;

import android.content.Context;
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
import com.fibelatti.accedomemory.adapters.MemoryGameAdapter;
import com.fibelatti.accedomemory.helpers.GameHelper;
import com.fibelatti.accedomemory.utils.ConfigurationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryGameActivity extends AppCompatActivity {
    private Context context;
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
        adapter = new MemoryGameAdapter(this, GameHelper.createGame(context));

        setUpLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    private void setUpLayout() {
        setContentView(R.layout.activity_memory_game);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayShowTitleEnabled(false);

        setUpRecyclerView();
    }

    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, ConfigurationUtils.getColumnsBasedOnTypeAndOrientation(context)));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
