package com.wz.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.support.annotation.Keep;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 * Android工具类
 * <p/>
 * 2015年3月4日 下午1:51:24
 *
 * @version 1.0.0
 */
@Keep
public class AndroidUtil {

    public static final String OS_TYPE = "02";
    private static final String TAG = "AndroidUtil";

    /**
     * 取得应用的版本号
     *
     * @param context context对象
     * @return String 应用版本号
     * @throws NameNotFoundException
     * @since 1.0.0
     */
    public static String getAppVersion(Context context) {

        if (context == null) {
            return null;
        } else {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                if (packageInfo != null) {
                    return packageInfo.versionName;
                }
            } catch (NameNotFoundException e) {
                WLog.e(TAG, e.getMessage(), e);
            }
            return null;
        }

    }


    /**
     * 取得应用的版本号
     *
     * @param context context对象
     * @return String 应用版本号
     * @throws NameNotFoundException
     * @since 1.0.0
     */
    public static int getAppVersionCode(Context context) {

        if (context == null) {
            return 0;
        } else {
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                if (packageInfo != null) {
                    return packageInfo.versionCode;
                }
            } catch (NameNotFoundException e) {
                WLog.e(TAG, e.getMessage(), e);
            }
            return 0;
        }

    }


    /**
     * 比较新版本是否比当前版本大
     *
     * @param newVersion
     * @param curVersion
     * @return
     * @throws
     * @permission boolean
     * @since 1.0.0
     */
    public static boolean compareVersion(String newVersion, String curVersion) {
        if (newVersion == null || curVersion == null) {
            return false;
        }
        String[] versionArray1 = newVersion.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = curVersion.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return (diff > 0);
    }


    /**
     * 获取手机序列号IMEI
     *
     * @param context context对象
     * @return String 手机序列号
     * @throws
     * @since 1.0.0
     */
    public static String getDeviceId(Context context) {

        String deviceId = "";
        if (context != null) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (!TextUtils.isEmpty(tm.getDeviceId())) {
                    deviceId = tm.getDeviceId();
                }
            } catch (Exception e) {
                WLog.e(TAG, e.getMessage(), e);
            }
        }
        return deviceId;

    }

    /**
     * 获取蓝牙设备的MAC信息
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getBluetoothMAC() {

        String bluetoothMAC = "";
        try {
            bluetoothMAC = BluetoothAdapter.getDefaultAdapter().getAddress();
        } catch (Exception e) {
            WLog.e(TAG, e.getMessage(), e);
        }
        return bluetoothMAC;
    }

    /**
     * 获取手机的IMSI号 (该信息存储在SIM卡中)
     *
     * @param context
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getIMSI(Context context) {
        String imsi = "";
        if (context != null) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (!TextUtils.isEmpty(tm.getSubscriberId())) {
                imsi = tm.getSubscriberId();
            }
        }
        return imsi;
    }

    /**
     * getAndroidId  手机设备的Android Id
     *
     * @param context context对象
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getAndroidId(Context context) {

        if (context == null) {
            return null;
        }
        return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
    }


    /**
     * 判断是否已联网
     *
     * @param context context对象
     * @return boolean true 已联网 false 未联网
     * @throws
     * @since 1.0.0
     */
    public static boolean isNetworkConnected(Context context) {

        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }

        return false;
    }


    /**
     * 获取网络类型
     *
     * @param context context对象
     * @return String 类型+信息
     * @throws
     * @since 1.0.0
     */
    public static String getNetworkTypeName(Context context) {

        String netType = "";
        if (context != null) {
            try {
                ConnectivityManager manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info != null && info.getExtraInfo() != null) {
                    netType = info.getTypeName() + "_" + info.getExtraInfo().replace("\"", "");
                }
            } catch (Exception e) {
                WLog.e(TAG, e.getMessage(), e);
            }
        }
        return netType;
    }


    public static int getNetworkType(Context context) {

        int netType = ConnectivityManager.TYPE_MOBILE;
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                netType = info.getType();
            }
        }

        return netType;
    }


    /**
     * 获取MAC地址
     *
     * @param context context对象
     * @return String MAC地址
     * @throws
     * @since 1.0.0
     */
    public static String getMAC(Context context) {

        if (context == null) {
            return null;
        }
        String macAddress = "";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            macAddress = info.getMacAddress();// 4C:AA:16:24:F5:43
        } catch (Exception e) {
            WLog.e(TAG, e.getMessage(), e);
        }
        if (TextUtils.isEmpty(macAddress))
            macAddress = "";
        return macAddress;
    }


    /**
     * 获取手机品牌
     *
     * @return String 手机品牌
     * @throws
     * @since 1.0.0
     */
    public static String getDeviceBrand() {

        return android.os.Build.BRAND;

    }

    /**
     * 获取手机主板序列号
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getSerial() {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取手机主板信息
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 获取手机Boot系统
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getBootloader() {
        return android.os.Build.BOOTLOADER;
    }

    /**
     * 获取手机设备型号
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * 获取手机设备名
     *
     * @return String 手机设备名
     * @throws
     * @since 1.0.0
     */
    public static String getDeviceName() {

        return android.os.Build.PRODUCT;

    }

    /**
     * 获取手机厂商信息
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取手机设备的硬件名称
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getHardware() {
        return android.os.Build.HARDWARE;
    }

    /**
     * 获取手机的主机名
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getHost() {
        return android.os.Build.HOST;
    }

    /**
     * 获取手机的编译id
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getBuildId() {
        return android.os.Build.ID;
    }

    /**
     * 获取手机设备指纹
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getFingerprint() {
        return android.os.Build.FINGERPRINT;
    }

    /**
     * 获取手机CPU类型
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getCPU_ABI() {
        return android.os.Build.CPU_ABI;
    }

    /**
     * 获取手机CPU类型
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getCPU_ABI2() {
        return android.os.Build.CPU_ABI2;
    }

    /**
     * 获取设备型号信息
     *
     * @return String 设备型号信息
     * @throws
     * @since 1.0.0
     */
    public static String getDeviceModel() {

        return android.os.Build.MODEL;

    }

    /**
     * 获取手机的操作系统
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getOsName() {

        return android.os.Build.VERSION.INCREMENTAL;
    }

    /**
     * 获取系统版本
     *
     * @return String 系统版本
     * @throws
     * @since 1.0.0
     */
    public static String getOsVersion() {

        return android.os.Build.VERSION.RELEASE;

    }

    /**
     * 获取手机的系统API版本
     *
     * @param
     * @return int
     * @api 6
     * @since 3.1.0
     */
    public static int getSDKVersion() {

        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取系统的运行模式是ART还是Dalvik
     * you can verify which runtime is in use by calling  System.getProperty("java.vm..version").
     * If ART is in use ,the property's value is "2.0.0" or higher.
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    //TODO
    public static String getRunMode() {
        String runMode = "";
        String version = System.getProperty("java.vm..version");
        if (!TextUtils.isEmpty(version)) {
            if (Integer.valueOf(version.substring(0, version.indexOf("."))) >= 2) {
                runMode = "ART";
            } else {
                runMode = "Dalvik";
            }
        }
        return runMode;
    }

    /**
     * 获取设备设备更新信息
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getDevChangeList() {
        return getBuildValue("ro.build.changelist");
    }

    /**
     * 获取设备设备更新日期
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getDevDateUtc() {
        return getBuildValue("ro.build.date.utc");
    }

    /**
     * 获取设备设备日期
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getDevDate() {
        return getBuildValue("ro.build.date");
    }

    /**
     * 获取设备的hidden_ver
     *
     * @param
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getHiddenVer() {
        return getBuildValue("ro.build.hidden_ver");
    }

    /**
     * 通过反射获取/system/build.prop文件中信息
     *
     * @param key 对应的Key值，root手机中可见key值
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getBuildValue(String key) {
        String value = null;
        try {
            Method method = android.os.Build.class.getDeclaredMethod("getString", String.class);
            method.setAccessible(true);
            value = (String) method.invoke(new android.os.Build(), key);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 判断手机是否获取Root权限
     *
     * @param
     * @return boolean
     * @api 6
     * @since 3.1.0
     */
    public static boolean isRoot() {

        boolean bool = false;
        try {
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())) {
                bool = false;
            } else {
                bool = true;
            }
        } catch (Exception e) {
            WLog.e(TAG, e.getMessage(), e);
        }
        return bool;
    }

    /**
     * 获取服务集标识（SSID）
     *
     * @param context context对象
     * @return String 服务集标识（SSID）
     * @throws
     * @since 1.0.0
     */
    public static String getSSID(Context context) {

        if (context == null) {
            return null;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);// 获取ssid等数据
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null && info.getSSID() != null) {
                return info.getSSID().replace("\"", "");
            }
        } catch (Exception e) {
            WLog.e(TAG, e.getMessage(), e);
        }
        return null;
    }


    /**
     * getBSSID 获取BSSID
     *
     * @param context
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    @SuppressWarnings("static-access")
    public static String getBSSID(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);// 获取ssid等数据
        WifiInfo info = wifiManager.getConnectionInfo();
        String bssid = info.getBSSID();
        return bssid;
    }


    /**
     * 获取sim序列号
     *
     * @param context context对象
     * @return String sim序列号
     * @throws
     * @since 1.0.0
     */
    public static String getSimSerialNumber(Context context) {

        if (context == null) {
            return null;
        }
        String SimSerialNumber = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {// sim序列号
                SimSerialNumber = tm.getSimSerialNumber();
            }
        } catch (Exception e) {
            WLog.e(TAG, e.getMessage(), e);
        }
        return SimSerialNumber;
    }


    /**
     * 获取屏幕信息对象
     *
     * @param act Activity对象
     * @return DisplayMetrics 屏幕信息类
     * @since 1.0.0
     */
    public static DisplayMetrics getDisplayMetrics(Activity act) {

        if (act == null) {
            return null;
        } else {
            Display display = act.getWindowManager().getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);

            return displayMetrics;
        }
    }


    /**
     * 获取屏幕信息对象
     *
     * @param context context对象
     * @return DisplayMetrics 屏幕信息类
     * @since 1.0.0
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {

        if (context != null) {
            return context.getResources().getDisplayMetrics();
        }
        return null;
    }


    /**
     * 获取位置区位码
     *
     * @param context context对象
     * @return int 位置区位码
     * @since 1.0.0
     */
    public static int getLac(Context context) {

        int lac = 0;
        if (context != null) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    // GsmCellLocation location = ( GsmCellLocation
                    // )tm.getCellLocation();
                    CellLocation celllocation = tm.getCellLocation();
                    if (celllocation instanceof GsmCellLocation) {
                        GsmCellLocation location = (GsmCellLocation) celllocation;
                        lac = location.getLac();
                    } else if (celllocation instanceof CdmaCellLocation) {
                        CdmaCellLocation location = (CdmaCellLocation) celllocation;
                        lac = location.getNetworkId();
                    }
                }
            } catch (Exception e) {
                WLog.e(TAG, e.getMessage(), e);
            }
        }
        return lac;
    }


    /**
     * 获取基站编号
     *
     * @param context context对象
     * @return int 基站编号
     * @since 1.0.0
     */
    public static int getCid(Context context) {

        int cid = 0;
        if (context != null) {
            try {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null) {
                    // GsmCellLocation location = ( GsmCellLocation
                    // )tm.getCellLocation();
                    CellLocation celllocation = tm.getCellLocation();
                    if (celllocation instanceof GsmCellLocation) {
                        GsmCellLocation location = (GsmCellLocation) celllocation;
                        cid = location.getCid();
                    } else if (celllocation instanceof CdmaCellLocation) {
                        CdmaCellLocation location = (CdmaCellLocation) celllocation;
                        cid = location.getBaseStationId();
                    }
                }
            } catch (Exception e) {
                WLog.e(TAG, e.getMessage(), e);
            }
        }
        return cid;
    }


    /**
     * sendMsg 发送短信
     *
     * @param act    Activity对象
     * @param msg    短信内容
     * @param number 发送电话号
     * @since 1.0.0
     */
    public static void sendMsg(Activity act, String msg, String number) {

        if (act == null || TextUtils.isEmpty(number)) {
            return;
        }

        Uri uri = Uri.parse("smsto:" + number);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", msg);
        try {
            act.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            WLog.e(TAG, e.getMessage(), e.getCause());
        }
    }


    /**
     * 检测是否具有SD卡
     *
     * @return boolean true 是 false 否
     * @permission
     * @since 1.0.0
     */
    public static boolean checkSDCardAvailable() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取存储根目录路径
     *
     * @return String 如果有sd卡，则返回sd卡的目录 如果没有sd卡，则返回存储目录
     * @permission
     * @since 1.0.0
     */
    public static String getSDPath() {

        if (checkSDCardAvailable()) {
            return Environment.getExternalStorageDirectory().getPath();
        } else {
            return Environment.getDownloadCacheDirectory().getPath();
        }
    }


    /**
     * 获取application的meta-data
     *
     * @param context 应用context
     * @param key     待获取的meta-data的key
     * @return String meta-data的value值，若无该key值，则返回null
     * @throws
     * @since 1.0.0
     */
    public static String getApplicationMetaData(Context context, String key) {

        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (NameNotFoundException e) {
            WLog.e(TAG, e.getMessage(), e);
        }

        return null;
    }

    /**
     * 获取IP地址
     *
     * @param context
     * @return java.lang.String
     * @api 6
     * @since 1.0.0
     */
    public static String getIp(Context context) {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            WLog.e(TAG, e.getMessage(), e);
        } catch (Exception e) {
            WLog.e(TAG, e.getMessage(), e);
        }
        return "127.0.0.1";
    }


    // private static String intToIp( int i ) {
    //
    // return ( i & 0xFF ) + "." + ( ( i >> 8 ) & 0xFF ) + "." + ( ( i >> 16 ) &
    // 0xFF ) + "." + ( ( i >> 24 ) & 0xFF );
    // }

    /**
     * 获取运营商的名称
     *
     * @param context
     * @return java.lang.String
     * @api 6
     * @since 1.0.0
     */
    public static String getOperateName(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getSimOperator();
        if (operator != null) {
            if ("46000".equals(operator) || "46002".equals(operator)) {
                return "中国移动";
            } else if ("46001".equals(operator)) {
                return "中国联通";
            } else if ("46003".equals(operator)) {
                return "中国电信";
            } else {
                return tm.getSimOperatorName();
            }
        }
        return null;
    }


    /**
     * 获取设备屏幕宽度
     *
     * @param act
     * @return int
     * @throws
     * @permission
     * @since 1.0.0
     */
    @Keep
    public static final int getMobileWidth(Activity act) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    /**
     * 获取设备屏幕宽度
     *
     * @param act
     * @return int
     * @throws
     * @permission
     * @since 1.0.0
     */
    @Keep
    public static final int getMobileHeight(Activity act) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    /**
     * isWifi(判断用户是否 在wifi 网络环境)
     *
     * @param context
     * @return
     * @throws
     * @permission boolean
     * @since 1.1.0
     */
    public static boolean isWifi(Context context) {

        if (context == null) {
            return false;
        }

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null) {
            return info.isAvailable();
        }
        return false;
    }

    /**
     * 获取当前手机的densityDpi
     *
     * @param context
     * @throws
     * @permission void
     * @api 5
     * @since 3.0.0
     */
    public static int getDensityDpi(Context context) {
        if (context == null) {
            return DisplayMetrics.DENSITY_XHIGH;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            return displayMetrics.densityDpi;
        }

        return DisplayMetrics.DENSITY_XHIGH;
    }

    /**
     * 获取当前手机的density
     *
     * @param context
     * @throws
     * @permission void
     * @api 6
     * @since 3.0.1
     */
    public static int getDensity(Context context) {
        if (context == null) {
            return DisplayMetrics.DENSITY_XHIGH;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getApplicationContext().getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            return (int) displayMetrics.density;
        }

        return DisplayMetrics.DENSITY_XHIGH / 160;
    }

    /**
     * getAppName 获取应用名称
     *
     * @param context
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getAppName(Context context) {
        if (context != null) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                return applicationInfo.loadLabel(context.getPackageManager()).toString();
            }
        }
        return null;
    }

    /**
     * getPackageName 获取包名
     *
     * @param context
     * @return java.lang.String
     * @api 6
     * @since 3.1.0
     */
    public static String getPackageName(Context context) {
        if (context != null) {
            return context.getPackageName();
        }
        return null;
    }

    /**
     * getTargetSdkVersion 获取targetSDK版本
     *
     * @param context
     * @return int
     * @api 6
     * @since 3.1.0
     */
    public static int getTargetSdkVersion(Context context) {

        if (context == null) {
            return 0;
        }
        int targetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        return targetSdkVersion;
    }

    /**
     * getPackageInfoFromApkFile 根据apk路径获取apk的PackageInfo
     *
     * @param context
     * @param apkPath
     * @return android.content.pm.PackageInfo
     * @api 6
     * @since 3.1.0
     */
    public static PackageInfo getPackageInfoFromApkFile(Context context, String apkPath) {

        if (context == null || TextUtils.isEmpty(apkPath)) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            PackageInfo packageInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
            return packageInfo;
        }
        return null;
    }

    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }
}
