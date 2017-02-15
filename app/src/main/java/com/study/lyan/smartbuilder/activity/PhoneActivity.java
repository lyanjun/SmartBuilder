package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.utils.GetViewTextUtils;
import com.study.lyan.smartbuilder.utils.KeyBordUtils;
import com.study.lyan.smartbuilder.utils.StaticClass;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.utils.UrlInterface;
import com.study.lyan.smartbuilder.view.LoginDialog;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class PhoneActivity extends BaseActivity {


    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.iv_company)
    ImageView ivCompany;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_del)
    Button btnDel;
    @BindView(R.id.btn_4)
    Button btn4;
    @BindView(R.id.btn_5)
    Button btn5;
    @BindView(R.id.btn_6)
    Button btn6;
    @BindView(R.id.btn_0)
    Button btn0;
    @BindView(R.id.btn_7)
    Button btn7;
    @BindView(R.id.btn_8)
    Button btn8;
    @BindView(R.id.btn_9)
    Button btn9;
    @BindView(R.id.btn_query)
    Button btnQuery;

    private LoginDialog dialog;
    private boolean flag;//标记为

    @Override
    protected int addLayout() {
        return R.layout.activity_phone;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        dialog = new LoginDialog(this);
        dialog.setHintText("正在查询");
        KeyBordUtils.hideSoftInputMethod(this,etNumber);//屏蔽键盘
    }

    @Override
    protected boolean showBackHomeButton() {
        return true;
    }

    /**
     * 点击事件-->输入键盘
     * @param view
     */
    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_del, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_0, R.id.btn_7, R.id.btn_8, R.id.btn_9, R.id.btn_query})
    public void onClick(View view) {
        String editStr = GetViewTextUtils.getTextTrim(etNumber);//获取输入框的内容
        Editable editable = etNumber.getText();//获取文本
        int index = etNumber.getSelectionStart();//获取光标
        switch (view.getId()) {
            case R.id.btn_0://0
            case R.id.btn_1://1
            case R.id.btn_2://2
            case R.id.btn_3://3
            case R.id.btn_4://4
            case R.id.btn_5://5
            case R.id.btn_6://6
            case R.id.btn_7://7
            case R.id.btn_8://8
            case R.id.btn_9://9
                if (flag){
                    flag = false;
                    etNumber.setText(StaticClass.NONE_TEXT);
                }
                editable.insert(index, GetViewTextUtils.getTextTrim((Button)view));
                break;
            case R.id.btn_del://删除
                if (index == 0){
                    return;//输入框中的内容已被清空
                }
                if (!TextUtils.isEmpty(editStr) && editStr.length() > 0){
                    //每次减一位
                    editable.delete(index-1, index);
                }
                break;
            case R.id.btn_query://查询
                if (editStr.length() >= 7){
                    HttpParams params = new HttpParams();
                    params.put("phone", editStr);
                    params.put("key", UrlInterface.PHONE_KEY);
                    dialog.show();
                    RxVolley.post(UrlInterface.PHONE_URL,params,callback);
                }else {
                    ToastUtils.shortToast(PhoneActivity.this,"至少输入5为数字");
                }
                break;
        }
    }

    /**
     * 全删除
     * @return
     */
    @OnLongClick(value = R.id.btn_del)
    public boolean onLongClick(){
        etNumber.setText(StaticClass.NONE_TEXT);
        return false;
    }

    /**
     * 请求结果
     * {
     "resultcode":"200",
     "reason":"Return Successd!",
     "result":{
     "province":"浙江",
     "city":"杭州",
     "areacode":"0571",
     "zip":"310000",
     "company":"移动",
     "card":""
     },
     "error_code":0
     }
     */
    private HttpCallback callback = new HttpCallback() {
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            dialog.dismiss();
            Logger.t("电话归属地").i(t);
            Logger.t("电话归属地").json(t);
            try {
                JSONObject jsonObject = new JSONObject(t);
                int errorCode = jsonObject.getInt("error_code");
                if (errorCode != 0){
                    ToastUtils.shortToast(PhoneActivity.this,"查询错误！");
                    return;
                }
                JSONObject jsonResult = jsonObject.getJSONObject("result");
                String province = jsonResult.getString("province");
                String city = jsonResult.getString("city");
                String areacode = jsonResult.getString("areacode");
                String zip = jsonResult.getString("zip");
                String company = jsonResult.getString("company");
                String card = jsonResult.getString("card");
                tvResult.setText("归属地:" + province + city + "\n"
                        + "区号:" + areacode + "\n"
                        + "邮编:" + zip + "\n"
                        + "运营商:" + company + "\n"
                        + "类型:" + card);

                //图片显示
                switch (company) {
                    case "移动":
                        ivCompany.setBackgroundResource(R.drawable.china_mobile);
                        break;
                    case "联通":
                        ivCompany.setBackgroundResource(R.drawable.china_unicom);
                        break;
                    case "电信":
                        ivCompany.setBackgroundResource(R.drawable.china_telecom);
                        break;
                }
                flag = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            dialog.dismiss();
            ToastUtils.shortToast(PhoneActivity.this,"获取数据失败！" + strMsg);
        }
    };


}
