package com.tson.utils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.tson.utils.lib.iv.GlideUtil
import com.tson.utils.lib.util.UtilsHelper
import com.tson.utils.lib.util.log.LogUtils
import com.tson.utils.view.statusbar.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button.setOnClickListener { v ->
            startActivity(
                Intent(
                    this@Main2Activity,
                    TestRecyclerViewActivity::class.java
                )
            )
        }

        UtilsHelper.instance(this)
        UtilsHelper.isDebug(true)
        UtilsHelper.isSaveLog(true)

        printInitLog()

        val iv = findViewById<ImageView>(R.id.imageView)
        val iv2 = findViewById<ImageView>(R.id.imageView2)

        val appStoreIconUrl = "https://avatars2.githubusercontent.com/u/27901367?s=460&v=4"
        //圆角
        GlideUtil.setCornerIcon(
            this, appStoreIconUrl, iv, 20f,
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )
        //圆形
        GlideUtil.setCornerIcon(
            this, appStoreIconUrl, iv2, 200f,
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )

        button2.setOnClickListener { v -> startActivity(Intent(this, DemoUtilsActivity::class.java)) }
        button3.setOnClickListener { v -> UtilsHelper.openLogView(application) }
        button5.setOnClickListener { v ->
            LogUtils.run {
                //                d("test", "123131231312")
//                e("test", "get data error, message (token is empty)")
//                i("test", "info , a= 1   b=2   c=3")
//                w(
//                    "test",
//                    "warn , service is no running , url is https://avatars2.githubusercontent.com/u/27901367?s=460&v=4"
//                )
//                v("test", "----------------------------------")
            }

        }
        button9.setOnClickListener {
            startActivity(Intent(this@Main2Activity, DownloadActivity::class.java))
        }

        button10.setOnClickListener {
            startActivity(Intent(this@Main2Activity, HttpActivity::class.java))
        }
        button12.setOnClickListener {
            startActivity(Intent(this@Main2Activity, MenuActivity::class.java))
        }

        statusBar()
    }

    private fun statusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }

    private fun printInitLog() {
//        LogUtils.i("test", "------00-----------00000----------00000----");
//        LogUtils.i("test", "------00---------00-----00-------00----00--");
//        LogUtils.i("test", "------00--------00-------00-----00------00-");
//        LogUtils.i("test", "------00--------00-------00----00----------");
//        LogUtils.i("test", "------00--------00-------00----00-----00000");
//        LogUtils.i("test", "------00--------00-------00-----00-------00");
//        LogUtils.i("test", "------00---------00-----00-------00-----00-");
//        LogUtils.i("test", "------00000-----00000-----------000--");
    }
}
