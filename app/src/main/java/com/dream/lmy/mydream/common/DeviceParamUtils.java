package com.dream.lmy.mydream.common;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import retrofit2.http.PUT;

public class DeviceParamUtils {

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceParam(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && telephonyManager != null) {
//            Log.e("TEST","IMEI："+telephonyManager.getImei());//IMEI
        }else {
            Log.e("TEST","IMEI："+telephonyManager.getDeviceId());//IMEI
        }
//        Log.e("TEST","手机号："+telephonyManager.getLine1Number());//手机号
        Log.e("TEST","网络国家iso："+telephonyManager.getNetworkCountryIso());//网络国家iso
        Log.e("TEST","网络运营商："+telephonyManager.getNetworkOperator());//网络运营商
        Log.e("TEST","网络运营商名称："+telephonyManager.getNetworkOperatorName());//网络运营商名称
        Log.e("TEST","网络类型："+telephonyManager.getNetworkType());//网络类型
        Log.e("TEST","手机类型："+telephonyManager.getPhoneType());//手机类型
//        Log.e("TEST","imsi："+telephonyManager.getSubscriberId());//imsi
        Log.e("TEST","数据状态："+telephonyManager.getDataState());//数据状态

        Log.e("TEST","sim卡国家iso："+telephonyManager.getSimCountryIso());//sim卡国家iso
        Log.e("TEST","sim卡运营商："+telephonyManager.getSimOperator());//
        Log.e("TEST","sim卡运营商名称："+telephonyManager.getSimOperatorName());
        Log.e("TEST","sim卡状态："+telephonyManager.getSimState());
//        Log.e("TEST","sim卡SerialNumber："+telephonyManager.getSimSerialNumber());

        Log.e("TEST","手机语言："+ Locale.getDefault().getLanguage());//手机语言

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        getDeviceInfo();
        return "";
    }

    /**
     * 获取手机设备信息
     * @return
     */
    public static String getDeviceInfo(){
        Log.e("TEST","用户名："+Build.USER);//用户名
        Log.e("TEST","硬件设备："+Build.DEVICE);//硬件设备
        Log.e("TEST","手机平台版本："+Build.DISPLAY);//手机平台版本
        Log.e("TEST","设备品牌："+Build.BRAND);//设备品牌
        Log.e("TEST","手机AndroidSDK版本号："+Build.VERSION.SDK_INT);//手机AndroidSDK版本号
        Log.e("TEST","手机Android版本号RELEASE："+Build.VERSION.RELEASE);//手机Android版本号RELEASE
        Log.e("TEST","："+Build.MANUFACTURER);
        Log.e("TEST","："+Build.ID);//ID
        return "";
    }

    /**
     * 每次获取到的值，都不一样
     * @return
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        Log.e("TEST","uuid："+uuid);//
        return uuid.toString();
    }

    /**
     * 获取手机硬件序列号，6.0系统需自行判断权限问题
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getSerial(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.e("TEST","硬件序列号："+Build.getSerial());//硬件序列号
            return Build.getSerial();
        } else {
            Log.e("TEST","硬件序列号："+Build.SERIAL);//硬件序列号
            return Build.SERIAL;
        }
    }

    /**
     *  SIM卡的IMSI码是国际移动用户识别码，SIM卡的唯一标识
     *  1-3 国家码，460表示中国运营商，否则为境外卡
     *  4-5 是运营码，移动00、02、04、07
     *  联通01、06，电信03、05、11，铁通20
     *  Android SDK只能取到第一张卡。这个跟设备厂商有关系
     * @param context
     * @return
     */
    @SuppressLint({"MissingPermission","HardwareIds"})
    public String getOperators(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String string = telephonyManager != null ? telephonyManager.getSubscriberId() : null;

        //telephonyManager.getNetworkOperatorName();//此方法可直接获取运营商名称，但在部分手机上，即使没有SIM卡，也会返回中国电信，故不推荐使用
        if (null == string){
            return "";
        }
        if (string.startsWith("46000") || string.startsWith("46002") || string.startsWith("46004")|| string.startsWith("46007")) {
            return "中国移动";
        } else if (string.startsWith("46001") || string.startsWith("46006")) {
            return "中国联通";
        } else if (string.startsWith("46003") || string.startsWith("46005") || string.startsWith("46011")) {
            return "中国电信";
        }
        return "";
    }

    public static String getPhoneProduct(){
        Log.e("TEST","设备正式名称："+Build.PRODUCT);//设备正式名称
        return Build.PRODUCT;
    }

    /**
     * 获取手机设备版本
     * @return
     */
    public static String getPhoneModel(){
        Log.e("TEST","手机设备版本："+Build.MODEL);//手机设备版本
        return Build.MODEL;
    }

    /***
     * 获取处理器
     * @return
     */
    public static String getBoard(){
        Log.e("TEST","处理器："+Build.BOARD);//处理器
        return Build.BOARD;
    }

    /**
     * 获取WiFi状态
     * @param Application
     * @return
     */
    public static int getWifiState(Application context){
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null){
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            Log.e("TEST","WIFI名称："+wifiInfo.getSSID());
            wifiInfo.getSupplicantState();
            return wifiManager.getWifiState();
        }
        return WifiManager.WIFI_STATE_UNKNOWN;
    }

    /**
     * 获取AndroidID
     * AndroidID，刷机wipe数据时，或恢复出厂设置时，会被重置
     * 该值可能被修改
     * @param context 上下文
     * @return AndroidID
     */
    public static String getAndroidId(Context context){
        return Settings.System.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }



}
