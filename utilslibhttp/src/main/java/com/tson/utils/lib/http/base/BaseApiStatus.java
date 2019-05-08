package com.tson.utils.lib.http.base;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created tangxin
 * Time 2019/2/18 7:20 PM
 * @author tangxin
 */
public abstract class BaseApiStatus<T> implements BaseStatus<T> {

    private static final String TAG = BaseApiStatus.class.getName();

    @Override
    public void request(@NonNull String params) {
        Log.d(TAG, "JDOPushApi params【" + params + "】");
    }

    @Override
    public void before() {
    }

    @Override
    public void error(@NonNull Throwable throwable) {
    }

    @Override
    public void isEmpty() {
    }
}
