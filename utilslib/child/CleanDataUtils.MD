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
