package com.tson.utils.lib.util.camera

import android.graphics.Bitmap
import android.net.Uri

/**
 *  Date 2019-07-17 18:00
 *
 * @author tangxin
 */
interface AllCallback {

    fun camera(bitmap: Bitmap?)

    fun camera(uri: Uri?)

    fun cancelCamera()

    fun cancelCrop()

    fun crop(uri: Uri?)

}