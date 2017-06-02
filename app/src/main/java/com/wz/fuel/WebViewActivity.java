package com.wz.fuel;

import android.graphics.Bitmap;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.TextView;

import com.wz.activity.WBaseActivity;
import com.wz.util.WLog;
import com.wz.webview.WebChromeClientInterface;
import com.wz.webview.WebViewClientInterface;
import com.wz.webview.WebViewManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends WBaseActivity implements WebViewClientInterface, WebChromeClientInterface {

    private static final String TAG = WebViewActivity.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_province)
    TextView mTvProvince;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        WebViewManager.getInstance(this).initWebView(mWebView, this, this);
    }

    public void loadUrl(View view) {
        mWebView.loadUrl("https://m1.cmbc.com.cn/CMBC_MBServer/app/market/registernew/01.jsp?DeviceDigest=CnO+qDW7fiGtSxZG8dd/sL3uI34=&DeviceType=03&Platform=android&DeviceInfo=869186020881436|6fbed700|android%205.1.1");
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        SslCertificate sslCertificate = view.getCertificate();
        if (sslCertificate != null) {
            WLog.d(TAG, sslCertificate.toString());
            WLog.d(TAG, "颁发者：" + sslCertificate.getIssuedBy().toString());
            WLog.d(TAG, "接受者：" + sslCertificate.getIssuedTo().toString());
        } else {
            WLog.d(TAG, "未获取证书信息！");
        }
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        WLog.d(TAG, "onReceivedSslError");
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

    }
}
