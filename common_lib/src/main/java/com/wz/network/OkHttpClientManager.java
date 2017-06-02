package com.wz.network;


import com.wz.network.interceptor.HeaderInterceptor;
import com.wz.network.interceptor.LoggingInterceptor;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * 创建Okhttp
 *
 * @author wz
 */
public class OkHttpClientManager {

    private static final int CONNECT_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIEMOUT = 30;
    private static final int MAX_CONNECT_NUMS = 20;
    private static final int KEEP_ALIVE_TIMES = 20;
    private static volatile OkHttpClientManager sInstance = null;
    private static final Object LOCK = new Object();
    private OkHttpClient mClient;
    // 管理、存储cookie
    private ConcurrentHashMap<String, List<Cookie>> mCookieStore = new ConcurrentHashMap<String, List<Cookie>>();


    private OkHttpClientManager() {
        initOkHttpClient();
    }

    public static OkHttpClientManager getInstance() {

        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new OkHttpClientManager();
                }
            }
        }
        return sInstance;
    }

    public OkHttpClient genOkHttpClient() {

        return mClient;
    }

    private void initOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 设置超时时间：连接超时、读取超时、写入超时
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIEMOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new HeaderInterceptor());
        builder.addInterceptor(new LoggingInterceptor());
        builder.addInterceptor(new RetryIntercepter(3));
        // 设置最大连接数和存活时间
        builder.connectionPool(new ConnectionPool(MAX_CONNECT_NUMS, KEEP_ALIVE_TIMES, TimeUnit.SECONDS));

        // 忽略https认证
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{new X509TrustManagerImpl()}, new SecureRandom());
            builder.sslSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        builder.hostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {

                // TODO Auto-generated method stub
                return true;
            }
        });

        // 自动处理cookies
        builder.cookieJar(new CookieJar() {

            @Override
            public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                // TODO Auto-generated method stub
                mCookieStore.put(url.host(), cookies);
            }


            @Override
            public synchronized List<Cookie> loadForRequest(HttpUrl url) {

                // TODO Auto-generated method stub
                List<Cookie> cookies = mCookieStore.get(url.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });

        mClient = builder.build();
    }

    public List<Cookie> getCookies(String host) {

        List<Cookie> cookies = null;
        if (mCookieStore != null && !mCookieStore.isEmpty()) {
            cookies = mCookieStore.get(host);
        }
        return cookies;
    }


    public List<Cookie> getAllCookies() {

        List<Cookie> cookies = null;
        if (mCookieStore != null && !mCookieStore.isEmpty()) {
            Set<String> urls = mCookieStore.keySet();
            for (String url : urls) {
                if (cookies == null) {
                    cookies = mCookieStore.get(url);
                } else {
                    cookies.addAll(mCookieStore.get(url));
                }
            }
        }
        return cookies;
    }


    public void cancelCallsWithTag(Object tag) {

        if (tag == null || mClient == null) {
            return;
        }

        synchronized (LOCK) {
            for (Call call : mClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag()))
                    call.cancel();
            }

            for (Call call : mClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag()))
                    call.cancel();
            }
        }
    }


    public void clean() {

        mClient = null;
        // 清除cookie
        mCookieStore.clear();
    }


    public void cleanCookies() {

        mCookieStore.clear();
    }

    /**
     * addCookies 添加单个域名对应的多个cookie
     *
     * @param host    对应的域名
     * @param cookies
     * @return void
     * @api 7
     * @since 3.2.0
     */
    public void addCookies(String host, List<Cookie> cookies) {

        if (cookies != null) {
            if (mCookieStore == null) {
                mCookieStore = new ConcurrentHashMap<>();
            }
            mCookieStore.put(host, cookies);
        }

    }

    /**
     * addCookie 添加cookie
     *
     * @param host   该cookie对应的域名
     * @param cookie
     * @return void
     * @api 7
     * @since 3.2.0
     */
    public void addCookie(String host, Cookie cookie) {

        if (cookie != null) {

            if (mCookieStore == null) {
                mCookieStore = new ConcurrentHashMap<>();
            }
            List<Cookie> cookies = mCookieStore.get(host);
            if (cookies == null) {
                cookies = new ArrayList<>();
            }
            cookies.add(cookie);
        }

    }
}
