package com.tson.utils.lib.util.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import com.tson.utils.lib.util.R
import com.tson.utils.lib.util.camera.entity.TipsEntity
import com.tson.utils.lib.util.file.FileUtils
import com.tson.utils.lib.util.log.LogUtils
import com.tson.utils.lib.util.permission.PermissionUtils
import java.io.File


/**
 *  Date 2019-07-17 16:03
 *
 * @author tangxin
 */
class CameraUtils {

    companion object {

        private const val TAG = "CameraUtils"
        private const val TAKE_PHOTO = 101
        private const val CROP_PHOTO = 102
        private const val OPEN_CAMERA_ACTION = "android.media.action.IMAGE_CAPTURE"
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
        private const val WRITE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val CAMERA_PERMISSION_CODE = 787
        private const val WRITE_PERMISSION_CODE = 797
        @SuppressLint("StaticFieldLeak")
        private var activity: Activity? = null
        private var uri: Uri? = null
        private var path: String? = null
        private var imageName: String? = null
        private var outUri: Uri? = null
        private var defTip = HashMap<String, TipsEntity>().also {
            it[WRITE_PERMISSION] = TipsEntity(R.string.tip_sdk_permission_title, R.string.tip_sdk_permission_msg)
            it[CAMERA_PERMISSION] = TipsEntity(R.string.tip_camera_permission_title, R.string.tip_camera_permission_msg)
        }

        fun getFile(path: String, imageName: String): File {
            LogUtils.w(TAG, "mkdirs -> path=$path   imageName$imageName")
            File(path).also {
                LogUtils.w(TAG, "mkdirs -> file is exists = ${it.exists()}")
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
            val outputImage = File("$path/$imageName")
            LogUtils.w(TAG, "outputImage path = ${outputImage.path}")
            if (!outputImage.exists()) {
                outputImage.createNewFile()
            }
            return outputImage
        }

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            onRequestPermissionsResult(requestCode, permissions, grantResults, null, null)
        }

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
                                       builder: AlertDialog.Builder?) {
            onRequestPermissionsResult(requestCode, permissions, grantResults, builder, null)
        }

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray,
                                       tips: HashMap<String, TipsEntity>) {
            onRequestPermissionsResult(requestCode, permissions, grantResults, null, tips)
        }

        private fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
                                               , builder: AlertDialog.Builder?, tips: HashMap<String, TipsEntity>?) {
            if (requestCode == CAMERA_PERMISSION_CODE || requestCode == WRITE_PERMISSION_CODE) {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openCamera(activity!!, path!!, imageName!!)
                    } else {
                        for (permission in permissions) {
                            if (!PermissionUtils.hasPermission(activity!!, permission)) {
                                //如果无权限，判断是否拒绝
                                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
                                    //如果拒绝，进行提示
                                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
                                        if (builder != null) {
                                            builder.setPositiveButton(activity!!.getString(R.string.permission_setting)
                                            ) { _, _ -> PermissionUtils.openSetting(activity!!, requestCode) }
                                            builder.setNegativeButton(activity!!.getString(R.string.qf_cancel)) { dialog, _ ->
                                                dialog.dismiss()
                                            }
                                            builder.create().show()
                                        } else {
                                            if (null != tips) {
                                                defTip = tips
                                            }
                                            (defTip[permission] as TipsEntity).also {
                                                PermissionUtils.createPermissionDialog(activity!!,
                                                        activity!!.getString(it.titleMsgId),
                                                        requestCode, false)
                                            }
                                        }
                                        return
                                    }
                                } else {
                                    return
                                }
                            } else {
                                openCamera(activity!!, path!!, imageName!!)
                                return
                            }
                        }
                    }
                }
            }
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, crop: Boolean, callback: CameraCallback) {
            when (requestCode) {
                TAKE_PHOTO -> when (resultCode) {//拍照
                    Activity.RESULT_OK -> {
                        LogUtils.w(TAG, "crop setting = $crop")
                        val a: Any? = data?.extras?.get("data")
                        val bitmap: Bitmap = if (null != a) {
                            a as Bitmap
                        } else {
                            BitmapFactory.decodeStream(activity?.contentResolver?.openInputStream(uri!!))
                        }
                        if (crop) {
                            LogUtils.w(TAG, "start crop camera")
                            val layoutId = callback.getCropLayout()
                            //todo 设置可自定义的裁剪控件


                        } else {
                            LogUtils.w(TAG, "callback camera result")
                            callback.camera(bitmap)
                            callback.camera(uri)
                        }
                    }
                    Activity.RESULT_CANCELED -> {
                        LogUtils.w(TAG, "cancel camera")
                        callback.cancelCamera()
                    }
                }
                CROP_PHOTO -> when (resultCode) {//裁剪
                    Activity.RESULT_OK -> {
                        callback.crop(outUri)
                    }
                    Activity.RESULT_CANCELED -> {
                        callback.cancelCrop()
                    }
                }
            }
        }

        fun openCamera(ac: Activity) {
            openCamera(ac, if (FileUtils.checkSDCard()) Environment.getExternalStorageDirectory().path + "/cut/" else ac.cacheDir.path)
        }

        fun openCamera(ac: Activity, path: String) {
            openCamera(ac, path, "${ac.packageName}_IMG_${System.currentTimeMillis()}.jpg")
        }

        fun openCamera(ac: Activity, path: String, imageName: String) {
            activity = ac
            this.path = path
            this.imageName = imageName
            LogUtils.w(TAG, "start check permission")
            if (PermissionUtils.checkPermission(ac, WRITE_PERMISSION, ""
                            , WRITE_PERMISSION_CODE, false)) {
                if (PermissionUtils.checkPermission(ac, CAMERA_PERMISSION, " ",
                                CAMERA_PERMISSION_CODE, false)) {
                    LogUtils.w(TAG, "check permission end , is success")
                    priOpenCamera(ac, path, imageName)
                }
            }
        }

        fun priOpenCamera(ac: Activity, path: String, imageName: String) {
            LogUtils.w(TAG, "open camera, cls = [${ac.javaClass.name}]")
            val end = imageName.substring(imageName.length - ".jpg".length, imageName.length)
            val outputImage = getFile(path, if (end == ".jpg" || end == ".JGP") imageName else "$imageName.jpg")
            LogUtils.w(TAG, "file = [${outputImage.path}]")
            uri = when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && FileUtils.checkSDCard()) {
                true -> {
                    LogUtils.w(TAG, "FileProvider => ${ac.packageName}.fileProvider")
                    FileProvider.getUriForFile(ac, "${ac.packageName}.fileProvider", outputImage)
                }
                else -> {
                    Uri.parse(outputImage.path)
                }
            }
            LogUtils.w(TAG, "uri = [${uri.toString()}]")
            ac.startActivityForResult(Intent(OPEN_CAMERA_ACTION).also {
                it.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }, TAKE_PHOTO)
        }
    }

}