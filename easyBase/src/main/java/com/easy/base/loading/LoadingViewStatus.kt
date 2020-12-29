package com.easy.base.loading

/**
 *  Date 2019-11-22 17:00
 *
 * @author tangxin
 */
interface LoadingViewStatus {

    fun requestLoading(): Int

    fun retry()

    fun close()

}