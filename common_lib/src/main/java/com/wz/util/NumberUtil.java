package com.wz.util;

/**
 * <br>
 * FIREFLY
 * <p>
 * com.wz.util
 *
 * @author wangzhe
 * @version 3.2.0
 * @date 2017/6/27 10:54
 * @api 7
 * <br>
 * CMBC-版权所有
 * <br>
 */
public class NumberUtil {
    public static boolean isZero(float f) {
        return Math.abs(f) < 1e-6;
    }

    /**
     * @param f
     * @param decimalLength 精度
     * @return
     */
    public static float format(float f, int decimalLength) {
        if (decimalLength > 0) {
            int tmp = 1;
            for (int i = 0; i < decimalLength; i++) {
                tmp = tmp * 10;
            }
            return (float) (Math.round(f * tmp) / tmp);
        } else {
            return f;
        }
    }
}
