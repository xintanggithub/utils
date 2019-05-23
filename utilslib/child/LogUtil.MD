#### 1.7 日志工具

使用前，请确认在初始化的时候有过设置开启日志、开启日志存储

```
UtilsHelper.also {
            it.instance(this@Activity) //实例化
            it.isDebug(true) //是否打开debug,打开则会输出日志
            var path = it.isSaveLog(true) //是否存储日志文件,true返回存储路径,false返回空字符串
        }

//打开日志界面
UtilsHelper.openLogView(getApplication())
```

如果没有设置，则不会缓存日志，提供的LogUtilView也无法产看日志信息

##### 1.7.1 打印日志到控制台

- 以debug为例

```
LogUtils.d(TAG,content);
```

- 其他的还有 d(DEBUG) 、 i(INFO) 、 w(WARN) 、 e(ERROR) 


##### 1.7.2 打开logUtilView查看日志

```
UtilsHelper.openLogView(context);
```

#### 1.7.3 日志文件目录说明

```
         * 目录结构：
         *
         *
         * ----- rootFile (不同的secret，目录不一样)
         * --------- log
         * -------------- 2019-03-13  (跟随日期变动，每天创建一个)
         * ------------------- 1  (按数量递增，大于1MB之后创建下一个)
```