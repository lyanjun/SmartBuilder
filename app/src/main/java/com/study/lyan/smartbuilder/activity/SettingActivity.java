package com.study.lyan.smartbuilder.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.helper.SharePreferenceHelper;

import butterknife.BindView;
import butterknife.OnCheckedChanged;

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
        isSpeak = (boolean) share.get("isSpeak",false);//获取状态
        swSpeak.setChecked(isSpeak);
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
        switch (requestCode){
            case 1000://开启语音播报
                share.put("isSpeak",isSpeak).commit();//存入开关状态
                break;
        }
    }

    /**
     * 开关的选中状态
     * @param buttonView
     * @param isChecked
     */
    @OnCheckedChanged(value = {R.id.sw_speak})
    public void checkedChange(CompoundButton buttonView, boolean isChecked){
        switch (buttonView.getId()){
            case R.id.sw_speak://语音播报
                isSpeak = isChecked;
                requestPermission(new String[]{Manifest.permission.RECORD_AUDIO},1000);
                break;
        }
    }
}
