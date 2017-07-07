package com.wz.fuel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.menu.MenuDataBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String title = "关于我们";
        if (intent != null) {
            MenuDataBean menu = intent.getParcelableExtra(AppConstants.EXTRA_MENU_DATA);
            if (menu != null) {
                title = menu.getMenuName();
            }
        }
        initToolbar(title);
    }

    private void initToolbar(String title) {
        mToolbarTitle.setText(title);
        mToolbar.setNavigationIcon(R.drawable.icon_toolbar_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animFinish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        animFinish();
    }
}
