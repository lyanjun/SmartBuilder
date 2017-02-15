package com.study.lyan.smartbuilder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {


    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    @Override
    protected int addLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        //获取传值
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        Logger.t("网址").i(url);//打印Log
        //设置标题
        getSupportActionBar().setTitle(title);
        //进行加载网页的逻辑
        mWebView.getSettings().setJavaScriptEnabled(true);//支持JS
        mWebView.getSettings().setSupportZoom(true);//支持缩放
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new MyWebViewClient());
        //加载网页
        mWebView.loadUrl(url);
        //本地显示
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private class MyWebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100){
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
    @Override
    protected boolean showBackHomeButton() {
        return true;
    }
}
