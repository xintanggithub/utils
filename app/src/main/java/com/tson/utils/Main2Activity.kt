package com.tson.utils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.tson.utils.lib.iv.GlideUtil
import com.tson.utils.lib.util.UtilsHelper
import com.tson.utils.lib.util.log.LogUtils
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button.setOnClickListener { v -> startActivity(Intent(this@Main2Activity, TestRecyclerViewActivity::class.java)) }

        UtilsHelper.instance(this)
        UtilsHelper.isDebug(true)
        UtilsHelper.isSaveLog(true)

        printInitLog()

        val iv = findViewById<ImageView>(R.id.imageView)
        val iv2 = findViewById<ImageView>(R.id.imageView2)

        val appStoreIconUrl = "https://avatars2.githubusercontent.com/u/27901367?s=460&v=4"
        //圆角
        GlideUtil.setCornerIcon(this, appStoreIconUrl, iv, 20f,
                RequestOptions().placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))
        //圆形
        GlideUtil.setCornerIcon(this, appStoreIconUrl, iv2, 200f,
                RequestOptions().placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background))

        button2.setOnClickListener { v -> startActivity(Intent(this, DemoUtilsActivity::class.java)) }
        button3.setOnClickListener { v -> UtilsHelper.openLogView(application) }
        button5.setOnClickListener { v ->
            run {
                LogUtils.d("test", "123131231312");
                LogUtils.e("test", "get data error, message (token is empty)");
                LogUtils.i("test", "info , a= 1   b=2   c=3");
                LogUtils.w("test", "warn , service is no running , url is https://avatars2.githubusercontent.com/u/27901367?s=460&v=4");
                LogUtils.v("test", "----------------------------------");
            }
        }
    }

    private fun printInitLog() {
        LogUtils.i("test", "------00-----------00000----------00000----");
        LogUtils.i("test", "------00---------00-----00-------00----00--");
        LogUtils.i("test", "------00--------00-------00-----00------00-");
        LogUtils.i("test", "------00--------00-------00----00----------");
        LogUtils.i("test", "------00--------00-------00----00-----00000");
        LogUtils.i("test", "------00--------00-------00-----00-------00");
        LogUtils.i("test", "------00---------00-----00-------00-----00-");
        LogUtils.i("test", "------00000-----00000-----------000--");
    }
}
