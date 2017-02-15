package com.study.lyan.smartbuilder.application;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.helper.InitHelper;

/**
 * Created by Lyan on 17/2/9.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        InitHelper.init(this);//初始化配置
    }
}
