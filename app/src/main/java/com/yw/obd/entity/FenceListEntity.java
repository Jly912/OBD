package com.yw.obd.entity;

import java.util.List;

/**
 * Created by apollo on 2017/8/28.
 */

public class FenceListEntity {

    /**
     * state : 0
     * deviceLat : 22.56640
     * deviceLng : 113.87271
     * geofences : [{"geofenceID":"4","fenceName":"Shaken","lat":"22.56641","lng":"113.87272","radius":"100.00","createTime":"2017-08-28"},{"geofenceID":"6","fenceName":"121","lat":"22.56641","lng":"113.87272","radius":"100.00","createTime":"2017-08-28"},{"geofenceID":"7","fenceName":"121","lat":"22.56641","lng":"113.87272","radius":"100.00","createTime":"2017-08-28"},{"geofenceID":"8","fenceName":"121","lat":"22.56641","lng":"113.87272","radius":"400.00","createTime":"2017-08-28"}]
     */

    private String state;
    private String deviceLat;
    private String deviceLng;
    private List<GeofencesBean> geofences;

    @Override
    public String toString() {
        return "FenceListEntity{" +
                "state='" + state + '\'' +
                ", deviceLat='" + deviceLat + '\'' +
                ", deviceLng='" + deviceLng + '\'' +
                ", geofences=" + geofences +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeviceLat() {
        return deviceLat;
    }

    public void setDeviceLat(String deviceLat) {
        this.deviceLat = deviceLat;
    }

    public String getDeviceLng() {
        return deviceLng;
    }

    public void setDeviceLng(String deviceLng) {
        this.deviceLng = deviceLng;
    }

    public List<GeofencesBean> getGeofences() {
        return geofences;
    }

    public void setGeofences(List<GeofencesBean> geofences) {
        this.geofences = geofences;
    }

    public static class GeofencesBean {
        /**
         * geofenceID : 4
         * fenceName : Shaken
         * lat : 22.56641
         * lng : 113.87272
         * radius : 100.00
         * createTime : 2017-08-28
         */

        private String geofenceID;
        private String fenceName;
        private String lat;
        private String lng;
        private String radius;
        private String createTime;

        @Override
        public String toString() {
            return "GeofencesBean{" +
                    "geofenceID='" + geofenceID + '\'' +
                    ", fenceName='" + fenceName + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    ", radius='" + radius + '\'' +
                    ", createTime='" + createTime + '\'' +
                    '}';
        }

        public String getGeofenceID() {
            return geofenceID;
        }

        public void setGeofenceID(String geofenceID) {
            this.geofenceID = geofenceID;
        }

        public String getFenceName() {
            return fenceName;
        }

        public void setFenceName(String fenceName) {
            this.fenceName = fenceName;
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

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
