package com.tson.utils.lib.util

import android.content.Context

/**
 *  Created tangxin
 *  Time 2019/5/5 10:17 AM
 */
class UtilsHelper {

    companion object {

        private var utilsHelper: UtilsHelper? = null

        fun instance(context: Context): UtilsHelper {
            if (null == utilsHelper) {
                utilsHelper = UtilsHelper();
                UtilsConfig.sContext = context
            }
            return utilsHelper as UtilsHelper
        }
    }

}