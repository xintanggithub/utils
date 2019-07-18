package com.tson.utils.lib.util.camera

import android.net.Uri

/**
 *  Date 2019-07-17 17:59
 *
 * @author tangxin
 */
abstract class CameraCallback : AllCallback {

    override fun camera(uri: Uri?) {
    }

    override fun cancelCamera() {
    }

    override fun cancelCrop() {
    }

    override fun crop(uri: Uri?) {
    }

    override fun getCropLayout(): Int = -1

}