package com.wz.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.wz.util.WLog;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行异步任务
 */
public class TaskService extends Service {

    private static final String TAG = TaskService.class.getSimpleName();

    private static final String ACTION_EXEC_TASK = "action_exec_task";
    private static final String ACTION_SEND_MESSAGE = "action_send_message";

    private static final String EXTRA_TASK_NAME = "extra_task_name";
    private static final String EXTRA_ACTION = "extra_action";

    private static Map<String, ITaskListener> sTaskListenerMap;

    static {
        sTaskListenerMap = new HashMap<String, ITaskListener>();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
    }

    public static void execTask(Context context, String taskName, Bundle bundle) {
        if (context == null || TextUtils.isEmpty(taskName)) {
            WLog.e(TAG, "context is null or taskName is null!");
            return;
        }
        Intent intent = new Intent(context, TaskService.class);
        intent.putExtra(EXTRA_ACTION, ACTION_EXEC_TASK);
        intent.putExtra(EXTRA_TASK_NAME, taskName);
        intent.putExtras(bundle);
        context.startService(intent);
    }

    public static void sendMessage(Context context, String taskName, Bundle bundle) {
        if (context == null || TextUtils.isEmpty(taskName)) {
            WLog.e(TAG, "context is null or taskName is null!");
            return;
        }
        Intent intent = new Intent(context, TaskService.class);
        intent.putExtra(EXTRA_ACTION, ACTION_SEND_MESSAGE);
        intent.putExtra(EXTRA_TASK_NAME, taskName);
        intent.putExtras(bundle);
        context.startService(intent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getStringExtra(EXTRA_ACTION);
            String taskName = intent.getStringExtra(EXTRA_TASK_NAME);
            ITaskListener taskListener = sTaskListenerMap.get(taskName);
            if (taskListener != null) {
                if (ACTION_EXEC_TASK.equals(action)) {
                    taskListener.onStart(intent);
                } else if (ACTION_SEND_MESSAGE.equals(action)) {
                    taskListener.onMessage(intent);
                }
            }
        }
    }

    public static void registerTaskListener(String taskName, ITaskListener taskListener) {
        if (!TextUtils.isEmpty(taskName)) {
            sTaskListenerMap.put(taskName, taskListener);
        }
    }

    public static void unregisterTaskListener(String taskName) {
        if (!TextUtils.isEmpty(taskName)) {
            sTaskListenerMap.remove(taskName);
        }
    }
}
