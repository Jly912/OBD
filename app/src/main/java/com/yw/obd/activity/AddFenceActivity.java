package com.yw.obd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.app.AppContext;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.TrackingInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.CaseData;
import com.yw.obd.widget.RingView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.yw.obd.R.id.seekBar_Radius;

/**
 * Created by apollo on 2017/8/24.
 * 添加电子栅栏
 */

public class AddFenceActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_radius)
    TextView tvRadius;
    @Bind(R.id.btn_person)
    ImageButton btnPerson;
    @Bind(R.id.btn_car)
    ImageButton btnCar;
    @Bind(seekBar_Radius)
    SeekBar seekBarRadius;
    @Bind(R.id.btn_reduce)
    Button btnReduce;
    @Bind(R.id.btn_increase)
    Button btnIncrease;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.rl_map)
    RelativeLayout rlMap;
    @Bind(R.id.btn_zoom_in)
    ImageButton btnZoomIn;
    @Bind(R.id.btn_zoom_out)
    ImageButton btnZoomOut;

    private LocationClient mLocClient;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;
    private boolean isFirstRequest = true;
    private MyLocationListener myLocationListenner = new MyLocationListener();
    private LatLng location_person, location_device;
    private boolean start;
    private RingView ringView;
    private float zoom;
    private int geofenceId = 0;
    private double lat, lng;
    private int location_type = 0;
    private TrackingInfo trackingInfo;
    private boolean isNew = true;

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;

            Log.e("print", "Bdloction" + location.getAddrStr());
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(0)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            location_person = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if (isFirstLoc) {
                isFirstLoc = false;
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(location_person).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null) {
                return;
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_fence;
    }

    @Override
    protected void init() {
        ivBack.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        isNew = intent.getBooleanExtra("new", true);
        if (!isNew) {//编辑
            tvTitle.setText(R.string.edit_electric_fence);
            String fenceName = intent.getStringExtra("name");
            String fenceId = intent.getStringExtra("fenceId");
            String lat = intent.getStringExtra("lat");
            String lng = intent.getStringExtra("lng");
            if (!TextUtils.isEmpty(lat)) {
                this.lat = Double.parseDouble(lat);
            }

            if (!TextUtils.isEmpty(lng)) {
                this.lng = Double.parseDouble(lng);
            }

            if (TextUtils.isEmpty(fenceId)) {
                this.geofenceId = 0;
            } else {
                this.geofenceId = Integer.parseInt(fenceId);
            }

            etName.setText(fenceName);
            etName.setSelection(fenceName.length());
        } else {//新增
            tvTitle.setText(R.string.add_electric_fence);
        }

        if (intent.getStringExtra("radius") != null && intent.getStringExtra("radius").length() > 0)
            seekBarRadius.setProgress((int) (Double.parseDouble(intent.getStringExtra("radius")) / 100 - 1));


        if (mBaiduMap == null) {
            mBaiduMap = map.getMap();
        }
        map.showScaleControl(true);
        map.showZoomControls(false);

        ringView = new RingView(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        rlMap.addView(ringView, params);

        //定位初始化
        mBaiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(AppContext.getContext());
        mLocClient.registerLocationListener(myLocationListenner);

        if (lat != 0 && lng != 0) {
            isFirstLoc = false;
            isFirstRequest = false;
            isNew = false;
            LatLng latLng = new LatLng(lat, lng);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(latLng)
                    .zoom(16.0f);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        } else {
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(new LatLng(35.915, 112.4035)).zoom(5.0f);
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }

        //定位初始化
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(10000);

        mLocClient.setLocOption(option);
        mLocClient.start();

        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setRadius();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {

            @Override
            public void onMapLoaded() {
                setRadius();
            }

        });
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                if (zoom != mBaiduMap.getMapStatus().zoom) {
                    setRadius();
                }
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng l = new LatLng(
                        (mapStatus.bound.northeast.latitude + mapStatus.bound.southwest.latitude) / 2,
                        (mapStatus.bound.northeast.longitude + mapStatus.bound.southwest.longitude) / 2);
                if (location_type == 1) {
                    if (location_person != null && (l.latitude != location_person.latitude || l.longitude != location_person.longitude)) {
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(location_person);
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    }
                } else if (location_type == 2) {
                    if (location_device != null && (l.latitude != location_device.latitude || l.longitude != location_device.longitude)) {
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(location_device);
                        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    }
                }
            }
        });

        getTracking();
    }

    private void getTracking() {
        Http.getTracking(this, AppData.GetInstance(this).getSelectedDevice() + "", new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            trackingInfo = new Gson().fromJson(res, TrackingInfo.class);
                            mBaiduMap.clear();
                            location_device = new LatLng(Double.parseDouble(trackingInfo.getLat()), Double.parseDouble(trackingInfo.getLng()));
                            String status = trackingInfo.getStatus();
                            String[] split = status.split("-");
                            int i = CaseData.returnIconInt(Integer.parseInt(trackingInfo.getCourse()), Integer.parseInt(split[0]));
                            OverlayOptions options = new MarkerOptions().position(location_device).icon(BitmapDescriptorFactory.fromResource(i)).zIndex(5);
                            mBaiduMap.addOverlay(options);
                            if (isNew) {
                                MapStatus.Builder builder = new MapStatus.Builder();
                                builder.target(location_device).zoom(16.0f);
                                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                            }

                            break;
                        case 2002:
                            AppData.showToast(AddFenceActivity.this, R.string.no_msg);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.btn_person, R.id.btn_car, R.id.btn_reduce, R.id.btn_increase, R.id.btn_submit, R.id.iv_back, R.id.btn_zoom_in, R.id.btn_zoom_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_zoom_in:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mBaiduMap
                        .getMapStatus().zoom + 1));
                if (mBaiduMap.getMapStatus().zoom >= mBaiduMap.getMaxZoomLevel()) {
                    btnZoomIn.setEnabled(false);
                    btnZoomOut.setEnabled(true);
                }
                break;
            case R.id.btn_zoom_out:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mBaiduMap
                        .getMapStatus().zoom - 1));
                if (mBaiduMap.getMapStatus().zoom <= mBaiduMap.getMinZoomLevel()) {
                    btnZoomOut.setEnabled(false);
                    btnZoomIn.setEnabled(true);
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_person:
                if (location_person != null) {
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(location_person).zoom(16.0f);
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                break;
            case R.id.btn_car:
                if (location_person != null) {
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(location_device).zoom(16.0f);
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                break;
            case R.id.btn_reduce:
                if (seekBarRadius.getProgress() > 0) {
                    seekBarRadius.setProgress(seekBarRadius.getProgress() - 1);
                    setRadius();
                }

                break;
            case R.id.btn_increase:
                if (seekBarRadius.getProgress() < seekBarRadius.getMax()) {
                    seekBarRadius.setProgress(seekBarRadius.getProgress() + 1);
                    setRadius();
                }
                break;
            case R.id.btn_submit:
                String fenceName = etName.getText().toString().trim();
                if (TextUtils.isEmpty(fenceName)) {
                    AppData.showToast(this, R.string.name_empty);
                    return;
                }

                double lat = 0;
                double lng = 0;
                double radius = 0;

                lat = (mBaiduMap.getMapStatus().bound.northeast.latitude +
                        mBaiduMap.getMapStatus().bound.southwest.latitude) / 2;
                lng = (mBaiduMap.getMapStatus().bound.northeast.longitude +
                        mBaiduMap.getMapStatus().bound.southwest.longitude) / 2;

                radius = (seekBarRadius.getProgress() + 1) * 100;
                if (radius < 100) {
                    AppData.showToast(this, R.string.radius_error_100);
                    return;
                }

                Log.d("print", "经度" + lat + "纬度" + lng + "半径" + radius);
                saveFence(AppData.GetInstance(this).getSelectedDevice() + "", fenceName, "", lat + "", lng + "", radius + "", geofenceId + "");
                break;
        }
    }

    private void saveFence(String deviceId, String fenceName, String remark, String lat, String lng, String radius, String geofenceId) {
        Http.saveFence(this, deviceId, fenceName, remark, lat, lng, radius, geofenceId, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                if (object != null) {
                    String res = (String) object;
                    if (res.equals("0")) {
                        AppData.showToast(AddFenceActivity.this, R.string.savefailed);
                    } else {
                        AppData.showToast(AddFenceActivity.this, R.string.saveSucess);
                        finish();
                    }
                }
            }
        });
    }

    private void setRadius() {
        zoom = this.mBaiduMap.getMapStatus().zoom;
        int radius = (seekBarRadius.getProgress() + 1) * 100;
        tvRadius.setText(getResources().getString(R.string.radius) + " " + radius + "m");

        double distance = Distance(mBaiduMap.getMapStatus().bound.northeast.latitude,
                mBaiduMap.getMapStatus().bound.northeast.longitude,
                mBaiduMap.getMapStatus().bound.southwest.latitude,
                mBaiduMap.getMapStatus().bound.southwest.longitude);

        distance = (radius / distance) * rlMap.getWidth();
        ringView.radius = distance;

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ringView
                .getLayoutParams();
        lp.width = ((int) distance * 2);
        lp.height = ((int) distance * 2);
        lp.setMargins((rlMap.getWidth() - lp.width) / 2,
                (rlMap.getHeight() - lp.height) / 2,
                (rlMap.getWidth() - lp.width) / 2,
                (rlMap.getHeight() - lp.height) / 2);
    }

    public static double Distance(double lat1, double long1, double lat2,
                                  double long2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    @Override
    protected void onResume() {
        map.onResume();
        super.onResume();
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        mBaiduMap.setMyLocationEnabled(false);
        map.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mLocClient != null) {
            mLocClient.stop();
            mBaiduMap.setMyLocationEnabled(false);
        }
        map.onDestroy();
        super.onDestroy();
    }
}
