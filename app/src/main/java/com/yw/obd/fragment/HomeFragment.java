package com.yw.obd.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.activity.AlarmActivity;
import com.yw.obd.activity.CarCheckActivity;
import com.yw.obd.adapter.PopuLvAdapter;
import com.yw.obd.base.BaseFragment;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.http.Http;
import com.yw.obd.service.PushService;
import com.yw.obd.util.AppData;
import com.yw.obd.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/25.
 */

public class HomeFragment extends BaseFragment {
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
    @Bind(R.id.tv_car_number)
    TextView tvCarNum;
    @Bind(R.id.tv_car)
    TextView tvCarName;
    @Bind(R.id.ll_car)
    LinearLayout llCar;
    @Bind(R.id.rl)
    RelativeLayout rl;

    private String device_id, carName;
    private AlertDialog loadingDia = null;

    private PopupWindow popupWindow;
    private DeviceListInfo deviceListInfo;
    private View popu;
    private ListView lv;
    private PopuLvAdapter adapter;

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

        popu = getActivity().getLayoutInflater().inflate(R.layout.layout_popu, null);
        lv = (ListView) popu.findViewById(R.id.lv);
        getDeviceList();
    }

    @OnClick({R.id.iv_alarm, R.id.iv_engine, R.id.ll_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_alarm://报警消息
                if (addDeviceFirst()) return;
                Intent intentMSG = new Intent(getActivity(), AlarmActivity.class);
                intentMSG.putExtra("id", device_id);
                getActivity().startActivity(intentMSG);
                break;
            case R.id.iv_engine://检测
                if (addDeviceFirst()) return;
                Intent intent = new Intent(getActivity(), CarCheckActivity.class);
                intent.putExtra("id", device_id);
                intent.putExtra("name", carName);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_car:
                if (addDeviceFirst()) return;
                showPopu(rl);
                break;
        }
    }

    private boolean addDeviceFirst() {
        if (deviceListInfo == null) {
            AppData.showToast(getActivity(), R.string.add_device_first);
            return true;
        }
        return false;
    }

    private boolean isExist = false;

    private void getDeviceList() {
//        loadingDia.show();
        Http.getDeviceList(getActivity(), new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object == null) {
                    AppData.showToast(getActivity(), R.string.connect_overtime);
                    if (loadingDia != null && loadingDia.isShowing()) {
                        loadingDia.dismiss();
                    }
                    return;
                }
                String res = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    int state = jsonObject.getInt("state");
                    AppData.GetInstance(getActivity()).setAlarmAlert(true);
                    switch (state) {
                        case 0:
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            int selectedDevice = AppData.GetInstance(getActivity()).getSelectedDevice();
                            for (int i = 0; i < deviceListInfo.getArr().size(); i++) {

                                if (deviceListInfo.getArr().get(i).getId().equals(selectedDevice + "")) {
                                    device_id = deviceListInfo.getArr().get(i).getId();
                                    carName = deviceListInfo.getArr().get(i).getName();
                                    tvCarName.setText(carName);
                                    tvCarNum.setText(deviceListInfo.getArr().get(i).getCarNum());
                                    AppData.GetInstance(getActivity()).setSN(deviceListInfo.getArr().get(i).getSn());
                                    isExist = true;
                                    getHomeData(device_id);
                                    break;
                                }
                            }

                            if (!isExist) {
                                device_id = deviceListInfo.getArr().get(0).getId();
                                carName = deviceListInfo.getArr().get(0).getName();
                                tvCarName.setText(carName);
                                tvCarNum.setText(deviceListInfo.getArr().get(0).getCarNum());
                                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
                                AppData.GetInstance(getActivity()).setSN(deviceListInfo.getArr().get(0).getSn());
                                getHomeData(device_id);
                            }

                            if (AppData.GetInstance(getActivity()).getAlarmAlert()) {//开启
                                getActivity().startService(new Intent(getActivity(), PushService.class));
                            }
                            break;
                        case 2002:
                            if (loadingDia != null && loadingDia.isShowing()) {
                                loadingDia.dismiss();
                            }
                            getActivity().startService(new Intent(getActivity(), PushService.class));
                            AppData.showToast(getActivity(), R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getHomeData(String id) {
        Http.getHomeData(getActivity(), id, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (loadingDia != null && loadingDia.isShowing()) {
                    loadingDia.dismiss();
                }
                String res = (String) object;
                try {
                    JSONObject jo = new JSONObject(res);
                    int state = Integer.parseInt(jo.getString("state"));
                    switch (state) {
                        case 0:
                            String tripFuel = jo.getString("tripFuel");
                            String carbon = jo.getString("carbon");
                            String rank = jo.getString("ranking");
                            tvFuel.setText(tripFuel + "/L");
                            tvCo.setText(carbon + "/g");
                            tvRound.setText(getResources().getString(R.string.homeLeft) + rank + getResources().getString(R.string.homeRight));
                            break;
                        case 2002:
                            tvFuel.setText(0.0 + "/L");
                            tvCo.setText(0.0 + "/g");
                            tvRound.setText(getResources().getString(R.string.homeLeft) + "0%" + getResources().getString(R.string.homeRight));
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
//            loadingDia.show();
            getDeviceList();
        } else {
            if (loadingDia != null) {
                loadingDia.dismiss();
            }
        }
    }


    private void showPopu(View parent) {
        if (popupWindow == null) {
            adapter = new PopuLvAdapter(getActivity(), deviceListInfo, false);
            lv.setAdapter(adapter);
            int width = DensityUtil.dip2px(getActivity(), 150);
            int height = DensityUtil.dip2px(getActivity(), 150);
            popupWindow = new PopupWindow(popu, width, height);
        }

        adapter.notifyDataSetChanged();
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
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
                tvCarName.setText(carName);
                tvCarNum.setText(deviceListInfo.getArr().get(position).getCarNum());
                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
                AppData.GetInstance(getActivity()).setSN(deviceListInfo.getArr().get(position).getSn());
//                loadingDia.show();
                getHomeData(device_id);
            }
        });
    }
}
