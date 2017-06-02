package com.wz.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加头信息的拦截器，在log拦截器前面添加，这样log拦截器才能打印出添加的header信息
 * Created by wz on 2017/2/17.
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
//                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36")
                .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8")
                .build();
        return chain.proceed(newRequest);
    }
}
