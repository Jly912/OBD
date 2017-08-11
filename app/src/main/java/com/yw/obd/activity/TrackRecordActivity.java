package com.yw.obd.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.adapter.TrackListAdapter;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.entity.TrackListInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.NetworkUtil;
import com.yw.obd.util.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by apollo on 2017/7/28.
 * 轨迹记录
 */

public class TrackRecordActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_car)
    TextView tvCar;
    @Bind(R.id.tv_car_number)
    TextView tvCarNumber;
    @Bind(R.id.btn_more)
    ImageButton btnMore;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.tv_start_time)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.tv_reset)
    TextView tvReset;
    @Bind(R.id.btn_left)
    ImageButton btnLeft;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.btn_right)
    ImageButton btnRight;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private TrackListAdapter adapter;
    private int selectID;
    private DeviceListInfo deviceListInfo;
    private List<String> deviceName;

    private Calendar startCalendar = Calendar.getInstance();
    private Calendar endCalendar = Calendar.getInstance();
    SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");

    private Date startTime;
    private Date endTime;
    private List<TrackListInfo.ArrBean> arr;
    private AlertDialog loadingDia = null;
    private String date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_track_record;
    }

    @Override
    protected void init() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(inflate)
                .create();
        loadingDia.show();

        arr = new ArrayList<>();
        selectID = AppData.GetInstance(this).getSelectedDevice();

        getDeviceList();

        long l = System.currentTimeMillis();
        Date datee = new Date(l);
        tvDate.setText(sdfdate.format(datee));
        getTrackList();

        date = tvDate.getText().toString();

        adapter = new TrackListAdapter(this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getData().size() != 0) {
                    Intent intent = new Intent(TrackRecordActivity.this, LocusActivity.class);
                    String startTime = arr.get(position).getStartTime();
                    String endTime = arr.get(position).getEndTime();
                    String historyId = arr.get(position).getId();
                    intent.putExtra("start", startTime);
                    intent.putExtra("end", endTime);
                    intent.putExtra("id", historyId);
                    startActivity(intent);
                }

            }
        });

        tvTitle.setText(getResources().getString(R.string.track_record));
        ivBack.setVisibility(View.VISIBLE);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.btn_blue));
        refreshLayout.setRefreshing(false);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                getTrackList();
            }
        });

    }

    private void getDeviceList() {
        Http.getDeviceList(this, new Http.OnListener() {
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
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            deviceName = new ArrayList<>();
                            for (DeviceListInfo.ArrBean device : deviceListInfo.getArr()) {
                                deviceName.add(device.getName() + " " + device.getCarNum());
                                if (Integer.parseInt(device.getId()) == selectID) {
                                    tvCar.setText(device.getName());
                                    tvCarNumber.setText(device.getCarNum());
                                }
                            }
                            break;
                        case 2002:
                            AppData.showToast(TrackRecordActivity.this, R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getTrackList() {
        loadingDia.show();
        if (NetworkUtil.isNetworkConnected(this)) {
            String startTime = tvDate.getText() + " " + tvStartTime.getText();
            String endTime = tvDate.getText() + " " + tvEndTime.getText();
            Http.getTrackList(this, startTime, endTime, new Http.OnListener() {
                @Override
                public void onSucc(Object object) {
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }

                    if (loadingDia != null && loadingDia.isShowing()) {
                        loadingDia.dismiss();
                    }

                    String result = (String) object;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        int state = Integer.parseInt(jsonObject.getString("state"));
                        switch (state) {
                            case 0:
                                TrackListInfo trackListInfo = new Gson().fromJson(result, TrackListInfo.class);
                                arr = trackListInfo.getArr();
                                adapter.setData(arr);
                                break;
                            case 2002:
                                adapter.getData().removeAll(arr);
                                adapter.setData(arr);
                                AppData.showToast(TrackRecordActivity.this, R.string.no_msg);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    }

    @OnClick({R.id.iv_back, R.id.btn_more, R.id.tv_start_time, R.id.tv_end_time, R.id.tv_reset, R.id.btn_left, R.id.tv_date, R.id.btn_right, R.id.ll_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_car:
            case R.id.btn_more:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.select_car)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, deviceName), 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DeviceListInfo.ArrBean arrBean = deviceListInfo.getArr().get(which);
                                tvCar.setText(arrBean.getName());
                                tvCarNumber.setText(arrBean.getCarNum());
                                selectID = Integer.parseInt(arrBean.getId());
                                AppData.GetInstance(TrackRecordActivity.this).setSelectedDevice(selectID);
                                getTrackList();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
            case R.id.tv_start_time:
                TimePickerDialog dialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                startCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                startCalendar.set(Calendar.MINUTE, minute);
                                startTime = startCalendar.getTime();
                                tvStartTime.setText(sdftime.format(startTime));
                            }
                        },
                        startCalendar.get(Calendar.HOUR_OF_DAY),
                        startCalendar.get(Calendar.MINUTE), true);
                dialog.show();
                break;
            case R.id.tv_end_time:

                TimePickerDialog dialog1 = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
                                // TODO Auto-generated method stub
                                endCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                endCalendar.set(Calendar.MINUTE, minute);
                                endTime = endCalendar.getTime();
                                tvEndTime.setText(sdftime.format(endTime));
                            }
                        },
                        endCalendar.get(Calendar.HOUR_OF_DAY),
                        endCalendar.get(Calendar.MINUTE), true);
                dialog1.show();
                break;
            case R.id.tv_reset:
                if (startTime == null || endTime == null) {
                    getTrackList();
                    return;
                }
                if (startTime.after(endTime)) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.waring)
                            .setMessage(R.string.waring_start_time_is_after_end)
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                long diff = endTime.getTime() - startTime.getTime();
                double days = (double) diff / (1000.0 * 60.0 * 60.0 * 24.0);
                if (days > 1) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.waring)
                            .setMessage(R.string.waring_time)
                            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                    dialog.dismiss();
                                }
                            }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                    return;

                }

                getTrackList();
                break;
            case R.id.btn_left:
                date = getDate(date, -1);
                tvDate.setText(date);
                getTrackList();
                break;
            case R.id.tv_date:
                DatePickerDialog dialog2 = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                startCalendar.set(Calendar.YEAR, year);
                                startCalendar.set(Calendar.MONTH, monthOfYear);
                                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                endCalendar.set(Calendar.YEAR, year);
                                endCalendar.set(Calendar.MONTH, monthOfYear);
                                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                endCalendar.set(Calendar.HOUR_OF_DAY, 23);
                                endCalendar.set(Calendar.MINUTE, 59);

                                // TODO Auto-generated method stub
                                startTime = startCalendar.getTime();
                                endTime = endCalendar.getTime();

                                date = sdfdate.format(startTime);
                                try {
                                    Date d=sdfdate.parse(date);
                                    Date date = new Date(System.currentTimeMillis());
                                    if (d.getTime()>date.getTime()){
                                        AppData.showToast(TrackRecordActivity.this,R.string.select_before);
                                        return;
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                tvDate.setText(sdfdate.format(startTime));
                            }

                        },
                        startCalendar.get(Calendar.YEAR),
                        startCalendar.get(Calendar.MONTH),
                        startCalendar.get(Calendar.DAY_OF_MONTH));
                dialog2.show();
                break;
            case R.id.btn_right:
                if (date.equals(TimeUtils.getToday())) {
                    return;
                }

                date = getDate(date, 1);
                tvDate.setText(date);
                getTrackList();
                break;
        }
    }

    private String getDate(String data, int i) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            // 通过日历获取下一天日期
            Calendar cal = Calendar.getInstance();
            cal.setTime(sf.parse(data));
            cal.add(Calendar.DAY_OF_YEAR, i);
            return sf.format(cal.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

}
