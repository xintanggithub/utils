package com.tson.utils.lib.http;


import android.annotation.SuppressLint;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * Created tangxin
 * Time 2019/3/6 11:11 AM
 */
public class SSLSocketClient {
    /**
     * Gets ssl socket factory.
     *
     * @return the ssl socket factory
     */
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("TrustAllX509TrustManager")
    private static TrustManager[] getTrustManager() {
        return new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};
    }

    /**
     * Gets hostname verifier.
     *
     * @return the hostname verifier
     */
    public static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @SuppressLint("BadHostnameVerifier")
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
    }
}