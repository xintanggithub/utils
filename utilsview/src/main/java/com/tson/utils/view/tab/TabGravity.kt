package com.tson.utils.view.tab

/**
 * Date 2019/5/17 6:19 PM
 *
 * @author tangxin
 */
internal enum class TabGravity(var gravity: Int, var gravityName: String?) {
    /**
     *
     *
     * 1. center
     *
     *
     * 2. left
     *
     *
     * 3. right
     *
     *
     * 4. top
     *
     *
     * 5. bottom
     */
    CENTER(1, "CENTER"),
    LEFT(2, "LEFT"),
    RIGHT(3, "RIGHT"),
    TOP(4, "TOP"),
    BOTTOM(5, "BOTTOM");


    companion object {

        fun getGravity(gravity: Int): TabGravity? {
            for (enums in TabGravity.values()) {
                if (enums.gravity == gravity) {
                    return enums
                }
            }
            return null
        }
    }
}
