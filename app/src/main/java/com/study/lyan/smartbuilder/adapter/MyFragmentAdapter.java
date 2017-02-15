package com.study.lyan.smartbuilder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Lyan on 17/2/9.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter{

    private List<String> titles;
    private List<Fragment> fragments;

    public MyFragmentAdapter(FragmentManager fm,List<String> titles,List<Fragment> fragments) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    //选中的页数
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    //返回个数
    @Override
    public int getCount() {
        return fragments.size();
    }
    //绑定标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
