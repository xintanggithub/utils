
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
