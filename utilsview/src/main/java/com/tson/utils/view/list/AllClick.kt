package com.tson.utils.view.list

import android.view.View

/**
 *  Date 2019-06-26 15:11
 *
 * @author tangxin
 */
interface AllClick {

    fun onclick(view: View)

    fun onclick(view: View, view2: View)

    fun onclick(view: View, view2: View, view3: View)

    fun onclick(vararg view: View)
}