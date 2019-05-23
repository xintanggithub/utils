package com.tson.utils.lib.util.log.view

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.View
import com.tson.utils.lib.util.R
import com.tson.utils.lib.util.log.LogFileUtils
import kotlinx.android.synthetic.main.activity_log_detail.*

class LogDetailActivity : AppCompatActivity() {

    companion object {
        var path: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_detail)
        path = intent.getStringExtra("txtFile")
        initView()
    }

    private fun initView() {
        back.setOnClickListener {
            finish()
        }
        progressBar2.visibility = View.VISIBLE
        LoadLog(object : LoadLogCallBack {
            override fun notify(string: Spanned) {
                content.text = string
                progressBar2.visibility = View.GONE
            }
        }).execute()
    }

    class LoadLog(private val callBack: LoadLogCallBack) :
            AsyncTask<String, String, Spanned>() {
        override fun doInBackground(vararg params: String): Spanned {
            return Html.fromHtml("<html><head><body>${LogFileUtils
                    .readFormSdcard(path)}</body></head></html>")
        }

        override fun onPostExecute(result: Spanned) {
            super.onPostExecute(result)
            callBack.notify(result)
        }
    }

    interface LoadLogCallBack {
        fun notify(string: Spanned)
    }

}
