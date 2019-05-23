#### 1.5 Sp使用介绍

```
val sp = SharePreferencesFactory(context)

//存入String到SP
sp.setStringValue("key","value")
//从SP获取String
sp.getStringValue("key")
```

- 其他类型同上，调用set  、 get对应方法即可