package com.fibelatti.accedomemory.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.fibelatti.accedomemory.R;

public abstract class ConfigurationUtils {
    public static int getColumnsBasedOnTypeAndOrientation(Context context) {
        Resources res = context.getResources();

        boolean isTablet = (res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        int orientation = res.getConfiguration().orientation;

        if (isTablet) {
            return res.getInteger(R.integer.game_columns_quantity_tablet);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return res.getInteger(R.integer.game_columns_quantity_phone_portrait);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return res.getInteger(R.integer.game_columns_quantity_phone_landscape);
        }

        return 0;
    }

    public static int getRowsBasedOnTypeAndOrientation(Context context) {
        Resources res = context.getResources();

        boolean isTablet = (res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        int orientation = res.getConfiguration().orientation;

        if (isTablet) {
            return res.getInteger(R.integer.game_rows_quantity_tablet);
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return res.getInteger(R.integer.game_rows_quantity_phone_portrait);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return res.getInteger(R.integer.game_rows_quantity_phone_landscape);
        }

        return 0;
    }
}
