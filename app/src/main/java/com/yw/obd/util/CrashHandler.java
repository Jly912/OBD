package com.yw.obd.util;

import android.content.Context;

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
        mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler()
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
