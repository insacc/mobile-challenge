package org.insacc.mobilechallenge;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by can on 31.07.2017.
 */

public class Util {

    public static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
