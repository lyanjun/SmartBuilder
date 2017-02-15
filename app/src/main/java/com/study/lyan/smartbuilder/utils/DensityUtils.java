package com.study.lyan.smartbuilder.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Lyan on 17/2/11.
 */

public class DensityUtils {

    /**
     * dp转px
     * @param context
     * @param values
     * @return
     */
    public static int dp2px(Context context,int values){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                values,context.getResources().getDisplayMetrics());
    }
    /**
     * sp转px
     * @param context
     * @param values
     * @returns
     */
    public static int sp2px(Context context,int values){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                values,context.getResources().getDisplayMetrics());
    }
}
