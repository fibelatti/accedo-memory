package com.fibelatti.accedomemory.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.models.Card;
import com.fibelatti.accedomemory.utils.ConfigurationUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemoryGameAdapter extends RecyclerView.Adapter<MemoryGameAdapter.CardViewHolder> {

    private Context context;
    private List<Card> cardList;

    public MemoryGameAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
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
        holder.image.setImageDrawable(card.getImage());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.card_front_image)
        public ImageView image;
        @BindView(R.id.card_front)
        public CardView cardFront;
        @BindView(R.id.card_back)
        public CardView cardBack;

        final AnimatorSet setFlipOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_out);
        final AnimatorSet setFlipIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_in);

        public CardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Card card = cardList.get(getAdapterPosition());

            if (card.isFaceDown()) {
                setFlipOut.setTarget(cardBack);
                setFlipIn.setTarget(cardFront);
                setFlipOut.start();
                setFlipIn.start();

                card.setStatusFaceUp();
            } else {
                setFlipOut.setTarget(cardFront);
                setFlipIn.setTarget(cardBack);
                setFlipOut.start();
                setFlipIn.start();

                card.setStatusFaceDown();
            }
        }
    }
}
