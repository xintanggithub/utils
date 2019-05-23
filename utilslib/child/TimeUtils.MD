#### 1.8 TimeUtils介绍

##### 1.8.1 获取现在时间 以pattern参数自定义的格式返回

```
fun getNowDateShort(pattern: String): Date
```

##### 1.8.2 将指定的毫秒数转换为 以pattern参数自定义的格式返回

```
fun ms2Date(_ms: Long, pattern: String): String
```

##### 1.8.3 将指定以pattern参数自定义的格式的时间转换为毫秒值

```
fun Date2ms(_data: String, pattern: String): Long
```