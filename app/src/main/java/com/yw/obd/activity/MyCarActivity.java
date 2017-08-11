package com.yw.obd.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.DeviceInfo;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/8/2.
 * 我的车辆
 */

public class MyCarActivity extends BaseActivity {

    @Bind(R.id.sp)
    Spinner sp;

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
    private List<Map<String, Object>> spData;
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
        getDeviceList();
    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.btn_add_car, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                if (selectPosition == 0) {
                    ivLeft.setClickable(false);
                    ivRight.setClickable(true);
                } else {
                    List<DeviceListInfo.ArrBean> arr = deviceListInfo.getArr();
                    DeviceListInfo.ArrBean arrBean = arr.get(--selectPosition);
                    String id = arrBean.getId();
                    getDevice(id);
                }
                break;
            case R.id.iv_right:
                Log.d("print", "deviceListInfo" + deviceListInfo);
                if (selectPosition == deviceListInfo.getArr().size() - 1) {
                    ivRight.setClickable(false);
                    ivLeft.setClickable(true);
                } else {
                    List<DeviceListInfo.ArrBean> arr = deviceListInfo.getArr();
                    DeviceListInfo.ArrBean arrBean = arr.get(++selectPosition);
                    String id = arrBean.getId();
                    getDevice(id);
                }
                break;
            case R.id.btn_add_car:
                startActivity(new Intent(this, AddCarActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 初始化spinner
     *
     * @param deviceListInfo
     */
    private void initSpanner(final DeviceListInfo deviceListInfo) {
        sp.setPrompt(getResources().getString(R.string.select_car));
        spData = new ArrayList<>();
        for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", deviceListInfo.getArr().get(i).getName());
            map.put("number", deviceListInfo.getArr().get(i).getCarNum());
            spData.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, spData, R.layout.layout_spinner, new String[]{"name", "number"},
                new int[]{R.id.tv_car, R.id.tv_car_number});
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("print", "position" + position);
                selectPosition = position;
                tvCarNum.setText(deviceListInfo.getArr().get(position).getCarNum());
                String device_id = deviceListInfo.getArr().get(position).getId();
                loadingDia.show();
                getDevice(device_id);
                AppData.GetInstance(MyCarActivity.this).setSelectedDevice(Integer.parseInt(device_id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDeviceList() {
        if (loadingDia != null && loadingDia.isShowing()) {
            loadingDia.dismiss();
        }
        Http.getDeviceList(this, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            initSpanner(deviceListInfo);
                            int selectedDevice = AppData.GetInstance(MyCarActivity.this).getSelectedDevice();
                            for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
                                String deviceId = deviceListInfo.getArr().get(i).getId();
                                if (Integer.parseInt(deviceId) == selectedDevice) {
                                    sp.setSelection(i);
                                    tvCarNum.setText(deviceListInfo.getArr().get(i).getCarNum());
                                    getDevice(deviceId);
                                }

                            }
                            break;
                        case 2002://没有查询到任何设备
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
        if (loadingDia != null && loadingDia.isShowing()) {
            loadingDia.dismiss();
        }
        Http.getDeviceDetail(this, id, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            DeviceInfo deviceInfo = new Gson().fromJson(res, DeviceInfo.class);
//                    String info = "车牌号：" + deviceInfo.getCarNum() + "\n" + "车辆品牌：" + deviceInfo.getCarBrand() + "\n"
//                            + "车辆型号：" + deviceInfo.getCarModel() + "\n"
//                            + "车辆年限：" + deviceInfo.getCarYear() + "\n"
//                            + "变速箱：" + deviceInfo.getCarGBox() + "\n"
//                            + "排量：" + deviceInfo.getCarOutput() + "\n"
//                            + "仪表盘里程：" + deviceInfo.getCarDis() + "\n"
//                            + "VIN：" + deviceInfo.getCarVIN() + "\n"
//                            + "设备IMEI号：" + deviceInfo.getSn() + "\n";
                            tvCarNum2.setText(deviceInfo.getCarNum());
                            tvCarBrand.setText(deviceInfo.getCarBrand());
                            tvCarType.setText(deviceInfo.getCarModel());
                            tvModelYear.setText(deviceInfo.getCarYear());
                            tvKm.setText(deviceInfo.getCarDis() + "KM");
                            tvGearCase.setText(deviceInfo.getCarGBox());
                            tvDisplacement.setText(deviceInfo.getCarOutput() + "L/T");
                            tvVin.setText(deviceInfo.getCarVIN());
                            tvDeviceSn.setText(deviceInfo.getSn());
                            sp.setSelection(selectPosition);
                            tvCarNum.setText(deviceInfo.getCarNum());
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


}
