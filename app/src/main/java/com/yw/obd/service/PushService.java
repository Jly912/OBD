package com.yw.obd.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yw.obd.R;
import com.yw.obd.activity.AlarmActivity;
import com.yw.obd.entity.DeviceListInfo;
import com.yw.obd.http.Http;
import com.yw.obd.util.AppData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apollo on 2017/8/17.
 */

public class PushService extends Service {
    private Thread getThread = null;
    private int alarmId;
    private int alarmIndex;
    private boolean isFirstLoad = true;
    private DeviceListInfo deviceListInfo;
    private String lastID = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Log.e("print", "Service---" + AppData.GetInstance(PushService.this).getAlarmAlert());
            if (!AppData.GetInstance(PushService.this).getAlarmAlert()) {
                return;
            }
            getNewWarn();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        mHandler.sendEmptyMessage(0);
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        getThread.start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotify() {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.logo)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
        Intent intent = new Intent(this, AlarmActivity.class);
    }

    @Override
    public void onDestroy() {
        NotificationManager no = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        no.cancelAll();
        if (getThread != null) {
            getThread.interrupt();
        }
        super.onDestroy();
    }

    private String deviceId, warnTxt, warnTime, warnSound;

    private void getNewWarn() {
//        Log.e("print", "========getNewWarn===========");
        String lastID = AppData.GetInstance(this).getLastID();
        if (TextUtils.isEmpty(lastID)) {
            lastID = "0";
        }
        Http.getNewWarn(this, lastID, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
//                Log.d("print", "--getWarn--" + res);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    int state = jsonObject.getInt("state");
                    switch (state) {
                        case 0:
                            alarmId = jsonObject.getInt("id");
                            AppData.GetInstance(PushService.this).setLastID(alarmId + "");
                            deviceId = jsonObject.getString("deviceID");
                            warnTxt = jsonObject.getString("warnTxt");
                            warnTime = jsonObject.getString("warnTime");
                            warnSound = jsonObject.getString("warnSound");
                            NotificationManager no = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            Notification.Builder builder = new Notification.Builder(PushService.this)
                                    .setSmallIcon(R.drawable.logo)//设置状态栏图标
                                    .setWhen(System.currentTimeMillis())//设置发生时间
                                    .setAutoCancel(true);//设置可以清除
                            Intent intent = new Intent(PushService.this, AlarmActivity.class);
                            String deviceName = "";
//                            Application app = PushService.this.getApplication();
                            intent.putExtra("id", deviceId);

                            getDeviceList();
                            if (deviceListInfo != null) {
                                for (int i = 0; i < deviceListInfo.getArr().size(); i++) {
                                    if (deviceId.equals(deviceListInfo.getArr().get(i).getId())) {
                                        deviceName = deviceListInfo.getArr().get(i).getName();
                                        break;
                                    }
                                }
                            }

//                            intent.putExtra("deviceName", deviceName);
                            alarmIndex++;
                            PendingIntent pi = PendingIntent.getActivity(PushService.this, alarmIndex, intent, 0);
                            builder.setContentIntent(pi);
                            builder.setContentTitle(deviceName + jsonObject.getString("warnTxt"));// 设置下拉列表里的标题
                            builder.setContentText(jsonObject.getString("warnTime"));// 设置上下文内容
                            Notification n = builder.getNotification();// 获取一个Notification

                            if (AppData.GetInstance(PushService.this).getAlertVibration()) {
                                n.defaults |= Notification.DEFAULT_VIBRATE;
                            }

                            if (AppData.GetInstance(PushService.this).getAlertSound()) {
                                n.defaults |= Notification.DEFAULT_SOUND;
                            }

                            no.notify(1, n);
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void getDeviceList() {
        Http.getDeviceList(this, new Http.OnListener() {
            @Override
            public void onSucc(Object object) {
                String res = (String) object;
                try {
                    int state = new JSONObject(res).getInt("state");
                    switch (state) {
                        case 0:
                            deviceListInfo = new Gson().fromJson(res, DeviceListInfo.class);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
