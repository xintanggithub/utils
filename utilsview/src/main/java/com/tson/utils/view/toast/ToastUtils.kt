package com.tson.utils.view.toast

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

/**
 *  Date 2019-07-23 11:34
 *
 * @author tangxin
 */
class ToastUtils {
    
    companion object {

        private var toast: Toast? = null

        /**
         * 防止重叠的toast
         */
        @SuppressLint("ShowToast")
        fun show(msg: String, context: Context) {
            if (null == toast) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            } else {
                toast?.setText(msg)
            }
            toast?.show()
        }

    }
}