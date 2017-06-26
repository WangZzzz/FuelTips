package com.wz.fuel.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wz.activity.WBaseActivity;
import com.wz.fuel.AppConstants;
import com.wz.fuel.R;
import com.wz.fuel.mvp.bean.FuelPriceBean;
import com.wz.fuel.mvp.bean.FuelRecordBean;
import com.wz.util.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddFuelRecordActivity extends WBaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.spinner_fuel_type)
    Spinner mSpinnerFuelType;
    @BindView(R.id.et_unit_price)
    EditText mEtUnitPrice;
    @BindView(R.id.et_price_cut)
    EditText mEtPriceCut;
    @BindView(R.id.et_price_discount)
    EditText mEtPriceDiscount;
    @BindView(R.id.et_fuel_liter)
    EditText mEtFuelLiter;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.et_fuel_other_type)
    EditText mEtFuelOtherType;
    @BindView(R.id.ll_other_fuel_type)
    LinearLayout mLlOtherFuelType;
    private FuelPriceBean mFuelPriceBean;

    private FuelRecordBean.FuelType mFuelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel_record);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        initToolbar();
        initSpinner();
        initData();
        initPrice();
    }

    private void initToolbar() {
        mToolbarTitle.setText("添加记录");
        mToolbar.setNavigationIcon(R.drawable.icon_toolbar_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void initSpinner() {
        mSpinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mFuelPriceBean = intent.getParcelableExtra(AppConstants.EXTRA_FUEL_PRICE_BEAN);
        }
    }

    private void initPrice() {
        if (mFuelPriceBean != null) {
            mEtUnitPrice.setText(mFuelPriceBean.price_gas_92 + "");
        }
    }

    @Override
    public void onBackPressed() {
        cancel();
    }


    private void cancel() {
        DialogUtil.showDialog(this, "提示", "放弃正在编辑的数据，退出？", true, null
                , "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        AddFuelRecordActivity.this.finish();
                    }
                }
                , "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    @OnClick({R.id.btn_confirm, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }
    }
}
