package com.yw.obd.entity;

import java.util.List;

/**
 * Created by apollo on 2017/8/3.
 */

public class DeviceListInfo {

    /**
     * state : 0
     * arr : [{"id":"1","name":"M201508120015","carNum":"车牌20015"},{"id":"2","name":"M201706180004","carNum":"车牌80004"},{"id":"3","name":"M201612010118","carNum":"车牌10118"}]
     */

    private String state;
    private List<ArrBean> arr;

    @Override
    public String toString() {
        return "DeviceListInfo{" +
                "state='" + state + '\'' +
                ", arr=" + arr +
                '}';
    }

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
         * id : 1
         * name : M201508120015
         * carNum : 车牌20015
         */

        private String id;
        private String name;
        private String carNum;
        private String sn;

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        @Override
        public String toString() {
            return "ArrBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", carNum='" + carNum + '\'' +
                    ", sn='" + sn + '\'' +
                    '}';
        }
    }
}
