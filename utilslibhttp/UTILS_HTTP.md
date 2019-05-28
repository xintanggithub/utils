
## utilslibhttp

### 0. 如何引入

#### 0.1 根目录下的build.gradle添加如下代码

```
allprojects {
    repositories {
        google()
        jcenter()
        //添加下面这个maven配置
        maven{
            //这是maven仓库地址
            url 'https://raw.githubusercontent.com/xintanggithub/maven/master'
        }
    }
}
```

#### 0.2 需要使用的module下build.gradle添加引用

```
    implementation "com.tson.utils.lib.http:lib:1.0.4"
```

#### 1. 使用

```

interface MarketApi {

    @GET("apps/list")
    fun marketList(@Query("page") page: Int,
                   @Query("pageSize") pageSize: Int,
                   @Query("channel") channel: String,
                   @Query("patch") patch: Boolean?): Observable<BaseEntity<List<PackageEntity>>>
}

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
```