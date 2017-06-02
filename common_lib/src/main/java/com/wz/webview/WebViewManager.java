package com.wz.webview;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.wz.BuildConfig;
import com.wz.util.WLog;


/**
 * Created by WangZ on 2017-02-18.
 */

public class WebViewManager {

    private static final String TAG = WebViewManager.class.getSimpleName();

    private volatile static WebViewManager sInstance;
    private Context mContext;
    private WebChromeClientInterface mWebChromeClientInterface;
    private WebViewClientInterface mWebViewClientInterface;

    private WebViewManager(Context context) {
        if (context != null) {
            mContext = context.getApplicationContext();
        }
    }

    public static WebViewManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WebViewManager.class) {
                if (sInstance == null) {
                    sInstance = new WebViewManager(context);
                }
            }
        }
        return sInstance;
    }

    public void initWebView(WebView webView) {
        initWebView(webView, null, null);
    }

    public void initWebView(WebView webView, WebViewClientInterface webViewClientInterface, WebChromeClientInterface webChromeClientInterface) {
        setWebChromeClientInterface(webChromeClientInterface);
        setWebViewClientInterface(webViewClientInterface);
        init(webView);
    }

    public void setWebChromeClientInterface(WebChromeClientInterface chromeClientInterface) {
        mWebChromeClientInterface = chromeClientInterface;
    }

    public void setWebViewClientInterface(WebViewClientInterface webViewClientInterface) {
        mWebViewClientInterface = webViewClientInterface;
    }

    private void init(WebView webView) {
        if (webView == null) {
            return;
        }
        WLog.d(TAG, "initWebView...");
        WebSettings webSettings = webView.getSettings();
        webView.clearCache(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //新版本方法
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);
        //支持缩放并不显示缩放按钮*****************************
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //**************************************************
        //自适应屏幕大小**************************************
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //**************************************************
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setUserAgentString(WebSettings.getDefaultUserAgent(mContext));
        } else {
            webSettings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        webView.setWebViewClient(new MyWebViewClient(mWebViewClientInterface));
        webView.setWebChromeClient(new MyWebChromeClient(mWebChromeClientInterface));
    }
}
