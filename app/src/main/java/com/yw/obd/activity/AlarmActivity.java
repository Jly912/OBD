package com.yw.obd.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.adapter.AlarmAdapter;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.WarnListInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/15.
 * 报警信息
 */

public class AlarmActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private WarnListInfo warnListInfo;
    private AlarmAdapter alarmAdapter;
    private int index = 1;

    private Dialog loadingDia;
    private boolean isLoad = false;
    private String deviceID;
    private int refresh_index = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected void init() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(inflate)
                .create();
        loadingDia.show();

        deviceID = getIntent().getStringExtra("id");
        Log.e("print", "init----" + deviceID);
        tvTitle.setText(R.string.alarm_msg);
        ivBack.setVisibility(View.VISIBLE);
        lv.setCacheColorHint(0);
        lv.setTextFilterEnabled(true);
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        if (isLoad) {
                            index++;
                            loadAlarmMsg(index);
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    isLoad = true;
                } else {
                    isLoad = false;
                }
            }
        });

        alarmAdapter = new AlarmAdapter(this);
        lv.setAdapter(alarmAdapter);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.btn_blue));
        refreshLayout.setRefreshing(false);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                if (!TextUtils.isEmpty(deviceID)) {
                    isLoad = false;
                    loadAlarmMsg(refresh_index);
                }
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(AlarmActivity.this)
                        .setTitle(R.string.promote)
                        .setMessage(R.string.del_msg)
                        .setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WarnListInfo.ArrBean arrBean = alarmAdapter.getData().get(position);
                                String msgID = arrBean.getId();
                                delMsg(msgID, position);
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return false;
            }
        });

        if (!TextUtils.isEmpty(deviceID)) {
            loadAlarmMsg(index);
        }
    }

    private void loadAlarmMsg(int index) {
        if (loadingDia != null && !loadingDia.isShowing()) {
            loadingDia.show();
        }
        Log.e("print", "loadAlarmMsg" + deviceID);
        Http.getWarnList(this, index, deviceID, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            warnListInfo = new Gson().fromJson(res, WarnListInfo.class);
                            if (isLoad) {
                                alarmAdapter.addData(warnListInfo.getArr());
                            } else {
                                alarmAdapter.clearData();
                                alarmAdapter.setData(warnListInfo.getArr());
                            }
                            AppData.GetInstance(AlarmActivity.this).setLastID(alarmAdapter.getData().get(0).getId());
                            break;
                        case 2002:
                            AppData.showToast(AlarmActivity.this, R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void delMsg(String msgID, final int position) {
        if (loadingDia != null && !loadingDia.isShowing()) {
            loadingDia.show();
        }
        Http.deleteMsg(this, deviceID, msgID, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
                String res = (String) object;
                int state = Integer.parseInt(res);
                switch (state) {
                    case 1://删除成功
                        AppData.showToast(AlarmActivity.this, R.string.del_succ);
                        alarmAdapter.removeData(position);
                        AppData.GetInstance(AlarmActivity.this).setLastID(alarmAdapter.getData().get(0).getId());
                        break;
                    default:
                        AppData.showToast(AlarmActivity.this, R.string.del_failed);
                        break;
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        if (loadingDia != null) {
            loadingDia.dismiss();
            loadingDia = null;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        if (loadingDia != null) {
            loadingDia.dismiss();
            loadingDia = null;
        }
        super.onDestroy();
    }

}
