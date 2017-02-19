package com.study.lyan.smartbuilder.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.utils.StaticClass;
import com.study.lyan.smartbuilder.utils.UrlInterface;
import com.study.lyan.smartbuilder.view.DispatchLinearLayout;

/**
 * Author LYJ
 * Created on 2017/2/18.
 * Time 14:17
 * 短信监听广播
 */

public class SmsReceiver extends BroadcastReceiver implements View.OnClickListener,
        DispatchLinearLayout.DispatchKeyEventListener{
    private String smsPhone;//发件人
    private String smsContent;//短信内容
    private WindowManager windowManager;//窗口管理器
    private WindowManager.LayoutParams layoutParams;//窗口参数
    private DispatchLinearLayout view;//显示样式
    private TextView tv_phone;//显示发件人
    private TextView tv_content;//显示短信内容
    private Button btn_send_sms;//发送短信
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();
        //只显示最新的短信消息
        if (windowManager != null && view != null){
            if (view.getParent() != null){
                windowManager.removeView(view);
            }
        }
        if (UrlInterface.SMS_ACTION.equals(action)){
            Logger.t("SmsReceiver").i("来短信了");
            //获取短信内容返回一个Object类型的数组
            Object[] objs = (Object[]) intent.getExtras().get("pdus");
            //遍历数组
            for (Object obj:objs){
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                //发件人
                smsPhone = sms.getOriginatingAddress();
                //内容
                smsContent = sms.getMessageBody();
                Logger.t("SmsReceiver").i("发件人：%s,内容：%s",smsPhone,smsContent);
                showSmsOnWindow();
            }
        }else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)){
            String reason = intent.getStringExtra(UrlInterface.SYSTEM_DIALOGS_RESON_KEY);
            //点击了Home键或者是多任务列表切换键后关闭弹窗
            if (UrlInterface.SYSTEM_DIALOGS_HOME_KEY.equals(reason) ||
                    UrlInterface.SYSTEM_DIALOGS_RECENT_APPS.equals(reason)){
                if (view.getParent() != null){
                    windowManager.removeView(view);
                }
            }
        }else {
            return;
        }
    }

    /**
     * 短信提示窗口
     */
    private void showSmsOnWindow() {
        //获取窗口服务
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //格式
        layoutParams.format = PixelFormat.TRANSLUCENT;//半透明
        //类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        view = (DispatchLinearLayout) View.inflate(context, R.layout.sms_item,null);
        view.setDispatchKeyEventListener(this);
        //初始化View中控件
        tv_phone = (TextView) view.findViewById(R.id.tv_phone);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        btn_send_sms = (Button) view.findViewById(R.id.btn_send_sms);
        //设置显示的内容
        tv_phone.setText("发件人：" + smsPhone);
        tv_content.setText(smsContent);
        btn_send_sms.setOnClickListener(this);
        //将布局添加到窗口
        windowManager.addView(view,layoutParams);
    }

    /**
     * 发送短信
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_sms://发送短信
                sendSms();
                //清空弹窗
                if (view.getParent() != null){
                    windowManager.removeView(view);
                }
                break;
        }
    }

    /**
     * 回复短信
     */
    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body", StaticClass.NONE_TEXT);
        context.startActivity(intent);
    }

    /**
     * 事件处理
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //判断是否按了返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            if (view.getParent() != null){
                windowManager.removeView(view);
            }
            return true;
        }
        return false;
    }
}
