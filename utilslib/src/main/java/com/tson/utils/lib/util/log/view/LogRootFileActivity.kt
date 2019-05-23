package com.tson.utils.lib.util.log.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tson.utils.lib.util.R
import com.tson.utils.lib.util.log.LogFileUtils
import com.tson.utils.lib.util.log.view.adapter.LogRootAdapter
import kotlinx.android.synthetic.main.activity_log_root_file.*
import java.io.File

class LogRootFileActivity : AppCompatActivity() {

    companion object {
        val handler = Handler()
    }

    var logAdapter: LogRootAdapter = LogRootAdapter(mutableListOf(), R.layout.item_log_list)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_root_file)
        progressBar.visibility = View.VISIBLE
        initView()
    }

    private fun initView() {
        back.setOnClickListener {
            finish()
        }
        clear.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            RemoveLog(object : doFileCallBack {
                override fun notify(list: MutableList<File>) {
                    progressBar.visibility = View.GONE
                    notifyAdapter()
                }
            }).execute()
        }

        val layoutManager = LinearLayoutManager(this)
        logAdapter.setOnclickListener {
            val file: File = it.getTag(it.id) as File
            startActivity(Intent(this@LogRootFileActivity,
                    LogListActivity::class.java).also {
                it.putExtra("logFile", file.path)
            })

        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = logAdapter
        notifyAdapter()
    }

    fun notifyAdapter() {
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
            val rootFile = File(LogFileUtils.dataPath + "/")
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

    class RemoveLog(private val callBack: doFileCallBack) :
            AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            LogFileUtils.removeLogCache()
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handler.postDelayed({
                callBack.notify(mutableListOf())
            }, 2000);
        }
    }

    interface doFileCallBack {
        fun notify(list: MutableList<File>)
    }

}
