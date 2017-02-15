package com.study.lyan.smartbuilder.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.BuildConfig;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.activity.MainActivity;
import com.study.lyan.smartbuilder.utils.ToastUtils;

import java.io.File;

import butterknife.OnClick;

/**
 * Created by Lyan on 17/2/11.
 */

public class ChangePhotoDialog extends CustomDialog implements View.OnClickListener{

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;
    private Activity activity;
    private Fragment fragment;
    private int which;
    private static final int ACTIVITY = 0;
    private static final int FRAGMENT = 1;
    public ChangePhotoDialog(Activity context){
        this(context,R.layout.dialog_photo, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT ,R.style.Theme_dialog, Gravity.BOTTOM);
        this.activity = context;
        which = ACTIVITY;
    }
    public ChangePhotoDialog(Fragment fragment){
        this(fragment.getContext(),R.layout.dialog_photo, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT ,R.style.Theme_dialog, Gravity.BOTTOM);
        this.fragment = fragment;
        which = FRAGMENT;
    }
    private ChangePhotoDialog(Context context,@LayoutRes int layout,int width, int height, @StyleRes int style,  int gravity){
        super(context,layout,style,width, height,gravity,R.style.pop_anim_sytle);
        setCanceledOnTouchOutside(false);
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_picture = (Button) findViewById(R.id.btn_picture);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera://打开相机获取图片
                Logger.t("更改头像").d("相机");
                toCamera();
                break;
            case R.id.btn_picture://打开图库获取图片
                Logger.t("更改头像").d("图库");
                toPicture();
                break;
            case R.id.btn_cancel://关闭弹窗
                Logger.t("更改头像").d("关闭");
                dismiss();//关闭弹窗
                break;
        }
    }
    public static final String PHOTO_IMAGE_FILE_NAME = "fileimg.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile;
    /**
     * 跳转相机
     */
    private void toCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        tempFile = new File(Environment.getExternalStorageDirectory(),
                PHOTO_IMAGE_FILE_NAME);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri());
        if (which == ACTIVITY){
            activity.startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }else {
            fragment.startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }
        dismiss();//关闭弹窗
    }

    /**
     * 获取图片文件
     */
    public File getImageFile(){
        return tempFile;
    }
    /**
     * 获取Uri
     * @return
     */
    public Uri getUri(){
        //判断是否是AndroidN以及更高的版本
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri= FileProvider.getUriForFile(getContext(),
                    BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
        } else {
            //判断内存卡是否可用
            contentUri = Uri.fromFile(tempFile);
        }
        return contentUri;
    }
    /**
     * 跳转图库
     */
    private void toPicture(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        if (which == ACTIVITY){
            activity.startActivityForResult(intent,IMAGE_REQUEST_CODE);
        }else {
            fragment.startActivityForResult(intent,IMAGE_REQUEST_CODE);
        }
        dismiss();//关闭弹窗
    }

}
