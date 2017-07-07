package com.wz.fuel.menu;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.adapter.MenuManager;
import com.wz.util.WLog;

public class MenuJump {

    private static final String TAG = MenuJump.class.getSimpleName();

    public static void jump(Context context, MenuDataBean menu) {
        if (context == null || menu == null) {
            return;
        }
        switch (menu.getJumpType()) {
            case MenuDataBean.TYPE_URL:
                jumpUrl(context, menu);
                break;
            case MenuDataBean.TYPE_SUB_MENUS:
                jumpSubMenus(context, menu);
                break;
            case MenuDataBean.TYPE_NATIVE:
                jumpNative(context, menu);
                break;

        }
    }

    private static void jumpUrl(Context context, MenuDataBean menu) {

    }

    private static void jumpSubMenus(Context context, MenuDataBean menu) {

    }

    private static void jumpNative(Context context, MenuDataBean menu) {
        Class clz = MenuManager.getInstance(context).getTemplate(menu.getTemplate());
        if (clz != null) {
            Intent intent = new Intent(context, clz);
            intent.putExtra(AppConstants.EXTRA_MENU_DATA, menu);
            try {
                context.startActivity(intent);
                if (context instanceof Activity) {
                    ((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                }
            } catch (ActivityNotFoundException e) {
                WLog.e(TAG, e.getMessage(), e);
            }
        } else {
            WLog.e(TAG, "can not find " + menu.getTemplate());
        }
    }
}
