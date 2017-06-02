package com.wz.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * 吐司工具
 */
public class ToastMsgUtil {
    private static final String TAG = ToastMsgUtil.class.getSimpleName();


    public static void showToast(Context context, String msg, int duration) {
        if (isMainThread()) {
            Toast.makeText(context, msg, duration).show();
        } else {
            Looper.prepare();
            Toast.makeText(context, msg, duration).show();
            Looper.loop();
        }
    }

    public static void info(Context context, String msg, int duration) {
        if (isMainThread()) {
            Toasty.info(context, msg, duration).show();
        } else {
            Looper.prepare();
            Toasty.info(context, msg, duration).show();
            Looper.loop();
        }
    }


    public static void error(Context context, String msg, int duration) {
        if (isMainThread()) {
            Toasty.error(context, msg, duration).show();
        } else {
            Looper.prepare();
            Toasty.error(context, msg, duration).show();
            Looper.loop();
        }
    }

    public static void normal(Context context, String msg, int duration) {
        if (isMainThread()) {
            Toasty.normal(context, msg, duration).show();
        } else {
            Looper.prepare();
            Toasty.normal(context, msg, duration).show();
            Looper.loop();
        }
    }


    public static void success(Context context, String msg, int duration) {
        if (isMainThread()) {
            Toasty.success(context, msg, duration).show();
        } else {
            Looper.prepare();
            Toasty.success(context, msg, duration).show();
            Looper.loop();
        }
    }

    private static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
