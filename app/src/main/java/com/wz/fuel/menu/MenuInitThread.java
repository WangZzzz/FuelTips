package com.wz.fuel.menu;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wz.fuel.AppConstants;
import com.wz.fuel.adapter.MenuManager;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.service.TaskService;
import com.wz.util.AndroidUtil;
import com.wz.util.AssetsUtil;
import com.wz.util.SpUtil;
import com.wz.util.WLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuInitThread extends Thread {

    private static final String TAG = MenuInitThread.class.getSimpleName();

    private Context mContext;
    private String mUrl;
    //menu信息json名称
    private String mMenuInfoFileName;
    //图片资源目录名称
    private String mResDirName;

    private ArrayList<MenuDataBean> mMenus;

    public MenuInitThread(Context context, String url, String menuInfoFileName, String resDirName) {
        mContext = context;
        mUrl = url;
        mMenuInfoFileName = menuInfoFileName;
        mResDirName = resDirName;
    }

    @Override
    public void run() {
        init();
        int appVersionCode = AndroidUtil.getAppVersionCode(mContext);
        SpUtil spUtil = new SpUtil(mContext, AppConstants.SP_CONFIG);
        int spVersionCode = spUtil.getInt(AppConstants.SP_APP_VERSION, 0);
        if (appVersionCode > spVersionCode) {
            spUtil.put(AppConstants.SP_APP_VERSION, appVersionCode);
            clearDb();
            copyResFromAssets();
            loadFromAssets();
        } else {
            loadFromDb();
        }
        loadFromServer();
    }

    private void init() {
        mMenus = new ArrayList<>();
    }

    /**
     * 将Assets中图片资源复制到/data/data/包名/file/imageRes下
     */
    private void copyResFromAssets() {
        AssetsUtil.copyFileFromAssets(mContext, mResDirName, MenuManager.getInstance(mContext).getResCacheDir());
    }

    private void loadFromAssets() {
        String menuInfoStr = AssetsUtil.readFile(mContext, mMenuInfoFileName);
        try {
            Gson gson = new Gson();
            if (!TextUtils.isEmpty(menuInfoStr)) {
                MenusBean menusBean = gson.fromJson(menuInfoStr, MenusBean.class);
                if (menusBean != null && menusBean.menus != null && menusBean.menus.size() > 0) {
                    insertDb(menusBean.menus);
                    mMenus.addAll(menusBean.menus);
                    sendSucMessage();
                    WLog.d(TAG, "menus_assets : " + Arrays.toString(mMenus.toArray()));
                } else {
                    WLog.d(TAG, "menus_assets : null");
                }
            }
        } catch (JsonSyntaxException e) {
            WLog.e(TAG, e.getMessage(), e);
        }
    }

    private void loadFromDb() {
        MenuDataBeanDao menuDao = GreenDaoManager.getInstance().getDaoSession().getMenuDataBeanDao();
        List<MenuDataBean> menus = menuDao.queryBuilder().orderAsc(MenuDataBeanDao.Properties.Sort).list();
        if (menus != null && menus.size() > 0) {
            mMenus.addAll(menus);
            sendSucMessage();
            WLog.d(TAG, "menus_db : " + Arrays.toString(mMenus.toArray()));
        } else {
            WLog.d(TAG, "menus_db : null");
        }
    }

    private void loadFromServer() {

    }

    private void mergeData(List<MenuDataBean> serverMenus) {

    }

    private void sendSucMessage() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(MenuManager.EXTRA_MENUS, mMenus);
        TaskService.sendMessage(mContext, MenuManager.TASK_INIT_MENUS, bundle);
    }

    private void sendErrorMessage(String errorMsg) {
        Bundle bundle = new Bundle();
        bundle.putString(MenuManager.EXTRA_ERROR_MSG, errorMsg);
        TaskService.sendMessage(mContext, MenuManager.TASK_INIT_MENUS, bundle);
    }

    private void insertDb(List<MenuDataBean> menus) {
        MenuDataBeanDao menuDao = GreenDaoManager.getInstance().getDaoSession().getMenuDataBeanDao();
        menuDao.insertInTx(menus);
    }

    private void clearDb() {
        MenuDataBeanDao menuDao = GreenDaoManager.getInstance().getDaoSession().getMenuDataBeanDao();
        menuDao.deleteAll();
    }
}
