package com.tson.utils.lib.download

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelStore
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.services.DownloadMgrInitialParams
import com.tson.utils.lib.download.callback.DownloadListener
import com.tson.utils.lib.download.utils.OkHttp3Connection
import com.tson.utils.lib.download.utils.log.LogUtils
import okhttp3.OkHttpClient
import java.util.*

/**
 * Created tangxin
 * Time 2018/10/31 2:33 PM
 */
class Downloader {

    private var application: Application? = null

    private var mViewModelStore: ViewModelStore? = null

    private var mViewModel: DownloadViewModel = of().get(DownloadViewModel::class.java)

    private var initCustomMaker: DownloadMgrInitialParams.InitCustomMaker? = null

    @SuppressLint("UseSparseArrays")
    private val mListener = HashMap<Int, DownloadListener>()

    /**
     * 初始化
     *
     * @param application the application
     * @return the this@Downloader
     */
    fun init(application: Application): Downloader {
        this.application = application
        val startTime = System.currentTimeMillis()
        LogUtils.d(TAG, "init start time=$startTime")
        initCustomMaker = FileDownloader.setupOnApplicationOnCreate(application)
        mViewModelStore = ViewModelStore()
        mViewModel.instance(application)
        val endTime = System.currentTimeMillis()
        LogUtils.d(TAG, "init end time =" + endTime + "   user time=" + (endTime - startTime))
        return this
    }

    /**
     * 自定义OkHttpClient.Builder
     *
     *
     * 需要的时候传入自己定义好的OkHttpClient.Builder，可以定制自己的日志输出、超时时间、SSL证书验证等等
     *
     *
     * 如果不设置该方法，会使用默认的OkHttpClient.Builder
     *
     * @param builder 自定义OkHttpClient.Builder
     * @return the this@Downloader
     */
    fun creatorOkHttpClientBuilder(builder: OkHttpClient.Builder): Downloader {
        if (null == initCustomMaker) {
            LogUtils.e(TAG, " please init Downloader ....")
            return this
        }
        initCustomMaker!!.connectionCreator(OkHttp3Connection.Creator(builder))
        return this
    }

    private fun of(): ViewModelProvider {
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application!!)
        return ViewModelProvider(mViewModelStore!!, factory)
    }

    /**
     * 添加监听器
     *
     * @param mListener the m listener
     */
    internal fun addListener(mListener: DownloadListener) {
        val flag = mViewModel.sp.setListenerFlag()
        LogUtils.d(TAG, "addListener success , bind flag is :$flag")
        this.mListener[flag] = mListener
    }

    /**
     * 删除监听器.
     *
     * @param mListener the m listener
     */
    internal fun removeListener(mListener: DownloadListener) {
        if (this.mListener.containsValue(mListener)) {
            for (mutableEntry in this.mListener) {
                if (mutableEntry.value == mListener) {
                    this.mListener.remove(mutableEntry.key)
                    break
                }
            }
            LogUtils.d(TAG, "removeListener success ")
        } else {
            LogUtils.d(TAG, "removeListener error ,$mListener is empty ")
        }
    }

    /**
     * 设置下载路径
     *
     * @param path 下载路径
     * @return the this@Downloader
     */
    fun setPath(path: String): Downloader {
        mViewModel!!.setPath(path)
        LogUtils.d(TAG, "setting download Path success , Path =$path")
        return this
    }

    /**
     * 设置重试次数
     *
     * @param retryCount 重试次数
     * @return this @Downloader
     */
    fun setRetryCount(retryCount: Int): Downloader {
        mViewModel!!.setRetryCount(retryCount)
        LogUtils.d(TAG, "setting retry count success , count =$retryCount")
        return this
    }

    /**
     * 设置最并发大任务数
     *
     * @param maxThreadCount 取值区间【1-12】
     * @return the this@Downloader
     */
    fun setMaxThreadCount(maxThreadCount: Int): Downloader {
        if (maxThreadCount < 1 || maxThreadCount > 12) {
            LogUtils.e(TAG, "MaxThreadCount：Between 1 and 12")
            Throwable("MaxThreadCount：Between 1 and 12")
            return this
        }
        mViewModel!!.setMaxThreadCount(maxThreadCount)
        LogUtils.d(TAG, "setting max thread count success , count =$maxThreadCount")
        return this
    }

    /**
     * 是否打开日志打印.
     *
     * @param isOpen true 打开  false 关闭
     * @return the this@Downloader
     */
    fun setDebugLog(isOpen: Boolean): Downloader {
        mViewModel!!.setDebugLog(isOpen)
        LogUtils.d(TAG, "debug log -> open status :$isOpen")
        return this
    }

    /**
     * 创建/开始下载.
     *
     * @param url 文件的下载链接
     */
    fun start(url: String) {
        mViewModel!!.start(url, object : DownloadListener() {
            override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                for (l in mListener.values) {
                    l.pending(task, soFarBytes, totalBytes)
                }
            }

            override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                for (l in mListener.values) {
                    l.progress(task, soFarBytes, totalBytes)
                }
            }

            override fun completed(task: BaseDownloadTask) {
                for (l in mListener.values) {
                    l.completed(task)
                }
            }

            override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {
                for (l in mListener.values) {
                    l.paused(task, soFarBytes, totalBytes)
                }
            }

            override fun error(task: BaseDownloadTask, e: Throwable) {
                for (l in mListener.values) {
                    l.error(task, e)
                }
            }

            override fun warn(task: BaseDownloadTask) {
                for (l in mListener.values) {
                    l.warn(task)
                }
            }

            override fun retry(task: BaseDownloadTask, ex: Throwable, retryingTimes: Int, soFarBytes: Int) {
                for (l in mListener.values) {
                    l.retry(task, ex, retryingTimes, soFarBytes)
                }
            }
        })
    }

    /**
     * 创建/开始下载.
     *
     * @param url      文件下载链接
     * @param listener 下载状态监听
     */
    fun start(url: String, listener: DownloadListener) {
        mViewModel!!.start(url, listener)
    }

    /**
     * 暂停.
     *
     * @param id 下载任务的ID
     */
    fun pause(id: Int) {
        mViewModel!!.pause(id)
    }

    /**
     * 暂停所有.
     */
    fun pauseAll() {
        mViewModel!!.pauseAll()
    }

    /**
     * 清除数据.
     *
     * @param id   下载任务的ID
     * @param path 存储路径
     */
    fun clear(id: Int, path: String) {
        mViewModel!!.clear(id, path)
    }

    /**
     * 清除所有数据.
     */
    fun clearAll() {
        mViewModel!!.clearAll()
    }

    /**
     * 获取已下载的大小(byte).
     *
     * @param id 下载任务的ID
     * @return ID对应任务的已下载大小 so far
     */
    fun getSoFar(id: Int): Long {
        return mViewModel!!.getSoFar(id)
    }

    /**
     * 获取文件总大小(byte).
     *
     * @param id 下载任务ID
     * @return 中大小 total
     */
    fun getTotal(id: Int): Long {
        return mViewModel!!.getTotal(id)
    }

    companion object {

        private val TAG = "Downloader"
    }

}
