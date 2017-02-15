package com.study.lyan.smartbuilder.view;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.study.lyan.smartbuilder.R;

/**
 * Created by Lyan on 17/2/11.
 */

public class LoginDialog extends CustomDialog{

    private TextView hintText;

    public LoginDialog(Context context){
        this(context,R.layout.dialog_loding,R.style.Theme_dialog);
    }
    private LoginDialog(Context context, @LayoutRes int layout, @StyleRes int style) {
        super(context, layout, style);
        setCanceledOnTouchOutside(false);
        hintText = (TextView)findViewById(R.id.hint_text);
    }
    /**
     * 设置提示文字
     * @param hint
     */
    public void setHintText(@NonNull String hint){
        hintText.setText(hint);
    }
}
