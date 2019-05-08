package com.tson.utils.lib.http

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  Created tangxin
 *  Time 2019/5/5 10:31 AM
 */
class RetrofitFactory {

    companion object {

        fun <T> createApi(service: Class<T>, url: String): T {
            return createApi(service, url, buildOkHttpClient(mLoggingInterceptor))
        }

        fun <T> createApi(service: Class<T>, url: String, okHttpClient: OkHttpClient): T {
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(service)
        }

//        fun <T> createData(
//            observable: Observable<BaseData<T>>,
//            apiStatus: BaseApiStatus<T>
//        ): Disposable {
//            return createData(observable, apiStatus, GetResultFilter())
//        }
//
//        fun <T> createData(
//            observable: Observable<BaseData<T>>,
//            apiStatus: BaseApiStatus<T>,
//            function: Function<BaseData<T>, Observable<T>>
//        ): Disposable {
//            apiStatus.before()
//            return observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap<T>(function)
//                .subscribe(apiStatus::success, apiStatus::error)
//        }
//
//        class GetResultFilter<T> : Function<BaseData<T>, Observable<T>> {
//            override fun apply(t: BaseData<T>): Observable<T> {
//                return Observable.just(t.base)
//            }
//        }

        fun buildOkHttpClient(interceptor: Interceptor): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
                .readTimeout(10, TimeUnit.SECONDS)//读取超时
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//忽略证书过期时间ø
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .writeTimeout(10, TimeUnit.SECONDS)//写入超时
            builder.addInterceptor(interceptor)//添加日志拦截器：显示链接信息
            builder.addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            return builder.build()
        }

        private val mLoggingInterceptor = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("X-AUTH-DEVICE-TYPE", "android")
                .method(original.method(), original.body())
                .build()
            val response = chain.proceed(request)
            response
        }
    }

}