package com.yw.obd.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.activity.TrackRecordActivity;
import com.yw.obd.base.BaseFragment;
import com.yw.obd.entity.DeviceInfo;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.entity.TrackingInfo;
import com.yw.obd.http.WebService;
import com.yw.obd.util.AppData;
import com.yw.obd.util.CaseData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

import static android.content.Context.SENSOR_SERVICE;


/**
 * Created by apollo on 2017/7/25.
 */

public class CarFragment extends BaseFragment implements SensorEventListener, WebService.WebServiceListener {
    @Bind(R.id.sp)
    Spinner sp;
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

    /*百度地图*/
    private BaiduMap baiduMap;
    private LocationClient mLocClient;
    private BitmapDescriptor markerd = BitmapDescriptorFactory.fromResource(R.drawable.btn_navigation_normal);
    private InfoWindow mInfoWindow;
    private boolean isShowInfoWindow = true;
    private View infoWindow;
    private TextView tvContent, tvStatus, tvLoc, tvVol, tvTime, tvDirect, tvSpeed, tvEle, tvAd;
    private RelativeLayout rlContent;
    private Button btnCommand, btnTrack, btnLocus, btnStreet;

    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

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

    private static final int GET_DEVICE_LIST = 1;
    private static final int GET_DEVICE = 2;
    private static final int GET_DEVICE_TRACKING = 3;
    private List<Map<String, Object>> spData;
    private int selectPosition = 0;
    private DeviceListInfo deviceListInfo;
    private static final String KEY = "20170801CHLOBDYW028M";

    private String selectDeviceName = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    protected void initView(View view) {
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

    }

    /**
     * 初始化弹窗
     */
    private void initPop() {
        infoWindow = getActivity().getLayoutInflater().inflate(R.layout.main_info_window, null);
        tvContent = (TextView) infoWindow.findViewById(R.id.tv_content);
        tvAd = (TextView) infoWindow.findViewById(R.id.tv_ad);
        tvDirect = (TextView) infoWindow.findViewById(R.id.tv_direct);
        tvEle = (TextView) infoWindow.findViewById(R.id.tv_ele);
        tvLoc = (TextView) infoWindow.findViewById(R.id.tv_loc);
        tvSpeed = (TextView) infoWindow.findViewById(R.id.tv_speed);
        tvTime = (TextView) infoWindow.findViewById(R.id.tv_time);
        tvStatus = (TextView) infoWindow.findViewById(R.id.tv_status);
        tvVol = (TextView) infoWindow.findViewById(R.id.tv_vol);
        rlContent = (RelativeLayout) infoWindow.findViewById(R.id.rl_content);
        btnCommand = (Button) infoWindow.findViewById(R.id.btn_command);
        btnLocus = (Button) infoWindow.findViewById(R.id.btn_locus);
        btnStreet = (Button) infoWindow.findViewById(R.id.btn_street);
        btnTrack = (Button) infoWindow.findViewById(R.id.btn_track);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tvTime.setText(getResources().getString(R.string.time) + "：" + sdf.format(date));

        btnCommand.setOnClickListener(new View.OnClickListener() {//命令
            @Override
            public void onClick(View v) {

            }
        });

        btnLocus.setOnClickListener(new View.OnClickListener() {//轨迹
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrackRecordActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnStreet.setOnClickListener(new View.OnClickListener() {//街景
            @Override
            public void onClick(View v) {

            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {//追踪
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 初始化spinner
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

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), spData, R.layout.layout_spinner, new String[]{"name", "number"},
                new int[]{R.id.tv_car, R.id.tv_car_number});
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("print", "position" + position);
                selectPosition = position;
                String device_id = deviceListInfo.getArr().get(position).getId();
                selectDeviceName = deviceListInfo.getArr().get(position).getName();
                getDevice(device_id);
                getDeviceTracking(device_id);
                AppData.GetInstance(getActivity()).setSelectedDevice(Integer.parseInt(device_id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    private void getDeviceTracking(String deviceId) {
        WebService web = new WebService(getActivity(), GET_DEVICE_TRACKING, false, "GetTracking");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(getActivity()).getUserName());
        property.put("password", AppData.GetInstance(getActivity()).getUserPass());
        property.put("deviceID", deviceId);
        property.put("timeZones", AppData.GetInstance(getActivity()).getTimeZone());
        property.put("mapType", "Baidu");
        property.put("language", Locale.getDefault().getLanguage());
        web.addWebServiceListener(this);
        web.SyncGet(property);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMap();
        getDeviceList();
    }

    @OnClick({R.id.iv_drop, R.id.iv_people, R.id.iv_car, R.id.iv_nav})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_drop:
                break;
            case R.id.iv_people:
                break;
            case R.id.iv_car:
                break;
            case R.id.iv_nav:
                break;
        }
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


        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                tvAd.setText("地址：" + arg0.getName());
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
//                //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
//                InfoWindow mInfoWindow = new InfoWindow(infoWindow, arg0, -47);
//                //显示InfoWindow
//                baiduMap.showInfoWindow(mInfoWindow);

            }
        });

        // 对Marker的点击
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                if (isShowInfoWindow) {
                    baiduMap.hideInfoWindow();
                    isShowInfoWindow = false;
                } else {
                    isShowInfoWindow = true;
                    TrackingInfo info = (TrackingInfo) marker.getExtraInfo().get("info");
                    String status = info.getStatus();
                    tvContent.setText(selectDeviceName);
                    if (status.equals("1")) {//运动
                        status = getResources().getString(R.string.sport);
                    } else if (status.equals("0")) {//未启用
                        status = getResources().getString(R.string.unable);
                    } else if (status.equals("2")) {//静止
                        status = getResources().getString(R.string.quiet);
                    } else if (status.equals("3")) {//离线
                        status = getResources().getString(R.string.offline);
                    } else if (status.equals("4")) {//欠费
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

                    tvVol.setText(getResources().getString(R.string.voltage));

                    tvSpeed.setText(getResources().getString(R.string.speed) + info.getSpeed() + "km/h");

                    tvTime.setText(getResources().getString(R.string.time) + "：" + info.getPositionTime());

                    //实例化一个地理编码查询对象
                    GeoCoder geoCoder = GeoCoder.newInstance();
                    //设置反地理编码位置坐标
                    //发起反地理编码请求(经纬度->地址信息)
                    geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(marker.getPosition()));
                    geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                        @Override
                        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                            //获取点击的坐标地址
                            String address = arg0.getAddress();
                            Log.d("print", "address" + address);
                            tvAd.setText(getResources().getString(R.string.address) + address);
                        }

                        @Override
                        public void onGetGeoCodeResult(GeoCodeResult arg0) {
                        }
                    });

                    mInfoWindow = new InfoWindow(infoWindow, marker.getPosition(), -47);
                    baiduMap.showInfoWindow(mInfoWindow);
                }


                return true;
            }
        });

    }

    private int state = 0x001;

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
                    deviceListInfo = new Gson().fromJson(result, DeviceListInfo.class);
                    initSpanner(deviceListInfo);
                    int selectedDevice = AppData.GetInstance(getActivity()).getSelectedDevice();
                    for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
                        String deviceId = deviceListInfo.getArr().get(i).getId();
                        if (Integer.parseInt(deviceId) == selectedDevice) {
                            sp.setSelection(i);
                            getDevice(deviceId);
                        }

                    }
                    break;
                case 2002://没有查询到任何设备
                    break;
            }


        } else if (id == GET_DEVICE) {
            switch (state) {
                case 0:
                    DeviceInfo deviceInfo = new Gson().fromJson(result, DeviceInfo.class);
                    sp.setSelection(selectPosition);
                    break;
                case 1009://没有权限
                    break;
            }
        } else if (id == GET_DEVICE_TRACKING) {//获得轨迹
            switch (state) {
                case 0://
                    TrackingInfo trackingInfo = new Gson().fromJson(result, TrackingInfo.class);
                    baiduMap.clear();
                    LatLng latLng = null;
                    OverlayOptions options = null;
                    Marker marker = null;
                    latLng = new LatLng(Double.parseDouble(trackingInfo.getLat()), Double.parseDouble(trackingInfo.getLng()));
                    options = new MarkerOptions().position(latLng).icon(markerd).zIndex(5);
                    marker = (Marker) baiduMap.addOverlay(options);
                    marker.setRotate(Float.parseFloat(trackingInfo.getCourse()));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info", trackingInfo);
                    marker.setExtraInfo(bundle);

                    // 将地图移到到最后一个经纬度位置
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
                    baiduMap.setMapStatus(u);
                    break;
            }
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

            MarkerOptions options = new MarkerOptions();
            options.icon(markerd);
            options.position(new LatLng(mCurrentLat, mCurrentLon));
            options.draggable(true);
            baiduMap.addOverlay(options);

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
        super.onResume();
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