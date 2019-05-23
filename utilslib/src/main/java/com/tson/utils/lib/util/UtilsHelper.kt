package com.tson.utils.lib.util

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.tson.utils.lib.util.log.LogFileUtils
import com.tson.utils.lib.util.log.view.LogRootFileActivity

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

        fun isDebug(isDebug: Boolean): UtilsHelper {
            UtilsConfig.debug = isDebug
            return utilsHelper as UtilsHelper
        }

        fun isSaveLog(isSave: Boolean): String {
            UtilsConfig.isSaveLog = isSave
            return if (isSave) LogFileUtils.dataPath else ""
        }

        fun openLogView(context: Context) {
            context.startActivity(Intent(context, LogRootFileActivity::class.java).also {
                it.setFlags(FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }

}