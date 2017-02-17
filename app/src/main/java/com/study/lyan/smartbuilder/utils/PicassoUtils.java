package com.study.lyan.smartbuilder.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.study.lyan.smartbuilder.R;

/**
 * Created by Lyan on 17/2/12.
 */

public class PicassoUtils {

    public static boolean debug = true;//调试模式
    public static final int LOAD_RES = R.drawable.load_default;//加载图片
    public static final int ERROR_RES = R.drawable.load_default;//错误图片
    /**
     * 加载网络图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageView(Context context, String url, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .into(imageView);
    }

    /**
     * 指定加载图片的大小
     *
     * @param context
     * @param url
     * @param width
     * @param height
     * @param imageView
     */
    public static void loadImageViewSize(Context context, String url, int width, int height, ImageView imageView) {
        Picasso.with(context)
                .setIndicatorsEnabled(debug);
        Picasso.with(context)
                .load(url)
                .config(Bitmap.Config.RGB_565)
                .resize(width, height)
                .centerCrop()
                .into(imageView);
    }
    /**
     * 设置加载图片错误图片
     *
     * @param context
     * @param url
     * @param load
     * @param error
     * @param imageView
     */
    public static void loadImageViewHolder(Context context, String url, @DrawableRes int load, @DrawableRes int error, ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .placeholder(load)
                .error(error)
                .into(imageView);
    }

    /**
     * 裁剪
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageViewCrop(Context context,String url,ImageView imageView){
        Picasso.with(context).setIndicatorsEnabled(debug);
        Picasso.with(context)
                .load(url)
                .placeholder(LOAD_RES)
                .error(ERROR_RES)
                .transform(new CropSquareTransformation())
                .into(imageView);
    }
    public static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "gg";
        }
    }
}
