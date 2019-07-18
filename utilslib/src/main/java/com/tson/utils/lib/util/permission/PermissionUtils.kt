package com.tson.utils.lib.util.permission

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.tson.utils.lib.util.R
import java.lang.reflect.InvocationTargetException
import java.util.*

/**
 * The type Permission utils.
 *
 * @author tangxin
 */
class PermissionUtils {

    companion object {
        private val CHECK_OP_NO_THROW = "checkOpNoThrow"
        private val OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION"
        private var StatusBoolean = false

        private val mRequestCode = -1

        private var mOnPermissionListener: OnPermissionListener? = null

        /**
         * The interface On permission listener.
         */
        interface OnPermissionListener {

            /**
             * On permission granted.
             */
            fun onPermissionGranted()

            /**
             * On permission denied.
             */
            fun onPermissionDenied()
        }

        /**
         * Request permissions.
         *
         * @param context     the context
         * @param permissions the permissions
         * @param listener    the listener
         */
        fun requestPermissions(context: Activity, permissions: Array<String>, listener: OnPermissionListener) {
            mOnPermissionListener = listener
            val deniedPermissions = getDeniedPermissions(context, *permissions)
            if (deniedPermissions.size > 0) {
                mOnPermissionListener!!.onPermissionDenied()
            } else {
                if (mOnPermissionListener != null) {
                    mOnPermissionListener!!.onPermissionGranted()
                }
            }
        }

        /**
         * 获取请求权限中需要授权的权限
         */
        private fun getDeniedPermissions(context: Context, vararg permissions: String): List<String> {
            val deniedPermissions = ArrayList<String>()
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                    deniedPermissions.add(permission)
                }
            }
            return deniedPermissions
        }

        /**
         * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
         *
         * @param requestCode  the request code
         * @param permissions  the permissions
         * @param grantResults the grant results
         */
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            if (mRequestCode != -1 && requestCode == mRequestCode) {
                if (mOnPermissionListener != null) {
                    if (verifyPermissions(grantResults)) {
                        mOnPermissionListener!!.onPermissionGranted()
                    } else {
                        mOnPermissionListener!!.onPermissionDenied()
                    }
                }
            }
        }

        /**
         * 验证所有权限是否都已经授权
         */
        private fun verifyPermissions(grantResults: IntArray): Boolean {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        /**
         * Has permission boolean.
         *
         * @param context    the context
         * @param permission the permission
         * @return the boolean
         */
        fun hasPermission(context: Context, permission: String): Boolean {
            try {
                return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            } catch (throwable: Throwable) {
                return false
            }

        }

        /**
         * 获取当前权限状态
         *
         * @param context the context
         * @param str     the str
         * @param code    the code
         * @return boolean
         */
        fun GetPermissionStatus(context: Activity, str: Array<String>, code: Int): Boolean {
            StatusBoolean = false
            requestPermissions(context, str, object : OnPermissionListener {
                override fun onPermissionGranted() {
                    StatusBoolean = true
                }

                override fun onPermissionDenied() {
                    StatusBoolean = false
                    //弹出权限被禁用的提示框
                    ActivityCompat.requestPermissions(context, str, code)
                }
            })
            return StatusBoolean
        }

        /**
         * Check permission boolean.
         *
         * @param activity      the activity
         * @param permission    the permission
         * @param dialogMessage the dialog message
         * @param requestCode   the request code
         * @param finished      the finished
         * @return the boolean
         */
        fun checkPermission(activity: Activity, permission: String, dialogMessage: String, requestCode: Int, finished: Boolean): Boolean {
            if (!hasPermission(activity, permission)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
                    createPermissionDialog(activity, dialogMessage, requestCode, finished)
                } else {
                    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
                }
                return false
            }
            return true
        }

        /**
         * Open setting boolean.
         *
         * @param activity    the activity
         * @param requestCode the request code
         * @return the boolean
         */
        fun openSetting(activity: Activity, requestCode: Int): Boolean {
            val intent = Intent()
            if (activity.packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
                intent.setPackage("com.android.car.settings")
                intent.putExtra("EXTRA_DEEPLINK_PACKAGE_NAME", activity.packageName)
            } else {
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", activity.packageName, null)
            }

            if (intent.resolveActivity(activity.packageManager) != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivityForResult(intent, requestCode)
                return true
            }
            return false
        }

        /**
         * Create permission dialog.
         *
         * @param activity          the activity
         * @param message           the message
         * @param requestCode       the request code
         * @param finished          the finished
         * @param checkNotification the check notification
         */
        @JvmOverloads
        fun createPermissionDialog(
                activity: Activity, message: String,
                requestCode: Int, finished: Boolean,
                checkNotification: CheckNotification? = null
        ) {
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(false)
            builder.setMessage(message)
            builder.setTitle("")
            builder.setPositiveButton(
                    activity.getString(R.string.permission_setting)
            ) { dialog, which -> openSetting(activity, requestCode) }
            builder.setNegativeButton(activity.getString(R.string.qf_cancel)) { dialog, which ->
                if (null != checkNotification) {
                    checkNotification.cancel()
                } else {
                    if (finished) {
                        activity.finish()
                    } else {
                        dialog.dismiss()
                    }
                }
            }
            builder.create().show()
        }

        /**
         * Is notification enabled boolean.
         *
         * @param context the context
         * @return the boolean
         */
        fun isNotificationEnabled(context: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val mAppOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                val appInfo = context.applicationInfo
                val pkg = context.applicationContext.packageName
                val uid = appInfo.uid
                val appOpsClass: Class<*>
                try {
                    appOpsClass = Class.forName(AppOpsManager::class.java.name)
                    val checkOpNoThrowMethod =
                            appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String::class.java)
                    val opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION)
                    val value = opPostNotificationValue.get(Int::class.java) as Int
                    return checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) as Int == AppOpsManager.MODE_ALLOWED
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: NoSuchMethodException) {
                    e.printStackTrace()
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

            }
            return true
        }

        /**
         * The interface Check notification.
         */
        interface CheckNotification {
            /**
             * Cancel.
             */
            fun cancel()
        }

    }
    /**
     * Create permission dialog.
     *
     * @param activity    the activity
     * @param message     the message
     * @param requestCode the request code
     * @param finished    the finished
     */
}