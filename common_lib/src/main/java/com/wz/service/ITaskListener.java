package com.wz.service;

import android.content.Intent;

/**
 * Task执行、发送消息使用
 */
public interface ITaskListener {
    void onStart(Intent intent);

    void onMessage(Intent intent);
}
