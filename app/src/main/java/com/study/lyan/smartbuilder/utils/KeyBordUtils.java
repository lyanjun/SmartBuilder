package com.study.lyan.smartbuilder.utils;

import android.app.Activity;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * Created by Lyan on 17/2/12.
 */

public class KeyBordUtils {
    /** ==== 隐藏系统键盘 ======*/
    //用这个方法关闭系统键盘就不会出现光标消失的问题了
    public static void hideSoftInputMethod(Activity activity ,EditText ed){

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String methodName = null;
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if(currentVersion >= 16){
            // 4.2
            methodName = "setShowSoftInputOnFocus";  //
        }else if(currentVersion >= 14){
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if(methodName == null){
            //最低级最不济的方式，这个方式会把光标给屏蔽
            ed.setInputType(InputType.TYPE_NULL);
        }else{
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
