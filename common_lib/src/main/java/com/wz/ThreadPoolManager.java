package com.wz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 *
 * @author wz
 */
public class ThreadPoolManager {
    private static volatile ThreadPoolManager sInstance;
    private static final String TAG = ThreadPoolManager.class.getSimpleName();
    private ExecutorService mExecutorService;
    private static final int DEFAULT_THREAD_SIZE = 5;


    public ThreadPoolManager() {
        mExecutorService = Executors.newFixedThreadPool(DEFAULT_THREAD_SIZE);
    }

    /**
     * getInstance 获取线程池管理器实例
     *
     * @return
     */
    public static ThreadPoolManager getInstance() {

        if (sInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadPoolManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * getThreadPool 获取线程池
     *
     * @param
     * @return java.util.concurrent.ExecutorService
     * @api 6
     * @since 3.1.0
     */
    public ExecutorService getThreadPool() {

        return mExecutorService;
    }


    /**
     * execute 执行任务
     *
     * @param runnable
     * @return void
     * @api 7
     * @since 3.2.0
     */
    public void execute(Runnable runnable) {

        if (runnable != null) {
            mExecutorService.execute(runnable);
        }
    }
}
