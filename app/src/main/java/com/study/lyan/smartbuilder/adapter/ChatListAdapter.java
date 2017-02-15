package com.study.lyan.smartbuilder.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.entity.ChatData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lyan on 17/2/12.
 */

public class ChatListAdapter extends BaseAdapter {

    public static final int VALUE_LEFT_TEXT = 0;//左边TYPE
    public static final int VALUE_RIGHT_TEXT = 1;//右边TYPE
    public static final int VALUE_TYPE_COUNT = VALUE_RIGHT_TEXT + 1;//类型总数

    private List<ChatData> mList;
    private LayoutInflater mInflater;

    public ChatListAdapter(Context mContext, List<ChatData> mList) {
        this.mList = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //复用类
        ViewLeftHolder leftHolder;
        ViewRightHolder rightHolder;
        //获取item类型
        int type = getItemViewType(position);
        //赋值->获取对应的数据
        ChatData data = mList.get(position);
        switch (type){
            case VALUE_LEFT_TEXT://机器人
                convertView = getConvertViewFromType(convertView,type);
                leftHolder = (ViewLeftHolder) convertView.getTag();
                leftHolder.tvLeftText.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT://用户
                convertView = getConvertViewFromType(convertView,type);
                rightHolder = (ViewRightHolder) convertView.getTag();
                rightHolder.tvRightText.setText(data.getText());
                break;
        }
        return convertView;
    }

    /**
     * 获取对应的复用类
     * @param convertView
     * @param type
     * @return
     */
    private View getConvertViewFromType(View convertView ,int type){
        if (convertView == null){
            switch (type){
                case VALUE_LEFT_TEXT://机器人
                    convertView = mInflater.inflate(R.layout.left_item,null);
                    ViewLeftHolder leftHolder = new ViewLeftHolder(convertView);
                    convertView.setTag(leftHolder);
                    break;
                case VALUE_RIGHT_TEXT://用户
                    convertView = mInflater.inflate(R.layout.right_item,null);
                    ViewRightHolder rightHolder = new ViewRightHolder(convertView);
                    convertView.setTag(rightHolder);
                    break;
            }
        }
        return convertView;
    }
    /**
     * 根据Item返回
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        ChatData data = mList.get(position);
        return data.getType();
    }

    /**
     * 返回所有Layout数据
     *
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return VALUE_TYPE_COUNT;
    }

    /**
     * 机器人布局
     */
    static class ViewLeftHolder{
        @BindView(R.id.tv_left_text)
        TextView tvLeftText;

        ViewLeftHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 用户布局
     */
    static class ViewRightHolder {
        @BindView(R.id.tv_right_text)
        TextView tvRightText;

        ViewRightHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
