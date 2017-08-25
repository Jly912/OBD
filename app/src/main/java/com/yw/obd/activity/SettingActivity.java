package com.yw.obd.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/24.
 * 设置界面
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_wifi_more)
    ImageButton ivWifiMore;
    @Bind(R.id.ll_set_wifi)
    LinearLayout llSetWifi;
    @Bind(R.id.iv_msg_more)
    ImageButton ivMsgMore;
    @Bind(R.id.ll_set_msg)
    LinearLayout llSetMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.setting);
    }

    @OnClick({R.id.iv_back, R.id.iv_wifi_more, R.id.ll_set_wifi, R.id.iv_msg_more, R.id.ll_set_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_wifi_more:
            case R.id.ll_set_wifi:
                startActivity(new Intent(this, WifiSetActivity.class));
                break;
            case R.id.iv_msg_more:
            case R.id.ll_set_msg:
                startActivity(new Intent(this, MsgSettingActivity.class));
                break;
        }
    }
}
