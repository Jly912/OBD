package com.yw.obd.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/17.
 */

public class FeedbackActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.tv_qq)
    TextView tvQq;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {
        tvTitle.setText(R.string.feedback);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
