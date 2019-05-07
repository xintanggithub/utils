package com.tson.utils.httpdemo;

/**
 * Created tangxin
 * Time 2019/2/19 2:31 PM
 */
public class InitClientResponse {

    private String clientId;
    private String alias;
    private String accessToken;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
