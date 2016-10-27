package com.fibelatti.accedomemory.views.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.helpers.GameHelper;
import com.fibelatti.accedomemory.models.Card;
import com.fibelatti.accedomemory.utils.ConfigurationUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryGameAdapter extends BaseAdapter {

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
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.cardList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.cardList.get(i).getDrawableId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Card card = this.cardList.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.list_item_card, null);

        int height = viewGroup.getMeasuredHeight() / ConfigurationUtils.getRowsBasedOnTypeAndOrientation(context);

        view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        CardViewHolder holder = new CardViewHolder(view, i);
        holder.image.setImageDrawable(ContextCompat.getDrawable(context, card.getDrawableId()));

        if (card.isFaceDown()) {
            holder.setCardFaceDown(false);
        } else if (card.isFaceUp()) {
            holder.setCardFaceUp(false);
        } else if (card.isFaceMatched()) {
            holder.setCardMatched();
        }

        return view;
    }

    public class CardViewHolder implements View.OnClickListener {
        @BindView(R.id.card_group)
        RelativeLayout cardGroup;
        @BindView(R.id.card_front_image)
        ImageView image;
        @BindView(R.id.card_front)
        CardView cardFront;
        @BindView(R.id.card_back)
        CardView cardBack;

        int index;

        final AnimatorSet setFlipOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_out);
        final AnimatorSet setFlipIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_in);

        public CardViewHolder(View view, int index) {
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

            this.index = index;
        }

        @Override
        public void onClick(View view) {
            if (GameHelper.getInstance().checkCard(index)) {
                setCardFaceUp(true);
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
            }
        }

        public void setCardMatched() {
            cardFront.setAlpha(1.0f);
            cardBack.setAlpha(0.0f);
        }
    }
}
