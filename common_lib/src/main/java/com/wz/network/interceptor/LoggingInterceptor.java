package com.wz.network.interceptor;


import com.wz.util.WLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 拦截器
 * Created by wz on 2017/2/17.
 */
public class LoggingInterceptor implements Interceptor {

    private static final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        WLog.d(TAG, String.format("request:%n[url--->%s]%n[headers--->%s]",
                request.url(), request.headers()));
        Response response = null;
        try {
            response = chain.proceed(request);
            WLog.d(TAG, String.format("response:%n[url--->%s]%n[code--->%d]%n[headers--->%s]",
                    response.request().url(), response.code(), response.headers()));
        } catch (Exception e) {
            WLog.e(TAG, String.format("requestError:%n[errorMessage--->%s]", e.getMessage()), e);
            throw e;
        }
        return response;
    }
}
