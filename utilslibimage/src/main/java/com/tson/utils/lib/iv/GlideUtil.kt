package com.tson.utils.lib.iv

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.util.DisplayMetrics
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by simon on 18-2-28.
 */
internal class GlideUtil {

    companion object {

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         */
        fun setRoundIcon(context: Context, uri: Uri, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets corner icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param radius    the radius
         */
        fun setCornerIcon(context: Context, uri: Uri, imageView: ImageView, radius: Float) {
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = false
                            circularBitmapDrawable.cornerRadius = radius
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets corner icon.
         *
         * @param context   the context
         * @param path      the path
         * @param imageView the image view
         * @param radius    the radius
         */
        fun setCornerIcon(context: Context, path: String, imageView: ImageView, radius: Float) {
            Glide.with(context)
                    .asBitmap()
                    .load(path)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = false
                            circularBitmapDrawable.cornerRadius = radius
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets corner icon.
         *
         * @param context   the context
         * @param path      the path
         * @param imageView the image view
         * @param radius    the radius
         * @param options   the options
         */
        fun setCornerIcon(context: Context, path: String, imageView: ImageView, radius: Float, options: RequestOptions) {
            Glide.with(context)
                    .asBitmap()
                    .load(path)
                    .apply(options)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = false
                            circularBitmapDrawable.cornerRadius = radius
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param file      the file
         * @param imageView the image view
         */
        fun setRoundIcon(context: Context, file: File, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(file)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param path      the path
         * @param imageView the image view
         */
        fun setRoundIcon(context: Context, path: String, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(path)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param res       the res
         * @param imageView the image view
         */
        fun setRoundIcon(context: Context, res: Int?, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(res)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setRoundIcon(context: Context, uri: Uri, imageView: ImageView, drawable: Drawable) {

            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .apply(requestOptions)
                    .load(uri)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setRoundIcon(context: Context, uri: File, imageView: ImageView, drawable: Drawable) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .apply(requestOptions)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setRoundIcon(context: Context, uri: String, imageView: ImageView, drawable: Drawable) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .apply(requestOptions)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }

        /**
         * Sets round icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setRoundIcon(context: Context, uri: Int?, imageView: ImageView, drawable: Drawable) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .apply(requestOptions)
                    .into(object : BitmapImageViewTarget(imageView) {
                        override fun setResource(resource: Bitmap?) {
                            val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            imageView.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }


        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         */
        fun setIcon(context: Context, uri: Uri, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         */
        fun setIcon(context: Context, uri: File, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         */
        fun setIcon(context: Context, uri: String, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param options   the options
         */
        fun setIcon(context: Context, uri: String, imageView: ImageView, options: RequestOptions) {
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(options)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         */
        fun setIcon(context: Context, uri: Int?, imageView: ImageView) {
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setIcon(context: Context, uri: Uri, imageView: ImageView, drawable: Drawable) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setIcon(context: Context, uri: File, imageView: ImageView, drawable: Drawable) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setIcon(context: Context, uri: String, imageView: ImageView, drawable: Drawable) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView)
        }

        /**
         * Sets icon.
         *
         * @param context   the context
         * @param uri       the uri
         * @param imageView the image view
         * @param drawable  the drawable
         */
        @SuppressLint("CheckResult")
        fun setIcon(context: Context, uri: Int?, imageView: ImageView, drawable: Drawable) {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(drawable)
            Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .apply(requestOptions)
                    .into(imageView)
        }

        /**
         * Px 2 dp int.
         *
         * @param context the context
         * @param px      the px
         * @return the int
         */
        fun px2dp(context: Context, px: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        /**
         * Dp 2 px int.
         *
         * @param context the context
         * @param dp      the dp
         * @return the int
         */
        fun dp2px(context: Context, dp: Int): Int {
            val displayMetrics = context.resources.displayMetrics
            return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
        }

        /**
         * Gets .
         *
         * @param srcPath the src path
         * @return the
         */
        fun getImage(srcPath: String): File? {
            val newOpts = BitmapFactory.Options()
            //开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true
            var be = 1
            val width = newOpts.outWidth
            val height = newOpts.outHeight

            if (width > height) {
                if (width > 1024) {
                    be = 1024 / width
                }
            } else {
                if (height > 1024) {
                    be = 1024 / height
                }
            }
            if (be <= 0) {
                be = 1
            }
            newOpts.inJustDecodeBounds = false
            newOpts.inSampleSize = be
            val bitmap = BitmapFactory.decodeFile(srcPath, newOpts)
            return compressImage(bitmap, srcPath)
        }

        /**
         * Compress image file.
         *
         * @param image the image
         * @param path  the path
         * @return the file
         */
        fun compressImage(image: Bitmap, path: String): File? {
            val baos = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var options = 100
            while (baos.toByteArray().size > 2 * 1024 * 1024) {
                baos.reset()
                options -= 10
                image.compress(Bitmap.CompressFormat.JPEG, options, baos)
            }
            val isBm = ByteArrayInputStream(baos.toByteArray())
            try {
                File(path)
                val pic = File(Environment.getExternalStorageDirectory().toString() + "/comp" + File(path).name)
                var len: Int
                val buf = ByteArray(8192)
                val fos = FileOutputStream(pic)
                while ((isBm.read(buf).also { len = it }) != -1) {
                    fos.write(buf, 0, len)
                }
                fos.flush()
                fos.close()
                return pic
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

    }

}
