package com.yw.obd.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.LoginInfo;
import com.yw.obd.entity.TimerCount;
import com.yw.obd.http.WebService;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/26.
 */

public class RegisterActivity extends BaseActivity implements WebService.WebServiceListener {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_send_code)
    TextView tvSendCode;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_pwd2)
    EditText etPwd2;
    @Bind(R.id.btn_next)
    Button btnNext;
    private static final int REGISTER = 1;
    private static final int SEND_CODE = 2;
    private static final String KEY = "20170801CHLOBDYW028M";
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = etPhone.getText().toString().trim();
            String code = etCode.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();
            String pwd2 = etPwd2.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                tvSendCode.setEnabled(false);
                tvSendCode.setPressed(false);
                tvSendCode.setTextColor(getResources().getColor(R.color.board_gray));
            } else {
                tvSendCode.setEnabled(true);
                tvSendCode.setPressed(true);
                tvSendCode.setTextColor(getResources().getColor(R.color.btn_blue));
            }

            if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwd2)) {
                btnNext.setEnabled(false);
                btnNext.setPressed(false);
                btnNext.setBackgroundColor(getResources().getColor(R.color.board_gray));
            } else {
                btnNext.setEnabled(true);
                btnNext.setPressed(true);
                btnNext.setBackgroundColor(getResources().getColor(R.color.btn_blue));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TimerCount timerCount;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);

        timerCount = new TimerCount(60000, 1000, tvSendCode, this);

        btnNext.setEnabled(false);
        btnNext.setPressed(false);
        btnNext.setBackgroundColor(getResources().getColor(R.color.board_gray));

        tvSendCode.setEnabled(false);
        tvSendCode.setPressed(false);
        tvSendCode.setTextColor(getResources().getColor(R.color.board_gray));
        tvSendCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvSendCode.getPaint().setAntiAlias(true);
        tvSendCode.getPaint().setColor(getResources().getColor(R.color.btn_blue));

        tvTitle.setText(R.string.phone_register);
        etPhone.addTextChangedListener(textWatcher);
        etPwd.addTextChangedListener(textWatcher);
        etPwd2.addTextChangedListener(textWatcher);
        etCode.addTextChangedListener(textWatcher);

    }

    @OnClick({R.id.tv_send_code, R.id.btn_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                sendCode(etPhone.getText().toString().trim());
                break;
            case R.id.btn_next:
                String phone = etPhone.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                String pwd2 = etPwd2.getText().toString().trim();
                if (!pwd.equals(pwd2)) {
                    AppData.showToast(this, R.string.pwd_not_same);
                    return;
                }

                register(phone, pwd, code);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void register(String phone, String pwd, String code) {
        WebService web = new WebService(this, REGISTER, false, "Register");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", phone);
        property.put("password", pwd);
        property.put("code", code);
        property.put("phoneType", 1);
        property.put("loginAPP", "JJG");
        property.put("appID", "");
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }

    private void sendCode(String phone) {
        WebService web = new WebService(this, SEND_CODE, false, "SendSMSCode");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", phone);
        property.put("typeID", 0);
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }


    @Override
    public void onWebServiceReceive(String method, int id, String result) {
        switch (id) {
            case REGISTER:
                try {
                    JSONObject jo = new JSONObject(result);
                    int state = Integer.parseInt(jo.getString("state"));
                    if (state == 0) {
                        LoginInfo loginInfo = new Gson().fromJson(result, LoginInfo.class);
                        AppData.showToast(this, R.string.register_succ);
                        startActivity(new Intent(this, RegisterSuccActivity.class));
                        finish();
                    } else if (state == 1003) {
                        AppData.showToast(this, R.string.user_existed);
                    } else if (state == 1004) {
                        AppData.showToast(this, R.string.code_error);
                    } else if (state == 1005) {
                        AppData.showToast(this, R.string.code_over_time);
                    } else {
                        AppData.showToast(this, R.string.register_failed);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case SEND_CODE:
                int state = Integer.parseInt(result);
                if (state == 0) {//发送失败
                    AppData.showToast(this, R.string.send_failed);
                    return;
                } else if (state == 1) {//发送成功
                    AppData.showToast(this, R.string.send_succ);
                    timerCount.start();
                } else if (state == 2) {//超过限制
                    AppData.showToast(this, R.string.over_limit);
                } else if (state == 3) {
                    AppData.showToast(this, R.string.sms_send_frequency);
                }
                break;
        }
    }

}
