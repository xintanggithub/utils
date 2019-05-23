
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

##### 1.4.8 复制文件

```
fun copyStream(`is`: InputStream, os: OutputStream): Int
```

##### 1.4.9 获取序列化对象

```
fun readerObject(filePath: String): Any
```

##### 1.4.10 写入序列化对象

- return 1 success 0 failed

```
fun writeObject(path: String, `object`: Any): Int
```


