package com.yw.obd.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

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

        getSetWarn();
        cbTakeOverMsg.setOnCheckedChangeListener(this);
        cbVibrate.setOnCheckedChangeListener(this);
        cbVoice.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.iv_back, R.id.ll_take_over_msg, R.id.ll_voice, R.id.ll_vibrate, R.id.btn_submit})
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
                int msg = cbTakeOverMsg.isChecked() ? 1 : 0;
                int vir = cbVibrate.isChecked() ? 1 : 0;
                int voice = cbVoice.isChecked() ? 1 : 0;
                String warStr = msg + "-" + voice + "-" + vir;
                Http.setWarn(this, AppData.GetInstance(this).getSelectedDevice() + "", 1 + "", warStr, new Http.OnListener() {
                    @Override
                    public void onSucc(Object object) {
                        if (object != null) {
                            String res = (String) object;
                            try {
                                JSONObject jsonObject = new JSONObject(res);
                                int state = jsonObject.getInt("state");
                                switch (state) {
                                    case 0:
                                        AppData.GetInstance(MsgSettingActivity.this).setAlarmAlert(cbTakeOverMsg.isChecked());
                                        AppData.GetInstance(MsgSettingActivity.this).setAlertSound(cbVoice.isChecked());
                                        AppData.GetInstance(MsgSettingActivity.this).setAlertVibration(cbVibrate.isChecked());
                                        AppData.showToast(MsgSettingActivity.this, R.string.setSucc);
                                        break;
                                    default:
                                        AppData.showToast(MsgSettingActivity.this, R.string.setFail);
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        CheckBox cb = (CheckBox) buttonView;
        switch (cb.getId()) {
            case R.id.cb_take_over_msg://接收通知
                if (!isChecked) {
                    cbVibrate.setEnabled(false);
                    cbVoice.setEnabled(false);
                    cbVibrate.setChecked(false);
                    cbVoice.setChecked(false);
                } else {
                    cbVibrate.setEnabled(true);
                    cbVoice.setEnabled(true);
                }
                break;
            case R.id.cb_voice:
                break;
            case R.id.cb_vibrate:
                break;
        }
    }

    private void getSetWarn() {
        Http.getSetWarn(this, AppData.GetInstance(this).getSelectedDevice() + "", 1 + "", new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        int state = jsonObject.getInt("state");
                        switch (state) {
                            case 0:
                                String warnStr = jsonObject.getString("warnSet");
                                String[] split = warnStr.split("-");
                                String msg = split[0];
                                String voice = split[1];
                                String vir = split[2];
                                if (msg.equals("1")) {
                                    cbTakeOverMsg.setChecked(true);
                                } else {
                                    cbTakeOverMsg.setChecked(false);
                                }

                                if (vir.equals("1")) {
                                    cbVibrate.setChecked(true);
                                } else {
                                    cbVibrate.setChecked(false);
                                }

                                if (voice.equals("1")) {
                                    cbVoice.setChecked(true);
                                } else {
                                    cbVoice.setChecked(false);
                                }
                                break;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
