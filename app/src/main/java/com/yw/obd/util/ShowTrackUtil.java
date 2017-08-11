package com.yw.obd.util;

import android.os.Handler;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.yw.obd.R;

import java.util.List;

/**
 * Created by apollo on 2017/8/10.
 */

public class ShowTrackUtil {

    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 80;
    private static final double DISTANCE = 0.00200;
    private Polyline mPolyline;
    private Handler mHandler;
    private List<LatLng> latLngs;
    private Marker mMoveMarker;
    private MapView mMapView;
    private BaiduMap baiduMap;

    public ShowTrackUtil(Polyline polyline, Handler handler, List<LatLng> latLngs, MapView mapView, BaiduMap baiduMap) {
        mPolyline = polyline;
        mHandler = handler;
        this.latLngs = latLngs;
        this.baiduMap = baiduMap;
        mMapView = mapView;

        OverlayOptions options2 = new MarkerOptions().flat(true).anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_track))
                .position(latLngs.get(0))
                .rotate((float) getAngle(0));
        mMoveMarker = (Marker) baiduMap.addOverlay(options2);
    }


    /**
     * 循环进行移动逻辑
     */
    public void moveLooper() {
        new Thread() {
            public void run() {
                for (int i = 0; i < latLngs.size() - 1; i++) {
                    final LatLng startPoint = latLngs.get(i);
                    final LatLng endPoint = latLngs.get(i + 1);
                    if (startPoint.latitude == endPoint.latitude && startPoint.longitude == endPoint.longitude) {
                        continue;
                    }
                    mMoveMarker.setPosition(startPoint);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // refresh marker's rotate
                            if (mMapView == null) {
                                return;
                            }
                            mMoveMarker.setRotate((float) getAngle(startPoint,
                                    endPoint));
                        }
                    });
                    double slope = getSlope(startPoint, endPoint);
                    // 是不是正向的标示
                    boolean isReverse = (startPoint.latitude > endPoint.latitude);

                    double intercept = getInterception(slope, startPoint);

                    double xMoveDistance = isReverse ? getXMoveDistance(slope) : -1 * getXMoveDistance(slope);

                    System.out.println("A " + startPoint.toString() + "  " + endPoint.toString());
                    System.out.println("B slope" + slope + " isReverse" + isReverse + "  intercept" + intercept + " xMoveDistance" + xMoveDistance);

                    for (double j = startPoint.latitude; !((j > endPoint.latitude) ^ isReverse);
                         j = j - xMoveDistance) {
                        LatLng latLng = null;
                        if (slope == Double.MAX_VALUE) {
                            System.out.println("slope == Double.MAX_VALUE");
                            latLng = new LatLng(j, startPoint.longitude);
                        } else {
                            System.out.println("slope != Double.MAX_VALUE");
                            latLng = new LatLng(j, (j - intercept) / slope);
                        }

                        final LatLng finalLatLng = latLng;
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mMapView == null) {
                                    return;
                                }
                                System.out.println(finalLatLng.latitude + "  " + finalLatLng.longitude);
                                mMoveMarker.setPosition(finalLatLng);
                            }
                        });
                        try {
                            Thread.sleep(TIME_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        }.start();
    }

    /**
     * 根据点获取图标转的角度
     */
    public double getAngle(int startIndex) {
        if ((startIndex + 1) >= mPolyline.getPoints().size()) {
            throw new RuntimeException("index out of bonds");
        }
        LatLng startPoint = mPolyline.getPoints().get(startIndex);
        LatLng endPoint = mPolyline.getPoints().get(startIndex + 1);
        return getAngle(startPoint, endPoint);
    }

    /**
     * 根据两点算取图标转的角度
     */
    public double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 90;
        return angle;
    }

    /**
     * 根据点和斜率算取截距
     */
    public double getInterception(double slope, LatLng point) {

        double interception = point.latitude - slope * point.longitude;
        return interception;
    }

    /**
     * 算斜率
     */
    public double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }

        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        if(slope == 0){
            return Double.MAX_VALUE;
        }
        return slope;

    }

    /**
     * 计算x方向每次移动的距离
     */
    public double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }
}
