package com.tson.utils.lib.util.log.view

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created tangxin
 * Time 2019/5/5 6:21 PM
 */
interface CallBack<T, E : ViewDataBinding> {

    /**
     * 获取dataBinding
     *
     * @param parent viewGroup
     * @return dataBinding
     */
    fun dataBinding(parent: ViewGroup): E

    /**
     * 返回foot view
     *
     * @param holder    holder
     * @param mData     数据
     * @param loadState 状态
     */
    fun footerHolder(holder: BaseAdapter.FooterViewHolder<*>, mData: List<T>, loadState: Int)

    /**
     * 获取layoutManager
     *
     * @return layoutManager
     */
    fun layoutManager(): RecyclerView.LayoutManager

}
