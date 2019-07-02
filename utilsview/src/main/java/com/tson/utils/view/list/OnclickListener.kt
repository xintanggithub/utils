package com.tson.utils.view.list

import android.view.View

/**
 *  Date 2019-06-26 15:12
 *
 * @author tangxin
 */
abstract class OnclickListener : AllClick {
    /**
     * 点击事件 回调方法
     *
     * @param view 点击的控件对象
     */

    override fun onclick(view: View, view2: View) {

    }

    override fun onclick(view: View, view2: View, view3: View) {

    }

    override fun onclick(vararg view: View) {

    }
}