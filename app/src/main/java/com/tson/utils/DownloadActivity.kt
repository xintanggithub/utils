package com.tson.utils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.liulishuo.filedownloader.BaseDownloadTask
import com.tson.utils.lib.download.DownLoadManager
import com.tson.utils.lib.download.callback.DownloadListener
import com.tson.utils.lib.util.log.LogUtils
import kotlinx.android.synthetic.main.activity_download.*
import okhttp3.OkHttpClient

class DownloadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "DownloadActivity"
        var id = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        button6.setOnClickListener {
            Thread(Runnable {
                DownLoadManager.instance.init(application)
                        .setDebugLog(true)
                        .setGlobalPost2UIInterval(60)
                        .setPath(application.filesDir.path + "/download/")
                        .setMaxThreadCount(3)
            }).start()
        }

        button7.setOnClickListener {
            //            DownLoadManager.instance.start(
//                "https://download.cdn.jidouauto.com/apk/2019-04-10/Auto_V3.2.9.12485_C04010264002.apk")
            DownLoadManager.instance.start(
                    "https://download.cdn.jidouauto.com/apk/2019-04-10/Auto_V3.2.9.12485_C04010264002.apk",
                    object : DownloadListener() {
                        override fun completed(task: BaseDownloadTask) {
                            LogUtils.v(TAG, "completed")
                        }

                        override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                            super.pending(task, soFarBytes, totalBytes)
                            LogUtils.v(TAG, "total:${task.totalBytes}   |  download:${task.soFarBytes}")
                        }

                        override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                            super.progress(task, soFarBytes, totalBytes)
                            id = task.id
                            LogUtils.v(TAG, "total:${task.totalBytes}   |  download:${task.soFarBytes}")
                        }

                        override fun error(task: BaseDownloadTask, e: Throwable) {
                            super.error(task, e)
                            LogUtils.v(TAG, "total:${task.totalBytes}   |  download:${task.soFarBytes}")
                        }
                    })
        }

        button8.setOnClickListener {
            DownLoadManager.instance.pause(id)
        }
    }

    fun test() {
        DownLoadManager.instance.clear(1, "")
        DownLoadManager.instance.clearAll()
        DownLoadManager.instance.creatorOkHttpClientBuilder(OkHttpClient.Builder())
        DownLoadManager.instance.getSoFar(1)
        DownLoadManager.instance.getTotal(1)
        DownLoadManager.instance.pause(1)
        DownLoadManager.instance.pauseAll()
        DownLoadManager.instance.setRetryCount(3)
        DownLoadManager.instance.start("")
        DownLoadManager.instance.start("", object : DownloadListener() {
            override fun completed(task: BaseDownloadTask) {

            }
        })

        val listener = object : DownloadListener() {
            override fun completed(task: BaseDownloadTask) {

            }
        }

        DownLoadManager.bindChangeListener(listener)
        DownLoadManager.unBindChangeListener(listener)
    }

}
