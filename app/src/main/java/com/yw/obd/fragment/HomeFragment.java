package com.yw.obd.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.activity.CarCheckActivity;
import com.yw.obd.base.BaseFragment;
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
 * Created by apollo on 2017/7/25.
 */

public class HomeFragment extends BaseFragment {
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
    private List<Map<String, Object>> spData;
    private String device_id,carName;
    private AlertDialog loadingDia = null;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setView(inflate)
                .create();
        loadingDia.show();

        getDeviceList();
    }

    private int selectposition = 0;
    private String deviceId = "";

    private void initSpanner(final DeviceListInfo deviceListInfo, int selectDevice) {
        sp.setPrompt(getResources().getString(R.string.select_car));
        spData = new ArrayList<>();
        for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", deviceListInfo.getArr().get(i).getName());
            map.put("number", deviceListInfo.getArr().get(i).getCarNum());
            spData.add(map);

            DeviceListInfo.ArrBean arrBean = deviceListInfo.getArr().get(i);
            if (Integer.parseInt(arrBean.getId()) == selectDevice) {
                selectposition = i;
                deviceId = arrBean.getId();
            }
        }

        Log.e("print", "Home----" + AppData.GetInstance(getActivity()).getSelectedDevice() + "______" + deviceId);
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), spData, R.layout.layout_spinner, new String[]{"name", "number"},
                new int[]{R.id.tv_car, R.id.tv_car_number});
        sp.setAdapter(adapter);
        sp.setSelection(selectposition);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                device_id = deviceListInfo.getArr().get(position).getId();
                carName=deviceListInfo.getArr().get(position).getName();
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
            case R.id.iv_engine://检测
                Intent intent = new Intent(getActivity(), CarCheckActivity.class);
                intent.putExtra("id", device_id);
                intent.putExtra("name",carName);
                getActivity().startActivity(intent);
                break;
        }
    }

    private void getDeviceList() {
        if (loadingDia != null && loadingDia.isShowing()) {
            loadingDia.dismiss();
        }
        Http.getDeviceList(getActivity(), new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            DeviceListInfo deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            initSpanner(deviceListInfo, AppData.GetInstance(getActivity()).getSelectedDevice());
                            break;
                        case 2002:
                            AppData.showToast(getActivity(), R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getDevice(String id) {
        Http.getDeviceDetail(getActivity(), id, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            DeviceInfo deviceInfo = new Gson().fromJson(res, DeviceInfo.class);
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
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            loadingDia.show();
            getDeviceList();
        }
    }
}
