package com.wz.util;

import java.math.BigDecimal;

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
     * 保留两位小数
     *
     * @param f
     * @return
     */
    public static float format(float f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
