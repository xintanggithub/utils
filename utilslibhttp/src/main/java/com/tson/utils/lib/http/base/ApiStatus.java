package com.tson.utils.lib.http.base;

/**
 * Created tangxin
 * Time 2019/2/18 7:20 PM
 */
public abstract class ApiStatus<T> implements BaseStatus<T> {

    private static final String TAG = ApiStatus.class.getName();

    @Override
    public void request(String params) {
//        LogUtils.d(TAG, "JDOPushApi params【" + params + "】");
    }

    @Override
    public void before() {
    }

    @Override
    public void error(Throwable throwable) {
    }

    @Override
    public void tokenInvalid(Throwable throwable) {
    }

    @Override
    public void isEmpty() {
    }
}
