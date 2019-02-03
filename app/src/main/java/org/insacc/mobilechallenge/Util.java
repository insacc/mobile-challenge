package org.insacc.mobilechallenge;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by can on 31.07.2017.
 */

public class Util {
    /**
     * Converts the given dp value @param dp to pixel value.
     * @param dp dp value which needs to be converted to pixel value.
     * @param context the context of the activity
     * @return the pixel value for the given dp value.
     */
    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
