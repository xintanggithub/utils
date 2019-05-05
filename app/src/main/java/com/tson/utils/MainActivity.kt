package com.tson.utils

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tson.utils.databinding.ItemFawFooterBinding
import com.tson.utils.view.list.BaseAdapter
import com.tson.utils.view.list.BaseAdapter.*
import com.tson.utils.view.list.CallBack
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = mutableListOf<String>("a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b",
                "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c", "a", "b", "c",
                "a", "b", "c")

        val adapter = MyAdapter(data, R.layout.item_layout, object : CallBack<String, ItemFawFooterBinding> {
            override fun footerHolder(holder: BaseAdapter.FooterViewHolder<*>, mData: List<String>, loadState: Int) {
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
                        .inflate(LayoutInflater.from(parent.context),
                                R.layout.item_faw_footer, parent, false)
            }
        })
        rv_list.layoutManager = LinearLayoutManager(this@MainActivity)
        rv_list.adapter = adapter

        adapter.loadMore()

        show_load.setOnClickListener {
            adapter.loadMore()
            adapter.addAll(data.toList())
        }

        hide_load.setOnClickListener {
            adapter.loadComplete()
        }
        not_more.setOnClickListener {
            adapter.noneMore()
        }

    }
}
