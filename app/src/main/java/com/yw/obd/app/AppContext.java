package com.yw.obd.app;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;

/**
 * Created by apollo on 2017/7/26.
 */

public class AppContext extends Application {

    private static Context context = null;

    private BMapManager mapManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 在使用 百度地图SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(context);
        if (mapManager == null) {
            mapManager = new BMapManager();
        }

    }

    public static Context getContext() {
        return context;
    }


}
