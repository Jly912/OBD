package com.yw.obd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.base.BaseActivity;
import com.yw.obd.entity.HistoryTrackInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;
import com.yw.obd.util.ShowTrackUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by apollo on 2017/7/28.
 * 轨迹
 */

public class LocusActivity extends BaseActivity {

    //标题栏
    @Bind(R.id.iv_back)
    ImageButton ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    //底部时间相关
    @Bind(R.id.tv_from_time)
    TextView tvFromTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.ivKm)
    ImageButton ivKm;
    @Bind(R.id.tv_km)
    TextView tvkm;

    //平均油耗、平均速度相关
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    @Bind(R.id.tv_accelerate)
    TextView tvAccelerate;
    @Bind(R.id.tv_moderate)
    TextView tvModerate;
    @Bind(R.id.tv_brake)
    TextView tvBrake;
    @Bind(R.id.tv_turn)
    TextView tvTurn;
    @Bind(R.id.iv_oil)
    ImageView ivOil;
    @Bind(R.id.tv_ave_fuel)
    TextView tvAveFuel;
    @Bind(R.id.iv_speed)
    ImageView ivSpeed;
    @Bind(R.id.tv_ave_speed)
    TextView tvAveSpeed;
    @Bind(R.id.ll_speed)
    LinearLayout llSpeed;

    //底部相关
    @Bind(R.id.iv_time)
    ImageView ivTime;
    @Bind(R.id.tv_drive_time)
    TextView tvDriveTime;
    @Bind(R.id.iv_money)
    ImageView ivMoney;
    @Bind(R.id.tv_ref_money)
    TextView tvRefMoney;
    @Bind(R.id.iv_fuel)
    ImageView ivFuel;
    @Bind(R.id.tv_spend_fuel)
    TextView tvSpendFuel;
    private int selectID;
    private String startTime = "";
    private String endTime = "";
    private String historyId = "";

    //地图相关
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.cb)
    CheckBox cb;

    private BaiduMap baiduMap;
    Marker markerStart, markerEnd;
    private InfoWindow infoWindows, infoWindowe;

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdEnd = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_red_marker);
    BitmapDescriptor bdStart = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_blue_marker);

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdEnd1 = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_end_marker);
    BitmapDescriptor bdStart1 = BitmapDescriptorFactory
            .fromResource(R.drawable.ic_start_marker);

    private LatLng le;
    private LatLng ls;

    private HistoryTrackInfo historyTrackInfo;

    //轨迹播放相关
    private Marker mMoveMarker;
    private Handler mHandler;
    private Polyline mPolyline;
    private ShowTrackUtil util;

    private List<LatLng> latLngs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_locus;
    }

    @Override
    protected void init() {
        mHandler = new Handler(Looper.getMainLooper());

        //获得轨迹起始时间和轨迹id
        Intent intent = getIntent();
        startTime = intent.getStringExtra("start");
        endTime = intent.getStringExtra("end");
        historyId = intent.getStringExtra("id");
        selectID = AppData.GetInstance(this).getSelectedDevice();

        tvTitle.setText(getResources().getString(R.string.locus));
        ivBack.setVisibility(View.VISIBLE);

        baiduMap = map.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        baiduMap.setMapStatus(msu);
        getTrack();//获得轨迹信息


    }

    @OnClick({R.id.iv_back, R.id.tv_from_time, R.id.tv_end_time, R.id.ivKm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_from_time:
                break;
            case R.id.tv_end_time:
                break;
            case R.id.ivKm:
                break;
        }
    }

    private void getTrack() {
        Http.getTrack(this, historyId, startTime, endTime, "Baidu", 1, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = Integer.parseInt(new JSONObject(res).getString("state"));
                    switch (state) {
                        case 0:
                            historyTrackInfo = new Gson().fromJson(res, HistoryTrackInfo.class);
                            String time = historyTrackInfo.getStartTime();
                            String time1 = historyTrackInfo.getEndTime();
                            String[] split = time.split(" ");
                            String[] split1 = time1.split(" ");
                            tvFromTime.setText(split[0] + "\n" + split[1]);
                            tvEndTime.setText(split1[0] + "\n" + split1[1]);

                            String distance = historyTrackInfo.getTripDis();
                            float distant = ((float) Integer.parseInt(distance)) / 1000;
                            if (Integer.parseInt(distance) < 1000) {
                                tvkm.setText(Integer.parseInt(distance) + "\n m");
                            } else {
                                DecimalFormat decimalFormat = new DecimalFormat(".00");
                                tvkm.setText(decimalFormat.format(distant) + "\n Km");
                            }

                            tvAccelerate.setText(historyTrackInfo.getTripAcce());
                            tvBrake.setText(historyTrackInfo.getTripBrak());
                            tvTurn.setText(historyTrackInfo.getTripSharp());
                            tvModerate.setText(historyTrackInfo.getTriDece());

                            tvAveFuel.setText(historyTrackInfo.getTripFuelAvg() + "L/100KM");
                            tvAveSpeed.setText(historyTrackInfo.getTripSpeedAvg() + "km/h");

                            String timeMinute = historyTrackInfo.getTimeMinute();
                            int day = Integer.parseInt(timeMinute)
                                    / (24 * 60);
                            int hour = (Integer.parseInt(timeMinute) - day * 24 * 60) / 60;
                            int minute = Integer.parseInt(timeMinute) - day
                                    * 24 * 60 - hour * 60;

                            String drive = (day > 0 ? day
                                    + getResources().getString(R.string.day)
                                    : "")
                                    + ((hour > 0 || day > 0) ? hour
                                    + getResources().getString(R.string.hour)
                                    : "") + minute
                                    + getResources().getString(R.string.minute);

                            tvDriveTime.setText(drive);

                            tvRefMoney.setText(historyTrackInfo.getTripFuelPrice() + getResources().getString(R.string.yuan));
                            tvSpendFuel.setText(historyTrackInfo.getTripFuel() + "L");
                            initOverlay(historyTrackInfo);
                            drawLine();


                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    /**
     * 画轨迹
     */
    private void drawLine() {
        latLngs = new ArrayList<>();
        for (HistoryTrackInfo.ArrBean bean : historyTrackInfo.getArr()) {
            LatLng latLng = new LatLng(Double.parseDouble(bean.getLat()), Double.parseDouble(bean.getLng()));
            latLngs.add(latLng);
        }

        OverlayOptions options = new PolylineOptions().width(10).color(getResources().getColor(R.color.btn_blue)).points(latLngs);
        mPolyline = (Polyline) baiduMap.addOverlay(options);

        util = new ShowTrackUtil(mPolyline, mHandler, latLngs, map, baiduMap);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//选中 播放
                    util.moveLooper();
                    map.showZoomControls(false);
                } else {//停止播放

                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        baiduMap.clear();
    }


    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        baiduMap.clear();
        markerEnd = null;
        markerStart = null;
    }

    /**
     * 重新添加Overlay
     *
     * @param view
     */
    public void resetOverlay(View view) {
        clearOverlay(null);
        initOverlay(historyTrackInfo);
    }

    /**
     * 画终点和起点位置
     *
     * @param info
     */
    private void initOverlay(HistoryTrackInfo info) {

        List<HistoryTrackInfo.ArrBean> arr = info.getArr();
        HistoryTrackInfo.ArrBean startInfo = arr.get(0);
        HistoryTrackInfo.ArrBean endInfo = arr.get(arr.size() - 1);

        ls = new LatLng(Double.parseDouble(startInfo.getLat()), Double.parseDouble(startInfo.getLng()));
        le = new LatLng(Double.parseDouble(endInfo.getLat()), Double.parseDouble(endInfo.getLng()));

//        LinearLayout inflate = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_start, null);
//        TextView tvTime = (TextView) inflate.findViewById(R.id.tv_time);
//        ImageView ivIcon = (ImageView) inflate.findViewById(R.id.iv_icon);
//
//        tvTime.setText(startInfo.getPt());
//        ivIcon.setImageResource(R.drawable.ic_start_marker);
//
//        MapViewLayoutParams.Builder layoutParamsBuilder = new MapViewLayoutParams.Builder();
//        layoutParamsBuilder.position(ls);
//        layoutParamsBuilder.align(
//                MapViewLayoutParams.ALIGN_CENTER_HORIZONTAL,
//                MapViewLayoutParams.ALIGN_CENTER_VERTICAL);
//        layoutParamsBuilder
//                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode);
//        map.addView(inflate, layoutParamsBuilder.build());

        MarkerOptions os = new MarkerOptions().position(ls).icon(bdStart1).zIndex(5);
        markerStart = (Marker) baiduMap.addOverlay(os.anchor(0.6f, 0.8f));

        MarkerOptions o2 = new MarkerOptions().position(le).icon(bdEnd1).zIndex(5);
        markerStart = (Marker) baiduMap.addOverlay(o2.anchor(0.6f, 0.8f));

//        LinearLayout ll = new LinearLayout(LocusActivity.this);
//        ll.setPadding(10, 5, 10, 5);
//        ll.setBackgroundResource(R.drawable.pic_pop_normal);
//        ll.setGravity(Gravity.CENTER);
//        ll.setOrientation(LinearLayout.HORIZONTAL);
//        ImageView iv = new ImageView(LocusActivity.this);
//        iv.setImageResource(R.drawable.ic_start_marker);
//        TextView tvS = new TextView(LocusActivity.this);
//        tvS.setText(startInfo.getPt());
//        ll.addView(iv);
//        ll.addView(tvS);
//        infoWindows = new InfoWindow(ll, markerStart.getPosition(), -47);
//        baiduMap.showInfoWindow(infoWindows);
//
//        MarkerOptions oe = new MarkerOptions().position(le).icon(bdEnd).zIndex(5);
//        markerEnd = (Marker) baiduMap.addOverlay(oe);
//
//        LinearLayout ll1 = new LinearLayout(LocusActivity.this);
//        ll1.setPadding(10, 5, 10, 5);
//        ll1.setBackgroundResource(R.drawable.pic_pop_normal);
//        ll1.setGravity(Gravity.CENTER);
//        ll1.setOrientation(LinearLayout.HORIZONTAL);
//        ImageView iv1 = new ImageView(LocusActivity.this);
//        iv1.setImageResource(R.drawable.ic_end_marker);
//        TextView tvS1 = new TextView(LocusActivity.this);
//        tvS1.setText(endInfo.getPt());
//        ll1.addView(iv1);
//        ll1.addView(tvS1);
//        infoWindowe = new InfoWindow(ll1, markerEnd.getPosition(), -47);
//        baiduMap.showInfoWindow(infoWindowe);

        LatLngBounds latLngBounds = new LatLngBounds.Builder().include(ls).include(le).build();
        MapStatusUpdate u = MapStatusUpdateFactory
                .newLatLngBounds(latLngBounds);
        baiduMap.setMapStatus(u);
    }
}
