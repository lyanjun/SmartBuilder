package com.study.lyan.smartbuilder.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.study.lyan.smartbuilder.R;

/**
 * Created by Lyan on 17/2/11.
 */

public class CustomDialog extends Dialog{

    /**
     * 默认宽高
     * @param context
     * @param layout
     * @param style
     */
    public CustomDialog(Context context,@LayoutRes int layout, @StyleRes int style ){
        this(context, layout,WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, style, Gravity.CENTER);
    }
    /**
     * 默认宽高
     * @param context
     * @param layout
     * @param style
     */
    public CustomDialog(Context context,@LayoutRes int layout, @StyleRes int style,int gravity ){
        this(context, layout,WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, style, gravity);
    }
    /**
     * 获取实例
     * @param context
     * @param layout
     * @param style
     * @param width
     * @param height
     * @param gravity
     * @param anim
     */
    public CustomDialog(Context context,@LayoutRes int layout, @StyleRes int style,
                        int width, int height, int gravity, @StyleRes  int anim){
        super(context, style);
        //设置属性
        setContentView(layout);
        setDialogWindow(width,height,gravity,anim);

    }

    /**
     * 定制宽高
     * @param context
     * @param layout
     * @param width
     * @param height
     * @param style
     * @param gravity
     */
    public CustomDialog(Context context,@LayoutRes int layout,int width, int height, @StyleRes int style,  int gravity){
        this(context,layout,style,width, height,gravity,R.style.pop_anim_sytle);
    }
    /**
     * 设置效果
     * @param width
     * @param height
     * @param gravity
     * @param anim
     */
    private void setDialogWindow(int width, int height, int gravity, @StyleRes  int anim){
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;//设置宽
        layoutParams.height = height;//设置高
        layoutParams.gravity = gravity;//设置位置
        window.setAttributes(layoutParams);//设置属性
        window.setWindowAnimations(anim);//设置动画
    }

}
