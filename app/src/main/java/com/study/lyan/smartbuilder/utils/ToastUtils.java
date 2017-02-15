package com.study.lyan.smartbuilder.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Lyan on 17/2/9.
 */

public class ToastUtils {
    /**
     * 短吐司
     * @param context
     * @param message
     */
    public static void shortToast(Context context,String message){
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长吐司
     * @param context
     * @param message
     */
    public static void longToast(Context context,String message){
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
