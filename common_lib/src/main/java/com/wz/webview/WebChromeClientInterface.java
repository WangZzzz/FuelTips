package com.wz.webview;

import android.webkit.WebView;

/**
 * Created by WangZ on 2017-02-18.
 */

public interface WebChromeClientInterface {
    void onReceivedTitle(WebView view, String title);

    void onProgressChanged(WebView view, int newProgress);
}
