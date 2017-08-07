package com.yw.obd.entity;

/**
 * Created by apollo on 2017/7/27.
 */

public class YwLatLng {

    private double lat;
    private double lng;
    private String content;

    public YwLatLng(){}

    public YwLatLng(double lat,double lng,String content){
        this.lat=lat;
        this.lng=lng;
        this.content=content;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
