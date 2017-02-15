package com.study.lyan.smartbuilder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.entity.MyUser;
import com.study.lyan.smartbuilder.entity.WechatData;
import com.study.lyan.smartbuilder.utils.DensityUtils;
import com.study.lyan.smartbuilder.utils.PicassoUtils;
import com.study.lyan.smartbuilder.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lyan on 17/2/12.
 */

public class WechatAdapter extends RecyclerView.Adapter<WechatAdapter.WechatHolder> {

    /**
     * Item点击事件
     */
    public interface OnItemClickListener{
        void onItemClick(View itemView,int position);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener (OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    private List<WechatData.ResultBean.ListBean> list;
    private Context context;
    public WechatAdapter(List<WechatData.ResultBean.ListBean> list ,Context mContext) {
        this.list = list;
        this.context = mContext;
    }

    @Override
    public WechatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wechat_item, parent, false);
        return new WechatHolder(view);
    }

    @Override
    public void onBindViewHolder(final WechatHolder holder, final int position) {
        WechatData.ResultBean.ListBean bean = list.get(position);
        holder.tvTitle.setText(bean.getTitle());//标题
        holder.tvSource.setText(bean.getSource());//简介
        if (itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
        if (TextUtils.isEmpty(bean.getFirstImg())){
            return;
        }
        //加载图片
        PicassoUtils.loadImageViewSize(context,bean.getFirstImg().trim(),
                DensityUtils.dp2px(context,100),DensityUtils.dp2px(context,80),holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class WechatHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_source)
        TextView tvSource;
        public WechatHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
