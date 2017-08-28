package com.yw.obd.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.yw.obd.app.AppContext;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AppData {
    private static Lock lockHelper = new ReentrantLock();
    private static AppData _object;
    private SharedPreferences sp;
    public boolean isLog;
    public static String Histroy;

    public AppData(Context content) {
        if (AppContext.getContext() != null)
            content = AppContext.getContext();
        sp = content.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public static void showToast(Context context, @StringRes int id) {
        Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static AppData GetInstance(Context content) {
        lockHelper.lock();
        if (_object == null)
            _object = new AppData(content);
        lockHelper.unlock();
        return _object;
    }

    public int getMapType() {
        return sp.getInt("mapType", 0);
    }

    public void setMapType(int mapType) {
        sp.edit()
                .putInt("mapType", mapType)
                .commit();
    }

    public int getUserId() {
        return sp.getInt("userId", 0);
    }

    public void setUserId(int userId) {
        sp.edit()
                .putInt("userId", userId)
                .commit();
    }

    public String getServerPath() {
        return sp.getString("serverPath", "");
    }

    public void setServerPath(String object) {
        sp.edit()
                .putString("serverPath", object)
                .commit();
    }

    public String getServer() {
        return sp.getString("server", "");
    }

    public void setServer(String object) {
        sp.edit()
                .putString("server", object)
                .commit();
    }

    public String getUserName() {
        return sp.getString("userName", "");
    }

    public void setUserName(String object) {
        sp.edit()
                .putString("userName", object)
                .commit();
    }

    public String getTimeZone() {
        return sp.getString("timeZone", "");
    }

    public void setTimeZone(String object) {
        sp.edit()
                .putString("timeZone", object)
                .commit();
    }

    public int getSelectedDevice() {
        return sp.getInt("selectedDevice", 0);
    }

    public void setSelectedDevice(int object) {
        sp.edit()
                .putInt("selectedDevice", object)
                .commit();
    }

    public String getSelectedDeviceName() {
        return sp.getString("selectedDeviceName", "");
    }

    public void setSelectedDeviceName(String object) {
        sp.edit()
                .putString("selectedDeviceName", object)
                .commit();
    }

    public String getUserPass() {
        return sp.getString("userPass", "");
    }

    public void setUserPass(String object) {
        sp.edit()
                .putString("userPass", object)
                .commit();
    }

    public String getCommand() {
        return sp.getString("command", "");
    }

    public void setCommand(String object) {
        sp.edit()
                .putString("command", object)
                .commit();
    }

    public boolean getLoginRemember() {
        return sp.getBoolean("loginRemember", false);
    }

    public void setLoginRemember(boolean r) {
        sp.edit()
                .putBoolean("loginRemember", r)
                .commit();
    }

    public int getLoginType() {
        return sp.getInt("loginType", 0);
    }

    public void setLoginType(int type) {
        sp.edit()
                .putInt("loginType", type)
                .commit();
    }

    //	public String getAlarmSet()
//	{
//		return sp.getString("alarmset", "0000");
//	}
//	public void setAlarmSet(String object)
//	{
//		sp.edit()  
//        .putString("alarmset", object)
//        .commit();
//	}
//	public String getAlarmSet2()
//	{
//		return sp.getString("alarmset2", "0-0-0-1-1-1-1-1-1-1-1-1-1");
//	}
//	public void setAlarmSet2(String object)
//	{
//		sp.edit()  
//        .putString("alarmset2", object)
//        .commit();
//	}
    public String getDeviceListArray() {
        return sp.getString("deviceListArray", "0000");
    }

    public void setDeviceListArray(String object) {
        sp.edit()
                .putString("deviceListArray", object)
                .commit();
    }

    public boolean getAlarmAlert() {
        return sp.getBoolean("AlarmAlert", false);
    }

    public void setAlarmAlert(boolean r) {
        sp.edit()
                .putBoolean("AlarmAlert", r)
                .commit();
    }

    public boolean getAlertSound() {
        return sp.getBoolean("AlertSound", false);
    }

    public void setAlertSound(boolean r) {
        sp.edit()
                .putBoolean("AlertSound", r)
                .commit();
    }

    public boolean getAlertVibration() {
        return sp.getBoolean("AlertVibration", false);
    }

    public void setAlertVibration(boolean r) {
        sp.edit()
                .putBoolean("AlertVibration", r)
                .commit();
    }

    public String getMonitorPhone() {
        return sp.getString("MonitorPhone", "");
    }

    public void setMonitorPhone(String object) {
        sp.edit()
                .putString("MonitorPhone", object)
                .commit();
    }

    public int getSelectedDeviceModel() {
        return sp.getInt("SelectedDeviceModel", 0);
    }

    public void setSelectedDeviceModel(int mapType) {
        sp.edit()
                .putInt("SelectedDeviceModel", mapType)
                .commit();
    }

    public String getPhoneNumber() {
        return sp.getString("PhoneNumber", "");
    }

    public void setPhoneNumber(String object) {
        sp.edit()
                .putString("PhoneNumber", object)
                .commit();
    }

    public String getLastID() {
        return sp.getString("LastID", "");
    }

    public void setLastID(String lastID) {
        sp.edit().putString("LastID", lastID).commit();
    }


    public String getSN() {
        return sp.getString("SN", "");
    }

    public void setSN(String SN) {
        sp.edit().putString("SN", SN).commit();
    }
}
