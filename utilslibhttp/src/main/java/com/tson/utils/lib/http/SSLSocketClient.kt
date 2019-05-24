package com.tson.utils.lib.http


import android.annotation.SuppressLint

import javax.net.ssl.*
import java.security.SecureRandom
import java.security.cert.X509Certificate

/**
 * Created tangxin
 * Time 2019/3/6 11:11 AM
 */
object SSLSocketClient {
    /**
     * Gets ssl socket factory.
     *
     * @return the ssl socket factory
     */
    val sslSocketFactory: SSLSocketFactory
        get() {
            try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustManager, SecureRandom())
                return sslContext.socketFactory
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

    private val trustManager: Array<TrustManager>
        @SuppressLint("TrustAllX509TrustManager")
        get() = arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

    /**
     * Gets hostname verifier.
     *
     * @return the hostname verifier
     */
    val hostnameVerifier: HostnameVerifier
        get() = HostnameVerifier { s, sslSession -> true }
}