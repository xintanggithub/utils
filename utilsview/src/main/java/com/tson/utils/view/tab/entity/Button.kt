package com.tson.utils.view.tab.entity

import android.graphics.drawable.Drawable
import android.support.annotation.IdRes

/**
 * Date 2019/5/17 6:59 PM
 *
 * @author tangxin
 */
class Button {

    @IdRes
    var id: Int = 0
    var defaultIcon: Drawable? = null
    var selectIcon: Drawable? = null
    var name: String? = null
}
