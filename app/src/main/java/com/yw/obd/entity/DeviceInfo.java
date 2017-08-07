package com.yw.obd.entity;

/**
 * Created by apollo on 2017/8/3.
 */

public class DeviceInfo {

    /**
     * state : 0
     * id : 1
     * carNum : 车牌20015
     * carBrand :
     * carModel :
     * carYear :
     * carGBox :
     * carOutput :
     * carDis : 0
     * carVIN :
     * sn : M201508120015
     */

    private String state;
    private String id;
    private String carNum;
    private String carBrand;
    private String carModel;
    private String carYear;
    private String carGBox;
    private String carOutput;
    private String carDis;
    private String carVIN;
    private String sn;

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "state='" + state + '\'' +
                ", id='" + id + '\'' +
                ", carNum='" + carNum + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carYear='" + carYear + '\'' +
                ", carGBox='" + carGBox + '\'' +
                ", carOutput='" + carOutput + '\'' +
                ", carDis='" + carDis + '\'' +
                ", carVIN='" + carVIN + '\'' +
                ", sn='" + sn + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarGBox() {
        return carGBox;
    }

    public void setCarGBox(String carGBox) {
        this.carGBox = carGBox;
    }

    public String getCarOutput() {
        return carOutput;
    }

    public void setCarOutput(String carOutput) {
        this.carOutput = carOutput;
    }

    public String getCarDis() {
        return carDis;
    }

    public void setCarDis(String carDis) {
        this.carDis = carDis;
    }

    public String getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(String carVIN) {
        this.carVIN = carVIN;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
