package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.adapter.GuideAdapter;
import com.study.lyan.smartbuilder.utils.StaticClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnPageChange;

public class GuideActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindViews(value = {R.id.point1, R.id.point2, R.id.point3})
    List<ImageView> points;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private List<View> viewList = new ArrayList<>();
    private View view1, view2, view3;

    @Override
    protected int addLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        getSupportActionBar().hide();//隐藏标题栏
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //填充控件
        view1 = getLayoutInflater().inflate(R.layout.page_item_one, null);
        view2 = getLayoutInflater().inflate(R.layout.page_item_two, null);
        view3 = getLayoutInflater().inflate(R.layout.page_item_three, null);
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        ivBack.setOnClickListener(this);
        //添加数据
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        //设置适配器
        mViewPager.setAdapter(new GuideAdapter(viewList));
        //设置默认效果
        setPoint(StaticClass.START_INDEX);
    }

    @Override
    protected boolean showBackHomeButton() {
        return false;
    }

    /**
     * 设置圆点指示器的显示效果
     *
     * @param position
     */
    private void setPoint(int position) {
        for (int i = 0; i < points.size(); i++) {
            int drawableResId = position == i ? R.drawable.point_on : R.drawable.point_off;
            points.get(i).setImageResource(drawableResId);
        }
    }

    /**
     * 滑动监听
     *
     * @param position
     */
    @OnPageChange(value = R.id.mViewPager, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position) {
        setPoint(position);//设置指示器效果
        int visibilityCode = position != viewList.size() - 1 ? View.VISIBLE : View.GONE;
        ivBack.setVisibility(visibilityCode);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://跳过
            case R.id.btn_start://跳转主界面
                startActivity(startTo(LoginActivity.class));
                finish();
                break;
        }
    }
}
