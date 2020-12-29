package com.easy.base.loading

import android.app.Activity

/**
 * Date 2019/6/17 4:55 PM
 *
 * @author tangxin
 */
interface LoadCallBack {

    fun show(activity: Activity)

    fun show(activity: Activity, content: String)

    fun show(activity: Activity, content: String, type: LoadType)

    fun error(activity: Activity, content: String, showRetry: Boolean)

    fun error(activity: Activity, iconId: Int, content: String, showRetry: Boolean)

    fun hide(activity: Activity)

    fun retry()

}
