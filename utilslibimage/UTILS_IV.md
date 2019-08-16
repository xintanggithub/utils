
## utilsIv

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
    implementation "com.tson.utils.lib.iv:lib:1.0.15"
```

### 1. 如何使用

#### 1.1 显示圆角图片

以下为圆形 和 圆角的实现方式，当圆角大小大于图片半径时，则为圆形

```
            String appStoreIconUrl = "http://a.hiphotos.baidu.com/image/h%3D300/sign=81bb1142c85c10383b7ec8c28210931c/2cf5e0fe9925bc31348b10c050df8db1ca137097.jpg";
            //圆角
            GlideUtil.Companion.setCornerIcon(this, appStoreIconUrl, iv, 20,
                    new RequestOptions().placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background));
            //圆形
            GlideUtil.Companion.setCornerIcon(this, appStoreIconUrl, iv2, 200,
                    new RequestOptions().placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background));
```

方法参数说明

- context 上下文

- path 需要显示url

- imageView加载图片的控件

- radius 圆角大小

- options 其他配置 (包括错误的占位图、加载中的占位图等等，具体参见com.bumptech.glide.request.RequestOptions)

```
fun setCornerIcon(context: Context, path: String, imageView: ImageView, radius: Float, options: RequestOptions)
```

### 添加GlideHelper工具类

```
        /**
         * 设置配置项：如占位图、错误时显示的图
         */
        fun optionsSetting(@Nullable placeholder: Drawable, @Nullable error: Drawable): RequestOptions
```

```
        /**
         * 圆角并兼容centerCrop
         */
        fun radiusAndCenterCrop(context: Context, url: String, options: RequestOptions, image: ImageView, radius: Int)
```

```
        /**
         * 圆形(显示在背景，前景为none)
         */
        fun circularSrcNone(context: Context, url: String, options: RequestOptions, iv: ImageView)
```

```
        /**
         * 圆形(显示在背景，前景为none)
         */
        fun circular(context: Context, url: String, options: RequestOptions, iv: ImageView)
```

```
        /**
         * view  ->  imageView
         * url   ->  load img url
         * radius  ->  圆角大小，超过半数为圆形，vague越大，同等值的radius圆角度也越大
         * vague  ->  标准模糊的多少倍（标准的为1-25，vague为1时，模糊等级为25  , 设置参考   模糊度= 25 X vague）
         * errorDrawable -> 错误时的占位图
         * config  ->   参考Bitmap.Config的枚举
         */
        fun vagueAndRadius(view: ImageView, url: String?, radius: Float, vague: Int,
                           errorDrawable: Int, config: Bitmap.Config)
```