package com.tson.utils.lib.util.log

import android.os.AsyncTask
import android.util.Log
import com.tson.utils.lib.util.UtilsConfig

/**
 * Created tangxin
 * Time 2019/2/18 2:14 PM
 */
class LogUtils {

    companion object {

        fun v(tag: String, msg: String) {
            if (!UtilsConfig.debug) {
                return
            }
            logcat(Log.VERBOSE, tag, msg)
        }

        fun d(tag: String, msg: String) {
            if (!UtilsConfig.debug) {
                return
            }
            logcat(Log.DEBUG, tag, msg)
        }

        fun i(tag: String, msg: String) {
            if (!UtilsConfig.debug) {
                return
            }
            logcat(Log.INFO, tag, msg)
        }

        fun w(tag: String, msg: String) {
            if (!UtilsConfig.debug) {
                return
            }
            logcat(Log.WARN, tag, msg)
        }

        fun e(tag: String, msg: String) {
            logcat(Log.ERROR, tag, msg)

        }

        private fun logcat(lv: Int, tag: String, msg: String) {
            var lvName = "VERBOSE"
            when (lv) {
                Log.VERBOSE -> Log.v(tag, msg)
                Log.DEBUG -> {
                    lvName = "DEBUG"
                    Log.d(tag, msg)
                }
                Log.INFO -> {
                    lvName = "INFO"
                    Log.i(tag, msg)
                }
                Log.WARN -> {
                    lvName = "WARN"
                    Log.w(tag, msg)
                }
                Log.ERROR -> {
                    lvName = "ERROR"
                    Log.e(tag, msg)
                }
            }
            if (UtilsConfig.isSaveLog) {
                val content = LogFileUtils.stampToDate(
                        System.currentTimeMillis(),
                        "yyyy-MM-dd HH:mm:ss SSS"
                ) + "  " + lvName + "  " + tag + "  :" + msg + "  <br/>"
                LogAsync(content).execute()
            }
        }

        internal class LogAsync(private val content: String) : AsyncTask<String, String, String>() {

            override fun doInBackground(vararg strings: String): String? {
                LogFileUtils.writeTxtToFile(content)
                return null
            }
        }
    }
}
