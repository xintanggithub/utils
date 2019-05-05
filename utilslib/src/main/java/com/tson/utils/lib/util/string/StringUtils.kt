package com.tson.utils.lib.util.string

import android.text.TextUtils
import java.math.BigDecimal
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern
import kotlin.experimental.and

/**
 *  Created tangxin
 *  Time 2019/5/5 1:38 PM
 */
class StringUtils {

    companion object {

        fun getRandomString(length: Int): String { //length表示生成字符串的长度
            val base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTWVUXYZ0123456789"
            val random = Random()
            val sb = StringBuilder()
            for (i in 0 until length) {
                val number = random.nextInt(base.length)
                sb.append(base[number])
            }
            return sb.toString()
        }

        fun md5Upper(string: String): String {
            return md5(string, true);
        }

        fun md5Lower(string: String): String {
            return md5(string, false);
        }

        fun md5(string: String, isUpper: Boolean): String {
            if (TextUtils.isEmpty(string)) {
                return ""
            }
            val md5: MessageDigest
            try {
                md5 = MessageDigest.getInstance("MD5")
                val bytes = md5.digest(string.toByteArray())
                val result = StringBuilder()
                for (b in bytes) {
                    var temp = Integer.toHexString((b and 0xff.toByte()).toInt())
                    if (temp.length == 1) {
                        temp = "0$temp"
                    }
                    result.append(temp)
                }
                if (isUpper) {
                    return result.toString().toUpperCase()
                } else {
                    return result.toString().toLowerCase()
                }
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return ""
        }

        /**
         * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
         *
         * @param value the value
         * @return the boolean
         */
        fun isEmpty(value: String?): Boolean {
            return !(value != null && !"".equals(value.trim { it <= ' ' }, ignoreCase = true)
                    && !"null".equals(value.trim { it <= ' ' }, ignoreCase = true))
        }

        /**
         * 判断字符串是否是邮箱
         *
         * @param email email
         * @return 字符串是否是邮箱 boolean
         */
        fun isEmail(email: String): Boolean {
            val str =
                "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" + "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"
            val p = Pattern.compile(str)
            val m = p.matcher(email)
            return m.matches()
        }

        /**
         * 判断手机号字符串是否合法
         *
         * @param phoneNumber the phone number
         * @return the boolean
         */
        fun isPhoneNumberValid(phoneNumber: String): Boolean {
            var isValid = false
            val expression = "^1[3|4|5|7|8]\\d{9}$"
            val pattern = Pattern.compile(expression)
            val matcher = pattern.matcher(phoneNumber)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }

        /**
         * 判断手机号字符串是否合法
         *
         * @param areaCode    区号
         * @param phoneNumber 手机号字符串
         * @return 手机号字符串是否合法 boolean
         */
        fun isPhoneNumberValid(areaCode: String, phoneNumber: String): Boolean {
            if (TextUtils.isEmpty(phoneNumber)) {
                return false
            }

            if (phoneNumber.length < 5) {
                return false
            }

            if (TextUtils.equals(areaCode, "+86") || TextUtils.equals(areaCode, "86")) {
                return isPhoneNumberValid(phoneNumber)
            }

            var isValid = false
            val expression = "^[0-9]*$"
            val pattern = Pattern.compile(expression)
            val matcher = pattern.matcher(phoneNumber)
            if (matcher.matches()) {
                isValid = true
            }
            return isValid
        }

        /**
         * 判断字符串是否为纯数字
         *
         * @param str 字符串
         * @return 是否纯数字 boolean
         */
        fun isNumber(str: String): Boolean {
            for (i in 0 until str.length) {
                if (!Character.isDigit(str[i])) {
                    return false
                }
            }
            return true
        }

        fun getFileSize(size: Long): String {
            val kiloByte = (size / 1024).toDouble()
            if (kiloByte < 1) {
                return "0K"
            }

            val megaByte = kiloByte / 1024
            if (megaByte < 1) {
                val result1 = BigDecimal(kiloByte.toString())
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB"
            }

            val gigaByte = megaByte / 1024
            if (gigaByte < 1) {
                val result2 = BigDecimal(megaByte.toString())
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB"
            }

            val teraBytes = gigaByte / 1024
            if (teraBytes < 1) {
                val result3 = BigDecimal(gigaByte.toString())
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)
            return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
        }
    }

}