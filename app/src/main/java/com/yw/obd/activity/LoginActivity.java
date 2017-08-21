package com.yw.obd.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.entity.LoginInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/25.
 */

public class LoginActivity extends BaseActivity {
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
    private Dialog loadingDia;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(inflate)
                .create();

        String userName = AppData.GetInstance(this).getUserName();
        etUser.setText(userName);
        etUser.setSelection(userName.length());
        if (AppData.GetInstance(this).getLoginRemember()) {
            cb.setChecked(true);
            String userPass = AppData.GetInstance(this).getUserPass();
            etPwd.setText(userPass);
        }

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

                if (!NetworkUtil.isNetworkConnected(this)) {
                    AppData.showToast(this, R.string.network_unable);
                    return;
                }

                if (loadingDia != null && !loadingDia.isShowing()) {
                    loadingDia.show();
                }
                Http.getLogin(this, user, pwd, new Http.OnListener() {
                    @Override
                    public void onSucc(Object object) {
                        if (loadingDia != null && loadingDia.isShowing()) {
                            loadingDia.dismiss();
                        }
                        String res = (String) object;
                        try {
                            int state = Integer.parseInt(new JSONObject(res).getString("state"));
                            switch (state) {
                                case 0://成功
                                    LoginInfo loginInfo = new Gson().fromJson(res, LoginInfo.class);
                                    AppData.showToast(LoginActivity.this, R.string.login_succ);
                                    AppData.GetInstance(LoginActivity.this).setUserName(etUser.getText().toString().trim());
                                    AppData.GetInstance(LoginActivity.this).setTimeZone(loginInfo.getInfo().getTimeZone());
                                    AppData.GetInstance(LoginActivity.this).setUserId(Integer.parseInt(loginInfo.getInfo().getUserID()));
                                    AppData.GetInstance(LoginActivity.this).setLoginRemember(cb.isChecked());
                                    AppData.GetInstance(LoginActivity.this).setUserPass(etPwd.getText().toString().trim());
                                    getDeviceList();
                                    break;
                                case 1008://手机号不存在
                                    AppData.showToast(LoginActivity.this, R.string.user_empty);
                                    break;
                                case 2001://登录名或密码错误
                                    AppData.showToast(LoginActivity.this, R.string.name_or_pwd_error);
                                    break;
                                case 1002://程序报错,异常.可能参数错误等
                                    AppData.showToast(LoginActivity.this, R.string.exe_error);
                                    break;
                                case 3001://key不对
                                    AppData.showToast(LoginActivity.this, R.string.key_error);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.tv_phone_register://手机注册
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", "register");
                startActivity(intent);
                break;
            case R.id.tv_forget_pwd://忘记密码
                Intent intent2 = new Intent(this, RegisterActivity.class);
                intent2.putExtra("type", "forget");
                startActivity(intent2);
                break;
        }
    }

    private DeviceListInfo deviceListInfo;

    private void getDeviceList() {
        Http.getDeviceList(this, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = new JSONObject(res).getInt("state");
                    switch (state) {
                        case 0:
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                            break;
                        case 2002:
                            Intent intent = new Intent(LoginActivity.this, AddCarActivity.class);
                            intent.putExtra("type", "login");
                            startActivity(intent);
                            finish();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (loadingDia != null) {
            loadingDia.dismiss();
            loadingDia = null;
        }
        super.onDestroy();
    }
}
