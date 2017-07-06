package com.wz.fuel.menu;

import android.content.Context;

import com.google.gson.Gson;
import com.wz.fuel.AppConstants;
import com.wz.fuel.adapter.MenuManager;
import com.wz.fuel.db.GreenDaoManager;
import com.wz.util.AndroidUtil;
import com.wz.util.AssetsUtil;
import com.wz.util.SpUtil;

import java.util.List;

public class MenuInitThread extends Thread {
    private Context mContext;
    private String mUrl;
    //menu信息json名称
    private String mMenuInfoFileName;
    //图片资源目录名称
    private String mResDirName;

    @Override
    public void run() {
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

    /**
     * 将Assets中图片资源复制到/data/data/包名/file/imageRes下
     */
    private void copyResFromAssets() {
        AssetsUtil.copyFileFromAssets(mContext, mResDirName, MenuManager.getInstance(mContext).getResCacheDir());
    }

    private void loadFromAssets() {
        String menuInfoStr = AssetsUtil.readFile(mContext, mMenuInfoFileName);
        Gson gson = new Gson();
    }

    private void loadFromDb() {

    }

    private void loadFromServer() {

    }

    private void insertDb(List<MenuDataBean> menus) {

    }

    private void clearDb() {
        MenuDataBeanDao menuDao = GreenDaoManager.getInstance().getDaoSession().getMenuDataBeanDao();
        menuDao.deleteAll();
    }
}
