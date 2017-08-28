package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.adapter.FenceLvAdapter;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.FenceListEntity;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

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

    private FenceListEntity fenceListEntity;
    private FenceLvAdapter adapter;

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

        adapter = new FenceLvAdapter(this);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ElectricFenceActivity.this, AddFenceActivity.class);
                intent.putExtra("new", false);
                FenceListEntity.GeofencesBean fence = (FenceListEntity.GeofencesBean) adapter.getItem(position);
                intent.putExtra("fenceId", fence.getGeofenceID());
                intent.putExtra("name", fence.getFenceName());
                intent.putExtra("lat", fence.getLat());
                intent.putExtra("lng", fence.getLng());
                intent.putExtra("radius", fence.getRadius());
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(ElectricFenceActivity.this)
                        .setTitle(R.string.promote)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String geofenceID = adapter.getData().get(position).getGeofenceID();
                                delFence(geofenceID, position);
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
            }
        });

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.btn_blue));
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getFenceList();
            }
        });

        getFenceList();
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right://添加
                Intent intent = new Intent(this, AddFenceActivity.class);
                intent.putExtra("new", true);
                startActivity(intent);
                break;
        }
    }

    private void getFenceList() {
        Http.getFenceList(this, AppData.GetInstance(this).getSelectedDevice() + "", new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
                if (object != null) {
                    String res = (String) object;
                    try {
                        int state = new JSONObject(res).getInt("state");
                        switch (state) {
                            case 0:
                                fenceListEntity = new Gson().fromJson(res, FenceListEntity.class);
                                adapter.setData(fenceListEntity.getGeofences());
                                break;
                            case 2002:
                                AppData.showToast(ElectricFenceActivity.this, R.string.no_msg);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void delFence(String fenceId, final int position) {
        Http.delFence(this, fenceId, AppData.GetInstance(this).getSelectedDevice() + "", new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    if (res.equals("0")) {
                        AppData.showToast(ElectricFenceActivity.this, R.string.del_failed);
                    } else {
                        AppData.showToast(ElectricFenceActivity.this, R.string.del_succ);
                        adapter.delData(position);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFenceList();
    }
}
