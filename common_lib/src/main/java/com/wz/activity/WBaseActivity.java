package com.wz.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.wz.AppManager;
import com.wz.util.DialogUtil;


/**
 * com.wz.selection
 *
 * @author wangzhe
 * @version 1.0
 * @date 2017/2/8 9:35
 * <br>
 * <br>
 */
public class WBaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.removeActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DialogUtil.showWaitingDialog(this, "提示", "退出应用？", "确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getInstance(WBaseActivity.this).exitApp();
            }
        }, "取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, "正在退出，请稍候...");
    }

    @Override
    public void finish() {
        super.finish();
    }
}
