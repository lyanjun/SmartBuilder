package com.study.lyan.smartbuilder.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.study.lyan.smartbuilder.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

import butterknife.BindView;

/**
 * 生成二维码
 */
public class QrCodeActivity extends BaseActivity {

    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;

    @Override
    protected int addLayout() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        int width = getResources().getDisplayMetrics().widthPixels;
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        Bitmap qrBitmap = EncodingUtils.createQRCode("我是上帝",width/2,width/2,logoBitmap);
        ivQrCode.setImageBitmap(qrBitmap);
    }

    @Override
    protected boolean showBackHomeButton() {
        return true;
    }
}
