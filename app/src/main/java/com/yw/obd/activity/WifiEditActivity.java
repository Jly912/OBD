package com.yw.obd.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private String sn = "";
    private String name = "";
    private String pwd = "";
    private int model = 38;
    private int getResponseTime = 0;
    private int commandType;

    private Handler getResponseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getResponse(commandType);

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wifi_setting;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.wifi_setting);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        sn = intent.getStringExtra("sn");
        pwd = intent.getStringExtra("pwd");

        etWifiName.setText(name);
        etWifiName.setSelection(name.length());
        etWifiPwd.setText(pwd);
        etWifiPwd.setSelection(pwd.length());
        etSn.setText(sn);
        etSn.setSelection(sn.length());
    }


    @OnClick({R.id.iv_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_submit:
                String sn = etSn.getText().toString().trim();
                name = etWifiName.getText().toString().trim();
                pwd = etWifiPwd.getText().toString().trim();
                if (TextUtils.isEmpty(sn) || TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                    AppData.showToast(this, R.string.input_empty);
                    return;
                }


                sendCommand(sn);
                break;
        }
    }

    private void sendCommand(String sn) {
        Http.sendCommand(this, sn, AppData.GetInstance(this).getSelectedDevice() + "", "8437", model + "", 1 + "," + name + "," + pwd, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    int state = Integer.parseInt(res);
                    switch (state) {
                        case -1:
                            AppData.showToast(WifiEditActivity.this, R.string.device_notexist);
                            break;
                        case -2:
                            AppData.showToast(WifiEditActivity.this, R.string.device_offline);
                            break;
                        case -3:
                            AppData.showToast(WifiEditActivity.this, R.string.command_send_failed);
                            break;
                        case -5:
                            AppData.showToast(WifiEditActivity.this, R.string.commandsave);
                            break;
                        default:
                            commandType = state;
                            AppData.showToast(WifiEditActivity.this, R.string.commandsendsuccess);
                            getResponse(state);
                            break;
                    }

                }
            }
        });
    }

    private void getResponse(int commandId) {
        ++getResponseTime;
        Http.getResponse(WifiEditActivity.this, commandId, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        int state = jsonObject.getInt("state");
                        switch (state) {
                            case 0:
                                int isResponse = jsonObject.getInt("isResponse");
                                String responseMsg = jsonObject.getString("responseMsg");
                                if (isResponse == 0) {
                                    if (getResponseTime < 3) {
                                        getResponseHandler.sendEmptyMessageDelayed(0,5000);
                                    } else {
                                        getResponseTime = 0;
                                        AppData.showToast(WifiEditActivity.this, R.string.commandsendtimeout);
                                    }

                                } else if (isResponse == 1) {
                                    AppData.showToast(WifiEditActivity.this, R.string.setSucc);
                                    finish();
                                }
                                break;
                            default:
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
