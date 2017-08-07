package com.yw.obd.entity;

/**
 * Created by apollo on 2017/8/2.
 */

public class LoginInfo {

    /**
     * state : 0
     * info : {"userID":"2","userName":"admin","loginName":"admin","timeZone":"China Standard Time","deviceCount":"3"}
     */

    private String state;
    private InfoBean info;

    @Override
    public String toString() {
        return "LoginInfo{" +
                "state='" + state + '\'' +
                ", info=" + info +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * userID : 2
         * userName : admin
         * loginName : admin
         * timeZone : China Standard Time
         * deviceCount : 3
         */

        private String userID;
        private String userName;
        private String loginName;
        private String timeZone;
        private String deviceCount;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public String getDeviceCount() {
            return deviceCount;
        }

        public void setDeviceCount(String deviceCount) {
            this.deviceCount = deviceCount;
        }

        @Override
        public String toString() {
            return "InfoBean{" +
                    "userID='" + userID + '\'' +
                    ", userName='" + userName + '\'' +
                    ", loginName='" + loginName + '\'' +
                    ", timeZone='" + timeZone + '\'' +
                    ", deviceCount='" + deviceCount + '\'' +
                    '}';
        }
    }
}
