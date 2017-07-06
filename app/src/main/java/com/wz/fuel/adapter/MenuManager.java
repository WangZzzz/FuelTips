package com.wz.fuel.adapter;

import android.content.Context;

import java.io.File;

public class MenuManager {

    private static volatile MenuManager sInstance;

    private Context mContext;

    private String mResCacheDir;

    private MenuManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static MenuManager getInstance(Context context) {
        if (context == null) {
            return null;
        }

        if (sInstance == null) {
            synchronized (MenuManager.class) {
                if (sInstance == null) {
                    sInstance = new MenuManager(context);
                }
            }
        }
        return sInstance;
    }

    public void init() {
        mResCacheDir = mContext.getFilesDir().getAbsolutePath() + "/imageRes";
        File file = new File(mResCacheDir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public String getResCacheDir() {
        return mResCacheDir;
    }
}
