package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.adapter.CourierAdapter;
import com.study.lyan.smartbuilder.entity.Courier;
import com.study.lyan.smartbuilder.utils.GetViewTextUtils;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.utils.UrlInterface;
import com.study.lyan.smartbuilder.view.LoginDialog;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CourierActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.btn_get_courier)
    Button btnGetCourier;
    @BindView(R.id.mListView)
    ListView mListView;

    private LoginDialog dialog;
    @Override
    protected int addLayout() {
        return R.layout.activity_courier;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        dialog = new LoginDialog(this);
        dialog.setHintText("查询中");
    }

    @Override
    protected boolean showBackHomeButton() {
        return true;
    }

    /**
     * 查询数据
     * @param view
     */
    @OnClick(value = R.id.btn_get_courier)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_get_courier://查询快递
                //获取输入框内容
                String name = GetViewTextUtils.getTextTrim(etName);
                String number = GetViewTextUtils.getTextTrim(etNumber);
                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)){
                    HttpParams params = new HttpParams();
                    params.put("key",UrlInterface.COURIER_KEY);
                    params.put("com",name);
                    params.put("no",number);
                    //获取网络数据
                    dialog.show();
                    RxVolley.get(UrlInterface.COURIER_URL, params, callback);

                }else {
                    ToastUtils.shortToast(this,"请输入订单号！");
                }
                break;
        }
    }

    /**
     * 网络数据请求结果
     */
    private HttpCallback callback = new HttpCallback() {
        /**
         * 请求成功
         * @param t
         */
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            dialog.dismiss();
            Logger.t("订单结果").json(t);
            Courier courier = new Gson().fromJson(t,Courier.class);
            List<Courier.ResultBean.ListBean> mList = courier.getResult().getList();
            Collections.reverse(mList);//倒序集合
            mListView.setAdapter(new CourierAdapter(CourierActivity.this, mList));
        }

        /**
         * 请求失败
         * @param errorNo
         * @param strMsg
         */
        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            dialog.dismiss();
            ToastUtils.shortToast(CourierActivity.this,"获取数据失败！" + strMsg);
        }
    };
}
