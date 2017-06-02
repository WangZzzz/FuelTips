package com.wz.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

/**
 * Created by WangZ on 2017-02-18.
 */

public interface WebViewClientInterface {
    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);

    void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);

}
