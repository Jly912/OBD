package com.yw.obd.entity;

/**
 * Created by apollo on 2017/8/18.
 */

public class OilDetailInfo {

    /**
     * state : 0
     * allDistance : 1159.0
     * allOIL : 126.0
     * oilPrice : 818.9
     * oilFuel100 : 9.4
     * carbon : 340.1
     */

    private String state;
    private String allDistance;
    private String allOIL;
    private String oilPrice;
    private String oilFuel100;
    private String carbon;

    @Override
    public String toString() {
        return "OilDetailInfo{" +
                "state='" + state + '\'' +
                ", allDistance='" + allDistance + '\'' +
                ", allOIL='" + allOIL + '\'' +
                ", oilPrice='" + oilPrice + '\'' +
                ", oilFuel100='" + oilFuel100 + '\'' +
                ", carbon='" + carbon + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAllDistance() {
        return allDistance;
    }

    public void setAllDistance(String allDistance) {
        this.allDistance = allDistance;
    }

    public String getAllOIL() {
        return allOIL;
    }

    public void setAllOIL(String allOIL) {
        this.allOIL = allOIL;
    }

    public String getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(String oilPrice) {
        this.oilPrice = oilPrice;
    }

    public String getOilFuel100() {
        return oilFuel100;
    }

    public void setOilFuel100(String oilFuel100) {
        this.oilFuel100 = oilFuel100;
    }

    public String getCarbon() {
        return carbon;
    }

    public void setCarbon(String carbon) {
        this.carbon = carbon;
    }
}
