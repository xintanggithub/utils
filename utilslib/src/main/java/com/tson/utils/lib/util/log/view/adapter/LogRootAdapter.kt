package com.tson.utils.lib.util.log.view.adapter

import com.tson.utils.lib.util.databinding.ItemLogListBinding
import com.tson.utils.lib.util.log.LogFileUtils
import com.tson.utils.lib.util.log.view.BaseAdapter
import java.io.File

/**
 * Created tangxin
 * Time 2019/5/5 6:58 PM
 */
class LogRootAdapter internal constructor(mData: List<File>, itemLayoutId: Int) :
    BaseAdapter<File, ItemLogListBinding>(mData, itemLayoutId) {

    override fun onBindViewHolders(position: Int, holder: BaseViewHolder<*>) {
        val itemLayoutBinding = holder.itemDataBinding as ItemLogListBinding
        val file = getData(position)
        val isFolder = file.isDirectory
        itemLayoutBinding.vm = file
        if (isFolder) {
            itemLayoutBinding.size = LogFileUtils.formatFileSize(LogFileUtils.getFolderSize(file))
        } else {
            itemLayoutBinding.size = LogFileUtils.formatFileSize(LogFileUtils.getFileSize(file))
        }
        itemLayoutBinding.root.setOnClickListener {
            it.setTag(it.id, file)
            onclickListener.onclick(it)
        }
    }

}
