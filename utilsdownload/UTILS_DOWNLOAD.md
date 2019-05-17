
## utils download

##### 下载模块

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
    implementation "com.tson.utils.lib.download:lib:1.0.0"
```

### 1. 使用

#### 1.1 在application的onCreate中初始化，以及配置

```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DownLoadManager.getInstance().init(this)
                .setDebugLog(true) //开启debug日志
                .setMaxThreadCount(3)//并发任务3个
                .setPath(FileUtils.getDataPath() + "download/")//设置下载路径(对应路径确保有读写权限)
                .setRetryCount(2);//重试次数2次
    }

}
```

#### 1.2 绑定下载监听

```
//绑定，一般在activity的onCreate （view初始化的地方）
DownLoadManager.bindChangeListener(downloadListener);
//解绑，一般是activity的onDestroy (销毁的)
DownLoadManager.unBindChangeListener(downloadListener);


//监听器
DownloadListener downloadListener=new DownloadListener() {
    @Override
    public void completed(BaseDownloadTask task) {
        //下载完成  
    }
};
```

其中completed为必须实现的回调方法，还有其他的下载状态变化回调方法可根据需求使用:

```
    //已准备好，等待下载，此时已经在下载队列   soFarBytes 已下载的byte数，totalBytes总大小
    void pending(BaseDownloadTask task, int soFarBytes, int totalBytes);
    //下载进度
    void progress(BaseDownloadTask task, int soFarBytes, int totalBytes);
    //暂停
    void paused(BaseDownloadTask task, int soFarBytes, int totalBytes);
    //错误
    void error(BaseDownloadTask task, Throwable e);
    //下载完成
    void completed(BaseDownloadTask task);
    //存在开始了一个在下载队列里已存在的任务(默认继续下载，不创建新的下载任务)
    void warn(BaseDownloadTask task);
    //重试  retryingTimes 重试次数  ex异常信息  soFarBytes 已下载大小
    void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes);
```

BaseDownloadTask里包含了当前下载任务的信息，包括：下载任务的ID、下载的url，保存在本地的路径等等。下载任务的ID是下载任务的标识，可以根据ID查询下载状态、大小等。


#### 1.3 下载任务的操作

```

//开始（创建/继续）
DownLoadManager.getDownloader().start(url);
//如果没有设置监听器，这里也可以设置
DownLoadManager.getDownloader().start(url,listener);

//暂停 id为BaseDownloadTask返回的任务ID
DownLoadManager.getDownloader().pause(id);
//暂停所有
DownLoadManager.getDownloader().pauseAll();

//清除单条下载任务数据 id为BaseDownloadTask返回的任务ID
//path为该文件本地存储的路径
DownLoadManager.getDownloader().clear(id,path);
//清除所有下载任务数据
DownLoadManager.getDownloader().clearAll();

//根据ID获取下载进度
DownLoadManager.getDownloader().getSoFar(id);
//根据ID获取下载任务对象总大小
DownLoadManager.getDownloader().getTotal(id);

```