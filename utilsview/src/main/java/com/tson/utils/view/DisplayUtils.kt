package com.tson.utils.view

import android.content.Context
import android.content.res.Resources

/**
 *  Created tangxin
 *  Time 2019/5/8 3:24 PM
 */
internal class DisplayUtils {
    companion object {
        /**
         * 将px值转换为dp值
         *
         * @param pxValue the px value
         * @return the int
         */
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 将dp值转换为px值
         *
         * @param dpValue the dp value
         * @return the int
         */
        fun dp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 将px值转换为sp值
         *
         * @param pxValue the px value
         * @return the int
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.scaledDensity
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 将sp值转换为px值
         *
         * @param dpValue the dp value
         * @return the int
         */
        fun sp2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.scaledDensity
            return (dpValue * scale + 0.5f).toInt()
        }

        //获取屏宽
        fun getScreenWidthSize(): Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }

        //获取屏高
        fun getScreenHeightSize(): Int {
            return Resources.getSystem().displayMetrics.heightPixels
        }
    }
}