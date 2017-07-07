package com.wz.fuel.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wz.activity.WBaseActivity;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.util.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseProvinceActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_province)
    TextView mTvProvince;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_selected_province)
    TextView mTvSelectedProvince;
    @BindView(R.id.lv_province)
    ListView mLvProvince;

    private ArrayAdapter<String> mAdapter;

    //31个省份，不包括香港，澳门，台湾
    private static final String[] PROVINCES = {"安徽", "北京", "重庆", "福建", "甘肃", "广东", "广西", "贵州", "海南",
            "河北", "河南", "黑龙江", "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东",
            "山西", "陕西", "上海", "四川", "天津", "西藏", "新疆", "云南", "浙江"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_province);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mToolbarTitle.setText("选择省份");
        mTvSelectedProvince.setText(AppConstants.sProvince);

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, PROVINCES);
        mLvProvince.setAdapter(mAdapter);

        mLvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppConstants.sProvince = PROVINCES[position];
                SpUtil spUtil = new SpUtil(ChooseProvinceActivity.this, AppConstants.SP_CONFIG);
                spUtil.put(AppConstants.SP_PROVINCE, AppConstants.sProvince);
                setResult(RESULT_OK);
                ChooseProvinceActivity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        this.finish();
    }
}
