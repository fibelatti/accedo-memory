package com.fibelatti.accedomemory.helpers;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.fibelatti.accedomemory.R;
import com.fibelatti.accedomemory.models.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameHelper {
    public static List<Card> createGame(Context context) {
        List<Card> cards = new ArrayList<>(16);
        List<Integer> images = new ArrayList<>(16);

        for (int i = 0; i < 2; i++) {
            images.add(R.drawable.card_cc);
            images.add(R.drawable.card_cloud);
            images.add(R.drawable.card_console);
            images.add(R.drawable.card_multiscreen);
            images.add(R.drawable.card_remote);
            images.add(R.drawable.card_tablet);
            images.add(R.drawable.card_tv);
            images.add(R.drawable.card_vr);
        }

        Collections.shuffle(images);

        for (int index : images) {
            cards.add(new Card(ContextCompat.getDrawable(context, index)));
        }

        return cards;
    }
}
