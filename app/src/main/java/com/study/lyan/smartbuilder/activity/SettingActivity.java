package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;

import com.study.lyan.smartbuilder.R;

/**
 * Created by Lyan on 17/2/9.
 * 设置界面
 */

public class SettingActivity extends BaseActivity{
    /**
     * 设置界面布局
     * @return
     */
    @Override
    protected int addLayout() {
        return R.layout.activity_setting;
    }
    /***
     * 初始化操作
     * @param savedInstanceState
     */
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

    }
    /**
     * 显示标题栏中的返回按钮
     * @return
     */
    @Override
    protected boolean showBackHomeButton() {
        return true;
    }
}
