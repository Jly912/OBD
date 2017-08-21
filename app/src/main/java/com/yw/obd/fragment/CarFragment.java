package com.yw.obd.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.activity.TrackRecordActivity;
import com.yw.obd.adapter.PopuLvAdapter;
import com.yw.obd.base.BaseFragment;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.entity.TrackingInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.CaseData;
import com.yw.obd.util.DensityUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

import static android.content.Context.SENSOR_SERVICE;
import static com.yw.obd.R.string.address;


/**
 * Created by apollo on 2017/7/25.
 */

public class CarFragment extends BaseFragment implements SensorEventListener {
    @Bind(R.id.iv_drop)
    ImageButton ivDrop;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.iv_people)
    ImageButton ivPeople;
    @Bind(R.id.iv_car)
    ImageButton ivCar;
    @Bind(R.id.iv_nav)
    ImageButton ivNav;
    @Bind(R.id.ll_car)
    LinearLayout llCar;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.tv_car)
    TextView tvCarName;
    @Bind(R.id.tv_car_number)
    TextView tvCarNum;
    /*百度地图*/
    private BaiduMap baiduMap;
    private LocationClient mLocClient;
    private InfoWindow mInfoWindow;
    private boolean isShowInfoWindow = true;
    private View infoWindow;
    private TextView tvContent, tvStatus, tvLoc, tvVol, tvTime, tvDirect, tvSpeed, tvAd;
    private RelativeLayout rlContent;
    private Button btnCommand, btnLocus;

    //定位相关
    private MyLocationListener myLocationListener = new MyLocationListener();
    private MyLocationConfiguration.LocationMode locationMode;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    /**
     * 最新一次的经纬度
     */
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    /**
     * 当前的精度
     */
    private float mCurrentAccracy;

    //UI相关
    boolean isFirstLoc = true;//是否首次定位
    private MyLocationData locData;

    private MyBaiduSdkReceiver receiver;
    private DeviceListInfo deviceListInfo;
    private AlertDialog loadingDia;

    private boolean lockM;
    private LatLng latLng;
    private TrackingInfo trackingInfo;

    private String device_id, carName;
    private PopupWindow popupWindow;
    private View popu;
    private ListView lv;
    private LinearLayout ll;
    private PopuLvAdapter adapter;

    public static CarFragment getInstance() {
        CarFragment carFragment = new CarFragment();
        return carFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initView(View view) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.progressdialog, null);
        loadingDia = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setView(inflate)
                .create();
        loadingDia.show();

        receiver = new MyBaiduSdkReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        intentFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        getActivity().registerReceiver(receiver, intentFilter);

        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        locationMode = MyLocationConfiguration.LocationMode.NORMAL;

        if (baiduMap == null) {
            baiduMap = map.getMap();
        }

        popu = getActivity().getLayoutInflater().inflate(R.layout.layout_popu, null);
        lv = (ListView) popu.findViewById(R.id.lv);
        ll = (LinearLayout) popu.findViewById(R.id.ll_out);
        ll.setBackgroundColor(getResources().getColor(R.color.white));

    }

    /**
     * 初始化弹窗
     */
    private void initPop() {
        infoWindow = getActivity().getLayoutInflater().inflate(R.layout.main_info_window, null);
        tvContent = (TextView) infoWindow.findViewById(R.id.tv_content);
        tvAd = (TextView) infoWindow.findViewById(R.id.tv_ad);
        tvDirect = (TextView) infoWindow.findViewById(R.id.tv_direct);
        tvLoc = (TextView) infoWindow.findViewById(R.id.tv_loc);
        tvSpeed = (TextView) infoWindow.findViewById(R.id.tv_speed);
        tvTime = (TextView) infoWindow.findViewById(R.id.tv_time);
        tvStatus = (TextView) infoWindow.findViewById(R.id.tv_status);
        tvVol = (TextView) infoWindow.findViewById(R.id.tv_vol);
        rlContent = (RelativeLayout) infoWindow.findViewById(R.id.rl_content);
        btnCommand = (Button) infoWindow.findViewById(R.id.btn_command);
        btnLocus = (Button) infoWindow.findViewById(R.id.btn_locus);
        tvAd.setText(getResources().getString(address));

        btnCommand.setOnClickListener(new View.OnClickListener() {//命令
            @Override
            public void onClick(View v) {

            }
        });

        btnLocus.setOnClickListener(new View.OnClickListener() {//轨迹
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrackRecordActivity.class);
                if (deviceListInfo != null) {
                    getActivity().startActivity(intent);
                }
            }
        });

    }

    private boolean isExist = false;

    private void getDeviceList() {
        Http.getDeviceList(getActivity(), new Http.OnListener() {
            @Override
            public void onSucc(Object object) {

                String res = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    int state = Integer.parseInt(jsonObject.getString("state"));
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
                                    loadingDia.show();
                                    getDeviceTracking(deviceListInfo.getArr().get(i).getId());
                                    isExist = true;
                                    return;
                                }
                            }

                            if (!isExist) {
                                device_id = deviceListInfo.getArr().get(0).getId();
                                carName = deviceListInfo.getArr().get(0).getName();
                                tvCarName.setText(carName);
                                tvCarNum.setText(deviceListInfo.getArr().get(0).getCarNum());
                                loadingDia.show();
                                getDeviceTracking(deviceListInfo.getArr().get(0).getId());
                            }
                            break;
                        case 2002:
                            if (loadingDia != null && loadingDia.isShowing()) {
                                loadingDia.dismiss();
                            }
                            AppData.showToast(getActivity(), R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getDeviceTracking(String deviceId) {
        Http.getTracking(getActivity(), deviceId, new Http.OnListener() {
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
                            trackingInfo = new Gson().fromJson(res, TrackingInfo.class);
                            baiduMap.clear();
                            latLng = new LatLng(Double.parseDouble(trackingInfo.getLat()), Double.parseDouble(trackingInfo.getLng()));
                            String status = trackingInfo.getStatus();
                            String[] split = status.split("-");
                            int i = CaseData.returnIconInt(Integer.parseInt(trackingInfo.getCourse()), Integer.parseInt(split[0]));
                            OverlayOptions options = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(i)).zIndex(5);
                            baiduMap.addOverlay(options);
                            showInfoWindow(trackingInfo, latLng);

                            Log.e("print", "lockM" + lockM + latLng + "_--" + mCurrentLat + "__-" + mCurrentLon);
                            if (lockM) {
                                // 将地图移到到最后一个经纬度位置
                                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(mCurrentLat, mCurrentLon));
                                baiduMap.setMapStatus(u);
                            } else {
                                // 将地图移到到最后一个经纬度位置
                                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
                                baiduMap.setMapStatus(u);
                            }
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

    private static final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 0x002;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_COARSE_LOCATION_REQUEST_CODE);
            setMap();
        } else {
            setMap();
        }

        getDeviceList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACCESS_COARSE_LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                setMap();
            } else {
                // Permission Denied
                AppData.showToast(getActivity(), getResources().getString(R.string.refuse_enter));
            }
        }
    }

    @OnClick({R.id.iv_people, R.id.iv_car, R.id.iv_nav, R.id.ll_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_people:
                if (trackingInfo == null) {
                    return;
                }
                lockM = true;
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(new LatLng(mCurrentLat, mCurrentLon),
                        baiduMap.getMapStatus().zoom < 12f ? 18 : baiduMap.getMapStatus().zoom));
                ivPeople.setImageResource(R.drawable.btn_people_checked);
                ivCar.setImageResource(R.drawable.btn_car_normal);
                break;
            case R.id.iv_car:
                if (trackingInfo == null) {
                    return;
                }
                lockM = false;
                baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(latLng,
                        baiduMap.getMapStatus().zoom < 12f ? 18 : baiduMap.getMapStatus().zoom));
                ivPeople.setImageResource(R.drawable.btn_people_normal);
                ivCar.setImageResource(R.drawable.btn_car_checked);
                break;
            case R.id.iv_nav://跳转到百度地图导航
                if (trackingInfo == null) {
                    return;
                }
                try {
                    Uri uri = Uri.parse("geo:" + trackingInfo.getoLat() + "," + trackingInfo.getoLng());
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                } catch (ActivityNotFoundException e) {
                    AppData.showToast(getActivity(), R.string.install_navi_first);
                }
                break;
            case R.id.ll_car:
                showPopu(rl);
                break;
        }
    }

    private void showPopu(View parent) {
        if (popupWindow == null) {
            adapter = new PopuLvAdapter(getActivity(), deviceListInfo, true);
            lv.setAdapter(adapter);
            int width = DensityUtil.dip2px(getActivity(), 150);
            int height = DensityUtil.dip2px(getActivity(), 150);
            popupWindow = new PopupWindow(popu, width, height);
        }

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
                tvCarNum.setText(deviceListInfo.getArr().get(position).getCarNum());
                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
                loadingDia.show();
                getDeviceTracking(deviceListInfo.getArr().get(position).getId());

            }
        });
    }

    private void setMap() {
        initPop();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");//设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);

        mLocClient.setLocOption(option);
        mLocClient.start();

        // 对Marker的点击
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if (isShowInfoWindow) {
                    baiduMap.hideInfoWindow();
                    isShowInfoWindow = false;
                } else {
                    isShowInfoWindow = true;
                    baiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });

    }

    private void showInfoWindow(TrackingInfo info, LatLng latLng) {
        if (info != null) {
            String status = info.getStatus();
            tvContent.setText(carName);
            if (status.equals("1-")) {//运动
                status = getResources().getString(R.string.sport);
            } else if (status.equals("0-")) {//未启用
                status = getResources().getString(R.string.unable);
            } else if (status.equals("2-")) {//静止
                status = getResources().getString(R.string.quiet);
            } else if (status.equals("3-")) {//离线
                status = getResources().getString(R.string.offline);
            } else if (status.equals("4-")) {//欠费
                status = getResources().getString(R.string.arrears);
            }
            tvStatus.setText(getResources().getString(R.string.status) + status);

            String isGPS = info.getIsGPS();
            if (isGPS.equals("0")) {
                isGPS = getResources().getString(R.string.base_station);
            } else if (isGPS.equals("1")) {
                isGPS = getResources().getString(R.string.gps);
            } else if (isGPS.equals("2")) {
                isGPS = getResources().getString(R.string.wifi);
            }

            tvLoc.setText(getResources().getString(R.string.locate) + isGPS);

            tvDirect.setText(getResources().getString(R.string.direct) + getResources().getString(
                    CaseData.returnCourse(Integer.parseInt(info.getCourse()))));

            tvVol.setText(getResources().getString(R.string.voltage) + info.getDy() + "V");

            tvSpeed.setText(getResources().getString(R.string.speed) + info.getSpeed() + "km/h");

            tvTime.setText(getResources().getString(R.string.time) + "：" + info.getPositionTime());

            Http.getAddress(getActivity(), info.getLat(), info.getLng(), "Baidu", new Http.OnListener() {
                @Override
                public void onSucc(Object object) {
                    String add = (String) object;
                    tvAd.setText(getResources().getString(address) + add);
                }
            });
            mInfoWindow = new InfoWindow(infoWindow, latLng, -47);
            baiduMap.showInfoWindow(mInfoWindow);

            // 将地图移到到最后一个经纬度位置
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.setMapStatus(u);
        }
    }

    /**
     * 定位SDK监听函数, 需实现BDLocationListener里的方法
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d("print", "location---" + location.getAddrStr());
            // map view 销毁后不在处理新接收的位置
            if (location == null || map == null) {
                return;
            }

            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();

            //构建生成定位数据对象
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            baiduMap.setMyLocationData(locData);

            if (isFirstLoc) {
                isFirstLoc = false;
                //描述地图状态将要发生的变化
                // //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
                LatLng ll = new LatLng(mCurrentLat, mCurrentLon);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
                //改变地图状态
                baiduMap.animateMapStatus(msu);
            }

        }

        @Override
        public void onConnectHotSpotMessage(String var1, int var2) {
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        double x = event.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            baiduMap.setMyLocationData(locData);
        }
        lastX = x;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        map.onResume();
        getDeviceList();
        Log.e("print", "onr");
        super.onResume();
        Log.e("print", "onr22");
        baiduMap.setMyLocationEnabled(true);
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        baiduMap.setMyLocationEnabled(false);
        map.onPause();
    }

    @Override
    public void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        //退出时销毁定位
        mLocClient.stop();
        //关闭定位图层
        baiduMap.setMyLocationEnabled(false);
        map.onDestroy();
        map = null;
        getActivity().unregisterReceiver(receiver);
        super.onDestroy();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            getDeviceList();
        }
    }

    class MyBaiduSdkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
                //网络错误
                Toast.makeText(getActivity(), "无网络", Toast.LENGTH_SHORT).show();
            } else if (action.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                //key校验失败
                Toast.makeText(getActivity(), "校验失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}