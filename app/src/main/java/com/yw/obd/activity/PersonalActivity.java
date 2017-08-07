package com.yw.obd.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apollo on 2017/8/1.
 * 个人资料
 */

public class PersonalActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.actionbar)
    LinearLayout actionbar;
    @Bind(R.id.iv_icon)
    CircleImageView ivIcon;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.iv_name_more)
    ImageButton ivNameMore;
    @Bind(R.id.ll_name)
    LinearLayout llName;
    @Bind(R.id.tv_sex)
    TextView tvSex;
    @Bind(R.id.iv_sex_more)
    ImageButton ivSexMore;
    @Bind(R.id.ll_sex)
    LinearLayout llSex;
    @Bind(R.id.tv_tel)
    TextView tvTel;
    @Bind(R.id.iv_tel_more)
    ImageButton ivTelMore;
    @Bind(R.id.ll_tel)
    LinearLayout llTel;
    @Bind(R.id.iv_add_more)
    ImageButton ivAddMore;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void init() {
        tvTitle.setText(getResources().getString(R.string.personal_profile));
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.iv_icon, R.id.iv_name_more, R.id.ll_name, R.id.iv_sex_more, R.id.ll_sex, R.id.iv_tel_more, R.id.ll_tel, R.id.iv_add_more, R.id.ll_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_icon:
                break;
            case R.id.iv_name_more:
                break;
            case R.id.ll_name:
                break;
            case R.id.iv_sex_more:
                break;
            case R.id.ll_sex:
                break;
            case R.id.iv_tel_more:
                break;
            case R.id.ll_tel:
                break;
            case R.id.iv_add_more:
                break;
            case R.id.ll_add:
                break;
        }
    }
}
