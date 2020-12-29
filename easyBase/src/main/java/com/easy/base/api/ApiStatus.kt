package com.easy.base.api

/**
 * Date 2019/6/17 3:47 PM
 *
 * @author tangxin
 */
abstract class ApiStatus<T> : BaseApiStatus<T> {

    override fun before() {}

    override fun isEmpty() {}

    override fun loadMore(t: T, isRefresh: Boolean) {}

    override fun error(throwable: Throwable) {
        println(throwable.message)
    }

}
