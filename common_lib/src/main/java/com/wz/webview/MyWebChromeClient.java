package com.wz.webview;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by WangZ on 2017-02-18.
 */

public class MyWebChromeClient extends WebChromeClient {

    private WebChromeClientInterface mInterface;

    public MyWebChromeClient(WebChromeClientInterface webChromeClientInterface) {
        mInterface = webChromeClientInterface;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (mInterface != null) {
            mInterface.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (mInterface != null) {
            mInterface.onProgressChanged(view, newProgress);
        }
    }
}
