package com.yw.obd.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apollo on 2017/8/25.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler instance = new CrashHandler();

    private Context mContext;

    private Map<String, String> infos = new HashMap<>();

    private CrashHandler() {
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    private void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入到该函数进行处理
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        final String message = ex.getLocalizedMessage();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                AppData.showToast(mContext, message);
                Looper.loop();
            }
        }.start();

        return true;
    }

    public void collectDeviceInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo!=null){
                String versionName = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String versionCode = packageInfo.versionCode + "";
                infos.put("versionName",versionCode);
                infos.put("versionCode",versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG,"an error occurred when collect packageInfo",e);
            e.printStackTrace();
        }

        Field[] declaredFields = Build.class.getDeclaredFields();

    }

}


