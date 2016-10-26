package com.fibelatti.accedomemory.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fibelatti.accedomemory.Constants;
import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.models.HighScore;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighScoreAdapter extends BaseAdapter {

    private Context context;
    private List<HighScore> highScoreList;

    public HighScoreAdapter(Context context) {
        this.context = context;
        this.highScoreList = new ArrayList<>();
    }

    public void setHighScoreList(List<HighScore> highScoreList) {
        this.highScoreList.clear();
        this.highScoreList.addAll(highScoreList);

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.highScoreList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.highScoreList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.highScoreList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final HighScore highScore = this.highScoreList.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.list_item_high_score, null);

        int height = (viewGroup.getMeasuredHeight()
                - context.getResources().getDimensionPixelSize(R.dimen.subtitle_height))
                / Constants.HIGH_SCORE_QUANTITY;

        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        HighScoreViewHolder holder = new HighScoreViewHolder(view);
        holder.textRank.setText(context.getString(R.string.high_score_column_text_rank, i + 1));
        holder.textName.setText(highScore.getPlayerName());
        holder.textScore.setText(highScore.getPlayerScoreString());

        return view;
    }

    public class HighScoreViewHolder {
        @BindView(R.id.text_rank)
        TextView textRank;
        @BindView(R.id.text_name)
        TextView textName;
        @BindView(R.id.text_score)
        TextView textScore;

        public HighScoreViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}