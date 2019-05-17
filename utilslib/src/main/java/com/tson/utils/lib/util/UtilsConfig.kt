package com.tson.utils.lib.util

import android.annotation.SuppressLint
import android.content.Context

/**
 * Created tangxin
 * Time 2019/5/5 2:00 PM
 */
internal class UtilsConfig {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var sContext: Context

        var debug: Boolean = true

        var isSaveLog: Boolean = false

    }

}
