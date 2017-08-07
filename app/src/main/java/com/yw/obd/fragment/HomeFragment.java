package com.yw.obd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseFragment;
import com.yw.obd.entity.DeviceInfo;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.http.WebService;
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
 * Created by apollo on 2017/7/25.
 */

public class HomeFragment extends BaseFragment implements WebService.WebServiceListener {
    @Bind(R.id.sp)
    Spinner sp;
    @Bind(R.id.iv_drop)
    ImageButton ivDrop;
    @Bind(R.id.iv_alarm)
    ImageButton ivAlarm;
    @Bind(R.id.tv_round)
    TextView tvRound;
    @Bind(R.id.tv_fuel)
    TextView tvFuel;
    @Bind(R.id.iv_engine)
    ImageButton ivEngine;
    @Bind(R.id.tv_co)
    TextView tvCo;
    private static final int GET_DEVICE_LIST = 1;
    private static final int GET_DEVICE = 2;
    private static final String KEY = "20170801CHLOBDYW028M";

    private List<Map<String, Object>> spData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDeviceList();
    }

    private void initSpanner(final DeviceListInfo deviceListInfo) {

        sp.setPrompt(getResources().getString(R.string.select_car));
        spData = new ArrayList<>();
        for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", deviceListInfo.getArr().get(i).getName());
            map.put("number", deviceListInfo.getArr().get(i).getCarNum());
            spData.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), spData, R.layout.layout_spinner, new String[]{"name", "number"},
                new int[]{R.id.tv_car, R.id.tv_car_number});
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("print", "position" + position);
                String device_id = deviceListInfo.getArr().get(position).getId();
                getDevice(device_id);
                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.iv_drop, R.id.iv_alarm, R.id.iv_engine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_drop:
                break;
            case R.id.iv_alarm:
                break;
            case R.id.iv_engine:
                break;
        }
    }

    private void getDeviceList() {
        WebService web = new WebService(getActivity(), GET_DEVICE_LIST, false, "GetDeviceList");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(getActivity()).getUserName());
        property.put("password", AppData.GetInstance(getActivity()).getUserPass());
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }

    private void getDevice(String id) {
        WebService web = new WebService(getActivity(), GET_DEVICE, false, "GetDeviceDetail");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(getActivity()).getUserName());
        property.put("password", AppData.GetInstance(getActivity()).getUserPass());
        property.put("deviceID", id);
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }

    private int state = -1;

    @Override
    public void onWebServiceReceive(String method, int id, String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            state = Integer.parseInt(jsonObject.getString("state"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (id == GET_DEVICE_LIST) {
            switch (state) {
                case 0://查询成功
                    DeviceListInfo deviceListInfo = new Gson().fromJson(result, DeviceListInfo.class);
                    initSpanner(deviceListInfo);
                    break;
                case 2002://没有查询到任何设备
                    break;
            }


        } else if (id == GET_DEVICE){
            switch (state){
                case 0:
                    DeviceInfo deviceInfo = new Gson().fromJson(result, DeviceInfo.class);

                    break;
                case 1009://没有权限
                    break;
            }
        }
    }
}
