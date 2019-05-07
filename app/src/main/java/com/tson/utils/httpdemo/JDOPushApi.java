package com.tson.utils.httpdemo;

import com.tson.utils.lib.http.base.BaseData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created tangxin
 * Time 2019/2/18 7:20 PM
 */
public interface JDOPushApi {

    String JDO_PUSH_HOST = "https://integrate.jidouauto.com/msc/";
    String SOCKET_URL = "wss://integrate.jidouauto.com/push/main?accessToken=";

    //获取clientId
    @POST("client/init")
    Observable<BaseData<ResponseBase<InitClientResponse>>> init(@Body InitClientIdRequest clientIdRequest);


}
