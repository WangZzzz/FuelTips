package com.wz.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wz.R;
import com.wz.util.WLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 原点指示器View
 */
public class PointIndicatorView extends LinearLayout {

    private static final String TAG = PointIndicatorView.class.getSimpleName();

    private Context mContext;
    //默认位置
    private int mDefaultIndex;

    private int mSelectedPoinResId = R.drawable.w_ic_point_selected;
    private int mNormalPointResId = R.drawable.w_ic_point_normal;
    private int mPointSize;

    private List<ImageView> mPointViews;
    private int mPointNum;

    private int mCurrentIndex;

    public PointIndicatorView(Context context) {
        this(context, null);
    }

    public PointIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPointViews = new ArrayList<>();
//        setPadding(10, 5, 10, 5);
        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.PointIndicatorView);
        mNormalPointResId = array.getResourceId(R.styleable.PointIndicatorView_normalPointDrawable, R.drawable.w_ic_point_normal);
        mSelectedPoinResId = array.getResourceId(R.styleable.PointIndicatorView_selectedPointDrawable, R.drawable.w_ic_point_selected);
        mPointSize = array.getDimensionPixelOffset(R.styleable.PointIndicatorView_pointSize, 35);
        mPointNum = array.getInt(R.styleable.PointIndicatorView_pointNum, 0);
        mDefaultIndex = array.getInt(R.styleable.PointIndicatorView_defaultIndex, 0);
        mCurrentIndex = mDefaultIndex;
        WLog.d(TAG, "size : " + mPointSize);
        if (mPointNum < 0) {
            throw new RuntimeException("point number : " + mPointNum);
        }
        if (mDefaultIndex < 0 || mDefaultIndex >= mPointNum) {
            throw new RuntimeException("out of bounds!");
        }

        for (int i = 0; i < mPointNum; i++) {
            if (i == mDefaultIndex) {
                addPoint(true);
            } else {
                addPoint(false);
            }
        }
    }

    private void addPoint(boolean isSelected) {
        int pointResId;
        if (isSelected) {
            pointResId = mSelectedPoinResId;
        } else {
            pointResId = mNormalPointResId;
        }
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(pointResId);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(mPointSize, mPointSize);
        layoutParams.setMargins(5, 5, 5, 5);
        imageView.setLayoutParams(layoutParams);
        addView(imageView);
        mPointViews.add(imageView);
    }

}
