package com.study.lyan.smartbuilder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.entity.Courier;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lyan on 17/2/12.
 */

public class CourierAdapter extends BaseAdapter {
    private List<Courier.ResultBean.ListBean> mList;
    private LayoutInflater mInflater;

    public CourierAdapter(Context mContext, List<Courier.ResultBean.ListBean> mList) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_courier_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Courier.ResultBean.ListBean bean = mList.get(position);
        viewHolder.tvRemark.setText(bean.getRemark());
        viewHolder.tvZone.setText(bean.getZone());
        viewHolder.tvDatetime.setText(bean.getDatetime());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_remark)
        TextView tvRemark;
        @BindView(R.id.tv_zone)
        TextView tvZone;
        @BindView(R.id.tv_datetime)
        TextView tvDatetime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
