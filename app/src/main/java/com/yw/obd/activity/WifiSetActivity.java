package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wifi_set_1;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.wifi_setting);

        cbWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
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
                startActivity(new Intent(this, WifiEditActivity.class));
                break;
        }
    }
}
