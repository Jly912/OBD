package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
 * Created by apollo on 2017/8/25.
 */

public class WifiSetActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tvSN)
    TextView tvSN;
    @Bind(R.id.ll_sn)
    LinearLayout llSn;
    @Bind(R.id.cb_wifi)
    CheckBox cbWifi;
    @Bind(R.id.ll_wifi)
    LinearLayout llWifi;
    @Bind(R.id.btnReset)
    Button btnReset;
    @Bind(R.id.btnSet)
    Button btnSet;

    private String status = "";
    private String name = "";
    private String pwd = "";
    private String sn = "";
    private int model = 38;
    private static final int OPEN = 1;
    private static final int CLOSE = 2;
    private static final int RESET = 3;
    private int getResponseTime = 0;
    private int commandType = 0;
    private boolean isFirst = false;

    private Handler getResponseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://关闭
                    getResponse(commandType, CLOSE);
                    break;
                case 1://打开
                    getResponse(commandType, OPEN);
                    break;
                case 2:
                    getResponse(commandType, RESET);
                    break;
            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wifi_set_1;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.wifi_setting);
        sn = AppData.GetInstance(this).getSN();
        Log.e("print", "sn---" + sn);
        tvSN.setText(sn);
        cbWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = cbWifi.isChecked();
                Log.d("print", "==onclick=" + checked);
                if (checked) {
                    if (isFirst) {
                        Intent intent = new Intent(WifiSetActivity.this, WifiEditActivity.class);
                        intent.putExtra("sn", sn);
                        intent.putExtra("name", name);
                        intent.putExtra("pwd", pwd);
                        startActivity(intent);
                        isFirst = false;
                        return;
                    }
                    //开启wifi
                    sendCommand(sn, 1 + "," + name + "," + pwd, OPEN);
                } else {
                    //关闭wifi
                    sendCommand(sn, 0 + ",,", CLOSE);
                }
            }
        });

        getWifiSetInfo();
    }

    @OnClick({R.id.iv_back, R.id.btnReset, R.id.btnSet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btnReset:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.sure_reset_wifi)
                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendCommand(sn, 2 + ",,", RESET);
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.btnSet:
                Intent intent = new Intent(this, WifiEditActivity.class);
                Log.e("print", "--set-" + name + "---" + sn + "----" + pwd);
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(sn) || TextUtils.isEmpty(pwd)) {
                    return;
                }
                intent.putExtra("sn", sn);
                intent.putExtra("name", name);
                intent.putExtra("pwd", pwd);
                startActivity(intent);
                break;
        }
    }

    private void getWifiSetInfo() {
        Http.getDeviceSetInfo(this, AppData.GetInstance(this).getSelectedDevice() + "", new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        int state = jsonObject.getInt("state");
                        switch (state) {
                            case 0:
                                String wifi = jsonObject.getString("wifi");
                                String[] split = wifi.split(",");
                                status = split[0];
                                if (status.equals("1")) {
                                    cbWifi.setChecked(true);
                                    if (split.length > 1) {
                                        name = split[1];
                                        pwd = split[2];
                                    }
                                } else {
                                    cbWifi.setChecked(false);
                                }
                                break;
                            case 2002:
                                isFirst = true;
                                cbWifi.setChecked(false);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendCommand(String sn, String paramter, final int type) {
        Http.sendCommand(this, sn, AppData.GetInstance(this).getSelectedDevice() + "", "8437", model + "", paramter, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    int state = Integer.parseInt(res);
                    switch (state) {
                        case -1:
                            cbWifi.setChecked(!cbWifi.isChecked());
                            AppData.showToast(WifiSetActivity.this, R.string.device_notexist);
                            break;
                        case -2:
                            cbWifi.setChecked(!cbWifi.isChecked());
                            AppData.showToast(WifiSetActivity.this, R.string.device_offline);
                            break;
                        case -3:
                            cbWifi.setChecked(!cbWifi.isChecked());
                            AppData.showToast(WifiSetActivity.this, R.string.command_send_failed);
                            break;
                        case -5:
                            cbWifi.setChecked(!cbWifi.isChecked());
                            AppData.showToast(WifiSetActivity.this, R.string.commandsave);
                            break;
                        default:
                            commandType = state;
                            AppData.showToast(WifiSetActivity.this, R.string.commandsendsuccess);
                            getResponse(state, type);
                            break;
                    }

                }
            }
        });
    }

    private void getResponse(int commandId, final int type) {
        ++getResponseTime;
        Log.e("print", "---getRes-" + getResponseTime);
        Http.getResponse(this, commandId, new Http.OnListener() {
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
                                        switch (type) {
                                            case OPEN:
                                                getResponseHandler.sendEmptyMessageDelayed(1, 5000);
                                                break;
                                            case CLOSE:
                                                getResponseHandler.sendEmptyMessageDelayed(0, 5000);
                                                break;
                                            case RESET:
                                                getResponseHandler.sendEmptyMessageDelayed(2, 5000);
                                                break;
                                        }
                                    } else {
                                        getResponseTime = 0;
                                        AppData.showToast(WifiSetActivity.this, R.string.setFail);
                                        switch (type) {
                                            case OPEN:
                                                cbWifi.setChecked(false);
                                                break;
                                            case CLOSE:
                                                cbWifi.setChecked(true);
                                                break;
                                            case RESET:
                                                break;
                                        }

                                    }


                                } else if (isResponse == 1) {
                                    if (type == OPEN) {
                                        Intent intent = new Intent(WifiSetActivity.this, WifiEditActivity.class);
                                        intent.putExtra("sn", sn);
                                        intent.putExtra("name", name);
                                        intent.putExtra("pwd", pwd);
                                        startActivity(intent);
                                    } else if (type == CLOSE) {
                                        AppData.showToast(WifiSetActivity.this, R.string.setSucc);
                                    } else if (type == RESET) {
                                        AppData.showToast(WifiSetActivity.this, R.string.setSucc);
                                        cbWifi.setChecked(false);
                                    }

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

    @Override
    protected void onResume() {
        super.onResume();
        getWifiSetInfo();
    }
}
