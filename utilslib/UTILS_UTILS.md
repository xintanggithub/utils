
## utilsLib

##### 工具集，提供常用的工具类，避免每次都重新添加相通工具类、工具方法，减少开发成本

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
    implementation "com.tson.utils.lib.util:lib:1.0.10"
```

### 1. 工具类使用

```

UtilsHelper.also {
            it.instance(this@Activity) //实例化
            it.isDebug(true) //是否打开debug,打开则会输出日志
            var path = it.isSaveLog(true) //是否存储日志文件,true返回存储路径,false返回空字符串
        }

//打开日志界面
UtilsHelper.openLogView(getApplication())
```

#### 1.1 清单 

- CleanDataUtils 应用缓存相关工具类 [点击查看](https://github.com/xintanggithub/utils/blob/master/utilslib/child/CleanDataUtils.MD)

- DisplayUtils 尺寸单位处理、位图模糊等工具类 [点击查看](https://github.com/xintanggithub/utils/blob/master/utilslib/child/DisplayUtils.MD)

- FileUtils 文件路径处理工具类 [点击查看](https://github.com/xintanggithub/utils/blob/master/utilslib/child/FileUtils.MD)

- BaseSharedPreferencesFactory SP基类 [点击查看](https://github.com/xintanggithub/utils/blob/master/utilslib/child/BaseSharedPreferencesFactory.MD)

- StringUtils 字符串工具类 [点击查看](https://github.com/xintanggithub/utils/blob/master/utilslib/child/StringUtils.MD)

- TimeUtils 时间格式工具类 [点击查看](https://github.com/xintanggithub/utils/blob/master/utilslib/child/TimeUtils.MD)

- LogUtils 日志工具类 [点击查看](https://github.com/xintanggithub/utils/blob/master/utilslib/child/LogUtil.MD)
