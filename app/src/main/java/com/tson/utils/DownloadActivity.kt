package com.tson.utils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.liulishuo.filedownloader.BaseDownloadTask
import com.tson.utils.lib.download.DownLoadManager
import com.tson.utils.lib.download.callback.ConnectServiceCallback
import com.tson.utils.lib.download.callback.DownloadListener
import com.tson.utils.lib.util.log.LogUtils
import kotlinx.android.synthetic.main.activity_download.*

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
                DownLoadManager.instance.init(application).connectService(object : ConnectServiceCallback {
                    override fun disConnect() {
                        DownLoadManager.instance.run {
                            setRetryCount(3)
                            setDebugLog(true)
                            setGlobalPost2UIInterval(60)
                            setMaxThreadCount(3)
                            setPath(this@DownloadActivity.filesDir.path + "/download/")
                        }
                    }

                    override fun connect() {
                    }
                })
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

}
