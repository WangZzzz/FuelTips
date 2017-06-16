package com.wz.fuel.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.wz.fuel.R;

public class AddFuelRecordPopupWindow extends PopupWindow {

    private View mContentView;
    private Activity mAct;

    public AddFuelRecordPopupWindow(Activity activity) {
        mAct = activity;
        mContentView = LayoutInflater.from(mAct).inflate(R.layout.add_record_pop_window, null);
        setContentView(mContentView);

        //设置
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        //设置背景,这有设置了此，才可以点击外面或者back消失
        setBackgroundDrawable(mAct.getResources().getDrawable(R.drawable.bg_pop_window_corner));
        //设置可以获取焦点
        setFocusable(true);
        //设置点击外边可以消失
        setOutsideTouchable(true);
        //设置可以触摸
        setTouchable(true);
        //设置弹出的窗体需要软键盘
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //设置软键盘覆盖，并自动调整大小
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setTouchInterceptor(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        //判断是不是点击了外边
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            return true;
                        }
                        //不是点击外部
                        return false;
                    }
                }
        );
    }

    private void initView() {

    }

    public void showPopupWidow(View parent) {
        if (!isShowing()) {
            showAtLocation(parent, Gravity.CENTER, 0, 0);
        }
    }

    public void hidePopupWindow() {
        if (isShowing()) {
            dismiss();
        }
    }


}
