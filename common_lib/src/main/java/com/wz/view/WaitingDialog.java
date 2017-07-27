package com.wz.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wz.R;
import com.wz.util.NumberUtil;

/**
 * 有等待的dialog，点击确认一段时间后才消失
 */
public class WaitingDialog extends Dialog {

    private TextView mTvTitle;
    private ProgressBar mProgressBar;
    private TextView mTvMessage;
    private TextView mTvCancel;
    private TextView mTvConfirm;

    private CharSequence mWaitingMessage;

    private int mWaitingTime = 2000;

    private Handler mHandler = new Handler() {

    };

    public WaitingDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {

        initView();
        initAction();
    }

    private void initView() {
        setContentView(R.layout.waiting_dialog_layout);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
    }

    private void initAction() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public static class Builder {
        private Context mContext;
        private int mTitleColor = -1;
        private int mMessageColor = -1;
        private float mTitleSize = 0f;
        private float mMessageSize = 0f;
        private CharSequence mTitle;
        private CharSequence mMessage;
        private CharSequence mPositiveMsg;
        private CharSequence mNegativeMsg;
        private CharSequence mWaitingMessage;
        private View.OnClickListener mConfirmListener;
        private View.OnClickListener mCancelListener;
        private int mWaitingTime = 2000;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setTitle(@Nullable CharSequence title) {
            mTitle = title;
            return this;
        }

        public Builder setTitleSize(float titleSize) {
            mTitleSize = titleSize;
            return this;
        }

        public Builder setTitleColor(int color) {
            mTitleColor = color;
            return this;
        }

        public Builder setMessage(@Nullable CharSequence message) {
            mMessage = message;
            return this;
        }

        public Builder setMessageSize(float messageSize) {
            mMessageSize = messageSize;
            return this;
        }

        public Builder setMessageColor(int color) {
            mMessageColor = color;
            return this;
        }


        /**
         * @param watingTime 等待时间，默认2000ms
         */
        public Builder setWatingTime(int watingTime) {
            mWaitingTime = watingTime;
            return this;
        }

        public Builder setPositiveMsg(CharSequence positiveMsg) {
            mPositiveMsg = positiveMsg;
            return this;
        }

        public Builder setNegativeMsg(CharSequence negativeMsg) {
            mNegativeMsg = negativeMsg;
            return this;
        }

        public Builder setOnConfirmClickListener(View.OnClickListener onConfirmClickListener) {
            mConfirmListener = onConfirmClickListener;
            return this;
        }

        public Builder setOnCancelClickListener(View.OnClickListener onCancelClickListener) {
            mCancelListener = onCancelClickListener;
            return this;
        }

        public Builder setWaitingMessage(CharSequence waitingMessage) {
            mWaitingMessage = waitingMessage;
            return this;
        }

        public WaitingDialog create() {
            WaitingDialog waitingDialog = new WaitingDialog(mContext);
            if (!TextUtils.isEmpty(mTitle)) {
                waitingDialog.setTitle(mTitle);
            }
            if (!TextUtils.isEmpty(mMessage)) {
                waitingDialog.setMessage(mMessage);
            }
            if (!NumberUtil.isZero(mTitleSize)) {
                waitingDialog.setTitleSize(mTitleSize);
            }
            if (!NumberUtil.isZero(mMessageSize)) {
                waitingDialog.setMessageSize(mMessageSize);
            }
            if (mTitleColor != -1) {
                waitingDialog.setTitleColor(mTitleColor);
            }
            if (mMessageColor != -1) {
                waitingDialog.setMessageColor(mMessageColor);
            }

            if (!TextUtils.isEmpty(mPositiveMsg)) {
                waitingDialog.setPositiveMsg(mPositiveMsg);
            }
            if (!TextUtils.isEmpty(mNegativeMsg)) {
                waitingDialog.setNegativeMsg(mNegativeMsg);
            }
            waitingDialog.setWaitingMessage(mWaitingMessage);
            waitingDialog.setOnCancelClickListener(mCancelListener);
            waitingDialog.setOnConfirmClickListener(mConfirmListener);
            waitingDialog.setWatingTime(mWaitingTime);

            return waitingDialog;
        }
    }

    public void setTitle(@Nullable CharSequence title) {
        mTvTitle.setText(title);
    }

    public void setTitleSize(float titleSize) {
        mTvTitle.setTextSize(titleSize);
    }

    public void setTitleColor(int color) {
        mTvTitle.setTextColor(color);
    }

    public void setMessage(@Nullable CharSequence message) {
        mTvMessage.setText(message);
    }

    public void setMessageSize(float titleSize) {
        mTvMessage.setTextSize(titleSize);
    }

    public void setMessageColor(int color) {
        mTvMessage.setTextColor(color);
    }


    /**
     * @param watingTime 等待时间，默认2000ms
     */
    public void setWatingTime(int watingTime) {
        mWaitingTime = watingTime;
    }

    public void setPositiveMsg(@Nullable CharSequence positiveMsg) {
        mTvConfirm.setText(positiveMsg);
    }

    public void setNegativeMsg(@Nullable CharSequence negativeMsg) {
        mTvCancel.setText(negativeMsg);
    }

    public void setWaitingMessage(CharSequence waitingMessage) {
        mWaitingMessage = waitingMessage;
    }

    public void setOnConfirmClickListener(final View.OnClickListener onConfirmClickLisener) {
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onConfirmClickLisener != null) {
                    startLoading();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onConfirmClickLisener.onClick(v);
                            dismiss();
                        }
                    }, mWaitingTime);
                }
            }
        });
    }

    public void setOnCancelClickListener(final View.OnClickListener onCancelClickListener) {
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelClickListener != null) {
                    onCancelClickListener.onClick(v);
                    dismiss();
                }
            }
        });
    }

    public void startLoading() {
        mTvCancel.setVisibility(View.INVISIBLE);
        mTvConfirm.setVisibility(View.INVISIBLE);
        mTvMessage.setText(mWaitingMessage);
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
