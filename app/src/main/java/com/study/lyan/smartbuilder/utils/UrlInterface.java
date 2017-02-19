package com.study.lyan.smartbuilder.utils;

/**
 * Created by Lyan on 17/2/12.
 * 网络接口
 */

public interface UrlInterface {
    //快递Key
    //http://v.juhe.cn/exp/index?key=b1e8d754748078858fcae9bab521e908&com=sf&no=575677355677
    String COURIER_KEY = "b1e8d754748078858fcae9bab521e908";
    String COURIER_URL = "http://v.juhe.cn/exp/index";
    //手机号码归属地5868c341b2a2c8a607f27cc04da4ef35
    //http://apis.juhe.cn/mobile/get?phone=13429667914&key=5868c341b2a2c8a607f27cc04da4ef35
    String PHONE_KEY = "5868c341b2a2c8a607f27cc04da4ef35";
    String PHONE_URL = "http://apis.juhe.cn/mobile/get";
    //问答机器人key
    //http://op.juhe.cn/robot/index
    String CHAT_LIST_KEY = "0ade3fdad1224b2306916f9525ad7b9b";
    String CHAT_LIST_URL = "http://op.juhe.cn/robot/index";
    //微信精选key
    String WECHAT_KEY = "1841b22916f40e3258794a668872abda";
    String WECHAT_URL = "http://v.juhe.cn/weixin/query";
    int START_PAGE = 1;//起始页
    int EVERY_COUNT = 20;//每页数据的个数
    //福利
    String GANK_GIRL = "http://gank.io/api/data/%s/%s/%s";
    //语音Key
    String VOICE_KEY = "58a7a017";
    //短信Action
    String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    String SYSTEM_DIALOGS_RESON_KEY = "reason";
    String SYSTEM_DIALOGS_HOME_KEY = "homekey";
    String SYSTEM_DIALOGS_RECENT_APPS = "recentapps";
}
