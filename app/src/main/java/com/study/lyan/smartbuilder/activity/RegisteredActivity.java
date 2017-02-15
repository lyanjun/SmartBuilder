package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.entity.MyUser;
import com.study.lyan.smartbuilder.utils.GetViewTextUtils;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.view.LoginDialog;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册界面
 */
public class RegisteredActivity extends BaseActivity {


    @BindView(R.id.et_user)
    EditText etUser;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btnRegistered)
    Button btnRegistered;
    @BindView(R.id.rb_boy)
    RadioButton rbBoy;
    @BindView(R.id.rb_girl)
    RadioButton rbGirl;

    private boolean isGender = true;//判断性别
    private LoginDialog dialog;
    @Override
    protected int addLayout() {
        return R.layout.activity_registered;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        dialog = new LoginDialog(this);
        dialog.setHintText("请稍等");
    }

    @Override
    protected boolean showBackHomeButton() {
        return true;
    }

    /**
     * 注册用户
     */
    @OnClick(value = R.id.btnRegistered)
    public void onClick() {
        //获取输入框的值
        String name = GetViewTextUtils.getTextTrim(etUser);
        String age = GetViewTextUtils.getTextTrim(etAge);
        String desc = GetViewTextUtils.getTextTrim(etDesc);
        String pass = GetViewTextUtils.getTextTrim(etPass);
        String password = GetViewTextUtils.getTextTrim(etPassword);
        String email = GetViewTextUtils.getTextTrim(etEmail);
        //判断是否为空
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) &&
                !TextUtils.isEmpty(pass) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(email)) {
            //判断两次密码输入是否一致
            if (!TextUtils.equals(pass, password)) {
                ToastUtils.shortToast(this, "两次输入的密码不一致！");
                return;
            }
            dialog.show();
            //判断简介是否为空
            desc = TextUtils.isEmpty(desc) ? "这个人很懒，什么都没有留下！" : desc;
            //注册
            MyUser user = new MyUser();
            user.setUsername(name);
            user.setPassword(password);
            user.setEmail(email);
            user.setAge(Integer.parseInt(age));
            user.setSex(isGender);
            user.setDesc(desc);
            user.signUp(saveListener);
        } else {
            ToastUtils.shortToast(this, "输入框不能为空！");
        }
    }

    /**
     * 返回注册结果
     */
    private SaveListener<MyUser> saveListener = new SaveListener<MyUser>() {
        @Override
        public void done(MyUser myUser, BmobException e) {
            dialog.dismiss();
            if (null == e) {
                ToastUtils.shortToast(RegisteredActivity.this, "注册成功！");
                finish();
            } else {
                ToastUtils.shortToast(RegisteredActivity.this, "注册失败！" + e.getMessage());
            }
        }
    };

    /**
     * 选择性别
     */
    @OnCheckedChanged(value = {R.id.rb_boy,R.id.rb_girl})
    public void onCheckedChanged(CompoundButton view) {
        switch (view.getId()) {
            case R.id.rb_boy:
                isGender = true;//男
                break;
            case R.id.rb_girl:
                isGender = false;//女
                break;
        }
    }

}
