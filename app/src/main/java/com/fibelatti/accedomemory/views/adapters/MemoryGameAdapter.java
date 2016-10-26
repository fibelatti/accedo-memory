package com.fibelatti.accedomemory.views.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.helpers.GameHelper;
import com.fibelatti.accedomemory.helpers.IGameHelperResultListener;
import com.fibelatti.accedomemory.models.Card;
import com.fibelatti.accedomemory.utils.ConfigurationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryGameAdapter extends RecyclerView.Adapter<MemoryGameAdapter.CardViewHolder> {
    private Context context;
    private List<Card> cardList;

    public MemoryGameAdapter(Context context) {
        this.context = context;
        this.cardList = new ArrayList<>();
    }

    public void setCardList(List<Card> cardList) {
        this.cardList.clear();
        this.cardList.addAll(cardList);
        notifyDataSetChanged();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_card, parent, false);

        int height = (parent.getMeasuredHeight()
                - context.getResources().getDimensionPixelSize(R.dimen.activity_memory_game_score_layout_height)
                - (context.getResources().getDimensionPixelSize(R.dimen.card_margin) * 4))
                / ConfigurationUtils.getRowsBasedOnTypeAndOrientation(context);

        itemView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Card card = cardList.get(position);
        holder.image.setImageDrawable(ContextCompat.getDrawable(context, card.getDrawableId()));

        if (card.isFaceDown()) {
            holder.setCardFaceDown(false);
        } else if (card.isFaceUp()) {
            holder.setCardFaceUp(false);
        } else if (card.isFaceMatched()) {
            holder.setCardMatched(false);
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, IGameHelperResultListener {
        @BindView(R.id.card_group)
        RelativeLayout cardGroup;
        @BindView(R.id.card_front_image)
        ImageView image;
        @BindView(R.id.card_front)
        CardView cardFront;
        @BindView(R.id.card_back)
        CardView cardBack;

        final AnimatorSet setFlipOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_out);
        final AnimatorSet setFlipIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_in);
        final AnimatorSet setCardElevationDown = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.elevate_down);

        public CardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (GameHelper.getInstance().addResultListener(this, getAdapterPosition())) {
                setCardFaceUp(true);
            }
        }

        @Override
        public void onResult(boolean isMatched) {
            if (isMatched) {
                setCardMatched(true);
            } else {
                setCardFaceDown(true);
            }
        }

        public void setCardFaceUp(boolean animate) {
            if (animate) {
                setFlipOut.setTarget(cardBack);
                setFlipIn.setTarget(cardFront);
                setFlipOut.start();
                setFlipIn.start();
            } else {
                cardFront.setAlpha(1.0f);
                cardBack.setAlpha(0.0f);
                ViewCompat.setElevation(cardGroup, context.getResources().getDimension(R.dimen.card_elevation_enabled));
            }
        }

        public void setCardFaceDown(boolean animate) {
            if (animate) {
                setFlipOut.setTarget(cardFront);
                setFlipIn.setTarget(cardBack);
                setFlipOut.start();
                setFlipIn.start();
            } else {
                cardFront.setAlpha(0.0f);
                cardBack.setAlpha(1.0f);
                ViewCompat.setElevation(cardGroup, context.getResources().getDimension(R.dimen.card_elevation_enabled));
            }
        }

        public void setCardMatched(boolean animate) {
            if (animate) {
                setCardElevationDown.setTarget(cardGroup);
                setCardElevationDown.start();
            } else {
                cardFront.setAlpha(1.0f);
                cardBack.setAlpha(0.0f);
                ViewCompat.setElevation(cardGroup, context.getResources().getDimension(R.dimen.card_elevation_disabled));
            }
        }
    }
}
