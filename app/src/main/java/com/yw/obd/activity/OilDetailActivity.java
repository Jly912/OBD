package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.adapter.PopuLvAdapter;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.entity.OilDetailInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/16.
 */

public class OilDetailActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_car)
    TextView tvCar;
    @Bind(R.id.tv_car_number)
    TextView tvCarNumber;
    @Bind(R.id.ll_car)
    LinearLayout llCar;
    @Bind(R.id.rl)
    RelativeLayout rl;


    @Bind(R.id.rb_day)
    RadioButton rbDay;
    @Bind(R.id.rb_week)
    RadioButton rbWeek;
    @Bind(R.id.rb_mon)
    RadioButton rbMon;
    @Bind(R.id.rg)
    RadioGroup rg;

    @Bind(R.id.iv_left)
    ImageButton ivLeft;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.iv_right)
    ImageButton ivRight;
    @Bind(R.id.tv_dis)
    TextView tvDis;

    //    底部
    @Bind(R.id.tv_all_oil)
    TextView tvAllOil;
    @Bind(R.id.tv_oil_price)
    TextView tvOilPrice;
    @Bind(R.id.tv_oil_des)
    TextView tvOilDes;
    @Bind(R.id.tv_ave_oil)
    TextView tvAveOil;
    @Bind(R.id.tv_co)
    TextView tvCo;

    private String device_id, carName, day, month, week;
    private PopupWindow popupWindow;
    private View popu;
    private ListView lv;
    private LinearLayout ll;
    private PopuLvAdapter adapter;
    private AlertDialog loadingDia;

    private DeviceListInfo deviceListInfo;
    private OilDetailInfo oilDetailInfo;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM");

    private int type = 1;
    private Date date;
    private String weekStr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oil_detail;
    }

    @Override
    protected void init() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(inflate)
                .create();

        popu = this.getLayoutInflater().inflate(R.layout.layout_popu, null);
        lv = (ListView) popu.findViewById(R.id.lv);
        ll = (LinearLayout) popu.findViewById(R.id.ll_out);
        ll.setBackgroundColor(getResources().getColor(R.color.white));

        getDeviceList();

        date = new Date(System.currentTimeMillis());
        day = sdf.format(date);
        month = sdf3.format(date);
        week = getWeek(date) + "|" + day;
        weekStr = getWeek(date) + "至" + day;

        loadData();
    }

    private void loadData() {
        rg.getChildAt(0).performClick();
        rbDay.setTextColor(getResources().getColor(R.color.white));
        tvDate.setText(day);
        getOilDetail(1, day);
        type = 1;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_day:
                        rbDay.setTextColor(getResources().getColor(R.color.white));
                        rbWeek.setTextColor(getResources().getColor(R.color.btn_blue));
                        rbMon.setTextColor(getResources().getColor(R.color.btn_blue));
                        tvOilDes.setText(R.string.day_ref_oil_feed);
                        tvDate.setText(day);
                        type = 1;
                        getOilDetail(type, day);
                        break;
                    case R.id.rb_week:
                        rbWeek.setTextColor(getResources().getColor(R.color.white));
                        rbDay.setTextColor(getResources().getColor(R.color.btn_blue));
                        rbMon.setTextColor(getResources().getColor(R.color.btn_blue));
                        tvOilDes.setText(R.string.week_ref_oil_feed);
                        tvDate.setText(weekStr);
                        type = 2;
                        getOilDetail(type, week);
                        break;
                    case R.id.rb_mon:
                        rbMon.setTextColor(getResources().getColor(R.color.white));
                        rbWeek.setTextColor(getResources().getColor(R.color.btn_blue));
                        rbDay.setTextColor(getResources().getColor(R.color.btn_blue));
                        tvOilDes.setText(R.string.month_ref_oil_feed);
                        tvDate.setText(month);
                        type = 3;
                        getOilDetail(type, month);
                        break;
                }
            }
        });
    }

    private void getOilDetail(int interval, final String dateStr) {
        loadingDia.show();
        Http.getOilDetail(this, AppData.GetInstance(this).getSelectedDevice() + "", interval, dateStr, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
                String res = (String) object;
                try {
                    int state = new JSONObject(res).getInt("state");
                    switch (state) {
                        case 0://请求成功
                            if (type == 2) {
                                tvDate.setText(weekStr);
                            } else {
                                tvDate.setText(dateStr);
                            }

                            oilDetailInfo = new Gson().fromJson(res, OilDetailInfo.class);
                            tvDis.setText(oilDetailInfo.getAllDistance());
                            tvAllOil.setText(oilDetailInfo.getAllOIL());
                            tvAveOil.setText(oilDetailInfo.getOilFuel100());
                            tvOilPrice.setText(oilDetailInfo.getOilPrice());
                            tvCo.setText(oilDetailInfo.getCarbon());
                            break;
                        case 1002://参数错误
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String tvDateStr = "";

    @OnClick({R.id.iv_back, R.id.ll_car, R.id.iv_left, R.id.tv_date, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_car:
                initPop(rl);
                break;
            case R.id.iv_left:
                String s = tvDate.getText().toString();
                if (type == 1) {//日
                    tvDateStr = getDate(s, -1);
                } else if (type == 2) {//周
                    String[] split = s.split("至");
                    String f = split[0];//周一日期
                    String nf = getDate(f, -7);
                    String ee = getDate(f, -1);
                    weekStr = nf + "至" + ee;
                    tvDateStr = nf + "|" + ee;
                } else if (type == 3) {//月
                    tvDateStr = getMonth(s, -1);
                }
                getOilDetail(type, tvDateStr);
                break;
            case R.id.tv_date:
                break;
            case R.id.iv_right:
                String s2 = tvDate.getText().toString();
                if (type == 1) {//日
                    if (sdf.format(date).equals(s2)) {
                        AppData.showToast(this, R.string.beyond_date);
                        return;
                    } else {
                        tvDateStr = getDate(s2, 1);
                    }
                } else if (type == 2) {//周
                    String[] split = s2.split("至");
                    if (split[1].equals(sdf.format(date))) {
                        AppData.showToast(this, R.string.beyond_date);
                        return;
                    } else {
                        String f = split[1];//周日日期
                        String nf = getDate(f, 1);
                        String ee = getDate(f, 7);
                        try {
                            if (sdf.parse(ee).after(date)) {
                                ee = sdf.format(date);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        weekStr = nf + "至" + ee;
                        tvDateStr = nf + "|" + ee;
                    }

                } else if (type == 3) {//月
                    if (sdf3.format(date).equals(s2)) {
                        AppData.showToast(this, R.string.beyond_date);
                        return;
                    } else {
                        tvDateStr = getMonth(s2, 1);
                    }
                }
                getOilDetail(type, tvDateStr);
                break;
        }
    }

    private void initPop(View parent) {
        if (popupWindow == null) {
            adapter = new PopuLvAdapter(this, deviceListInfo, true);
            lv.setAdapter(adapter);
            int width = DensityUtil.dip2px(this, 150);
            int height = DensityUtil.dip2px(this, 150);
            popupWindow = new PopupWindow(popu, width, height);
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;

        popupWindow.showAsDropDown(parent, xPos, 0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

                device_id = deviceListInfo.getArr().get(position).getId();
                carName = deviceListInfo.getArr().get(position).getName();
                tvCar.setText(carName);
                tvCarNumber.setText(deviceListInfo.getArr().get(position).getCarNum());
                AppData.GetInstance(OilDetailActivity.this).setSelectedDevice(Integer.parseInt(device_id));
                loadData();

            }
        });
    }

    private boolean isExist = false;

    private void getDeviceList() {
        if (loadingDia != null && !loadingDia.isShowing()) {
            loadingDia.show();
        }
        Http.getDeviceList(this, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
                String res = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    int state = Integer.parseInt(jsonObject.getString("state"));
                    switch (state) {
                        case 0:
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);

                            int selectedDevice = AppData.GetInstance(OilDetailActivity.this).getSelectedDevice();
                            for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
                                if (deviceListInfo.getArr().get(i).getId().equals(selectedDevice + "")) {
                                    device_id = deviceListInfo.getArr().get(i).getId();
                                    carName = deviceListInfo.getArr().get(i).getName();
                                    tvCar.setText(carName);
                                    tvCarNumber.setText(deviceListInfo.getArr().get(i).getCarNum());
//                                    getOil(deviceListInfo.getArr().get(i).getId());
                                    isExist = true;
                                    return;
                                }
                            }

                            if (!isExist) {
                                device_id = deviceListInfo.getArr().get(0).getId();
                                carName = deviceListInfo.getArr().get(0).getName();
                                tvCar.setText(carName);
                                tvCarNumber.setText(deviceListInfo.getArr().get(0).getCarNum());
//                                getOil(deviceListInfo.getArr().get(0).getId());
                            }

                            break;
                        case 2002:
                            AppData.showToast(OilDetailActivity.this, R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private String getMonth(String data, int i) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
            // 通过日历获取下一天日期
            Calendar cal = Calendar.getInstance();
            cal.setTime(sf.parse(data));
            cal.add(Calendar.MONTH, i);
            return sf.format(cal.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    private int des = 0;

    private String getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1://周日
                des = -6;
                break;
            case 2:
                des = 0;
                break;
            case 3:
                des = -1;
                break;
            case 4:
                des = -2;
                break;
            case 5:
                des = -3;
                break;
            case 6://周四
                des = -4;
                break;
            case 7://周五
                des = -5;
                break;

        }

        return getDate(sdf.format(date), des);
    }
}
