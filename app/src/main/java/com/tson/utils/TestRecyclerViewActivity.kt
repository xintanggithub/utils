package com.tson.utils

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tson.utils.databinding.ItemFawFooterBinding
import com.tson.utils.view.list.BaseAdapter
import com.tson.utils.view.list.BaseAdapter.*
import com.tson.utils.view.list.CallBack
import com.tson.utils.view.list.OnclickListener
import kotlinx.android.synthetic.main.activity_main.*

class TestRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data1 = mutableListOf(
                "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b",
                "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c",
                "a", "b", "c", "d"
        )

        val data = mutableListOf<String>()
        data.addAll(data1)

        val layoutManager = GridLayoutManager(this@TestRecyclerViewActivity, 3)

        val adapter = MyAdapter(data, R.layout.item_layout, object : CallBack<String, ItemFawFooterBinding> {
            override fun layoutManager(): RecyclerView.LayoutManager {
                return layoutManager
            }

            override fun footerHolder(holder: BaseAdapter.FooterViewHolder<*>, mData: MutableList<String>, loadState: Int) {
                val item = holder.itemDataBinding as ItemFawFooterBinding
                if (mData.isEmpty()) {
                    item.progress.visibility = View.INVISIBLE
                    item.tvTips.text = ""
                } else {
                    when (loadState) {
                        LOADING -> {
                            item.progress.visibility = View.VISIBLE
                            item.tvTips.setText(R.string.loading_content_hint)
                        }
                        LOADING_COMPLETE -> {
                            item.progress.visibility = View.INVISIBLE
                            item.tvTips.text = ""
                        }
                        LOADING_END -> {
                            item.progress.visibility = View.GONE
                            item.tvTips.setText(R.string.no_more)
                        }
                        else -> {
                        }
                    }
                }
            }

            override fun dataBinding(parent: ViewGroup): ItemFawFooterBinding {
                return DataBindingUtil
                        .inflate(
                                LayoutInflater.from(parent.context),
                                R.layout.item_faw_footer, parent, false
                        )
            }
        })
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        adapter.loadMore()

        adapter.setOnclickListener(object : OnclickListener(){
            override fun onclick(view: View) {

            }
        })


        show_load.setOnClickListener {
            adapter.loadMore()
            adapter.addAll(data1.toList())
        }

        hide_load.setOnClickListener {
            adapter.loadComplete()
        }
        not_more.setOnClickListener {
            adapter.noneMore()
        }

        button4.setOnClickListener { adapter.loadMore() }
    }
}
