### BottomSheet使用

#### 1.创建
```
private var test = BottomSheet<String>()
```

#### 2.使用
```
            test.setData(list, "1232", object : BottomSheet.ChooseItemListener<String> {
                override fun onResult(position: Int, result: String) {
                    // todo
                }
            }, object : BottomSheet.BindTextListener {
                override fun bindText(tv: TextView, position: Int) {
                    tv.text = list[position]
                }
            })
            test.show(supportFragmentManager, CameraActivity::getLocalClassName.toString() + "1")
``` 