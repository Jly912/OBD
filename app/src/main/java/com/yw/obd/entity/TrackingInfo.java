package com.yw.obd.entity;

import java.io.Serializable;

/**
 * Created by apollo on 2017/8/7.
 */

public class TrackingInfo implements Serializable {

    /**
     * state : 0
     * positionTime : 2017-08-02 19:46:14
     * lat : 22.77632
     * lng : 113.91326
     * speed : 0.00
     * course : 999
     * isStop : 0
     * isGPS : 0
     * status : 3-
     */

    private String state;
    private String positionTime;
    private String lat;
    private String lng;
    private String speed;
    private String course;
    private String isStop;
    private String isGPS;
    private String status;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(String positionTime) {
        this.positionTime = positionTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop;
    }

    public String getIsGPS() {
        return isGPS;
    }

    public void setIsGPS(String isGPS) {
        this.isGPS = isGPS;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TrackingInfo{" +
                "state='" + state + '\'' +
                ", positionTime='" + positionTime + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", speed='" + speed + '\'' +
                ", course='" + course + '\'' +
                ", isStop='" + isStop + '\'' +
                ", isGPS='" + isGPS + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
