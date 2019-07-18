package com.tson.utils.lib.util.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
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
        private const val OPEN_CROP_CAMERA_ACTION = "com.android.camera.action.CROP"
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
        private const val CAMERA_PERMISSION_CODE = 787
        private const val TYPE = "image/*"
        @SuppressLint("StaticFieldLeak")
        private var activity: Activity? = null
        private var uri: Uri? = null
        private var path: String? = null
        private var imageName: String? = null
        private var outUri: Uri? = null

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
            if (requestCode == CAMERA_PERMISSION_CODE) {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        LogUtils.w(TAG, "start reCheck permission")
                        priOpenCamera(activity!!, path!!, imageName!!)
                    } else {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!, CAMERA_PERMISSION)) {
                            LogUtils.w(TAG, "check permission end , is error : need author $CAMERA_PERMISSION")
                            PermissionUtils.createPermissionDialog(activity!!, "这个权限必须要，给老子整起！",
                                    requestCode, false)
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
                        if (crop) {
                            LogUtils.w(TAG, "start crop")
                            val rootFile = this.path + "/crop/"
                            File(rootFile).also {
                                if (!it.exists()) {
                                    it.mkdirs()
                                }
                            }
                            val files = File("$rootFile${System.currentTimeMillis()}.jpg")
                            if (!files.exists()) {
                                files.createNewFile()
                            }
                            outUri = Uri.parse(files.path)
                            cropCamera(activity!!, outUri!!, 150, 150)
                        } else {
                            LogUtils.w(TAG, "callback camera result")
                            val a: Any? = data?.extras?.get("data")
                            if (null != a) {
                                callback.camera(a as Bitmap)
                            } else {
                                val bitmap = BitmapFactory.decodeStream(activity?.contentResolver?.openInputStream(uri!!))
                                callback.camera(bitmap)
                            }
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
            if (PermissionUtils.checkPermission(ac, CAMERA_PERMISSION, "使用拍照需要以下权限。。。。",
                            CAMERA_PERMISSION_CODE, false)) {
                LogUtils.w(TAG, "check permission end , is success")
                priOpenCamera(ac, path, imageName)
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

        fun cropCamera(ac: Activity, imgUrl: Uri, w: Int, h: Int) {
            val intent = Intent(OPEN_CROP_CAMERA_ACTION).also {
                it.type = TYPE
                it.setDataAndType(imgUrl, TYPE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                }
                it.putExtra("crop", "true")
                it.putExtra("scale", true)
                it.putExtra("aspectX", 1)
                it.putExtra("aspectY", 1)
                it.putExtra("outputX", w)
                it.putExtra("outputY", h)
                it.putExtra("return-data", true)
                it.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl)
                it.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
                it.putExtra("noFaceDetection", true)
            }
            val apps = ac.packageManager.queryIntentActivities(intent, 0)
            if (apps == null || apps.isEmpty()) {
                LogUtils.e(TAG, "Active application of unProcessable tailoring , please check for your system app list")
                return
            }
            ac.startActivityForResult(intent, CROP_PHOTO)
        }

        fun openPhoto() {

        }

    }

}