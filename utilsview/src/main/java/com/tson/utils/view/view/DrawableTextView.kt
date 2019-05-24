package com.tson.utils.view.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatDrawableManager
import android.util.AttributeSet
import com.tson.utils.view.R

/**
 * Created by tangxin on 2017/4/20.
 */
class DrawableTextView(context: Context, attrs: AttributeSet?, defStyle: Int) :
    android.support.v7.widget.AppCompatTextView(context, attrs, defStyle) {

    constructor(context: Context) : this(context, null) {
        onDrawable(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        onDrawable(context, attrs)
    }

    init {
        onDrawable(context, attrs)
    }

    @SuppressLint("RestrictedApi")
    private fun onDrawable(context: Context, attrs: AttributeSet?) {
        if (null == attrs) {
            Throwable("drawableTextView attrs is null")
            return
        }
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView)
        val drawableManager = AppCompatDrawableManager.get()

        val drawableWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableWidth, -1)
        val drawableHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableHeight, -1)

        var drawableLeft: Drawable? = null
        var drawableLeftWidth = -1
        var drawableLeftHeight = -1
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableLeft, 0)) {
            drawableLeft = drawableManager.getDrawable(
                getContext(),
                ta.getResourceId(R.styleable.DrawableTextView_drawableLeft, 0)
            )
            drawableLeftWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableLeftWidth, -1)
            drawableLeftHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableLeftHeight, -1)
            drawableLeftWidth = getNumValue(drawableLeftWidth, drawableLeft, WIDTH_TYPE)
            drawableLeftHeight = getNumValue(drawableLeftHeight, drawableLeft, HEIGHT_TYPE)
        }

        var drawableTop: Drawable? = null
        var drawableTopWidth = -1
        var drawableTopHeight = -1
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableTop, 0)) {
            drawableTop =
                drawableManager.getDrawable(getContext(), ta.getResourceId(R.styleable.DrawableTextView_drawableTop, 0))
            drawableTopWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableTopWidth, -1)
            drawableTopHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableTopHeight, -1)
            drawableTopWidth = getNumValue(drawableTopWidth, drawableTop, WIDTH_TYPE)
            drawableTopHeight = getNumValue(drawableTopHeight, drawableTop, HEIGHT_TYPE)
        }

        var drawableRight: Drawable? = null
        var drawableRightWidth = -1
        var drawableRightHeight = -1
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableRight, 0)) {
            drawableRight = drawableManager.getDrawable(
                getContext(),
                ta.getResourceId(R.styleable.DrawableTextView_drawableRight, 0)
            )
            drawableRightWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableRightWidth, -1)
            drawableRightHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableRightHeight, -1)
            drawableRightWidth = getNumValue(drawableRightWidth, drawableRight, WIDTH_TYPE)
            drawableRightHeight = getNumValue(drawableRightHeight, drawableRight, HEIGHT_TYPE)
        }

        var drawableBottom: Drawable? = null
        var drawableBottomWidth = -1
        var drawableBottomHeight = -1
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableBottom, 0)) {
            drawableBottom = drawableManager.getDrawable(
                getContext(),
                ta.getResourceId(R.styleable.DrawableTextView_drawableBottom, 0)
            )
            drawableBottomWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableBottomWidth, -1)
            drawableBottomHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableBottomHeight, -1)
            drawableBottomWidth = getNumValue(drawableBottomWidth, drawableBottom, WIDTH_TYPE)
            drawableBottomHeight = getNumValue(drawableBottomHeight, drawableBottom, HEIGHT_TYPE)
        }

        if (drawableLeftWidth != -1 && drawableLeftHeight != -1) {
            drawableLeft?.setBounds(0, 0, drawableLeftWidth, drawableLeftHeight)
        }

        if (drawableTopWidth != -1 && drawableTopHeight != -1) {
            drawableTop?.setBounds(0, 0, drawableTopWidth, drawableTopHeight)
        }

        if (drawableRightWidth != -1 && drawableRightHeight != -1) {
            drawableRight?.setBounds(0, 0, drawableRightWidth, drawableRightHeight)
        }

        if (drawableBottomWidth != -1 && drawableBottomHeight != -1) {
            drawableBottom?.setBounds(0, 0, drawableBottomWidth, drawableBottomHeight)
        }

        val drawables = compoundDrawables
        var textDrawable: Drawable? = null
        for (drawable in drawables) {
            if (drawable != null) {
                textDrawable = drawable
            }
        }

        if (drawableWidth != -1 && drawableHeight != -1) {
            textDrawable?.setBounds(0, 0, drawableWidth, drawableHeight)
        }
        setCompoundDrawables(
            drawableLeft ?: drawables[0],
            drawableTop ?: drawables[1],
            drawableRight ?: drawables[2],
            drawableBottom ?: drawables[3]
        )
        ta.recycle()
    }

    private fun getNumValue(num: Int, drawable: Drawable?, mType: Int): Int {
        return if (num == -1) if (mType == WIDTH_TYPE) drawable!!.intrinsicWidth else drawable!!.intrinsicHeight else num
    }

    companion object {

        private val WIDTH_TYPE = 1

        private val HEIGHT_TYPE = 2
    }

}
