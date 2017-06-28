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
import com.wz.util.NumberUtil;
import com.wz.util.TimeUtil;
import com.wz.util.ToastMsgUtil;
import com.wz.util.WLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;

import static com.wz.fuel.mvp.bean.FuelRecordBean.TYPE_DIESEL_0;
import static com.wz.fuel.mvp.bean.FuelRecordBean.TYPE_GAS_89;
import static com.wz.fuel.mvp.bean.FuelRecordBean.TYPE_GAS_92;
import static com.wz.fuel.mvp.bean.FuelRecordBean.TYPE_GAS_95;
import static com.wz.fuel.mvp.bean.FuelRecordBean.TYPE_OTHERS;

public class AddFuelRecordActivity extends WBaseActivity implements View.OnClickListener {

    private static final String TAG = AddFuelRecordActivity.class.getSimpleName();

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
    @BindView(R.id.tv_fuel_date)
    TextView mTvFuelDate;
    private FuelPriceBean mFuelPriceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fuel_record);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        initData();
        initToolbar();
        initSpinner();
        initAction();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mFuelPriceBean = intent.getParcelableExtra(AppConstants.EXTRA_FUEL_PRICE_BEAN);
        }

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mTvFuelDate.setText(year + " 年 " + month + " 月 " + day + " 日");
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
        mSpinnerFuelType.setSelection(1);
        mSpinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WLog.d(TAG, "油品类型：" + FuelRecordBean.getFuelTypeString(position));
                if (position > 3) {
                    mLlOtherFuelType.setVisibility(View.VISIBLE);
                    mEtUnitPrice.setText("");
                    mEtUnitPrice.setHint("0.00");
                } else {
                    mLlOtherFuelType.setVisibility(View.GONE);
                    switch (position) {
                        case TYPE_GAS_89:
                            mEtUnitPrice.setText(mFuelPriceBean.price_gas_89 + "");
                            break;
                        case TYPE_GAS_92:
                            mEtUnitPrice.setText(mFuelPriceBean.price_gas_92 + "");
                            break;
                        case TYPE_GAS_95:
                            mEtUnitPrice.setText(mFuelPriceBean.price_gas_95 + "");
                            break;
                        case TYPE_DIESEL_0:
                            mEtUnitPrice.setText(mFuelPriceBean.price_diesel_0 + "");
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initAction() {
        mTvFuelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示日期选择
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePicker picker = new DatePicker(AddFuelRecordActivity.this);
                picker.setCanceledOnTouchOutside(true);
                picker.setUseWeight(true);
//                picker.setTopPadding(ConvertUtils.toPx(this, 10));
                picker.setRangeEnd(2025, 1, 1);
                picker.setRangeStart(2016, 1, 1);
                picker.setSelectedItem(year, month, day);
                picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mTvFuelDate.setText(year + " 年 " + month + " 月 " + day + " 日");
                    }
                });
                picker.show();
            }
        });
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
                showConfirmDialog();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }
    }

    private void showConfirmDialog() {
        float unitPrice = Float.parseFloat(mEtUnitPrice.getText().toString());
        float discount = 0.0f;
        if (!"".equals(mEtPriceDiscount.getText().toString())) {
            discount = Float.parseFloat(mEtPriceDiscount.getText().toString());
        }
        if (NumberUtil.isZero(discount)) {
            //无折扣
            discount = 10.0f;
        }
        float cut = 0.0f;
        if (!"".equals(mEtPriceCut.getText().toString())) {
            cut = Float.parseFloat(mEtPriceCut.getText().toString());
        }
        float desUnitPrice = unitPrice * (discount / 10) - cut;
        float fuelLiters = 0.0f;
        if (!"".equals(mEtFuelLiter.getText().toString())) {
            fuelLiters = Float.parseFloat(mEtFuelLiter.getText().toString());
        }
        float totalPrice = fuelLiters * desUnitPrice;
        if (desUnitPrice < 0 || NumberUtil.isZero(desUnitPrice) || NumberUtil.isZero(fuelLiters) || NumberUtil.isZero(totalPrice)) {
            ToastMsgUtil.error(AddFuelRecordActivity.this, "数据输入有误，请重新输入!", 1);
        } else {
            FuelRecordBean fuelRecordBean = new FuelRecordBean();
            fuelRecordBean.unitPrice = desUnitPrice;
            fuelRecordBean.totalPrice = totalPrice;
            fuelRecordBean.litres = fuelLiters;
            long fuelDate = TimeUtil.string2Millis(mTvFuelDate.getText().toString(), new SimpleDateFormat(AppConstants.DATE_FORMAT));
            fuelRecordBean.fuelDate = fuelDate;
            int selectedPosition = mSpinnerFuelType.getSelectedItemPosition();
            fuelRecordBean.fuelType = selectedPosition;
            if (selectedPosition >= TYPE_OTHERS) {
                fuelRecordBean.fuelTypeStr = mEtFuelOtherType.getText().toString();
            } else {
                fuelRecordBean.fuelTypeStr = FuelRecordBean.getFuelTypeString(selectedPosition);
            }
            WLog.d(TAG, "单价：" + NumberUtil.format(desUnitPrice));
            WLog.d(TAG, "容量：" + fuelLiters);
            WLog.d(TAG, "日期：" + mTvFuelDate.getText());
            WLog.d(TAG, "总价：" + NumberUtil.format(totalPrice));
            WLog.d(TAG, "油品类型：" + fuelRecordBean.fuelTypeStr);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
