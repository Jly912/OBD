package com.yw.obd.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/23.
 * 电子栅栏
 */

public class ElectricFenceActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_right)
    TextView tvRight;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_electric_fence;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText(R.string.add_ele);
        tvTitle.setText(R.string.electric_fence);

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.btn_blue));
        refreshLayout.setRefreshing(false);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right://添加
                startActivity(new Intent(this, AddFenceActivity.class));
                break;
        }
    }
}
