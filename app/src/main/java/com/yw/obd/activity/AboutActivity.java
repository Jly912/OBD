package com.yw.obd.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.app.AppContext;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/17.
 */

public class AboutActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_logo)
    ImageView ivLogo;
    @Bind(R.id.tvCode)
    TextView tvCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.about);
        PackageInfo packageInfo = null;
        try {
            packageInfo = AppContext.getContext()
                    .getPackageManager()
                    .getPackageInfo(AppContext.getContext().getPackageName(), 0);
            tvCode.setText(packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
