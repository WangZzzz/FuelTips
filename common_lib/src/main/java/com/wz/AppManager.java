package com.wz;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * com.wz.selection
 *
 * @author wangzhe
 * @version 1.0
 * @date 2017/2/8 9:35
 * <br>
 * <br>
 */
public class AppManager {

    private static volatile AppManager sInstance;

    private Context mContext;

    private AppManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static AppManager getInstance(Context context) {
        if (context == null) {
            return null;
        }
        if (sInstance == null) {
            synchronized (AppManager.class) {
                if (sInstance == null) {
                    sInstance = new AppManager(context);
                }
            }
        }
        return sInstance;
    }

    public void init() {
    }

    public void exitApp() {
        finishAll();
    }

    private static List<Activity> sActivities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        sActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        sActivities.remove(activity);
    }


    public static void finishAll() {
        for (Activity activity : sActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
