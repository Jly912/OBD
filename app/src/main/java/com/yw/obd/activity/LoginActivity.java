package com.yw.obd.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.LoginInfo;
import com.yw.obd.http.WebService;
import com.yw.obd.util.AppData;
import com.yw.obd.util.NetworkUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/25.
 */

public class LoginActivity extends BaseActivity implements WebService.WebServiceListener {
    @Bind(R.id.et_user)
    EditText etUser;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.cb)
    CheckBox cb;
    @Bind(R.id.tv_experience)
    TextView tvExperience;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_phone_register)
    TextView tvPhoneRegister;
    @Bind(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    private static final int LOGIN = 1;
    private static final String KEY = "20170801CHLOBDYW028M";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.tv_experience, R.id.btn_login, R.id.tv_phone_register, R.id.tv_forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_experience://我要体验
                break;
            case R.id.btn_login://登录
                String user = etUser.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd)) {
                    AppData.showToast(this, R.string.input_empty);
                    return;
                }

                login(user, pwd);
                break;
            case R.id.tv_phone_register://手机注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget_pwd://忘记密码
                break;
        }
    }

    @Override
    protected void init() {
        String userName = AppData.GetInstance(this).getUserName();
        etUser.setText(userName);
        etUser.setSelection(userName.length());
        if (AppData.GetInstance(this).getLoginRemember()) {
            cb.setChecked(true);
            String userPass = AppData.GetInstance(this).getUserPass();
            etPwd.setText(userPass);
        }

    }

    private void login(String loginName, String pwd) {
        if (!NetworkUtil.isNetworkConnected(this)) {
            AppData.showToast(this, R.string.network_unable);
            return;
        }

        WebService web = new WebService(this, LOGIN, false, "Login");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", loginName);
        property.put("password", pwd);
        property.put("phoneType", 1);
        property.put("loginAPP", "JJG");
        property.put("appID", "");
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }

    @Override
    public void onWebServiceReceive(String method, int id, String result) {
        if (id == LOGIN) {
            LoginInfo loginInfo = new Gson().fromJson(result, LoginInfo.class);
            int state = Integer.parseInt(loginInfo.getState());
            switch (state) {
                case 0://成功
                    AppData.showToast(this, R.string.login_succ);
                    AppData.GetInstance(this).setUserName(loginInfo.getInfo().getUserName());
                    AppData.GetInstance(this).setTimeZone(loginInfo.getInfo().getTimeZone());
                    AppData.GetInstance(this).setUserId(Integer.parseInt(loginInfo.getInfo().getUserID()));
                    AppData.GetInstance(this).setLoginRemember(cb.isChecked());
                    AppData.GetInstance(this).setUserPass(cb.isChecked() ? etPwd.getText().toString().trim() : "");

                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                    break;
                case 1008://手机号不存在
                    AppData.showToast(this, R.string.user_empty);
                    break;
                case 2001://登录名或密码错误
                    AppData.showToast(this, R.string.name_or_pwd_error);
                    break;
                case 1002://程序报错,异常.可能参数错误等
                    AppData.showToast(this, R.string.exe_error);
                    break;
                case 3001://key不对
                    AppData.showToast(this, R.string.key_error);
                    break;
            }
        }
    }

}
