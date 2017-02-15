package com.study.lyan.smartbuilder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.entity.GirlImage;
import com.study.lyan.smartbuilder.utils.PicassoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lyan on 17/2/12.
 */

public class GrilAdapter extends RecyclerView.Adapter<GrilAdapter.GirlHolder> {

    /**
     * Item点击事件
     */
    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }
    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener (OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    private List<GirlImage> list;
    private Context context;
    public GrilAdapter(List<GirlImage> list , Context mContext) {
        this.list = list;
        this.context = mContext;
    }

    @Override
    public GirlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.girl_item, parent, false);
        return new GirlHolder(view);
    }

    @Override
    public void onBindViewHolder(final GirlHolder holder, final int position) {
        GirlImage girlImage = list.get(position);
        String url = girlImage.getUrl();
        int width = girlImage.getWidth();
        int height = girlImage.getHeight();
        ViewGroup.LayoutParams params = holder.ivImg.getLayoutParams();
        params.width = width;
        params.height = height;
        holder.ivImg.setLayoutParams(params);
        if (itemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
        if (TextUtils.isEmpty(url)){
            return;
        }
        //加载图片
        PicassoUtils.loadImageViewCrop(context,url.trim(),holder.ivImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class GirlHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.girl)
        ImageView ivImg;
        public GirlHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
