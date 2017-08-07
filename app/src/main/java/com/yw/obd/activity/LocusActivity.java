package com.yw.obd.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/28.
 * 轨迹
 */

public class LocusActivity extends BaseActivity {

    //标题栏
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    //地图相关
    @Bind(R.id.map)
    MapView map;

    //底部时间相关
    @Bind(R.id.tv_from_time)
    TextView tvFromTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.iv_cur_speed)
    ImageButton ivCurSpeed;
    @Bind(R.id.tv_cur_speed)
    TextView tvCurSpeed;

    //平均油耗、平均速度相关
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.tv_accelerate)
    TextView tvAccelerate;
    @Bind(R.id.tv_moderate)
    TextView tvModerate;
    @Bind(R.id.tv_brake)
    TextView tvBrake;
    @Bind(R.id.tv_turn)
    TextView tvTurn;
    @Bind(R.id.iv_oil)
    ImageView ivOil;
    @Bind(R.id.tv_ave_fuel)
    TextView tvAveFuel;
    @Bind(R.id.iv_speed)
    ImageView ivSpeed;
    @Bind(R.id.tv_ave_speed)
    TextView tvAveSpeed;
    @Bind(R.id.ll_speed)
    LinearLayout llSpeed;

    //底部相关
    @Bind(R.id.iv_time)
    ImageView ivTime;
    @Bind(R.id.tv_drive_time)
    TextView tvDriveTime;
    @Bind(R.id.iv_money)
    ImageView ivMoney;
    @Bind(R.id.tv_ref_money)
    TextView tvRefMoney;
    @Bind(R.id.iv_fuel)
    ImageView ivFuel;
    @Bind(R.id.tv_spend_fuel)
    TextView tvSpendFuel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_locus;
    }

    @Override
    protected void init() {
        tvTitle.setText(getResources().getString(R.string.locus));
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_back, R.id.tv_from_time, R.id.tv_end_time, R.id.iv_cur_speed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_from_time:
                break;
            case R.id.tv_end_time:
                break;
            case R.id.iv_cur_speed:
                break;
        }
    }
}
