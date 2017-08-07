package com.yw.obd.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/3.
 */

public class RegisterSuccActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_succ;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.phone_register);
    }

    @OnClick({R.id.iv_back, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}
