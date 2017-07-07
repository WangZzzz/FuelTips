package com.wz.fuel.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.wz.ThreadPoolManager;
import com.wz.fuel.menu.MenuDataBean;
import com.wz.fuel.menu.MenuInitThread;
import com.wz.service.ITaskListener;
import com.wz.service.TaskService;
import com.wz.util.WLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {

    private static final String TAG = MenuManager.class.getSimpleName();

    private static volatile MenuManager sInstance;

    private Context mContext;

    private String mResCacheDir;

    private List<MenuDataBean> mMenuList;

    public static final String EXTRA_MENUS = "extra_menus";
    public static final String EXTRA_ERROR_MSG = "extra_error_msg";

    public static final String TASK_INIT_MENUS = "task_init_menus";

    private Map<String, Class> mTemplateMap;

    private String mServerUrl;
    private String mMenuInfoName;
    private String mResDirName;


    private ITaskListener mMenuInitListener = new ITaskListener() {
        @Override
        public void onStart(Intent intent) {
            MenuInitThread menuInitThread = new MenuInitThread(mContext, "", "MenuInfo.json", "imageRes");
            ThreadPoolManager.getInstance().execute(menuInitThread);
        }

        @Override
        public void onMessage(Intent intent) {
            if (intent != null) {
                List<MenuDataBean> menuList = intent.getParcelableArrayListExtra(EXTRA_MENUS);
                if (menuList != null && menuList.size() > 0) {
                    WLog.d(TAG, "menu_onMessage : " + Arrays.toString(menuList.toArray()));
                    mMenuList.clear();
                    mMenuList.addAll(menuList);
                    sortMenus();
                } else {
                    WLog.d(TAG, "menu_onMessage : null");
                }
            }
        }
    };

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

    public void init(String serverUrl, String menuInfoName, String resDirName, Map<String, Class> templateMap) {
        mMenuList = new ArrayList<MenuDataBean>();
        mResCacheDir = mContext.getFilesDir().getAbsolutePath() + "/imageRes";
        mServerUrl = serverUrl;
        mMenuInfoName = menuInfoName;
        if (templateMap != null) {
            this.mTemplateMap = templateMap;
        } else {
            mTemplateMap = new HashMap<>();
        }
        File file = new File(mResCacheDir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        initMenu();
    }

    private void initMenu() {
        TaskService.registerTaskListener(TASK_INIT_MENUS, mMenuInitListener);
        TaskService.execTask(mContext, TASK_INIT_MENUS, null);
    }


    public String getResCacheDir() {
        return mResCacheDir;
    }

    public Map<String, Class> getTemplateMap() {
        return mTemplateMap;
    }

    /**
     * 返回key对应的activity
     *
     * @param key
     * @return
     */
    public Class getTemplate(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        return mTemplateMap.get(key);
    }

    public List<MenuDataBean> getMenuList() {
        return mMenuList;
    }

    /**
     *
     */
    private void sortMenus() {
        if (mMenuList != null) {
            Collections.sort(mMenuList, new Comparator<MenuDataBean>() {
                @Override
                public int compare(MenuDataBean o1, MenuDataBean o2) {
                    return o1.getSort() - o2.getSort();
                }
            });
            WLog.d(TAG, "sort");
        }
    }
}
