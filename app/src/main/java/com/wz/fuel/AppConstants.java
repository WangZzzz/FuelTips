package com.wz.fuel;

/**
 * 常量类型
 */
public class AppConstants {
    //油价更新时间
    public static long sFuelPriceUpdateTime = 0;

    //全局记录省份
    public static String sProvince = "";

    private static final int BASE_REQUEST_CODE = 10000;

    //选取省份
    public static final int REQUEST_CHOOSE_PROVINCE = BASE_REQUEST_CODE + 1;

    public static final int REQUEST_ADD_FUEL_RECORD = BASE_REQUEST_CODE + 2;

    public static final String EXTRA_FUEL_PRICE_BEAN = "extra_fuel_price_bean";


    public static final String SP_PROVINCE = "sp_province";

    public static final String SP_CONFIG = "global_config";

    public static final String DB_NAME = "fuel_tips";

    public static final String DATE_FORMAT = "yyyy 年 MM 月 dd 日";
}
