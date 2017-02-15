package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.entity.MyUser;
import com.study.lyan.smartbuilder.helper.SharePreferenceHelper;
import com.study.lyan.smartbuilder.utils.GetViewTextUtils;
import com.study.lyan.smartbuilder.utils.StaticClass;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.view.LoginDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.btn_registered)
    Button btnRegistered;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.keep_psw)
    CheckBox keepPsw;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.forget_paw)
    TextView forgetPaw;
    //用户信息
    private final MyUser user = new MyUser();
    private SharePreferenceHelper share;
    private String name;//用户
    private String pass;//密码
    private LoginDialog dialog;//自定义弹窗
    @Override
    protected int addLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        share = SharePreferenceHelper.getInstance();
        //设置复选框默认效果
        keepPsw.setChecked((Boolean) share.get(StaticClass.IS_SAVE_PASSWORED,false));
        if (keepPsw.isChecked()){
            etName.setText(String.valueOf(share.get(StaticClass.LOGIN_USER,StaticClass.NONE_TEXT)));
            etPass.setText(String.valueOf(share.get(StaticClass.LOGIN_PASS,StaticClass.NONE_TEXT)));
        }
        //实例化自定义弹窗
        dialog = new LoginDialog(this);
        dialog.setHintText("正在登陆");
    }

    @Override
    protected boolean showBackHomeButton() {
        return false;
    }


    /**
     * 自发生改变
     */
    @OnTextChanged(value = {R.id.et_name,R.id.et_pass},callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChanged() {
        //获取输入框的值
        name = GetViewTextUtils.getTextTrim(etName);
        pass = GetViewTextUtils.getTextTrim(etPass);
    }
    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick(value = {R.id.btn_registered,R.id.btnLogin,R.id.forget_paw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_registered://注册
                //跳转到注册界面
                startActivity(startTo(RegisteredActivity.class));
                break;
            case R.id.btnLogin://登陆

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)){
                    dialog.show();//显示弹窗
                    user.setUsername(name);
                    user.setPassword(pass);
                    user.login(saveListener);
                }else {
                    ToastUtils.shortToast(this,"输入框不能为空！");
                }
                break;
            case R.id.forget_paw://忘记密码
                //跳转到找回密码
                startActivity(startTo(ForgetPassWordActivity.class));
                break;
        }
    }

    /**
     * 返回登陆结果
     */
    private SaveListener<MyUser> saveListener = new SaveListener<MyUser>() {
        @Override
        public void done(MyUser myUser, BmobException e) {
            dialog.dismiss();//隐藏弹窗
            if (null == e) {
                //判断邮箱是否验证
                if (user.getEmailVerified()){
                    ToastUtils.shortToast(LoginActivity.this, "登陆成功！");
                    startActivity(startTo(MainActivity.class));
                    finish();
                }else {
                    ToastUtils.shortToast(LoginActivity.this, "请验证邮箱！");
                }

            } else {
                ToastUtils.shortToast(LoginActivity.this, "登陆失败！" + e.getMessage());
            }
        }
    };

    /**
     * 在界面销毁时保存状态
     */
    @Override
    protected void onDestroy() {
        boolean saveSate = keepPsw.isChecked();
        //保存状态
        share.put(StaticClass.IS_SAVE_PASSWORED,saveSate);
        //是否记住密码
        if (saveSate){
            share.put(StaticClass.LOGIN_USER,name).put(StaticClass.LOGIN_PASS,pass);
        }else {
            share.remove(StaticClass.LOGIN_USER).remove(StaticClass.LOGIN_PASS);
        }
        share.commit();//提交
        super.onDestroy();
    }
}
