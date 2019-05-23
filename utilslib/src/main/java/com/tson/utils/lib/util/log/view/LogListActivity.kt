package com.tson.utils.lib.util.log.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tson.utils.lib.util.R
import com.tson.utils.lib.util.log.view.adapter.LogRootAdapter
import kotlinx.android.synthetic.main.activity_log_list.*
import java.io.File

class LogListActivity : AppCompatActivity() {

    companion object {
        val handler = Handler()
        var path: String = ""
    }

    var logAdapter: LogRootAdapter = LogRootAdapter(mutableListOf(), R.layout.item_log_list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_list)
        path = intent.getStringExtra("logFile")
        initView()
    }

    private fun initView() {
        back.setOnClickListener {
            finish()
        }

        val layoutManager = LinearLayoutManager(this)
        logAdapter.setOnclickListener {
            val file: File = it.getTag(it.id) as File
            startActivity(Intent(this@LogListActivity,
                    LogDetailActivity::class.java).also {
                it.putExtra("txtFile", file.path)
            })
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = logAdapter
        notifyAdapter()
    }

    private fun notifyAdapter() {
        LogAsync(object : doFileCallBack {
            override fun notify(list: MutableList<File>) {
                logAdapter.setData(list.toList())
                progressBar.visibility = View.GONE
            }
        }).execute()
    }

    class LogAsync(private val callBack: doFileCallBack) :
            AsyncTask<String, String, MutableList<File>>() {

        override fun doInBackground(vararg strings: String): MutableList<File> {
            var list = mutableListOf<File>()
            val rootFile = File(path)
            if (rootFile.exists()) {
                val files = rootFile.listFiles()
                list = files.toMutableList();
            }
            return list
        }

        override fun onPostExecute(result: MutableList<File>) {
            super.onPostExecute(result)
            callBack.notify(result)
        }
    }

    interface doFileCallBack {
        fun notify(list: MutableList<File>)
    }

}
