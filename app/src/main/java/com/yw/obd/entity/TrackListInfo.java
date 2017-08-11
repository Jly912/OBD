package com.yw.obd.entity;

import java.util.List;

/**
 * Created by apollo on 2017/8/8.
 */

public class TrackListInfo {


    /**
     * state : 0
     * arr : [{"id":"2","startTime":"2017-08-02 13:51:15","endTime":"2017-08-02 13:52:28","startAddress":"广东省深圳市宝安区松白路4535明一商务大厦附近8米","endAddress":"广东省深圳市宝安区松白路4535明一商务大厦附近32米","stopMinutes":"0","distance":"43"},{"id":"2","startTime":"2017-08-02 14:25:07","endTime":"2017-08-02 16:27:47","startAddress":"广东省深圳市宝安区松白路4535深圳市大兴宝典汽车有限公司附近25米","endAddress":"广东省深圳市宝安区X259(光明大道)光明新区财政局西186米","stopMinutes":"0","distance":"40406"}]
     */

    private String state;
    private List<ArrBean> arr;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ArrBean> getArr() {
        return arr;
    }

    public void setArr(List<ArrBean> arr) {
        this.arr = arr;
    }

    public static class ArrBean {
        /**
         * id : 2
         * startTime : 2017-08-02 13:51:15
         * endTime : 2017-08-02 13:52:28
         * startAddress : 广东省深圳市宝安区松白路4535明一商务大厦附近8米
         * endAddress : 广东省深圳市宝安区松白路4535明一商务大厦附近32米
         * stopMinutes : 0
         * distance : 43
         */

        private String id;
        private String startTime;
        private String endTime;
        private String startAddress;
        private String endAddress;
        private String stopMinutes;
        private String distance;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getStartAddress() {
            return startAddress;
        }

        public void setStartAddress(String startAddress) {
            this.startAddress = startAddress;
        }

        public String getEndAddress() {
            return endAddress;
        }

        public void setEndAddress(String endAddress) {
            this.endAddress = endAddress;
        }

        public String getStopMinutes() {
            return stopMinutes;
        }

        public void setStopMinutes(String stopMinutes) {
            this.stopMinutes = stopMinutes;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
