package com.easy.base.api

/**
 * Date 2019/6/17 3:44 PM
 *
 * @author tangxin
 */
interface BaseApiStatus<T> {

    fun before()

    fun success(t: T)

    fun isEmpty()

    fun loadMore(t: T, isRefresh: Boolean)

    fun error(throwable: Throwable)

}
