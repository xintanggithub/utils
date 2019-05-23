package com.tson.utils.lib.util.log.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by tangxin
 *
 *
 *
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {

    private lateinit var mLinearLayoutManager: LinearLayoutManager

    /**
     * Instantiates a new Endless recycler on scroll listener.
     *
     * @param linearLayoutManager the linear layout manager
     */
    constructor(
        linearLayoutManager: LinearLayoutManager
    ) {
        this.mLinearLayoutManager = linearLayoutManager
    }

    /**
     * Instantiates a new Endless recycler on scroll listener.
     */
    constructor() {

    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0) {//判断上滑下滑
            val lastVisiblePos = mLinearLayoutManager.findLastVisibleItemPosition()
            val totalItemCount = mLinearLayoutManager.itemCount
            if (lastVisiblePos == totalItemCount - 1) {//滑动到此处就自动加载下一页
                //加载时可以根据加载状态限制是否再次加载（加载过程中也可能存在反复滑动至底部）
                onLoadMore()
            } else {
                onExistenceDistance()
            }
        }
    }

    /**
     * On load more.
     */
    abstract fun onLoadMore()

    /**
     * On existence distance.
     */
    abstract fun onExistenceDistance()
}
