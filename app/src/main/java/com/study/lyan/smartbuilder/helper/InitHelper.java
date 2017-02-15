package com.study.lyan.smartbuilder.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by Lyan on 17/2/9.
 */

public class InitHelper {
    private List<String> mTitle;//标题
    private static Context mContext;
    private Typeface fontStyle;//字体样式
    private InitHelper(){
        init();
    }
    private static class SingleTon{
        public static InitHelper instance = new InitHelper();
    }

    /**
     * 初始化
     * @return
     */
    public static void init(Context context){
        mContext = context.getApplicationContext();
        Logger.init("智能管家").logLevel(LogLevel.FULL);//初始化Log工具
        SharePreferenceHelper.init(mContext);//配置数据储存帮助类
        CrashReport.initCrashReport(mContext, StaticClass.BUGLY_APP_ID,
                StaticClass.BUGLY_IS_DEBUG);//配置bugly
        Bmob.initialize(mContext, StaticClass.BMOB_APP_ID);//初始化Bmob

    }
    /**
     * 获取单例
     * @return
     */
    public static InitHelper getInstance(){
        return SingleTon.instance;
    }

    /**
     * 初始化
     */
    private void init(){
        initTitle();
        initTypeFace();
    }
    /**
     * 初始化标题
     */
    private void initTitle(){
        mTitle = new ArrayList<>();
        Resources resources = mContext.getResources();
        mTitle.add(resources.getString(R.string.butler));
        mTitle.add(resources.getString(R.string.wechat));
        mTitle.add(resources.getString(R.string.girl));
        mTitle.add(resources.getString(R.string.user));
    }

    /**
     * 返回标题
     * @return
     */
    public List<String> getTitle(){
        return mTitle;
    }

    /**
     * 初始化字体样式
     */
    private void initTypeFace(){
        fontStyle = Typeface.createFromAsset(mContext.getAssets(),"fonts/YYG.TTF");
    }
    /**
     * 获取字体样式
     */
    public Typeface getTypeFace(){
        return fontStyle;
    }
}
