package com.study.lyan.smartbuilder.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

/**
 * Created by Lyan on 17/2/9.
 */

public abstract class BaseActivity extends MPermissionsActivity{
    protected final int WRITE_SETTINGS = 99;
    protected final int SYSTEM_ALERT_WINDOW = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (showBackHomeButton()){//显示返回键
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setElevation(0);//去掉阴影
        setContentView(addLayout());//加载布局
        ButterKnife.bind(this);//注解绑定
        onCreateActivity(savedInstanceState);//初始化界面
    }



    /**
     * 获取布局资源ID
     * @return
     */
    protected abstract int addLayout();
    /**
     * 出事话操作
     * @param savedInstanceState
     */
    protected abstract void onCreateActivity(Bundle savedInstanceState);

    /**
     * 菜单栏操作
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://返回箭头
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 标题栏是否显示返回键
     * @return
     */
    protected abstract boolean showBackHomeButton();

    /**
     * 跳转界面
     * @param clazz
     * @return
     */
    protected final <T extends Activity>Intent startTo(Class<T> clazz){
        return new Intent(this,clazz);
    }

    /**
     * 管理服务
     * @param clazz
     * @param <T>
     * @return
     */
    protected final <T extends Service>Intent service(Class<T> clazz){
        return new Intent(this,clazz);
    }

    /**
     * WRITE_SETTINGS权限检查
     */
    protected void checkedWriteSettings(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.System.canWrite(this)){
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, WRITE_SETTINGS);
            }else {
                onSystemPermissionSuccess(WRITE_SETTINGS);
            }
        }else {
            onSystemPermissionSuccess(WRITE_SETTINGS);
        }
    }

    /**
     * SYSTEM_ALERT_WINDOW权限检查
     */
    protected void checkedSystemAlertWindow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, SYSTEM_ALERT_WINDOW);
            }else {
                onSystemPermissionSuccess(SYSTEM_ALERT_WINDOW);
            }
        }else {
            onSystemPermissionSuccess(SYSTEM_ALERT_WINDOW);
        }
    }

    /**
     * 数据返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.t("系统权限申请").i("请求码：%s,返回码：%s",requestCode,resultCode);
        switch (requestCode){
            case WRITE_SETTINGS:
                if (!Settings.System.canWrite(this)) {//权限申请失败
                    onSystemPermissionFail(WRITE_SETTINGS);
                } else {//权限申请成功
                    onSystemPermissionSuccess(WRITE_SETTINGS);
                }
                break;
            case SYSTEM_ALERT_WINDOW:
                if (!Settings.canDrawOverlays(this)) {//权限申请失败
                    onSystemPermissionFail(SYSTEM_ALERT_WINDOW);
                } else {//权限申请成功
                    onSystemPermissionSuccess(SYSTEM_ALERT_WINDOW);
                }
                break;
        }
    }

    /**
     * 获取系统权限成功
     * @param code
     */
    protected void onSystemPermissionSuccess(int code){}

    /**
     * 获取系统权限失败
     * @param code
     */
    protected void onSystemPermissionFail(int code){}
}
