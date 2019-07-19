### CameraUtils

提供了调用小相机拍照的功能：

- 兼容7.0前后的差异

- 自带权限校验、申请

- 可自定义拒绝权限后，需要打开到设置界面的弹窗

- 可自定义弹窗提示信息

#### 如何使用

- 1. 准备工作，实现activity的onRequestPermissionsResult

```
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //添加如下代码
        CameraUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
```

- 2. 设置拍照回调

```

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //实现如下代码
        CameraUtils.onActivityResult(requestCode, resultCode, data,  object : CameraCallback() {
            override fun camera(bitmap: Bitmap?) {
                //图片以bitmap类型返回
            }

            override fun camera(uri: Uri?) {
                //图片以uri类型返回
                img.setImageURI(uri)
            }
        })
    }
```

- 3. 调用

```

    private fun initView() {
        open_camera.setOnClickListener {
            //调用拍照
            CameraUtils.openCamera(this@CameraActivity)
        }
    }
```

