package com.tson.utils.lib.util.sp

import android.content.Context
import com.tson.utils.lib.util.BuildConfig

/**
 * Created tangxin
 * Time 2018/10/11 下午1:47
 */
class SharePreferencesFactory
/**
 * Instantiates a new Setting preferences factory.
 *
 * @param context the context
 */
    (context: Context) : BaseSharedPreferencesFactory(context) {

    override val key: String
        get() = BuildConfig.APPLICATION_ID + "_utils_lib_sp"

    companion object {

        private val TAG = "SharePreferencesFactory"
    }
}
