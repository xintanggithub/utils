
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
    implementation "com.tson.utils.lib.util:lib:1.0.2"
```

### 1. 工具类使用

#### 1.1 清单 

- CleanDataUtils 应用缓存相关工具类

- DisplayUtils 尺寸单位处理、位图模糊等工具类

- FileUtils 文件路径处理工具类

- BaseSharedPreferencesFactory SP基类

- StringUtils 字符串工具类

- TimeUtils 时间格式工具类

#### 1.2 CleanDataUtils工具类介绍

##### 1.2.1 查询所有的缓存大小，返回的字符串为已格式化的大小

```
fun getTotalCacheFormatSize(context: Context): String
```

##### 1.2.2 查询所有的缓存大小，返回Long型字节数

```
fun getTotalCacheSize(context: Context): Long
```

##### 1.2.3 清空缓存数据

```
fun clearAllCache(context: Context)
```

##### 1.2.4 清空应用内部缓存数据

```
fun cleanInternalCache(context: Context)
```

##### 1.2.5 清空本应用所有缓存数据

```
fun cleanDatabases(context: Context)
```

##### 1.2.6 清空SP的缓存数据

```
fun cleanSharedPreference(context: Context)
```

##### 1.2.7 清除外部缓存

```
fun cleanExternalCache(context: Context)
```

##### 1.2.8 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除

```
fun cleanCustomCache(filePath: String)
```

##### 1.2.9 删除指定目录下文件及目录

```
fun deleteFolderFile(filePath: String, deleteThisPath: Boolean)
```

##### 1.2.10 大小格式化

```
fun getFormatSize(size: Double): String
```

#### 1.3 DisplayUtils工具类介绍

##### 1.3.1 将px值转换为dp值

```
fun px2dp(pxValue: Float): Int
```

##### 1.3.2 将dp值转换为px值

```
fun dp2px(dpValue: Float): Int
```

##### 1.3.3 将px值转换为sp值

```
fun px2sp(pxValue: Float): Int
```

##### 1.3.4 将sp值转换为px值

```
fun sp2px(dpValue: Float): Int 
```

##### 1.3.5 获取屏幕宽度

```
fun getScreenWidthPixels(context: Activity): Int
```

##### 1.3.6 获取屏幕高度

```
fun getScreenHeightPixels(context: Activity): Int 
```

##### 1.3.7 view转为bitmap

```
fun viewToBitmap(view: View): Bitmap
```

##### 1.3.8 获取模糊虚化的bitmap

- bitmap  要模糊的图片

- radius  模糊等级 >=0 && <=25

```
fun getBlurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap
```

##### 1.3.9 Android的模糊方法

- bitmap  要模糊的图片

- radius  模糊等级 >=0 && <=25

```
fun blurBitmap(context: Context, bitmap: Bitmap, radius: Int):Bitmap
```

#### 1.4 FileUtils工具类介绍

##### 1.4.1 获取数据目录

```
val dataPath: String
```

##### 1.4.2 根据文件名称和路径，获取sd卡中的文件，以File形式返回byte

```
fun getFile(fileName: String, folder: String): File?
```

##### 1.4.3 根据文件名称和路径，获取sd卡中的文件，判断文件是否存在，存在返回true

```
fun checkFile(fileName: String, folder: String): Boolean
```

##### 1.4.4 检测SDCard是否可用

```
fun checkSDCard(): Boolean
```

##### 1.4.5 返回File 如果没有就创建

```
fun getDirectory(path: String): File
```

##### 1.4.6 删除文件夹

```
fun deleteDirectory(sPath: String): Boolean
```

##### 1.4.7 写入文件

```
fun writeFile(path: String, content: String): Int
```
