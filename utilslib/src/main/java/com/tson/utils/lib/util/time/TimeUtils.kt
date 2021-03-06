package com.tson.utils.lib.util.time

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 *  Created tangxin
 *  Time 2019/5/5 2:18 PM
 */
class TimeUtils {

    companion object {

        /**
         * 获取现在时间 以pattern参数自定义的格式返回
         * <br/>
         * yyyy-MM-dd HH:mm:ss
         *
         * @return返回自定义时间格式
         */
        @SuppressLint("SimpleDateFormat")
        fun getNowDateShort(pattern: String): String {
            val currentTime = Date()
            val formatter = SimpleDateFormat(pattern)
            val dateString = formatter.format(currentTime)
            return dateString
        }

        fun getCurrentByDay(): String = getNowDateShort("yyyy-MM-dd")

        fun getDayOfWeek(dateTime: String): Int {
            val cal = Calendar.getInstance()
            if (dateTime == "") {
                cal.time = Date(System.currentTimeMillis())
            } else {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                var date: Date?
                try {
                    date = sdf.parse(dateTime)
                } catch (e: ParseException) {
                    date = null
                    e.printStackTrace()
                }

                if (date != null) {
                    cal.time = Date(date.time)
                }
            }
            return cal.get(Calendar.DAY_OF_WEEK)
        }

        /**
         * 将指定的毫秒数转换为 以pattern参数自定义的格式返回
         * <br/>
         * yyyy-MM-dd HH:mm:ss
         */
        fun ms2Date(_ms: Long, pattern: String): String {
            val date = Date(_ms)
            val format = SimpleDateFormat(pattern, Locale.getDefault())
            return format.format(date)
        }

        /**
         * 将指定以pattern参数自定义的格式的时间转换为毫秒值
         * <br/>
         * yyyy-MM-dd HH:mm:ss
         */
        @SuppressLint("SimpleDateFormat")
        fun Date2ms(_data: String, pattern: String): Long {
            val format = SimpleDateFormat(pattern)
            try {
                val date = format.parse(_data)
                return date.time
            } catch (e: Exception) {
                return 0
            }

        }

    }

}