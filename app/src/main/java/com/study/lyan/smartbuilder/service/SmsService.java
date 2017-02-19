package com.study.lyan.smartbuilder.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.listener.SmsReceiver;
import com.study.lyan.smartbuilder.utils.UrlInterface;

/**
 * 监听短信服务
 */
public class SmsService extends Service {

    private SmsReceiver smsReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.t("SmsService").i("初始化短信监听服务");
        //注册广播
        smsReceiver = new SmsReceiver();//广播接收器实例
        IntentFilter filter = new IntentFilter();//过滤器
        filter.addAction(UrlInterface.SMS_ACTION);//过滤动作
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);//监听Home键
        filter.setPriority(Integer.MAX_VALUE);//设置级别
        registerReceiver(smsReceiver,filter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Logger.t("SmsService").i("关闭短信监听服务");
        unregisterReceiver(smsReceiver);//反注册
        super.onDestroy();
    }
}
