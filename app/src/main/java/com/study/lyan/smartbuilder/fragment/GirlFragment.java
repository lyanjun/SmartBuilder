package com.study.lyan.smartbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.adapter.GrilAdapter;
import com.study.lyan.smartbuilder.entity.GirlImage;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.utils.UrlInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;


/**
 * Created by Lyan on 17/2/9.
 * 美女社区
 */

public class GirlFragment extends BaseFragment implements XRecyclerView.LoadingListener ,GrilAdapter.OnItemClickListener {

    @BindView(R.id.mListView)
    XRecyclerView mRecyclerView;
    private List<GirlImage> list;//数据
    private GrilAdapter adapter;//数据适配器
    private int page = UrlInterface.START_PAGE;
    private int count = UrlInterface.EVERY_COUNT;
    private boolean isLoadMore;//是否加载更多
    private boolean isLoadingEnd = true;//是否在联网
    private int windowWidth;
    private Random random = new Random();
    @Override
    protected View toCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, null);
        return view;
    }

    @Override
    protected void setView() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        windowWidth = dm.widthPixels;
        list = new ArrayList<>();//数据
        adapter = new GrilAdapter(list,getContext());//适配器
        adapter.setOnItemClickListener(this);//设置Item点击事件
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        manager.setItemPrefetchEnabled(false);
        mRecyclerView.setLayoutManager(manager);//设置显示效果
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);//刷新样式
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);//加载更多样式
        mRecyclerView.setAdapter(adapter);//设置适配器
        mRecyclerView.setLoadingListener(this);//设置加载监听
        mRecyclerView.refresh();//自动加载
    }

    @Override
    public void onRefresh() {
        if (isLoadingEnd){
            page = UrlInterface.START_PAGE;//回复默认值
            isLoadMore = false;//设置不是加载更多
            isLoadingEnd = false;//加载中
            getWechatData();
        }
    }

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
        try {
            String url = String.format(UrlInterface.GANK_GIRL, URLEncoder.encode("福利","utf-8"),count,page);
            Logger.w(url);
            RxVolley.get(url.trim(),callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取网络数据结果
     */
    private HttpCallback callback = new HttpCallback() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            Logger.json(t);
            Logger.w(t);
            //解析数据
            try {
                JSONObject jsonObject = new JSONObject(t);
                boolean result = jsonObject.getBoolean("error");
                if (result){
                    if (isLoadMore){
                        mRecyclerView.loadMoreComplete();//加载更多结束
                        mRecyclerView.setNoMore(true);//没有更多了
                        adapter.notifyDataSetChanged();//适配器更新数据
                    }else {
                        mRecyclerView.refreshComplete();//加载刷新结束
                    }
                    return;
                }
                List<GirlImage> imageList = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length();i++){
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    String url = object.getString("url");
                    GirlImage girlImage = new GirlImage();
                    girlImage.setWidth(windowWidth/2);
                    girlImage.setUrl(url);
                    girlImage.setHeight(windowWidth/2*(random.nextInt(4) + 3)/3);
                    imageList.add(girlImage);
                }
                if (isLoadMore){//加载更多
                    list.addAll(imageList);//装载数据
                    mRecyclerView.loadMoreComplete();//加载更多结束
                    adapter.notifyDataSetChanged();//适配器更新数据
                }else {//刷新数据
                    list.clear();//清空数据
                    list.addAll(imageList);//装载数据
                    mRecyclerView.refreshComplete();//加载刷新结束
                    adapter.notifyDataSetChanged();//适配器更新数据
                }
                isLoadingEnd = true;//加载完成
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            ToastUtils.shortToast(getContext(),"获取数据失败！");
        }
    };
    @Override
    public void onItemClick(View itemView, int position) {
        ToastUtils.shortToast(getContext(),position + "");
    }
}
