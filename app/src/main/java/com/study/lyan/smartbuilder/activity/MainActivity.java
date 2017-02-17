package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.adapter.MyFragmentAdapter;
import com.study.lyan.smartbuilder.fragment.ButlerFragment;
import com.study.lyan.smartbuilder.fragment.GirlFragment;
import com.study.lyan.smartbuilder.fragment.UserFragment;
import com.study.lyan.smartbuilder.fragment.WechatFragment;
import com.study.lyan.smartbuilder.helper.InitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

public class MainActivity extends BaseActivity{

    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;//选项卡
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;//翻页
    private List<Fragment> mFragment;//模块
    @BindView(R.id.fab_setting)
    FloatingActionButton fabSetting;//悬浮按钮

    /**
     * 设置界面布局
     * @return
     */
    @Override
    protected int addLayout() {
        return R.layout.activity_main;
    }
    /***
     * 初始化操作
     * @param savedInstanceState
     */
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        initData();
        initView();
        
    }

    /**
     * 不显示标题栏中的返回按钮
     * @return
     */
    @Override
    protected boolean showBackHomeButton() {
        return false;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //初始化模块集合
        mFragment = new ArrayList<>();
        mFragment.add(new ButlerFragment());
        mFragment.add(new WechatFragment());
        mFragment.add(new GirlFragment());
        mFragment.add(new UserFragment());
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //预加载数据
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //绑定适配器
        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),
                InitHelper.getInstance().getTitle(), mFragment));
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
        //设置悬浮按钮默认隐藏
        fabSetting.setVisibility(View.GONE);
    }

    /**
     * 点击事件
     * @param view
     */
    @OnClick(value = R.id.fab_setting)
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.fab_setting://跳转设置界面
                startActivity(startTo(SettingActivity.class));
                break;
        }

    }

    /**
     * ViewPager的滑动事件
     * @param position
     */
    @OnPageChange(value = R.id.mViewPager,callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position) {
        int changeCode = position == 0 ? View.GONE : View.VISIBLE;
        fabSetting.setVisibility(changeCode);//当显示第一个界面的时候隐藏按钮
    }
}
