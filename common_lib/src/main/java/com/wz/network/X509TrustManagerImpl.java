package com.wz.network;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;


/**
 * 处理https证书，忽略
 *
 * @author wz
 */
public class X509TrustManagerImpl implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {

        // TODO Auto-generated method stub
    }


    @Override
    public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {

        // TODO Auto-generated method stub
    }


    @Override
    public X509Certificate[] getAcceptedIssuers() {

        // TODO Auto-generated method stub
        X509Certificate[] certificates = new X509Certificate[]{};
        return certificates;
    }
}
