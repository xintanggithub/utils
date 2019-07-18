package com.tson.utils.photo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tson.utils.R
import com.tson.utils.lib.util.UtilsHelper
import com.tson.utils.lib.util.camera.CameraCallback
import com.tson.utils.lib.util.camera.CameraUtils
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        UtilsHelper.also {
            it.instance(this@CameraActivity)
            it.isDebug(true)
            it.isSaveLog(false)
        }
        initView()
    }

    private fun initView() {
        open_camera.setOnClickListener {
            CameraUtils.openCamera(this@CameraActivity)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        CameraUtils.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        CameraUtils.onActivityResult(requestCode, resultCode, data, check.isChecked, object : CameraCallback() {
            override fun camera(bitmap: Bitmap?) {
            }

            override fun camera(uri: Uri?) {
                img.setImageURI(uri)
            }
        })
    }

}
