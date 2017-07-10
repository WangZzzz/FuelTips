package com.wz.fuel.message;

import android.os.Bundle;

public class MessageEvent {
    public int messageType;
    public Bundle data;

    private static final int TYPE_BASE_CODE = 10000;
    //刷新所有fragment
    public static final int TYPE_REFRESH_ALL_FRAGMENT = TYPE_BASE_CODE + 1;
    //刷新"今日油价"
    public static final int TYPE_REFRESH_FUEL_PRICE_FRAGMENT = TYPE_BASE_CODE + 2;
    //刷新“加油记录”
    public static final int TYPE_REFRESH_FUEL_RECORD_FRAGMENT = TYPE_BASE_CODE + 3;
    //刷新“加油统计”
    public static final int TYPE_REFRESH_FUEL_STATISTICS_FRAGMENT = TYPE_BASE_CODE + 4;
    //刷新"我”
    public static final int TYPE_REFRESH_MINE_FRAGMENT = TYPE_BASE_CODE + 5;
}
