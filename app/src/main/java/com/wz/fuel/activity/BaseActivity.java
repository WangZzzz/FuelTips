package com.wz.fuel.activity;

import com.wz.activity.WBaseActivity;
import com.wz.fuel.R;

public class BaseActivity extends WBaseActivity {

    /**
     * 使用动画效果结束
     */
    public void animFinish() {
        finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_right);
    }
}
