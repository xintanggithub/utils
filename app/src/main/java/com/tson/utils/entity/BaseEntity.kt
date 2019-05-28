package com.tson.utils.entity

/**
 * Date 2019/5/27 11:26 AM
 *
 * @author tangxin
 */
class BaseEntity<T> {

    /**
     * The Result.
     */
    var result: Boolean = false
    /**
     * The Reason.
     */
    var reason: String? = null
    /**
     * The Package set.
     */
    var package_set: T? = null

}
