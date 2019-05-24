package com.tson.utils.view.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Time 2019/3/26 2:52 PM
 *
 * @author tangxin
 */
abstract class BaseAdapter<T, E : ViewDataBinding> : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected var mData: MutableList<T> = mutableListOf()
    private var itemLayoutId: Int = 0
    protected var context: Context? = null
    protected var onclickListeners: OnclickListener? = null

    /**
     * 普通布局
     */
    private val TYPE_ITEM = 1
    /**
     * 脚布局
     */
    private val TYPE_FOOTER = 2
    /**
     * 当前加载状态，默认为加载完成
     */
    private var loadState = 2
    private var mCallBack: CallBack<T, E>? = null

    private var showFooter = false

    val allData: List<T>?
        get() = mData

    internal fun getOnclickListener(): OnclickListener {
        if (null == onclickListeners) {
            onclickListeners = object : OnclickListener {
                override fun onclick(view: View) {

                }
            }
        }
        return onclickListeners!!
    }

    fun setOnclickListener(onclickListener: OnclickListener) {
        this.onclickListeners = onclickListener
    }

    constructor(mData: MutableList<T>, itemLayoutId: Int) {
        this.mData = mData
        this.itemLayoutId = itemLayoutId
    }

    constructor(mData: MutableList<T>, itemLayoutId: Int, callBack: CallBack<T, E>) {
        this.mData = mData
        this.itemLayoutId = itemLayoutId
        setShowFooter()
        mCallBack = callBack
        setGridLayoutManager()
    }

    fun setData(mData: MutableList<T>) {
        this.mData = mData
        notifyDataSetChanged()
    }

    fun replace(index: Int, data: T) {
        this.mData[index] = data
        notifyItemChanged(index)
    }

    fun getData(position: Int): T? {
        return mData[position]
    }

    fun addAll(mData: List<T>) {
        val isEmpty = mData.isEmpty()
        this.mData.addAll(mData)
        //防止从0到有，并且为第一页加载数据时，自动滚动到最底部
        if (!isEmpty) {
            loadComplete()
            notifyItemRangeInserted(this.mData.size - mData.size, this.mData.size)
        } else {
            notifyDataSetChanged()
        }
    }

    fun add(data: T) {
        val isEmpty = mData.isEmpty()
        this.mData.add(data)
        if (!isEmpty) {
            loadComplete()
            notifyItemInserted(this.mData.size - 1)
        } else {
            this.mData.add(data)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        this.mData.clear()
        notifyDataSetChanged()
        //            notifyItemRangeInserted(this.mData.size(), this.mData.size());
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        if (viewType == TYPE_ITEM) {
            val itemBinding = DataBindingUtil.inflate<E>(
                    LayoutInflater.from(parent.context), itemLayoutId,
                    parent, false
            )
            return BaseViewHolder(itemBinding)
        } else {
            if (null == mCallBack) {
                Log.e("BaseAdapter", "设置了显示footer,但是缺少footer的必要参数")
            }
            return FooterViewHolder(mCallBack!!.dataBinding(parent))
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
        if (viewHolder is BaseViewHolder<*>) {
            this.onBindViewHolders(i, viewHolder)
        } else {
            val baseViewHolder = viewHolder as FooterViewHolder<*>
            mCallBack!!.footerHolder(baseViewHolder, mData, loadState)
        }
    }

    abstract fun onBindViewHolders(position: Int, holder: BaseViewHolder<*>)

    override fun getItemViewType(position: Int): Int {
        // 最后一个item设置为FooterView
        return if (showFooter) {
            if (position + 1 == itemCount) {
                TYPE_FOOTER
            } else {
                TYPE_ITEM
            }
        } else {
            TYPE_ITEM
        }
    }

    open class BaseViewHolder<E : ViewDataBinding> internal constructor(var itemDataBinding: E) :
            RecyclerView.ViewHolder(itemDataBinding.root)

    open class FooterViewHolder<E : ViewDataBinding> internal constructor(var itemDataBinding: E) :
            RecyclerView.ViewHolder(itemDataBinding.root)

    fun loadMore() {
        if (loadState != LOADING) {
            loadState = LOADING
            setShowFooter()
            notifyItemRangeChanged(mData.size, mData.size + 1)
        }
    }

    fun loadComplete() {
        loadState = LOADING_COMPLETE
        if (showFooter) {
            if (mData.isNotEmpty()) {
                notifyItemRemoved(mData.size)
            } else {
                notifyDataSetChanged()
            }
        }
        setHideFooter()
    }

    fun noneMore() {
        loadState = LOADING_END
        if (showFooter) {
            if (!mData.isEmpty()) {
                notifyItemRemoved(mData.size)
            } else {
                notifyDataSetChanged()
            }
        }
        setHideFooter()
    }

    private fun setShowFooter() {
        this.showFooter = true
    }

    private fun setHideFooter() {
        this.showFooter = false
    }

    override fun getItemCount(): Int {
        return if (showFooter) mData.size + 1 else mData.size
    }

    interface OnclickListener {
        /**
         * 点击事件 回调方法
         *
         * @param view 点击的控件对象
         */
        fun onclick(view: View)
    }

    /**
     * 将RecyclerView的网格布局中的某个item设置为独占一行、只占一列，只占两列、等等
     */
    private fun setGridLayoutManager() {
        if (null != mCallBack) {
            val layout = mCallBack?.layoutManager()
            if (layout is GridLayoutManager) {
                layout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val type = getItemViewType(position)
                        return if (type == TYPE_ITEM) {
                            //只占一行中的一列
                            1
                        } else {
                            //独占一行
                            layout.spanCount
                        }
                    }
                }
            }
        }
    }

    companion object {
        /**
         * 正在加载
         */
        const val LOADING = 1
        /**
         * 加载完成
         */
        const val LOADING_COMPLETE = 2
        /**
         * 加载到底
         */
        const val LOADING_END = 3
    }

}
