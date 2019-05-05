package com.tson.utils.lib.http.base

/**
 * Created tangxin
 * Time 2019/2/18 7:20 PM
 */
interface BaseStatus<T> {

    fun request(params: String)

    fun before()

    fun isEmpty()

    fun success(data: T)

    fun error(throwable: Throwable)

    fun tokenInvalid(throwable: Throwable)

}
