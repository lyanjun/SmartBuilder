package com.study.lyan.smartbuilder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * Created by Lyan on 17/2/9.
 */

public abstract class BaseActivity extends AppCompatActivity{

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

}
