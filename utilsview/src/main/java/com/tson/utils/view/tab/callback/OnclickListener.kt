package com.tson.utils.view.tab.callback

import com.tson.utils.view.tab.entity.Button

/**
 * Date 2019/5/17 7:12 PM
 *
 * @author tangxin
 */
interface OnclickListener {

    /**
     * 点击事件回调
     *
     * @param index  点击的button位置
     * @param button button数据
     */
    fun onclick(index: Int, button: Button)

    /**
     * 长按事件回调
     *
     * @param index  点击的button位置
     * @param button button数据
     */
    fun onLongClick(index: Int, button: Button)

}
