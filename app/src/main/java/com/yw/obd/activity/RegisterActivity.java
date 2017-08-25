package com.yw.obd.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/26.
 */

public class RegisterActivity extends BaseActivity {
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
    @Bind(R.id.btn_forget)
    Button btnForget;

    private int typeCode;

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

                btnForget.setEnabled(false);
                btnForget.setPressed(false);
                btnForget.setBackgroundColor(getResources().getColor(R.color.board_gray));
            } else {
                btnNext.setEnabled(true);
                btnNext.setPressed(true);
                btnNext.setBackgroundColor(getResources().getColor(R.color.btn_blue));

                btnForget.setEnabled(true);
                btnForget.setPressed(true);
                btnForget.setBackgroundColor(getResources().getColor(R.color.btn_blue));
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

        etPhone.addTextChangedListener(textWatcher);
        etPwd.addTextChangedListener(textWatcher);
        etPwd2.addTextChangedListener(textWatcher);
        etCode.addTextChangedListener(textWatcher);

        String type = getIntent().getStringExtra("type");
        if (type.equals("register")) {
            tvTitle.setText(R.string.phone_register);
            typeCode = 0;
        } else if (type.equals("forget")) {
            tvTitle.setText(R.string.forget);
            btnNext.setVisibility(View.GONE);
            btnForget.setVisibility(View.VISIBLE);
            typeCode = 1;
        }

    }

    @OnClick({R.id.tv_send_code, R.id.btn_next, R.id.iv_back, R.id.btn_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                Log.e("print", "tvSendCode");
                timerCount.start();
                Http.sendSMSCode(this, etPhone.getText().toString().trim(), typeCode, new Http.OnListener() {
                    @Override
                    public void onSucc(Object object) {
                        String res = (String) object;
                        int state = Integer.parseInt(res);
                        switch (state) {
                            case 0:
                                AppData.showToast(RegisterActivity.this, R.string.send_failed);
                                break;
                            case 1:
                                AppData.showToast(RegisterActivity.this, R.string.send_succ);
                                timerCount.start();
                                break;
                            case 2:
                                AppData.showToast(RegisterActivity.this, R.string.over_limit);
                                break;
                            case 3:
                                AppData.showToast(RegisterActivity.this, R.string.sms_send_frequency);
                                break;
                        }
                    }
                });
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
            case R.id.btn_forget:
                String tel = etPhone.getText().toString().trim();
                String c = etCode.getText().toString().trim();
                String pwd11 = etPwd.getText().toString().trim();
                String pwd22 = etPwd2.getText().toString().trim();
                if (!pwd11.equals(pwd22)) {
                    AppData.showToast(this, R.string.pwd_not_same);
                    return;
                }
                forgetPwd(tel, c, pwd22);
                break;
        }
    }

    private void register(String phone, String pwd, String code) {
        Http.register(this, phone, pwd, code, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                Log.e("print", "res----" + res);
                try {
                    JSONObject jo = new JSONObject(res);
                    int state = Integer.parseInt(jo.getString("state"));
                    if (state == 0) {
                        LoginInfo loginInfo = new Gson().fromJson(res, LoginInfo.class);
                        AppData.showToast(RegisterActivity.this, R.string.register_succ);
                        Intent intent = new Intent(RegisterActivity.this, RegisterSuccActivity.class);
                        intent.putExtra("type", "register");
                        startActivity(intent);
                        finish();
                    } else if (state == 1003) {
                        AppData.showToast(RegisterActivity.this, R.string.user_existed);
                    } else if (state == 1004) {
                        AppData.showToast(RegisterActivity.this, R.string.code_error);
                    } else if (state == 1005) {
                        AppData.showToast(RegisterActivity.this, R.string.code_over_time);
                    } else {
                        AppData.showToast(RegisterActivity.this, R.string.register_failed);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void forgetPwd(String tel, String code, String pwd) {
        Http.forgetPwd(this, tel, pwd, code, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = new JSONObject(res).getInt("state");
                    switch (state) {
                        case 0:
                            AppData.showToast(RegisterActivity.this, R.string.update_succ);
                            finish();
                            break;
                        case 1003:
                            AppData.showToast(RegisterActivity.this, R.string.user_empty);
                            break;
                        case 2005:
                            AppData.showToast(RegisterActivity.this, R.string.update_succ);
                            finish();
                            break;
                        case 2004:
                            AppData.showToast(RegisterActivity.this, R.string.update_failed);
                            break;
                        case 2001:
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
        timerCount.cancel();
        super.onDestroy();
    }
}
