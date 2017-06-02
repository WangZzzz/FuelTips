package com.wz.network;


import com.wz.util.WLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp重发
 *
 * @author wz
 */
public class RetryIntercepter implements Interceptor {

    private static final String TAG = RetryIntercepter.class.getSimpleName();

    private int maxRetryTimes = 3;
    private int retryTimes = 0;

    public RetryIntercepter(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        WLog.d(TAG, "retry : " + retryTimes);
        while (response != null && !response.isSuccessful() && retryTimes < maxRetryTimes) {
            retryTimes++;
            response = chain.proceed(request);
        }
        return response;
    }
}
