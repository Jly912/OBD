package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.adapter.PopuLvAdapter;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.DeviceInfo;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/2.
 * 我的车辆
 */

public class MyCarActivity extends BaseActivity {
    @Bind(R.id.iv_left)
    ImageButton ivLeft;
    @Bind(R.id.tv_car_num)
    TextView tvCarNum;
    @Bind(R.id.iv_right)
    ImageButton ivRight;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.btn_add_car)
    Button btnAddCar;
    @Bind(R.id.actionbar)
    LinearLayout actionbar;
    //基本信息
    @Bind(R.id.tv_car_num2)
    TextView tvCarNum2;
    @Bind(R.id.tv_car_brand)
    TextView tvCarBrand;
    @Bind(R.id.tv_car_type)
    TextView tvCarType;
    @Bind(R.id.tv_model_year)
    TextView tvModelYear;
    @Bind(R.id.tv_gear_case)
    TextView tvGearCase;
    @Bind(R.id.tv_displacement)
    TextView tvDisplacement;
    @Bind(R.id.tv_km)
    TextView tvKm;
    @Bind(R.id.tv_vin)
    TextView tvVin;
    @Bind(R.id.tv_device_sn)
    TextView tvDeviceSn;

    @Bind(R.id.ll_car)
    LinearLayout llCar;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.tv_car)
    TextView tvCarName;
    @Bind(R.id.tv_car_number)
    TextView tvCarNum3;

    private String device_id, carName;
    private PopupWindow popupWindow;
    private View popu;
    private ListView lv;
    private LinearLayout llContent;
    private PopuLvAdapter adapter;

    private int selectPosition = 0;
    private DeviceListInfo deviceListInfo;
    private AlertDialog loadingDia = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mycar;
    }

    @Override
    protected void init() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setView(inflate)
                .create();
        loadingDia.show();

        popu = this.getLayoutInflater().inflate(R.layout.layout_popu, null);
        lv = (ListView) popu.findViewById(R.id.lv);
        llContent = (LinearLayout) popu.findViewById(R.id.ll_out);
        llContent.setBackgroundColor(getResources().getColor(R.color.white));
        getDeviceList();
    }


    private void showPopu(View parent) {
        if (popupWindow == null) {
            adapter = new PopuLvAdapter(this, deviceListInfo, true);
            lv.setAdapter(adapter);
            int width = DensityUtil.dip2px(this, 150);
            int height = DensityUtil.dip2px(this, 150);
            popupWindow = new PopupWindow(popu, width, height);
        }

        adapter.notifyDataSetChanged();
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2
                - popupWindow.getWidth() / 2;
        Log.i("print", "xPos:" + xPos);

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
                tvCarName.setText(carName);
                tvCarNum3.setText(deviceListInfo.getArr().get(position).getCarNum());
                AppData.GetInstance(MyCarActivity.this).setSelectedDevice(Integer.parseInt(device_id));
                loadingDia.show();
                getDevice(deviceListInfo.getArr().get(position).getId());
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.btn_add_car, R.id.iv_back, R.id.ll_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                if (deviceListInfo == null) {
                    ivRight.setClickable(false);
                    ivLeft.setClickable(false);
                    return;
                }
                if (selectPosition == 0) {
                    ivLeft.setClickable(false);
                    ivRight.setClickable(true);
                } else {
                    List<DeviceListInfo.ArrBean> arr = deviceListInfo.getArr();
                    DeviceListInfo.ArrBean arrBean = arr.get(--selectPosition);
                    String name = arrBean.getName();
                    tvCarName.setText(name);
                    String id = arrBean.getId();
                    loadingDia.show();
                    getDevice(id);
                }
                break;
            case R.id.iv_right:
                Log.d("print", "deviceListInfo" + deviceListInfo);
                if (deviceListInfo == null) {
                    ivRight.setClickable(false);
                    ivLeft.setClickable(false);
                    return;
                }
                if (selectPosition == deviceListInfo.getArr().size() - 1) {
                    ivRight.setClickable(false);
                    ivLeft.setClickable(true);
                } else {
                    List<DeviceListInfo.ArrBean> arr = deviceListInfo.getArr();
                    DeviceListInfo.ArrBean arrBean = arr.get(++selectPosition);
                    String name = arrBean.getName();
                    tvCarName.setText(name);
                    String id = arrBean.getId();
                    loadingDia.show();
                    getDevice(id);
                }
                break;
            case R.id.btn_add_car:
                startActivity(new Intent(this, AddCarActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_car:
                showPopu(rl);
                break;
        }
    }

    private boolean isExist = false;

    private void getDeviceList() {
        Http.getDeviceList(this, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            int selectedDevice = AppData.GetInstance(MyCarActivity.this).getSelectedDevice();
                            for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
                                if (deviceListInfo.getArr().get(i).getId().equals(selectedDevice + "")) {
                                    device_id = deviceListInfo.getArr().get(i).getId();
                                    carName = deviceListInfo.getArr().get(i).getName();
                                    tvCarName.setText(carName);
                                    tvCarNum3.setText(deviceListInfo.getArr().get(i).getCarNum());
                                    getDevice(deviceListInfo.getArr().get(i).getId());
                                    isExist = true;
                                    return;
                                }
                            }

                            if (!isExist) {
                                device_id = deviceListInfo.getArr().get(0).getId();
                                carName = deviceListInfo.getArr().get(0).getName();
                                tvCarName.setText(carName);
                                tvCarNum3.setText(deviceListInfo.getArr().get(0).getCarNum());
                                getDevice(deviceListInfo.getArr().get(0).getId());
                            }

                            break;
                        case 2002://没有查询到任何设备
                            if (loadingDia != null && loadingDia.isShowing()) {
                                loadingDia.dismiss();
                            }
                            AppData.showToast(MyCarActivity.this, R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getDevice(String id) {

        Http.getDeviceDetail(this, id, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            DeviceInfo deviceInfo = new Gson().fromJson(res, DeviceInfo.class);
                            tvCarNum2.setText(deviceInfo.getCarNum());
                            tvCarBrand.setText(deviceInfo.getCarBrand());
                            tvCarType.setText(deviceInfo.getCarModel());
                            tvModelYear.setText(deviceInfo.getCarYear());
                            tvKm.setText(deviceInfo.getCarDis() + "KM");
                            tvGearCase.setText(deviceInfo.getCarGBox());
                            tvDisplacement.setText(deviceInfo.getCarOutput() + "L/T");
                            tvVin.setText(deviceInfo.getCarVIN());
                            tvDeviceSn.setText(deviceInfo.getSn());
                            tvCarNum.setText(deviceInfo.getCarNum());
                            tvCarNum3.setText(deviceInfo.getCarNum());
                            AppData.GetInstance(MyCarActivity.this).setSelectedDevice(Integer.parseInt(deviceInfo.getId()));
                            break;
                        case 1009://没有权限
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDeviceList();
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
