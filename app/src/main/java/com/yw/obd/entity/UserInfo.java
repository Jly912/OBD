package com.yw.obd.entity;

import java.io.Serializable;

/**
 * Created by apollo on 2017/8/9.
 */

public class UserInfo implements Serializable {

    /**
     * state : 0
     * name : admin2
     * loginName : admin
     * gender : 0
     * phone : 1511111111
     * briDate : 2017-07-02
     * address : addresss
     */

    private String state;
    private String name;
    private String loginName;
    private String gender;
    private String phone;
    private String briDate;
    private String address;

    @Override
    public String toString() {
        return "UserInfo{" +
                "state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                ", briDate='" + briDate + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBriDate() {
        return briDate;
    }

    public void setBriDate(String briDate) {
        this.briDate = briDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
