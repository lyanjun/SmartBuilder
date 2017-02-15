package com.study.lyan.smartbuilder.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.study.lyan.smartbuilder.R;
import com.study.lyan.smartbuilder.entity.MyUser;
import com.study.lyan.smartbuilder.utils.GetViewTextUtils;
import com.study.lyan.smartbuilder.utils.ToastUtils;
import com.study.lyan.smartbuilder.view.LoginDialog;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPassWordActivity extends BaseActivity {


    @BindView(R.id.et_now_pass)
    EditText etNowPass;
    @BindView(R.id.et_new_pass)
    EditText etNewPass;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btnForget)
    Button btnForget;
    private String email;//邮箱
    private String old;//旧密码
    private String new1;//新密码
    private String new2;//确认新密码
    private int index;//标记
    private LoginDialog dialog;
    @Override
    protected int addLayout() {
        return R.layout.activity_forget_pass_word;
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
     * 点击事件
     *
     * @param view
     */
    @OnClick(value = {R.id.btnUpdate, R.id.btnForget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnUpdate://重置密码
                old = GetViewTextUtils.getTextTrim(etNowPass);
                new1 = GetViewTextUtils.getTextTrim(etNewPass);
                new2 = GetViewTextUtils.getTextTrim(etNewPassword);
                if (!TextUtils.isEmpty(old) && !TextUtils.isEmpty(new1)
                        && !TextUtils.isEmpty(new2)){
                    if (!TextUtils.equals(new1,new2)){
                        ToastUtils.shortToast(this, "两次输入的密码不一致！");
                        return;
                    }
                    dialog.show();
                    index = 0;
                    //通过修改密码更改密码
                    MyUser.updateCurrentUserPassword(old,new2,updateListener);
                }else {
                    ToastUtils.shortToast(this, "请检查输入框！");
                }
                break;
            case R.id.btnForget://忘记密码
                email = GetViewTextUtils.getTextTrim(etEmail);
                if (TextUtils.isEmpty(email)) {
                    ToastUtils.shortToast(this, "请输入邮箱！");
                    return;
                }
                dialog.show();
                index = 1;
                //通过邮箱重置密码
                MyUser.resetPasswordByEmail(email, updateListener);
                break;
        }
    }

    /**
     * 使用邮箱更新密码回调
     */
    private UpdateListener updateListener = new UpdateListener() {
        @Override
        public void done(BmobException e) {
            String success = index == 1 ? "邮箱已发送至：" + email + "！" : "修改密码成功！";
            String failure = index == 1 ? "邮箱发送失败！" : "修改密码失败！";
            dialog.dismiss();
            if (null == e) {
                ToastUtils.shortToast(ForgetPassWordActivity.this, success);
                finish();
            } else {
                ToastUtils.shortToast(ForgetPassWordActivity.this, failure);
            }
        }
    };
}
