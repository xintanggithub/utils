package com.tson.utils.lib.iv.helper

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.tson.utils.lib.iv.R
import com.tson.utils.lib.iv.option.GlideRoundTransform

/**
 * Date 2019/6/3 11:16 AM
 *
 * @author tangxin
 */
class GlideHelper {

    companion object {
        /**
         * 圆角并兼容centerCrop
         */
        fun radiusAndCenterCrop(context: Context, url: String, options: RequestOptions, image: ImageView, radius: Int) {
            Glide.with(context)
                .load(url)
                .apply(options.also { it.transform(GlideRoundTransform(context, radius)) })
                .into(image);
        }

        /**
         * 圆形(显示在背景，前景为none)
         */
        fun circularSrcNone(context: Context, url: String, options: RequestOptions, iv: ImageView) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(object : BitmapImageViewTarget(iv) {
                    override fun setResource(resource: Bitmap?) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            iv.run {
                                setImageResource(R.drawable.none_24dp)
                                background = RoundedBitmapDrawableFactory.create(context.resources, resource).also {
                                    it.isCircular = true
                                }
                            }
                        }
                    }
                })
        }

        /**
         * 圆形(显示在背景，前景为none)
         */
        fun circular(context: Context, url: String, options: RequestOptions, iv: ImageView) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(object : BitmapImageViewTarget(iv) {
                    override fun setResource(resource: Bitmap?) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            iv.run {
                                setImageDrawable(RoundedBitmapDrawableFactory.create(context.resources, resource).also {
                                    it.isCircular = true
                                })
                            }
                        }
                    }
                })
        }
    }

}
