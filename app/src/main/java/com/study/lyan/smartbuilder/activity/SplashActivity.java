package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.helper.InitHelper;
import com.study.lyan.smartbuilder.helper.SharePreferenceHelper;
import com.study.lyan.smartbuilder.utils.StaticClass;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * 欢迎界面
 * Created by Lyan on 17/2/10.
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_splash)
    TextView tvSplash;
    private SharePreferenceHelper shareHelper;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (isFirst()) {//判断是否为第一次运行
                        shareHelper.put(StaticClass.SHARE_IS_FIRST, false).commit();
                        //跳转引导页
                        startActivity(startTo(GuideActivity.class));
                    } else {
                        //跳转主界面
                        startActivity(startTo(LoginActivity.class));
                    }
                    finish();
                    break;
            }
            return false;
        }
    });

    @Override
    protected int addLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        getSupportActionBar().hide();//隐藏标题栏
        shareHelper = SharePreferenceHelper.getInstance();
        //延迟操作
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        //设置字体
        tvSplash.setTypeface(InitHelper.getInstance().getTypeFace());
        
    }

    @Override
    protected boolean showBackHomeButton() {
        return false;
    }

    /**
     * 判断是否为第一次运行
     *
     * @return
     */
    private boolean isFirst() {
        boolean result = (boolean) shareHelper.get(StaticClass.SHARE_IS_FIRST, true);
        return result;
    }

    //禁止返回键
    @Override
    public void onBackPressed() {}

}
