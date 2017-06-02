package com.wz.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


/**
 * com.wz.selection.util
 *
 * @author wangzhe
 * @version 1.0
 * @date 2017/2/8 9:35
 * <br>
 * <br>
 */
public class DialogUtil {

    public static void showDialog(Context context, String message
            , String positiveMsg, DialogInterface.OnClickListener positiveClickListener
            , String negativeMsg, DialogInterface.OnClickListener negativeClickListener) {
        showDialog(context, "提示", message, false, null, positiveMsg, positiveClickListener, negativeMsg, negativeClickListener);
    }

    public static void showDialog(Context context, String message
            , String btnMsg, DialogInterface.OnClickListener onClickListener) {
        showDialog(context, "提示", message, false, null, btnMsg, onClickListener, null, null);
    }

    public static void showDialog(Context context, String title, String message
            , boolean cancelable, DialogInterface.OnCancelListener onCancelListener
            , String positiveMsg, DialogInterface.OnClickListener positiveClickListener
            , String negativeMsg, DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        if (!TextUtils.isEmpty(positiveMsg)) {
            builder.setPositiveButton(positiveMsg, positiveClickListener);
        }

        if (!TextUtils.isEmpty(negativeMsg)) {
            builder.setNegativeButton(negativeMsg, negativeClickListener);
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
