package com.wz.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by WangZ on 2017-02-18.
 */

public class MyWebViewClient extends WebViewClient {

    private WebViewClientInterface mInterface;

    public MyWebViewClient(WebViewClientInterface webViewClientInterface) {
        mInterface = webViewClientInterface;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        //数据统计
        MobclickAgent.onPageStart(url);
        if (mInterface != null) {
            mInterface.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        MobclickAgent.onPageEnd(url);
        if (mInterface != null) {
            mInterface.onPageFinished(view, url);
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (mInterface != null) {
            mInterface.onReceivedError(view, request, error);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        if (mInterface != null) {
            mInterface.onReceivedSslError(view, handler, error);
        }
    }
}
