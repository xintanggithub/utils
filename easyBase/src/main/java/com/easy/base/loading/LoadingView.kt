package com.easy.base.loading

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.easy.R

/**
 *  Date 2019-11-22 17:00
 *  错误的布局均可自定义
 *  如果针对一个activity或fragment的多个view需要显示不同的类型的错误界面，
 *  可以根据childrens按错误类型替换对应错误界面完成，基础的替换如下逻辑
 *  Wrong layout can be customized
 *  If multiple views for an activity or fragment need to display different types of error
 *  interfaces, you can replace the corresponding error interface according to the children's
 *  error type. The basic replacement is as follows:
 * @author tangxin
 */
class LoadingView : LoadCallBack {

    private lateinit var loadStatus: LoadingViewStatus

    override fun show(activity: Activity) {
        show(activity, "正在加载，请稍后...")
    }

    fun show(fragment: Fragment) {
        show(fragment.activity!!, "正在加载，请稍后...")
    }

    fun show(fragment: Fragment, color: Int) {
        show(fragment.activity!!, "正在加载，请稍后...", LoadType.SMALL, color)
    }

    override fun show(activity: Activity, content: String) {
        show(activity, content, LoadType.SMALL)
    }

    override fun show(activity: Activity, content: String, type: LoadType) {
        show(activity, content, type, R.color.grey_text_color)
    }

    fun show(activity: Activity, content: String, type: LoadType, color: Int) {
        // none loading view
        val layoutId = loadStatus.requestLoading()
        if (layoutId == -1) return
        var loadViewId = R.layout.small_layout_loading
        if (type == LoadType.MAX) loadViewId = R.layout.max_layout_loading
        //add loading view
        val layout = LayoutInflater.from(activity.application)
            .inflate(loadViewId, null)
        layout.findViewById<TextView>(R.id.content).run {
            text = content
            setTextColor(activity.getColor(color))
        }
        activity.findViewById<LinearLayout>(layoutId).also {
            it.removeAllViews()
            it.addView(layout)
        }
    }

    override fun error(activity: Activity, content: String, showRetry: Boolean) {
        error(activity, R.drawable.ic_error_24dp, content, showRetry)
    }

    fun error(fragment: Fragment, content: String, showRetry: Boolean) {
        error(fragment.activity!!, R.drawable.ic_error_24dp, content, showRetry)
    }

    /**
     * The base class method of the error interface, the layout can be customized
     */
    override fun error(activity: Activity, iconId: Int, content: String, showRetry: Boolean) {
        val layoutId = loadStatus.requestLoading()
        if (layoutId == -1) return
        val layout = LayoutInflater.from(activity.application)
            .inflate(R.layout.load_error_layout, null).also {
                it.findViewById<ImageView>(R.id.error_icon).also { a ->
                    a.setImageResource(iconId)
                }
                it.findViewById<TextView>(R.id.error_message).also { b ->
                    b.text = content
                }
                it.findViewById<TextView>(R.id.retry).also { c ->
                    c.visibility = if (showRetry) View.VISIBLE else View.GONE
                    c.setOnClickListener {
                        loadStatus.retry()
                    }
                }
            }
        activity.findViewById<LinearLayout>(layoutId).also {
            it.removeAllViews()
            //do add error view
            it.addView(layout)
        }
    }

    override fun retry() {
        // 循环重试时，可以调用该空方法标识重试含义，不用先hide再show，导致动画不流畅
    }

    override fun hide(activity: Activity) {
        //do hide code
        try {
            val layoutId = loadStatus.requestLoading()
            if (layoutId == -1) return
            activity.findViewById<LinearLayout>(layoutId).also { it.removeAllViews() }
        } catch (e: Exception) {
        }
    }


    fun callBack(loadStatus: LoadingViewStatus): LoadCallBack {
        this.loadStatus = loadStatus
        return this
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var loadingView: LoadingView? = null

        fun instance(): LoadingView {
            if (loadingView == null) {
                loadingView = LoadingView()
            }
            return loadingView!!
        }
    }

}