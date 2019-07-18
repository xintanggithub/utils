package com.tson.utils.lib.util.camera.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable


/**
 *  Date 2019-07-18 19:40
 *
 * @author tangxin
 */
class FloatDrawable(private val mContext: Context) : Drawable() {
    private val offset = 50
    private val mLinePaint = Paint()
    private val mLinePaint2 = Paint()

    //根据dip计算的像素值，做适配用的
    val borderWidth: Int
        get() = dipTopx(mContext, offset.toFloat())

    val borderHeight: Int
        get() = dipTopx(mContext, offset.toFloat())

    init {
        mLinePaint.setARGB(200, 50, 50, 50)
        mLinePaint.strokeWidth = 1f
        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.isAntiAlias = true
        mLinePaint.color = Color.WHITE
        mLinePaint2.setARGB(200, 50, 50, 50)
        mLinePaint2.strokeWidth = 7f
        mLinePaint2.style = Paint.Style.STROKE
        mLinePaint2.isAntiAlias = true
        mLinePaint2.color = Color.WHITE
    }

    override fun draw(canvas: Canvas) {
        val left = bounds.left
        val top = bounds.top
        val right = bounds.right
        val bottom = bounds.bottom
        val mRect = Rect(left + dipTopx(mContext, offset.toFloat()) / 2, top + dipTopx(mContext, offset.toFloat()) / 2, right - dipTopx(mContext, offset.toFloat()) / 2, bottom - dipTopx(mContext, offset.toFloat()) / 2)
        //画默认的选择框
        canvas.drawRect(mRect, mLinePaint)
        //画四个角的四个粗拐角、也就是八条粗线
        canvas.drawLine(left + dipTopx(mContext, offset.toFloat()) / 2 - 3.5f, (top + dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                left + dipTopx(mContext, offset.toFloat()) - 8f,
                (top + dipTopx(mContext, offset.toFloat()) / 2).toFloat(), mLinePaint2)
        canvas.drawLine((left + dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (top + dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (left + dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (top + dipTopx(mContext, offset.toFloat()) / 2 + 30).toFloat(), mLinePaint2)
        canvas.drawLine(right - dipTopx(mContext, offset.toFloat()) + 8f,
                (top + dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (right - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (top + dipTopx(mContext, offset.toFloat()) / 2).toFloat(), mLinePaint2)
        canvas.drawLine((right - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                top + dipTopx(mContext, offset.toFloat()) / 2 - 3.5f,
                (right - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (top + dipTopx(mContext, offset.toFloat()) / 2 + 30).toFloat(), mLinePaint2)
        canvas.drawLine(left + dipTopx(mContext, offset.toFloat()) / 2 - 3.5f, (bottom - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                left + dipTopx(mContext, offset.toFloat()) - 8f,
                (bottom - dipTopx(mContext, offset.toFloat()) / 2).toFloat(), mLinePaint2)
        canvas.drawLine((left + dipTopx(mContext, offset.toFloat()) / 2).toFloat(), (bottom - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (left + dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                bottom.toFloat() - (dipTopx(mContext, offset.toFloat()) / 2).toFloat() - 30f, mLinePaint2)
        canvas.drawLine(right - dipTopx(mContext, offset.toFloat()) + 8f, (bottom - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (right - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                (bottom - dipTopx(mContext, offset.toFloat()) / 2).toFloat(), mLinePaint2)
        canvas.drawLine((right - dipTopx(mContext, offset.toFloat()) / 2).toFloat(), bottom.toFloat()
                - (dipTopx(mContext, offset.toFloat()) / 2).toFloat() - 30f,
                (right - dipTopx(mContext, offset.toFloat()) / 2).toFloat(),
                bottom - dipTopx(mContext, offset.toFloat()) / 2 + 3.5f, mLinePaint2)
    }

    override fun setBounds(bounds: Rect) {
        super.setBounds(Rect(bounds.left - dipTopx(mContext, offset.toFloat()) / 2,
                bounds.top - dipTopx(mContext, offset.toFloat()) / 2, bounds.right + dipTopx(mContext, offset.toFloat()) / 2, bounds.bottom + dipTopx(mContext, offset.toFloat()) / 2))
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(cf: ColorFilter?) {
    }

    @SuppressLint("WrongConstant")
    override fun getOpacity(): Int {
        return 0
    }

    fun dipTopx(context: Context, dpValue: Float): Int {
        val scale = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

}