package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        cbWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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
                                status = split[1];
                                name = split[2];
                                pwd = split[3];
                                if (status.equals("1")) {
                                    cbWifi.setChecked(true);
                                } else {
                                    cbWifi.setChecked(false);
                                }
                                break;
                            case 2002:
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
}
