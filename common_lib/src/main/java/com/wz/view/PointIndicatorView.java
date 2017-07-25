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

import java.util.ArrayList;
import java.util.List;

/**
 * 原点指示器View
 */
public class PointIndicatorView extends LinearLayout {

    private static final String TAG = PointIndicatorView.class.getSimpleName();

    private Context mContext;
    //选中和未选中时的图片
    private int mSelectedPoinResId;
    private int mNormalPointResId;
    //圆点大小
    private int mPointSize;

    private List<ImageView> mPointViews;
    //圆点数量
    private int mPointNum;

    //当前索引
    private int mCurrentIndex;

    //圆点间的间隔
    private int mMargin;
    private int mMarginTop;
    private int mMarginLeft;
    private int mMarginRight;
    private int mMarginBottom;

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
        setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.PointIndicatorView);
        mNormalPointResId = array.getResourceId(R.styleable.PointIndicatorView_normalPointDrawable, R.drawable.w_ic_point_normal);
        mSelectedPoinResId = array.getResourceId(R.styleable.PointIndicatorView_selectedPointDrawable, R.drawable.w_ic_point_selected);
        mPointSize = array.getDimensionPixelOffset(R.styleable.PointIndicatorView_pointSize, 35);
        mPointNum = array.getInt(R.styleable.PointIndicatorView_pointNum, 0);
        mMargin = array.getDimensionPixelOffset(R.styleable.PointIndicatorView_pointMargin, 0);
        mMarginTop = array.getDimensionPixelOffset(R.styleable.PointIndicatorView_pointMarginTop, 0);
        mMarginBottom = array.getDimensionPixelOffset(R.styleable.PointIndicatorView_pointMarginBottom, 0);
        mMarginLeft = array.getDimensionPixelOffset(R.styleable.PointIndicatorView_pointMarginLeft, 0);
        mMarginRight = array.getDimensionPixelOffset(R.styleable.PointIndicatorView_pointMarginRight, 0);

        mCurrentIndex = array.getInt(R.styleable.PointIndicatorView_defaultIndex, 0);
        if (mPointNum < 0) {
            throw new RuntimeException("point number : " + mPointNum);
        }
        if (mCurrentIndex < 0 || mCurrentIndex >= mPointNum) {
            throw new RuntimeException("out of bounds!");
        }

        for (int i = 0; i < mPointNum; i++) {
            if (i == mCurrentIndex) {
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
        if (mMargin != 0) {
            layoutParams.setMargins(mMargin, mMargin, mMargin, mMargin);
        } else {
            layoutParams.setMargins(mMarginLeft, mMarginTop, mMarginRight, mMarginBottom);
        }
        imageView.setLayoutParams(layoutParams);
        addView(imageView);
        mPointViews.add(imageView);
    }


    /**
     * 向右移动一位
     */
    public int moveRight() {
        return move(1);
    }

    /**
     * 向左移动一位
     */
    public int moveLeft() {
        return move(-1);
    }

    /**
     * 移动到最头上
     */
    public int moveStart() {
        if (mCurrentIndex == 0) {
            return mCurrentIndex;
        }
        return move(mCurrentIndex);
    }

    /**
     * 移动到尾端
     */
    public int moveEnd() {
        if (mCurrentIndex == mPointViews.size() - 1) {
            //已经在最后一个
            return mCurrentIndex;
        }
        return move(mPointViews.size() - (mCurrentIndex + 1));
    }

    private int move(int offset) {
        ImageView ivOld = mPointViews.get(mCurrentIndex);
        ivOld.setImageResource(mNormalPointResId);
        mCurrentIndex = mCurrentIndex + offset;
        if (mCurrentIndex >= mPointViews.size()) {
            mCurrentIndex = 0;
        } else if (mCurrentIndex < 0) {
            mCurrentIndex = mPointViews.size() - 1;
        }
        ImageView ivNew = mPointViews.get(mCurrentIndex);
        ivNew.setImageResource(mSelectedPoinResId);
        return mCurrentIndex;
    }

    /**
     * 获取当前索引位置
     *
     * @return
     */
    public int getCurrentIndex() {
        return mCurrentIndex;
    }
}