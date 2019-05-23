package com.tson.utils.view.tab

/**
 * Date 2019/5/17 6:30 PM
 *
 * @author tangxin
 */
enum class TabOrientation private constructor(var orientation: Int, var orientationName: String?) {
    /**
     * tab 模式:
     *
     *1 horizontal 横向
     *
     * 2 vertical 纵向
     */
    HORIZONTAL(1, "HORIZONTAL"),
    VERTICAL(2, "VERTICAL")
}
