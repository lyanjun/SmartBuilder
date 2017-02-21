package com.study.lyan.smartbuilder.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.helper.SharePreferenceHelper;
import com.study.lyan.smartbuilder.service.SmsService;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Lyan on 17/2/9.
 * 设置界面
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.sw_speak)
    Switch swSpeak;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.sw_sms)
    Switch swSms;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.tv_scan_result)
    TextView tvScanResult;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.ll_qr_code)
    LinearLayout llQrCode;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.ll_my_location)
    LinearLayout llMyLocation;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    private SharePreferenceHelper share;
    private boolean isSpeak;//是否开启语音播报
    private boolean isSms;//短信提醒

    /**
     * 设置界面布局
     *
     * @return
     */
    @Override
    protected int addLayout() {
        return R.layout.activity_setting;
    }

    /***
     * 初始化操作
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        share = SharePreferenceHelper.getInstance();
        isSpeak = (boolean) share.get("isSpeak", false);//获取状态
        swSpeak.setChecked(isSpeak);//设置按钮的状态
        isSms = (boolean) share.get("isSms", false);//获取状态
        swSms.setChecked(isSms);//设置按钮的状态
    }

    /**
     * 显示标题栏中的返回按钮
     *
     * @return
     */
    @Override
    protected boolean showBackHomeButton() {
        return true;
    }

    /**
     * 获取权限成功
     *
     * @param requestCode
     */
    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 1000://开启语音播报
                share.put("isSpeak", isSpeak).commit();//存入开关状态
                break;
            case 2000://开启短信监听
                checkedSystemAlertWindow();//检查弹窗权限
//                share.put("isSms",isSms).commit();//存入开关状态
//                //开启或关闭一个监听短信的服务
//                if (isSms){//开启监听短信的服务
//                    startService(service(SmsService.class));
//                }else {//关闭监听短信的服务
//                    stopService(service(SmsService.class));
//                }
                break;
            case 3000:
                //扫描二维码
                startActivityForResult(startTo(CaptureActivity.class),4000);
                break;
        }
    }

    /**
     * 获取系统权限成功
     *
     * @param code
     */
    @Override
    protected void onSystemPermissionSuccess(int code) {
        super.onSystemPermissionSuccess(code);
        if (code == SYSTEM_ALERT_WINDOW) {
            share.put("isSms", isSms).commit();//存入开关状态
            //开启或关闭一个监听短信的服务
            if (isSms) {//开启监听短信的服务
                startService(service(SmsService.class));
            } else {//关闭监听短信的服务
                stopService(service(SmsService.class));
            }
        }
    }

    /**
     * 获取系统权限失败
     *
     * @param code
     */
    @Override
    protected void onSystemPermissionFail(int code) {
        super.onSystemPermissionFail(code);
        if (code == SYSTEM_ALERT_WINDOW) {
            isSms = false;//取消选中状态
            swSms.setChecked(isSms);//重置按钮状态
        }
    }

    /**
     * 权限获取失败
     *
     * @param requestCode
     */
    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);
        switch (requestCode) {
            case 1000://开启语音播报
                isSpeak = false;//取消选中状态
                swSpeak.setChecked(isSpeak);//重置按钮状态
                break;
            case 2000://开启短信监听
                isSms = false;//取消选中状态
                swSms.setChecked(isSms);//重置按钮状态
                break;
            case 3000://扫描二维码
                ToastUtils.shortToast(this,"获取摄像头权限失败！");
                break;
        }
    }

    /**
     * 开关的选中状态
     *
     * @param buttonView
     * @param isChecked
     */
    @OnCheckedChanged(value = {R.id.sw_speak, R.id.sw_sms})
    public void checkedChange(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_speak://语音播报
                isSpeak = isChecked;//获取按钮当前状态
                requestPermission(new String[]{Manifest.permission.RECORD_AUDIO}, 1000);
                break;
            case R.id.sw_sms://短信提醒
                isSms = isChecked;//获取按钮当前状态
                requestPermission(new String[]{Manifest.permission.RECEIVE_SMS}, 2000);
                break;

        }
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick(value = {R.id.ll_scan,R.id.ll_qr_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_scan://二维码扫描
                requestPermission(new String[]{Manifest.permission.CAMERA},3000);
                break;
            case R.id.ll_qr_code://生成二维码
                startActivity(startTo(QrCodeActivity.class));
                break;
        }
    }
    /**
     * 返回界面结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch(requestCode){
                case 4000://获取二维码信息
                    String scanResult = data.getExtras().getString("result");
                    tvScanResult.setText(scanResult);//显示二维码信息
                    break;
            }
        }
    }
}
