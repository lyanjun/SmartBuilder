package com.study.lyan.smartbuilder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.HttpParams;
import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.activity.CourierActivity;
import com.study.lyan.smartbuilder.adapter.ChatListAdapter;
import com.study.lyan.smartbuilder.adapter.CourierAdapter;
import com.study.lyan.smartbuilder.entity.ChatData;
import com.study.lyan.smartbuilder.entity.Courier;
import com.study.lyan.smartbuilder.utils.GetViewTextUtils;
import com.study.lyan.smartbuilder.utils.StaticClass;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.utils.UrlInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lyan on 17/2/9.
 * 服务管家
 */

public class ButlerFragment extends BaseFragment {

    @BindView(R.id.mChatListView)
    ListView mChatListView;
    @BindView(R.id.et_text)
    EditText etText;
    @BindView(R.id.btn_send)
    Button btnSend;

    private List<ChatData> mList = new ArrayList<>();
    private ChatListAdapter adapter;
    @Override
    protected View toCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, null);
        return view;
    }

    @Override
    protected void setView() {
        //设置适配器
        adapter = new ChatListAdapter(getContext(),mList);
        mChatListView.setAdapter(adapter);
        //默认提示
        sendText(ChatListAdapter.VALUE_LEFT_TEXT,"你好，我是上帝！");
    }

    /**
     * 发送内容
     */
    @OnClick(value = R.id.btn_send)
    public void onClick(){
        String inputStr = GetViewTextUtils.getTextFromView(etText);
        if (!TextUtils.isEmpty(inputStr) && inputStr.length() < 30){
            etText.setText(StaticClass.NONE_TEXT);//清空输入窗
            sendText(ChatListAdapter.VALUE_RIGHT_TEXT,inputStr);//添加内容到列表
            //与后台交互
            HttpParams params = new HttpParams();
            params.put("key",UrlInterface.CHAT_LIST_KEY);
            params.put("info",inputStr);
            RxVolley.post(UrlInterface.CHAT_LIST_URL,params,callback);
        }else {
            ToastUtils.shortToast(getContext(),"请填写内容！");
        }

    }


//    {
//        "reason":"成功的返回",
//            "result": /*根据code值的不同，返回的字段有所不同*/
//        {
//            "code":100000, /*返回的数据类型，请根据code的值去数据类型API查询*/
//                "text":"你好啊，希望你今天过的快乐"
//        },
//        "error_code":0
//    }
    /**
     * 获取数据结果
     *
     */
    private HttpCallback callback = new HttpCallback() {
        /**
         * 请求成功
         * @param t
         */
        @Override
        public void onSuccess(String t) {
            super.onSuccess(t);
            Logger.t("订单结果").json(t);
            try {
                String text;
                JSONObject jsonObject = new JSONObject(t);
                String reason = jsonObject.getString("reason");
                //拿到返回值
                if ("成功的返回".equals(reason)){
                    JSONObject json = jsonObject.getJSONObject("result");
                    text = json.getString("text");
                }else {
                    text = "本上帝不懂！";
                }
                //拿到机器人的返回值之后添加在left item
                sendText(ChatListAdapter.VALUE_LEFT_TEXT,text);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /**
         * 请求失败
         * @param errorNo
         * @param strMsg
         */
        @Override
        public void onFailure(int errorNo, String strMsg) {
            super.onFailure(errorNo, strMsg);
            ToastUtils.shortToast(getContext(),"获取数据失败！" + strMsg);
        }
    };
    /**
     * 将内容显示到界面中
     * @param type
     * @param text
     */
    private void sendText(int type,String text){
        ChatData data = new ChatData();
        data.setType(type);
        data.setText(text);
        //添加到集合中
        mList.add(data);
        //通知适配器更新
        adapter.notifyDataSetChanged();
        //滚动到列表的最底部
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
