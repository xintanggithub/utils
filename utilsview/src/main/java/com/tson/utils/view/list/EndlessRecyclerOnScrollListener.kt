package com.tson.utils.view.list


import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by tangxin
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {

    private lateinit var mLinearLayoutManager: LinearLayoutManager

    constructor(linearLayoutManager: LinearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager
    }

    constructor() {
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {//判断上滑下滑
            val lastVisiblePos = mLinearLayoutManager.findLastVisibleItemPosition()
            val totalItemCount = mLinearLayoutManager.itemCount
            if (lastVisiblePos == totalItemCount - 1) {
                //加载过程中也可能存在反复滑动至底部
                onLoadMore()
            } else {
                onExistenceDistance()
            }
        }
    }

    abstract fun onLoadMore()

    abstract fun onExistenceDistance()
}
