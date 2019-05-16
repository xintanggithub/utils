package com.tson.utils.lib.download

import com.tson.utils.lib.download.callback.DownloadListener
import com.tson.utils.lib.download.utils.log.LogUtils

/**
 * Created tangxin
 * Time 2018/10/31 2:31 PM
 * @author tangxin
 */
class DownLoadManager {
    companion object {

        private val TAG = "DownLoadManager"

        private var sDownloader: Downloader? = null

        /**
         * 实例化
         *
         * @return Downloader对象 instance
         */
        val instance: Downloader
            get() {
                if (null == sDownloader) {
                    sDownloader = Downloader()
                }
                LogUtils.d(TAG, "instance success")
                return sDownloader!!
            }

        /**
         * 获取Downloader对象.
         *
         * @return Downloader对象  downloader
         */
        val downloader: Downloader?
            get() {
                if (null == sDownloader) {
                    Throwable("sDownloader is null,please【DownLoadManager.getInstance().init(ctx)】")
                }
                return sDownloader
            }

        /**
         * 绑定监听
         *
         * @param listener 下载监听器
         */
        fun bindChangeListener(listener: DownloadListener) {
            sDownloader!!.addListener(listener)
        }

        /**
         * 解绑监听器.
         *
         * @param listener 下载监听器
         */
        fun unBindChangeListener(listener: DownloadListener) {
            sDownloader!!.removeListener(listener)
        }
    }
}
