package com.wz.fuel;

import android.app.Application;
import android.content.Context;

import com.wz.fuel.db.GreenDaoManager;

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
    }

    public static Context getContext() {
        return sContext;
    }
}
