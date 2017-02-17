package com.study.lyan.smartbuilder.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lyan on 17/2/9.
 */

public abstract class BaseFragment extends Fragment{
    private Unbinder unbinder;
    protected Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = toCreateView(inflater,container,savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
        setView();
        return view;
    }

    /**
     * 设置界面
     */
    protected abstract void setView();

    /**
     * 初始化视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    protected abstract View toCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 解绑定
     */
    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * 跳转界面
     * @param clazz
     * @return
     */
    protected final <T extends Activity>Intent startTo(Class<T> clazz){
        return new Intent(getActivity(),clazz);
    }
}
