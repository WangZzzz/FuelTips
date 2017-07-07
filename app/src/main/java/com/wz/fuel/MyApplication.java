package com.wz.fuel;

import android.app.Application;
import android.content.Context;

import com.wz.AppManager;
import com.wz.fuel.activity.AboutActivity;
import com.wz.fuel.activity.DonationActivity;
import com.wz.fuel.activity.SettingsActivity;
import com.wz.fuel.adapter.MenuManager;
import com.wz.fuel.db.GreenDaoManager;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        init();
    }

    /**
     * 做一些初始化操作
     */
    private void init() {
        //初始化GreenDao
        GreenDaoManager.getInstance().init(this, AppConstants.DB_NAME);
        AppManager.getInstance(this).init();
        Map<String, Class> templateMap = new HashMap<String, Class>();
        templateMap.put("about", AboutActivity.class);
        templateMap.put("donation", DonationActivity.class);
        templateMap.put("settings", SettingsActivity.class);
        MenuManager.getInstance(this).init("", "MenuInfo.json", "imageRes", templateMap);
    }

    public static Context getContext() {
        return sContext;
    }
}
