package com.study.lyan.smartbuilder.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.activity.CourierActivity;
import com.study.lyan.smartbuilder.activity.LoginActivity;
import com.study.lyan.smartbuilder.activity.PhoneActivity;
import com.study.lyan.smartbuilder.entity.MyUser;
import com.study.lyan.smartbuilder.helper.SharePreferenceHelper;
import com.study.lyan.smartbuilder.utils.GetViewTextUtils;
import com.study.lyan.smartbuilder.utils.StaticClass;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.utils.UtilsTools;
import com.study.lyan.smartbuilder.view.ChangePhotoDialog;
import com.study.lyan.smartbuilder.view.CustomDialog;
import com.study.lyan.smartbuilder.view.LoginDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.I;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lyan on 17/2/9.
 * 个人中心
 */

public class UserFragment extends BaseFragment {

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.edit_user)
    TextView editUser;
    //名称、性别、年龄、简介
    @BindViews(value = {R.id.et_username, R.id.et_sex, R.id.et_age, R.id.et_desc})
    List<EditText> etList;
    @BindView(R.id.btn_update_ok)
    Button btnUpdateOk;
    @BindView(R.id.tv_courier)
    TextView tvCourier;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_exit_user)
    Button btnExitUser;
    private SharePreferenceHelper share;
    private LoginDialog dialog;
    private ChangePhotoDialog changePhotoDialog;
    @Override
    protected View toCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        return view;
    }

    @Override
    protected void setView() {
        //默认设置为不可点击
        setEditEnabled(false);
        //获取当前用户
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        //显示个人信息
        etList.get(StaticClass.USER_NAME).setText(user.getUsername());
        etList.get(StaticClass.USER_SEX).setText(user.getSex() ? "男" : "女");
        etList.get(StaticClass.USER_AGE).setText(String.valueOf(user.getAge()));
        etList.get(StaticClass.USER_DESC).setText(user.getDesc());
        //设置弹窗
        dialog = new LoginDialog(getActivity());
        dialog.setHintText("跟新中");
        changePhotoDialog = new ChangePhotoDialog(this);
        share = SharePreferenceHelper.getInstance();
        //显示头像
        UtilsTools.getImageFromShare(share,profileImage);
    }

    /**
     * 设置输入框是否可以点击
     *
     * @param flag
     */
    private void setEditEnabled(boolean flag) {
        for (int i = 0; i < etList.size(); i++) {
            etList.get(i).setEnabled(flag);
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick(value = {R.id.btn_exit_user, R.id.edit_user,
            R.id.btn_update_ok,R.id.profile_image,
            R.id.tv_courier ,R.id.tv_phone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit_user://退出登录
                MyUser.logOut();//清除缓存用户对象
                BmobUser currentUser = MyUser.getCurrentUser();//获取当前用户
                if (null == currentUser) {
                    ToastUtils.shortToast(getContext(), "退出登录成功！");
                    startActivity(startTo(LoginActivity.class));//跳转到登录界面
                    getActivity().finish();
                }
                break;
            case R.id.edit_user://编辑资料
                setEditEnabled(true);//设置为可点击
                btnUpdateOk.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_update_ok://确定修改
                //获取输入框中的值
                String name = GetViewTextUtils.getTextTrim(etList.get(StaticClass.USER_NAME));
                String sex = GetViewTextUtils.getTextTrim(etList.get(StaticClass.USER_SEX));
                String age = GetViewTextUtils.getTextTrim(etList.get(StaticClass.USER_AGE));
                String desc = GetViewTextUtils.getTextTrim(etList.get(StaticClass.USER_DESC));
                //判断输入框中的内容是否为空
                if (!TextUtils.isEmpty(name) &&
                        !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(age)) {
                    dialog.show();
                    //跟新属性
                    MyUser updateUser = new MyUser();
                    updateUser.setUsername(name);
                    updateUser.setSex(TextUtils.equals("男", sex) ? true : false);
                    updateUser.setAge(Integer.parseInt(age));
                    desc = TextUtils.isEmpty(desc) ? "这个人很懒，什么都没有留下！" : desc;
                    updateUser.setDesc(desc);
                    //修改用户信息
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    updateUser.update(bmobUser.getObjectId(), updateListener);
                } else {
                    ToastUtils.shortToast(getActivity(), "输入框不能为空！");
                }
                break;
            case R.id.profile_image://更改头像
                changePhotoDialog.show();
                break;
            case R.id.tv_courier://快递查询
                startActivity(startTo(CourierActivity.class));
                break;
            case R.id.tv_phone://归属地查询
                startActivity(startTo(PhoneActivity.class));
                break;
        }
    }

    /**
     * 更新结果
     */
    private UpdateListener updateListener = new UpdateListener() {
        @Override
        public void done(BmobException e) {
            dialog.dismiss();
            if (null == e) {
                setEditEnabled(false);
                btnUpdateOk.setVisibility(View.GONE);
                ToastUtils.shortToast(getActivity(), "修改成功！");
            } else {
                ToastUtils.shortToast(getActivity(), "修改失败！");
            }
        }
    };

    /**
     * 获取图片的结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case ChangePhotoDialog.IMAGE_REQUEST_CODE://图库
                    Logger.t("图库").i(data.getData().getPath());
                    cutPicture(data.getData());//裁剪
                    break;
                case ChangePhotoDialog.CAMERA_REQUEST_CODE://相机
                    Logger.t("相机").i(changePhotoDialog.getUri().getPath());
                    cutPicture(changePhotoDialog.getUri());//裁剪
                    break;
                case ChangePhotoDialog.RESULT_REQUEST_CODE://裁剪返回
                    if (data != null){
                        Bundle bundle = data.getExtras();
                        if (bundle != null){
                            Bitmap bitmap = bundle.getParcelable("data");
                            profileImage.setImageBitmap(bitmap);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪
     * @param uri
     */
    private void cutPicture(Uri uri){
        if (null == uri){
            Logger.i("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪宽高比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪图图片的质量
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        //返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent,ChangePhotoDialog.RESULT_REQUEST_CODE);//跳转裁剪界面
    }

    /**
     * 销毁时调用
     */
    @Override
    public void onDestroy() {
        //保存头像
        UtilsTools.putImageToShare(share,profileImage);
        super.onDestroy();
    }
}
