package com.tson.utils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.tson.utils.entity.BaseEntity
import com.tson.utils.entity.PackageEntity
import com.tson.utils.lib.http.RetrofitFactory
import com.tson.utils.lib.util.log.LogUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_http.*
import retrofit2.http.GET
import retrofit2.http.Query

class HttpActivity : AppCompatActivity() {

    val host: String = "https://api.jidouauto.com/market/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http)
        button11.setOnClickListener {
            RetrofitFactory.createApi(MarketApi::class.java, host)
                    .marketList(1, 20, "audi", true)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        run {
                            LogUtils.w("httpActivity", Gson().toJson(it))
                            textView2.text = Gson().toJson(it)
                        }
                    }

        }
    }
}


interface MarketApi {

    @GET("apps/list")
    fun marketList(@Query("page") page: Int,
                   @Query("pageSize") pageSize: Int,
                   @Query("channel") channel: String,
                   @Query("patch") patch: Boolean?): Observable<BaseEntity<List<PackageEntity>>>

}