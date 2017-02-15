package com.study.lyan.smartbuilder.utils;

import android.widget.TextView;

/**
 * Created by Lyan on 17/2/11.
 */

public class GetViewTextUtils {

    /**
     * 获取文本
     * @param view
     * @param <T>
     * @return
     */
    public static <T extends TextView> String getTextFromView(T view){
        return view.getText().toString();
    }

    /**
     * 获取文本去掉开头和结尾的空格
     * @param view
     * @param <T>
     * @return
     */
    public static <T extends TextView> String getTextTrim(T view){
        return view.getText().toString().trim();
    }
}
