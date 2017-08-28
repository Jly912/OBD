package com.yw.obd.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.util.AppData;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/24.
 * wifi设置
 */

public class WifiEditActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_sn)
    EditText etSn;
    @Bind(R.id.et_wifi_name)
    EditText etWifiName;
    @Bind(R.id.et_wifi_pwd)
    EditText etWifiPwd;
    @Bind(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wifi_setting;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.wifi_setting);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String sn = intent.getStringExtra("sn");
        String pwd = intent.getStringExtra("pwd");
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(sn) || TextUtils.isEmpty(pwd)) {
            return;
        }
        etWifiName.setText(name);
        etWifiName.setSelection(name.length());
        etWifiPwd.setText(pwd);
        etWifiPwd.setSelection(pwd.length());
        etSn.setText(sn);
        etSn.setText(sn.length());
    }


    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                String sn = etSn.getText().toString().trim();
                String name = etWifiName.getText().toString().trim();
                String pwd = etWifiPwd.getText().toString().trim();
                if (TextUtils.isEmpty(sn) || TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                    AppData.showToast(this, R.string.input_empty);
                    return;
                }


                break;
        }
    }
}
