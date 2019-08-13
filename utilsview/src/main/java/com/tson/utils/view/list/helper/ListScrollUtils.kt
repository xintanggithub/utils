package com.tson.utils.view.list.helper

import android.support.v7.widget.RecyclerView

/**
 *  Date 2019-08-13 19:55
 * 改变recyclerView滑动速度  默认8000
 * @author tangxin
 */
class ListScrollUtils {
    companion object {
        fun setMaxFlingVelocity(recyclerView: RecyclerView, velocity: Int) {
            try {
                val field = recyclerView.javaClass.getDeclaredField("mMaxFlingVelocity")
                field.isAccessible = true
                field.set(recyclerView, velocity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}