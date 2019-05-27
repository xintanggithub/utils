package com.tson.utils.entity;

/**
 * Date 2019/5/27 11:26 AM
 *
 * @author tangxin
 */
public class BaseEntity<T> {

    /**
     * The Result.
     */
    public boolean result;
    /**
     * The Reason.
     */
    public String reason;
    /**
     * The Package set.
     */
    public T package_set;

}
