#### 1.6 StringUtils 工具类介绍

##### 1.6.1   随机生成指定长度字符串

```
//length表示生成字符串的长度
fun getRandomString(length: Int): String  
```

##### 1.6.2 生成大写MD5串

```
fun md5Upper(string: String): String 
```

##### 1.6.3 生成小写MD5串

```
fun md5Lower(string: String): String 
```

##### 1.6.4 生成指定类型MD5串

- string 需要MD的字符串

- isUpper 是否需要大写的穿 true 大写，false 小写

```
fun md5(string: String, isUpper: Boolean): String 
```

##### 1.6.5 判断字符串是否为空

- 返回true为空，false不为空

```
fun isEmpty(value: String?): Boolean
```

##### 1.6.6 是否为邮箱

```
fun isEmail(email: String): Boolean
```

##### 1.6.7 校验手机号码是否合法（不建议使用）

```
//不加区号
fun isPhoneNumberValid(phoneNumber: String): Boolean
//加区号
fun isPhoneNumberValid(areaCode: String, phoneNumber: String): Boolean
```

##### 1.6.8 校验字符串是否为纯数字

```
fun isNumber(str: String): Boolean 
```

##### 1.6.9 格式化字节大小

- 根据大小返回B、KB、MB、GB

```
fun getFileSize(size: Long): String 
```