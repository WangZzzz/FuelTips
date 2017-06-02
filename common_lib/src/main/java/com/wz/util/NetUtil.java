package com.wz.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * com.wz.selection.util
 *
 * @author wangzhe
 * @version 1.0
 * @date 2017/2/8 9:35
 * <br>
 * <br>
 */
public class NetUtil {
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

}
