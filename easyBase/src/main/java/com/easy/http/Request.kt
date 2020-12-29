package com.easy.http

import kotlinx.coroutines.*

/**
 *  Date 2020-08-04 13:59
 *
 * @author Tson
 */

class Request {

    companion object {

        var timeoutMillis = 50_000L

        fun <T> doGet(
            block: suspend CoroutineScope.() -> T?,
            success: (T?) -> Unit = {},
            fail: (T: Throwable) -> Unit = {}
        ) =
            GlobalScope.launch {
                try {
                    withTimeout(timeoutMillis) {
                        success(withContext(Dispatchers.IO) {
                            block()
                        })
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    fail(e)
                }
            }
    }
}