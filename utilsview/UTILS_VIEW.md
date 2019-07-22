
## utilsView

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
    implementation "com.tson.utils.view:lib:1.0.20"
```

### 1. BaseAdapter的使用 [点击这里查看详情](https://github.com/xintanggithub/utils/blob/master/utilsview/BASEADAPTER.md)

### 2. TabButton的使用 [点击这里查看详情](https://github.com/xintanggithub/utils/blob/master/utilsview/TABBUTTON.md)

### 3. 状态栏沉浸式的使用 [点击这里查看详情](https://github.com/xintanggithub/utils/blob/master/utilsview/md/statusBar.MD)

### 4. AppBarLayout.Behavior的使用 [点击这里查看详情](https://github.com/xintanggithub/utils/blob/master/utilsview/md/behavior.MD)
