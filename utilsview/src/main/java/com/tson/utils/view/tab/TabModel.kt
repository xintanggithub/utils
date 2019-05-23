package com.tson.utils.view.tab

/**
 * Date 2019/5/17 6:08 PM
 *
 *
 * 1： 文字在上 icon在下
 *
 *
 * TEXT_TOP_ICON_BOTTOM
 *
 *
 * 2： 文字在左 icon在右
 *
 *
 * TEXT_LEFT_ICON_RIGHT
 *
 *
 * 3： 文字在下 icon在上
 *
 *
 * TEXT_BOTTOM_ICON_TOP
 *
 *
 * 4： 文字在右 icon在左
 *
 *
 * TEXT_RIGHT_ICON_LEFT
 *
 * @author tangxin
 */
enum class TabModel private constructor(var type: Int, var typeName: String?) {
    /**
     * 文字在上 icon在下
     */
    TEXT_TOP_ICON_BOTTOM(1, "TEXT_TOP_ICON_BOTTOM"),
    /**
     * 文字在左 icon在右
     */
    TEXT_LEFT_ICON_RIGHT(2, "TEXT_LEFT_ICON_RIGHT"),
    /**
     * 文字在下 icon在上
     */
    TEXT_BOTTOM_ICON_TOP(3, "TEXT_BOTTOM_ICON_TOP"),
    /**
     * 文字在右 icon在左
     */
    TEXT_RIGHT_ICON_LEFT(4, "TEXT_RIGHT_ICON_LEFT");


    companion object {

        fun getModel(type: Int): TabModel? {
            for (enums in TabModel.values()) {
                if (enums.type == type) {
                    return enums
                }
            }
            return null
        }
    }
}
