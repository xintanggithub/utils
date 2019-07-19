package com.tson.utils.view.fragment

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import com.tson.utils.view.DisplayUtils
import com.tson.utils.view.R

import java.util.Arrays

/**
 * @author tangxin
 */
class BottomSheet<T> : BottomSheetDialogFragment() {

    private val MIX_RELEVANT_SIZE = 1
    private var mData: List<T>? = null
    private var result: ChooseItemListener<T>? = null
    private var bindTextListener: BindTextListener? = null
    private var mTitle: String? = null
    private var rlBgLayout: RelativeLayout? = null
    private var mRecyclerView: RecyclerView? = null
    private var isEmptyTitle = true//是否有title
    private val listAdapter: ListAdapter
    var selectIndex = -1

    init {
        listAdapter = ListAdapter()
    }

    interface ChooseItemListener<T> {
        fun onResult(position: Int, result: T)

    }

    interface BindTextListener {
        fun bindText(tv: TextView, position: Int)
    }

    fun getBindTextListener(): BindTextListener {
        if (null == bindTextListener) {
            bindTextListener = object : BindTextListener {
                override fun bindText(tv: TextView, position: Int) {}
            }
        }
        return bindTextListener!!
    }

    fun setData(data: Array<T>, title: String, mResult: ChooseItemListener<T>, bindTextListener: BindTextListener) {
        setData(Arrays.asList(*data), title, mResult, bindTextListener)
    }

    fun setData(data: List<T>, title: String, mResult: ChooseItemListener<T>, bindTextListener: BindTextListener) {
        this.bindTextListener = bindTextListener
        mData = data
        result = mResult
        mTitle = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        val mTitleView = view.findViewById<AppCompatTextView>(R.id.tv_title)
        isEmptyTitle = isNotEmpty(mTitle)
        if (isEmptyTitle) {
            mTitleView.text = mTitle
        } else {
            mTitleView.visibility = View.GONE
        }
        rlBgLayout = view.findViewById(R.id.bg_layout)
        setHeight()
        initListAdapter()
    }

    fun addMore(data: List<T>) {
        listAdapter.notifyDataSetChanged()
    }

    private fun setHeight() {
        val lp = rlBgLayout!!.layoutParams
        rlBgLayout!!.layoutParams
        if (DisplayUtils.getScreenHeightSize() <= DisplayUtils.dp2px(
                context!!,
                (ITEM_SIZE * mData!!.size + ITEM_SIZE * if (isEmptyTitle) 3 else 2).toFloat()
            )
        ) {
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            lp.height = DisplayUtils.dp2px(
                context!!,
                (ITEM_SIZE * mData!!.size + ITEM_SIZE * (if (isEmptyTitle) 2 else 1) + LINES_HEIGHT).toFloat()
            )
        }
        rlBgLayout!!.layoutParams = lp
    }


    private fun initListAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity)
        mRecyclerView!!.layoutManager = linearLayoutManager
        mRecyclerView!!.adapter = listAdapter
        setFooterView(mRecyclerView!!)
    }


    private fun setFooterView(view: RecyclerView) {
        val footer = LayoutInflater.from(activity).inflate(R.layout.item_cancel, view, false)
        listAdapter.setFooterView(footer)
        footer.findViewById<View>(R.id.tv_cancel).setOnClickListener { dismiss() }
    }

    private inner class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
        private var mFooterView: View? = null

        fun setFooterView(footerView: View) {
            mFooterView = footerView
            notifyItemInserted(itemCount - MIX_RELEVANT_SIZE)
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == itemCount - MIX_RELEVANT_SIZE) {
                TYPE_FOOTER
            } else TYPE_NORMAL
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return if (mFooterView != null && viewType == TYPE_FOOTER) {
                ViewHolder(mFooterView!!)
            } else ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_bottom_list,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (getItemViewType(position) == TYPE_NORMAL) {
                val itemData = mData!![position]
                if (itemData is String) {
                    holder.tvContent.text = itemData
                } else {
                    getBindTextListener().bindText(holder.tvContent, position)
                }
                if (position == selectIndex) {
                    holder.tvContent.setTextColor(context!!.resources.getColor(R.color.select_color))
                } else {
                    holder.tvContent.setTextColor(context!!.resources.getColor(R.color.content_color))
                }
                holder.tvContent.setOnClickListener { v ->
                    if (null != result) {
                        result!!.onResult(position, itemData)
                    }
                    selectIndex = position
                    dismiss()
                }
            }
        }

        override fun getItemCount(): Int {
            return if (mFooterView == null) {
                mData!!.size
            } else {
                mData!!.size + MIX_RELEVANT_SIZE
            }
        }

        internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            lateinit var tvContent: AppCompatTextView

            init {
                if (itemView !== mFooterView) {
                    tvContent = itemView.findViewById(R.id.tv_content)
                }
            }
        }

    }

    /**
     * 判String断是否为空
     */
    fun isNotEmpty(str: String?): Boolean {
        return if (null == str || "" == str || TextUtils.isEmpty(str) || "null" == str) {
            false
        } else {
            true
        }
    }

    companion object {

        private val TAG = "BottomSheet"
        val TYPE_FOOTER = 1  //带有Footer
        val TYPE_NORMAL = 2
        private val ITEM_SIZE = 50
        private val LINES_HEIGHT = 9
    }

}
