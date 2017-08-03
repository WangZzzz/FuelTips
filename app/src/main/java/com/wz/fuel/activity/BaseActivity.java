package com.wz.fuel.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.wz.activity.WBaseActivity;
import com.wz.fuel.R;
import com.wz.util.StatusBarUtil;

public class BaseActivity extends WBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColorBar(this, Color.parseColor("#3A94FF"));
    }

    /**
     * 使用动画效果结束
     */
    public void animFinish() {
        finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_right);
    }
}
