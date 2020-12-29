package com.easy

import com.easy.config.HttpConfig

/**
 *  Date 2020-08-04 14:35
 *
 * @author Tson
 */
class EasyManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { EasyManager() }
    }

    fun buildHttp() = HttpConfig.instance

}