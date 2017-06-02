package com.wz.fuel.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.wz.activity.WBaseActivity;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends WBaseActivity {

    private static final int WAIT_TIME = 3000;

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        startAnimation();
        SpUtil spUtil = new SpUtil(this, AppConstants.SP_CONFIG);
        AppConstants.sProvince = spUtil.getString(AppConstants.SP_PROVINCE, "");


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, WAIT_TIME);
    }

    private void startAnimation() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIvSplash, "scaleX", 1f, 1.1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIvSplash, "scaleY", 1f, 1.1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(WAIT_TIME);
        animatorSet.start();
    }

    @Override
    public void onBackPressed() {
    }
}
