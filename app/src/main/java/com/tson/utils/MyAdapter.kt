package com.tson.utils

import com.tson.utils.databinding.ItemFawFooterBinding
import com.tson.utils.databinding.ItemLayoutBinding
import com.tson.utils.view.list.BaseAdapter
import com.tson.utils.view.list.CallBack

/**
 * Created tangxin
 * Time 2019/5/5 6:58 PM
 */
class MyAdapter internal constructor(mData: MutableList<String>, itemLayoutId: Int, callBack: CallBack<String, ItemFawFooterBinding>) :
    BaseAdapter<String, ItemLayoutBinding>(mData, itemLayoutId, callBack) {

    override fun onBindViewHolders(position: Int, holder: BaseAdapter.BaseViewHolder<*>) {
        val itemLayoutBinding = holder.itemDataBinding as ItemLayoutBinding
        itemLayoutBinding.vm = getData(position) + "  ------  " + position
    }

}
