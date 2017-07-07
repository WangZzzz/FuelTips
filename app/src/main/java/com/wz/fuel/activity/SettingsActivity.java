package com.wz.fuel.activity;

import android.os.Bundle;

import com.wz.fuel.R;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onBackPressed() {
        animFinish();
    }
}
