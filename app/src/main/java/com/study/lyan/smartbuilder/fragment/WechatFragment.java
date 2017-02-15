package com.study.lyan.smartbuilder.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.activity.WebViewActivity;
import com.study.lyan.smartbuilder.adapter.WechatAdapter;
import com.study.lyan.smartbuilder.entity.Courier;
import com.study.lyan.smartbuilder.entity.WechatData;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.utils.UrlInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lyan on 17/2/9.
 * 微信精选
 */

public class WechatFragment extends BaseFragment implements XRecyclerView.LoadingListener ,WechatAdapter.OnItemClickListener {

    @BindView(R.id.mListView)
    XRecyclerView mRecyclerView;//列表
    private List<WechatData.ResultBean.ListBean> list;//数据
    private WechatAdapter adapter;//数据适配器
    private int page = UrlInterface.START_PAGE;//页数
    private int count = UrlInterface.EVERY_COUNT;//加载的数据
    private boolean isLoadMore;//是否加载更多
    private boolean isLoadingEnd = true;//是否在联网
    @Override
    protected View toCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, null);
        return view;
    }

    @Override
    protected void setView() {
        list = new ArrayList<>();//数据
        adapter = new WechatAdapter(list,getContext());//适配器
        adapter.setOnItemClickListener(this);//设置Item点击事件
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//线性垂直
        mRecyclerView.setLayoutManager(layoutManager);//设置显示效果
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);//刷新样式
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);//加载更多样式
        mRecyclerView.setAdapter(adapter);//设置适配器
        mRecyclerView.setLoadingListener(this);//设置加载监听
        mRecyclerView.refresh();//自动加载
        //设置分割线
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider_sample);
        mRecyclerView.addItemDecoration(mRecyclerView.new DividerItemDecoration(dividerDrawable));
    }

    /**
     * 加载刷新
     */
    @Override
    public void onRefresh() {
        if (isLoadingEnd){
            page = UrlInterface.START_PAGE;//回复默认值
            isLoadMore = false;//设置不是加载更多
            isLoadingEnd = false;//加载中
            getWechatData();
        }
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        if (isLoadingEnd){
            page++;//回复默认值
            isLoadMore = true;//设置不是加载更多
            isLoadingEnd = false;//加载中
            getWechatData();
        }
    }

    /**
     * 获取网络数据
     */
    private void getWechatData(){
        HttpParams params = new HttpParams();
        params.put("pno",page);
        params.put("ps",count);
        params.put("key", UrlInterface.WECHAT_KEY);
        RxVolley.get(UrlInterface.WECHAT_URL,params,callback);
    }
    /**
     * 获取网络数据结果
     */
    private HttpCallback callback = new HttpCallback() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            Logger.json(t);
            //解析数据
            WechatData data = new Gson().fromJson(t,WechatData.class);
            String result = data.getReason();
            if (!"请求成功".equals(result)){
                if (isLoadMore){
                    mRecyclerView.loadMoreComplete();//加载更多结束
                    mRecyclerView.setNoMore(true);//没有更多了
                    adapter.notifyDataSetChanged();//适配器更新数据
                }else {
                    mRecyclerView.refreshComplete();//加载刷新结束
                }
                return;
            }
            if (isLoadMore){//加载更多
                list.addAll(data.getResult().getList());//装载数据
                mRecyclerView.loadMoreComplete();//加载更多结束
                adapter.notifyDataSetChanged();//适配器更新数据
            }else {//刷新数据
                list.clear();//清空数据
                list.addAll(data.getResult().getList());//装载数据
                mRecyclerView.refreshComplete();//加载刷新结束
                adapter.notifyDataSetChanged();//适配器更新数据
            }
            isLoadingEnd = true;//加载完成
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            ToastUtils.shortToast(getContext(),"获取数据失败！");
        }
    };

    /**
     * item点击事件
     * @param itemView
     * @param position
     */
    @Override
    public void onItemClick(View itemView, int position) {
        //跳转界面
        Intent intent = startTo(WebViewActivity.class);
        WechatData.ResultBean.ListBean bean = list.get(position);
        intent.putExtra("title",bean.getTitle());
        intent.putExtra("url",bean.getUrl());
        startActivity(intent);
    }
}
