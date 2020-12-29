package com.tson.utils

import android.app.Activity
import android.os.Bundle
import com.easy.EasyManager
import com.easy.http.Request
import com.easy.http.RetrofitFactory
import com.google.gson.Gson
import com.tson.utils.entity.Gm
import com.tson.utils.lib.util.log.LogUtils
import kotlinx.android.synthetic.main.activity_button4.*

class Button4Activity : Activity() {

    lateinit var api: Api

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button4)
        initBtn.setOnClickListener {
            EasyManager.instance.buildHttp()
                .header("test", "222")
                .header("test2", "3333")
                .header("test3", "4444")
                .host("http://49.234.239.133/")
            api = RetrofitFactory.createApi(Api::class.java)
        }
        query.setOnClickListener {
            Request.doGet({
                getGm()
            }, {
                LogUtils.w("response", Gson().toJson(it))
            }, {
                LogUtils.w("error", it.message ?: "")
            })
        }
    }

    private suspend fun getGm(): Gm {
        return api.getGmAsync(1, 20)
    }
}
