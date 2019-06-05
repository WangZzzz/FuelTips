package com.wz.util;

import java.text.DecimalFormat;

public class NumberUtil {
    public static boolean isZero(float f) {
        return Math.abs(f) < 1e-6;
    }

    /**
     * @param f
     * @return
     */
    public static String format(float f) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数，不足的补0
        return df.format(f);//返回的是String类型的
    }
}
