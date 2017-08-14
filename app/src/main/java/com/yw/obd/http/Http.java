package com.yw.obd.http;

import android.content.Context;
import android.util.Log;

import com.yw.obd.util.AppData;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by apollo on 2017/8/8.
 */

public class Http {

    private static final int GET_TRACK = 1;
    private static final int UPDATE_OIL = 2;
    private static final int GET_USER_INFO = 3;
    private static final int UPDATE_USER = 4;
    private static final int GET_OIL = 5;
    private static final int GET_ADD = 6;
    private static final int GET_CAR_OIL = 7;
    private static final int CHECK_CAR = 8;
    private static final int LOGIN = 9;
    private static final int REGISTER = 0x001;
    private static final int SEND_CODE = 0x002;
    private static final int GET_DEVICE_LIST = 0x003;
    private static final int GET_TRACK_LIST = 0x004;
    private static final int GET_DEVICE_TRACKING = 0x005;
    private static final int GET_DEVICE = 0x006;
    private static final String KEY = "20170801CHLOBDYW028M";

    /**
     * 登录
     *
     * @param context
     * @param loginName
     * @param pwd
     * @param listener
     */
    public static void getLogin(Context context, String loginName, String pwd, final OnListener listener) {
        Log.e("print", "---getLogin-" + loginName + "----" + pwd);
        WebService web = new WebService(context, LOGIN, false, "Login");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", loginName);
        property.put("password", pwd);
        property.put("phoneType", 1);
        property.put("loginAPP", "JJG");
        property.put("appID", "");
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);

    }

    /**
     * 注册
     *
     * @param context
     * @param loginName
     * @param pwd
     * @param code
     * @param listener
     */
    public static void register(Context context, String loginName, String pwd, String code, final OnListener listener) {
        WebService web = new WebService(context, REGISTER, false, "Register");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", loginName);
        property.put("password", pwd);
        property.put("code", code);
        property.put("phoneType", 1);
        property.put("loginAPP", "JJG");
        property.put("appID", "");
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 发送验证码
     *
     * @param context
     * @param loginName
     * @param listener
     */
    public static void sendSMSCode(Context context, String loginName, final OnListener listener) {
        WebService web = new WebService(context, SEND_CODE, false, "SendSMSCode");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", loginName);
        property.put("typeID", 0);
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 获得设备列表
     *
     * @param context
     * @param listener
     */
    public static void getDeviceList(Context context, final OnListener listener) {
        WebService web = new WebService(context, GET_DEVICE_LIST, false, "GetDeviceList");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 获得单个设备详情
     *
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void getDeviceDetail(Context context, String deviceId, final OnListener listener) {
        WebService web = new WebService(context, GET_DEVICE, false, "GetDeviceDetail");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("deviceID", deviceId);
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 获得历史轨迹记录
     *
     * @param context
     * @param startTime
     * @param endTime
     * @param listener
     */
    public static void getTrackList(Context context, String startTime, String endTime, final OnListener listener) {
        WebService web = new WebService(context, GET_TRACK_LIST, false, "GetDevicesHistory");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("timeZones", AppData.GetInstance(context).getTimeZone());
        property.put("language", Locale.getDefault().getLanguage());
        property.put("startTime", startTime);
        property.put("endTime", endTime);
        property.put("deviceID", AppData.GetInstance(context).getSelectedDevice());
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 获得轨迹
     *
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void getTracking(Context context, String deviceId, final OnListener listener) {
        WebService web = new WebService(context, GET_DEVICE_TRACKING, false, "GetTracking");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("deviceID", deviceId);
        property.put("timeZones", AppData.GetInstance(context).getTimeZone());
        property.put("mapType", "Baidu");
        property.put("language", Locale.getDefault().getLanguage());
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 获得车辆品牌
     *
     * @param context
     * @param typeId
     * @param id
     * @param listener
     */
    public static void getCarBrandInfo(Context context, int typeId, int id, OnListener listener) {

    }


    /**
     * 获得历史轨迹(界面单段轨迹)
     *
     * @param context
     * @param historyId
     * @param startTime
     * @param endTime
     * @param mapType
     * @param showLBS
     * @param listener
     */
    public static void getTrack(Context context, String historyId, String startTime, String endTime, String mapType, int showLBS, final OnListener listener) {
        WebService webService = new WebService(context, GET_TRACK, false, "GetDevicesHistoryByMap");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("deviceID", AppData.GetInstance(context).getSelectedDevice());
        property.put("historyID", historyId);
        property.put("startTime", startTime);
        property.put("endTime", endTime);
        property.put("mapType", mapType);
        property.put("timeZones", AppData.GetInstance(context).getTimeZone());
        property.put("showLBS", showLBS);
        property.put("language", Locale.getDefault().getLanguage());
        webService.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }

            }
        });
        webService.SyncGet(property);
    }

    /**
     * 车辆检测
     *
     * @param context
     * @param deviceId
     * @param listener
     */
    public static void checkCar(Context context, String deviceId, final OnListener listener) {
        WebService web = new WebService(context, CHECK_CAR, false, "CarCheck");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("deviceID", deviceId);
        property.put("timeZones", AppData.GetInstance(context).getTimeZone());
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 根据经纬度获得地址
     *
     * @param context
     * @param lat
     * @param lng
     * @param mapType
     * @param listener
     */
    public static void getAddress(Context context, String lat, String lng, String mapType, final OnListener listener) {
        WebService web = new WebService(context, GET_ADD, false, "GetAddressByLatlng");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("lat", lat);
        property.put("lng", lng);
        property.put("mapType", mapType);
        property.put("language", Locale.getDefault().getLanguage());
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 获得油耗
     *
     * @param context
     * @param searchTime
     * @param deviceId
     * @param listener
     */
    public static void getCarOil(Context context, String searchTime, String deviceId, final OnListener listener) {
        WebService web = new WebService(context, GET_CAR_OIL, false, "GetCarOIL");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("deviceID", deviceId);
        property.put("timeZones", AppData.GetInstance(context).getTimeZone());
        property.put("searchTime", searchTime);
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 获得油价
     *
     * @param context
     * @param listener
     */
    public static void getOilPrice(Context context, final OnListener listener) {
        WebService web = new WebService(context, GET_OIL, false, "GetOILPrice");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });
        web.SyncGet(property);
    }

    /**
     * 更新油价
     *
     * @param context
     * @param oilPrice
     * @param listener
     */
    public static void updateOil(Context context, String oilPrice, final OnListener listener) {
        WebService webService = new WebService(context, UPDATE_OIL, false, "UpdateOILPrice");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("oilPrice", oilPrice);
        webService.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }

            }
        });
        webService.SyncGet(property);
    }

    /**
     * 获得用户信息
     *
     * @param context
     * @param listener
     */
    public static void getUserInfo(Context context, final OnListener listener) {
        WebService web = new WebService(context, GET_USER_INFO, false, "GetUserInfo");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });

        web.SyncGet(property);
    }

    /**
     * 更新用户信息
     *
     * @param context
     * @param username
     * @param gender
     * @param birth
     * @param phone
     * @param add
     * @param listener
     */
    public static void updateUserInfo(Context context, String username, int gender, String birth
            , String phone, String add, final OnListener listener) {
        WebService web = new WebService(context, UPDATE_USER, false, "UpdateUserInfo");
        HashMap<String, Object> property = new HashMap<>();
        property.put("key", KEY);
        property.put("loginName", AppData.GetInstance(context).getUserName());
        property.put("password", AppData.GetInstance(context).getUserPass());
        property.put("userName", username);
        property.put("gender", gender);
        property.put("briDate", birth);
        property.put("phone", phone);
        property.put("address", add);
        web.addWebServiceListener(new WebService.WebServiceListener() {
            @Override
            public void onWebServiceReceive(String method, int id, String result) {
                if (listener != null) {
                    listener.onSucc(result);
                }
            }
        });

        web.SyncGet(property);
    }

    public interface OnListener {
        void onSucc(Object object);
    }
}
