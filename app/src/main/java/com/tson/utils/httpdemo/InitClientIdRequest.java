package com.tson.utils.httpdemo;

/**
 * Created tangxin
 * Time 2019/2/19 10:34 AM
 */
public class InitClientIdRequest {

    private String clientId;
    private String appId;
    private String appKey;
    private Long timestamp;
    private String nonceStr;
    private String sign;

    public String getClientId() {
        return clientId;
    }

    public InitClientIdRequest setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getAppId() {
        return appId;
    }

    public InitClientIdRequest setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public InitClientIdRequest setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public InitClientIdRequest setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public InitClientIdRequest setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public InitClientIdRequest setSign(String sign) {
        this.sign = sign;
        return this;
    }
}
