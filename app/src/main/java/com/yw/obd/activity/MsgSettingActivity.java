package com.yw.obd.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/24.
 * 消息设置
 */

public class MsgSettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.cb_take_over_msg)
    CheckBox cbTakeOverMsg;
    @Bind(R.id.ll_take_over_msg)
    LinearLayout llTakeOverMsg;
    @Bind(R.id.cb_voice)
    CheckBox cbVoice;
    @Bind(R.id.ll_voice)
    LinearLayout llVoice;
    @Bind(R.id.cb_vibrate)
    CheckBox cbVibrate;
    @Bind(R.id.ll_vibrate)
    LinearLayout llVibrate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msg_setting;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.msg_setting);

        cbTakeOverMsg.setOnCheckedChangeListener(this);
        cbVibrate.setOnCheckedChangeListener(this);
        cbVoice.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_take_over_msg, R.id.ll_voice, R.id.ll_vibrate,R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_take_over_msg:
                break;
            case R.id.ll_voice:
                break;
            case R.id.ll_vibrate:
                break;
            case R.id.btn_submit:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox cb = (CheckBox) buttonView;
        switch (cb.getId()) {
            case R.id.cb_take_over_msg:
                break;
            case R.id.cb_voice:
                break;
            case R.id.cb_vibrate:
                break;
        }
    }
}
