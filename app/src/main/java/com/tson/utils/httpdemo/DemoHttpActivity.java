package com.tson.utils.httpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.tson.utils.R;
import com.tson.utils.lib.http.RetrofitFactory;
import com.tson.utils.lib.http.base.ApiStatus;
import com.tson.utils.lib.util.string.StringUtils;
import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.Request;

public class DemoHttpActivity extends AppCompatActivity {

    private String JDO_PUSH_HOST = "https://integrate.jidouauto.com/msc/";
    private JDOPushApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_http);
        api = RetrofitFactory.Companion.createApi(JDOPushApi.class, JDO_PUSH_HOST,
                RetrofitFactory.Companion.buildOkHttpClient(mLoggingInterceptor));

        
        findViewById(R.id.button3).setOnClickListener(v -> {
            requestHttp();
        });
    }

    private final static Interceptor mLoggingInterceptor = chain -> {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("X-AUTH-DEVICE-TYPE", "android")
                .header("channel", "Audi")
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    };

    private void requestHttp() {
        InitClientIdRequest request = new InitClientIdRequest();
        request.setAppId("194577a7e20bdcc7afbb718f502c134c").setAppKey("204577a7e20bdcc7afbb718f502c3798")
                .setNonceStr(StringUtils.Companion.getRandomString(8))
                .setTimestamp(System.currentTimeMillis() / 100)
                .setClientId("");
        String secret = "769077a7e20bdcc7afbb718f502c8764";
        String params = String.format("%s%s%s%s%s%s", request.getClientId()
                , request.getAppId(), request.getAppKey()
                , request.getTimestamp(), request.getNonceStr()
                , secret);
        String sign = StringUtils.Companion.md5Upper(params);
        request.setSign(sign);
        Disposable disposable = RetrofitFactory.Companion.createData(api.init(request), new ApiStatus<ResponseBase<InitClientResponse>>() {
            @Override
            public void success(ResponseBase<InitClientResponse> initClientResponseResponseBase) {
                if (initClientResponseResponseBase != null) {

                }
            }
        });
    }


}
