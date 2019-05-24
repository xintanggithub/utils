package com.tson.utils.lib.http.base

import android.util.Log

/**
 * Created tangxin
 * Time 2019/2/18 7:20 PM
 * @author tangxin
 */
abstract class BaseApiStatus<T> : BaseStatus<T> {

    override fun request(params: String) {
        Log.d(TAG, "params【$params】")
    }

    override fun before() {}

    override fun error(throwable: Throwable) {}

    override fun isEmpty() {}

    companion object {

        private val TAG = BaseApiStatus::class.java.name
    }
}
