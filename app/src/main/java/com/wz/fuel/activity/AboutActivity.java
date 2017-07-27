package com.wz.fuel.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.menu.MenuDataBean;
import com.wz.util.AndroidUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_app_name)
    TextView mTvAppName;
    @BindView(R.id.tv_check_update)
    TextView mTvCheckUpdate;
    @BindView(R.id.tv_update_info)
    TextView mTvUpdateInfo;
    @BindView(R.id.tv_email)
    TextView mTvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
        initAction();
    }


    private void initView() {
        initToolbar();
        String appVersion = AndroidUtil.getAppVersion(this);
        mTvAppName.setText(getString(R.string.app_name) + appVersion);
    }

    private void initToolbar() {
        Intent intent = getIntent();
        String title = "关于我们";
        if (intent != null) {
            MenuDataBean menu = intent.getParcelableExtra(AppConstants.EXTRA_MENU_DATA);
            if (menu != null) {
                title = menu.getMenuName();
            }
        }
        mToolbarTitle.setText(title);
        mToolbar.setNavigationIcon(R.drawable.icon_toolbar_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animFinish();
            }
        });
    }

    private void initAction() {
        mTvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:" + getString(R.string.email_address));
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(intent, "选择邮件客户端"));
            }
        });

        mTvCheckUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUpdate();
            }
        });
    }

    private void checkUpdate() {

    }

    @Override
    public void onBackPressed() {
        animFinish();
    }
}
