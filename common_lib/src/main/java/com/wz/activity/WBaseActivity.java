package com.wz.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
        DialogUtil.showDialog(this, "退出应用？", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppManager.exitApp();
            }
        }, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
