package com.wz.util;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

/**
 * <br>
 * FIREFLY
 * <p>
 * com.wz.util
 *
 * @author wangzhe
 * @version 3.2.0
 * @date 2017/5/17 14:57
 * @api 7
 * <br>
 * CMBC-版权所有
 * <br>
 */
public class WifiUtil {
    public static void startAp(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "";
    }
}
