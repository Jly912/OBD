package com.yw.obd.entity;

import java.util.List;

/**
 * Created by apollo on 2017/8/16.
 */

public class WarnListInfo {

    /**
     * state : 0
     * nowPage : 1
     * resSize : 536
     * arr : [{"id":"922","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 14:02","createDate":"2017/08/15 14:02"},{"id":"921","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:56","createDate":"2017/08/15 13:56"},{"id":"920","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:55","createDate":"2017/08/15 13:55"},{"id":"919","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:55","createDate":"2017/08/15 13:55"},{"id":"918","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:53","createDate":"2017/08/15 13:53"},{"id":"917","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:49","createDate":"2017/08/15 13:49"},{"id":"916","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:49","createDate":"2017/08/15 13:49"},{"id":"915","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:44","createDate":"2017/08/15 13:44"},{"id":"914","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 13:30","createDate":"2017/08/15 13:30"},{"id":"913","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 12:31","createDate":"2017/08/15 12:32"},{"id":"912","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 12:31","createDate":"2017/08/15 12:32"},{"id":"911","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 12:30","createDate":"2017/08/15 12:30"},{"id":"910","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 12:29","createDate":"2017/08/15 12:30"},{"id":"909","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 12:29","createDate":"2017/08/15 12:29"},{"id":"908","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:58","createDate":"2017/08/15 11:58"},{"id":"907","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:55","createDate":"2017/08/15 11:55"},{"id":"906","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:55","createDate":"2017/08/15 11:55"},{"id":"905","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:40","createDate":"2017/08/15 11:41"},{"id":"904","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:32","createDate":"2017/08/15 11:32"},{"id":"903","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:32","createDate":"2017/08/15 11:32"},{"id":"902","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:31","createDate":"2017/08/15 11:31"},{"id":"901","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:21","createDate":"2017/08/15 11:21"},{"id":"900","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 11:04","createDate":"2017/08/15 11:04"},{"id":"899","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:56","createDate":"2017/08/15 10:56"},{"id":"898","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:40","createDate":"2017/08/15 10:40"},{"id":"897","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:40","createDate":"2017/08/15 10:40"},{"id":"896","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:25","createDate":"2017/08/15 10:25"},{"id":"895","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:25","createDate":"2017/08/15 10:25"},{"id":"894","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:19","createDate":"2017/08/15 10:19"},{"id":"893","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:13","createDate":"2017/08/15 10:13"},{"id":"892","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:13","createDate":"2017/08/15 10:13"},{"id":"891","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 10:01","createDate":"2017/08/15 10:01"},{"id":"890","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 00:03","createDate":"2017/08/15 00:03"},{"id":"889","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/15 00:02","createDate":"2017/08/15 00:02"},{"id":"888","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 20:40","createDate":"2017/08/14 20:40"},{"id":"887","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 20:08","createDate":"2017/08/14 20:08"},{"id":"886","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 20:06","createDate":"2017/08/14 20:06"},{"id":"885","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 19:07","createDate":"2017/08/14 19:07"},{"id":"884","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 18:56","createDate":"2017/08/14 18:56"},{"id":"883","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 18:50","createDate":"2017/08/14 18:50"},{"id":"882","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 18:48","createDate":"2017/08/14 18:48"},{"id":"881","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 18:33","createDate":"2017/08/14 18:33"},{"id":"880","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 18:21","createDate":"2017/08/14 18:21"},{"id":"879","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 18:00","createDate":"2017/08/14 18:00"},{"id":"878","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 17:54","createDate":"2017/08/14 17:54"},{"id":"877","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 17:12","createDate":"2017/08/14 17:13"},{"id":"876","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 17:11","createDate":"2017/08/14 17:11"},{"id":"875","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 17:06","createDate":"2017/08/14 17:06"},{"id":"874","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 17:01","createDate":"2017/08/14 17:01"},{"id":"873","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 17:01","createDate":"2017/08/14 17:01"},{"id":"872","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:43","createDate":"2017/08/14 16:43"},{"id":"871","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:38","createDate":"2017/08/14 16:38"},{"id":"870","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:36","createDate":"2017/08/14 16:36"},{"id":"869","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:29","createDate":"2017/08/14 16:29"},{"id":"868","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:29","createDate":"2017/08/14 16:29"},{"id":"867","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:29","createDate":"2017/08/14 16:29"},{"id":"866","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:29","createDate":"2017/08/14 16:29"},{"id":"865","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:29","createDate":"2017/08/14 16:29"},{"id":"864","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:29","createDate":"2017/08/14 16:29"},{"id":"863","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:24","createDate":"2017/08/14 16:24"},{"id":"862","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 16:21","createDate":"2017/08/14 16:21"},{"id":"861","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 15:39","createDate":"2017/08/14 15:39"},{"id":"860","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 15:17","createDate":"2017/08/14 15:17"},{"id":"859","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 15:14","createDate":"2017/08/14 15:14"},{"id":"858","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 15:05","createDate":"2017/08/14 15:05"},{"id":"857","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 14:28","createDate":"2017/08/14 14:28"},{"id":"856","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 14:22","createDate":"2017/08/14 14:22"},{"id":"855","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 14:13","createDate":"2017/08/14 14:13"},{"id":"854","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 14:11","createDate":"2017/08/14 14:11"},{"id":"853","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 14:01","createDate":"2017/08/14 14:01"},{"id":"852","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:52","createDate":"2017/08/14 13:52"},{"id":"851","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:51","createDate":"2017/08/14 13:51"},{"id":"850","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:44","createDate":"2017/08/14 13:44"},{"id":"849","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:36","createDate":"2017/08/14 13:36"},{"id":"848","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:33","createDate":"2017/08/14 13:33"},{"id":"847","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:25","createDate":"2017/08/14 13:25"},{"id":"846","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:15","createDate":"2017/08/14 13:15"},{"id":"845","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:14","createDate":"2017/08/14 13:14"},{"id":"844","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:12","createDate":"2017/08/14 13:12"},{"id":"843","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:12","createDate":"2017/08/14 13:12"},{"id":"842","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:10","createDate":"2017/08/14 13:10"},{"id":"841","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 13:00","createDate":"2017/08/14 13:00"},{"id":"840","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:46","createDate":"2017/08/14 12:46"},{"id":"839","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:44","createDate":"2017/08/14 12:44"},{"id":"838","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:43","createDate":"2017/08/14 12:43"},{"id":"837","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:33","createDate":"2017/08/14 12:33"},{"id":"836","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:32","createDate":"2017/08/14 12:32"},{"id":"835","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:26","createDate":"2017/08/14 12:26"},{"id":"834","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:25","createDate":"2017/08/14 12:25"},{"id":"833","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:23","createDate":"2017/08/14 12:23"},{"id":"832","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:21","createDate":"2017/08/14 12:21"},{"id":"831","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:21","createDate":"2017/08/14 12:21"},{"id":"830","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:11","createDate":"2017/08/14 12:11"},{"id":"829","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:10","createDate":"2017/08/14 12:10"},{"id":"828","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:08","createDate":"2017/08/14 12:08"},{"id":"827","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:06","createDate":"2017/08/14 12:07"},{"id":"826","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:06","createDate":"2017/08/14 12:07"},{"id":"825","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 12:01","createDate":"2017/08/14 12:01"},{"id":"824","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 11:49","createDate":"2017/08/14 11:49"},{"id":"823","name":"CLS","model":"OBD","warn":"","deviceDate":"2017/08/14 11:42","createDate":"2017/08/14 11:42"}]
     */

    private String state;
    private String nowPage;
    private String resSize;
    private List<ArrBean> arr;

    @Override
    public String toString() {
        return "WarnListInfo{" +
                "state='" + state + '\'' +
                ", nowPage='" + nowPage + '\'' +
                ", resSize='" + resSize + '\'' +
                ", arr=" + arr +
                '}';
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNowPage() {
        return nowPage;
    }

    public void setNowPage(String nowPage) {
        this.nowPage = nowPage;
    }

    public String getResSize() {
        return resSize;
    }

    public void setResSize(String resSize) {
        this.resSize = resSize;
    }

    public List<ArrBean> getArr() {
        return arr;
    }

    public void setArr(List<ArrBean> arr) {
        this.arr = arr;
    }

    public static class ArrBean {
        /**
         * id : 922
         * name : CLS
         * model : OBD
         * warn :
         * deviceDate : 2017/08/15 14:02
         * createDate : 2017/08/15 14:02
         */

        private String id;
        private String name;
        private String model;
        private String warn;
        private String deviceDate;
        private String createDate;

        @Override
        public String toString() {
            return "ArrBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", model='" + model + '\'' +
                    ", warn='" + warn + '\'' +
                    ", deviceDate='" + deviceDate + '\'' +
                    ", createDate='" + createDate + '\'' +
                    '}';
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

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getWarn() {
            return warn;
        }

        public void setWarn(String warn) {
            this.warn = warn;
        }

        public String getDeviceDate() {
            return deviceDate;
        }

        public void setDeviceDate(String deviceDate) {
            this.deviceDate = deviceDate;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }
}
