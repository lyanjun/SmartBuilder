package com.study.lyan.smartbuilder.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.helper.SharePreferenceHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lyan on 17/2/9.
 */

public class UtilsTools {

    /**
     * 保存图片到SharePreferenceHelper中
     * @param share
     * @param image
     * @param <T>
     */
    public static <T extends ImageView> void putImageToShare(SharePreferenceHelper share, T image){
        //保存图片
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //bitmap压缩成字节流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        //利用Base64字节输出流转换成String
        byte[] byteArray = outputStream.toByteArray();
        String imgString = new String(Base64.encodeToString(byteArray,Base64.DEFAULT));
        //将String保存到SharePreference中
        share.put("image_title",imgString);
        share.commit();
    }
    /**
     * 获取保存到SharePreferenceHelper的图片，并显示在制定的控件上
     * @param share
     * @param image
     * @param <T>
     */
    public static <T extends ImageView> void getImageFromShare(SharePreferenceHelper share, T image){
        //拿到图片数据
        String imageString = String.valueOf(share.get("image_title",StaticClass.NONE_TEXT));
        if (!"".equals(imageString)){
            //使用Base64转换String
            byte[] byteArray = Base64.decode(imageString,Base64.DEFAULT);
            //转化成字节输入流
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
            //生成Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            //显示图片
            image.setImageBitmap(bitmap);
        }else {
            image.setImageResource(R.drawable.add_pic);
        }
    }
}
