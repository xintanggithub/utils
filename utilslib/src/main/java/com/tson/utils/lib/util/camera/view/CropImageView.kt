package com.tson.utils.lib.util.camera.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 *  Date 2019-07-18 19:35
 *
 * @author tangxin
 */
class CropImageView : View {
    // 在touch重要用到的点，
    companion object {
        const val STATUS_SINGLE = 1
        const val STATUS_MULTI_START = 2
        const val STATUS_MULTI_TOUCHING = 3
        const val EDGE_LT = 1
        const val EDGE_RT = 2
        const val EDGE_LB = 3
        const val EDGE_RB = 4
        const val EDGE_MOVE_IN = 5
        const val EDGE_MOVE_OUT = 6
        const val EDGE_NONE = 7

    }

    // 在touch重要用到的点，
    private var mX_1 = 0f
    private var mY_1 = 0f
    // 当前状态
    private var mStatus = STATUS_SINGLE
    // 默认裁剪的宽高
    private var cropWidth = 150
    private var cropHeight = 150
    private var currentEdge = EDGE_NONE;

    private var oriRationWH = 0f
    var maxZoomOut = 5.0f
    var minZoomIn = 0.333333f

    private var mDrawable: Drawable? = null
    private var mFloatDrawable: FloatDrawable? = null

    private var mDrawableSrc = Rect()// 图片Rect变换时的Rect
    private var mDrawableDst = Rect()// 图片Rect
    private var mDrawableFloat = Rect()// 浮层的Rect
    var isFrist = true;
    var isTouchInSquare = true

    var mContext: Context? = null

    // 进行图片的裁剪，所谓的裁剪就是根据Drawable的新的坐标在画布上创建一张新的图片
    val cropImage: Bitmap
        get() {
            var tmpBitmap: Bitmap? = Bitmap.createBitmap(width, height,
                    Bitmap.Config.RGB_565)
            val canvas = Canvas(tmpBitmap!!)
            mDrawable!!.draw(canvas)
            val matrix = Matrix()
            val scale = mDrawableSrc.width().toFloat() / mDrawableDst.width().toFloat()
            matrix.postScale(scale, scale)
            val ret = Bitmap.createBitmap(tmpBitmap, mDrawableFloat.left,
                    mDrawableFloat.top, mDrawableFloat.width(),
                    mDrawableFloat.height(), matrix, true)
            tmpBitmap.recycle()
            tmpBitmap = null
            return ret
        }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    @SuppressLint("NewApi", "ObsoleteSdkInt")
    private fun init(context: Context) {
        this.mContext = context
        try {
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                this.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mFloatDrawable = FloatDrawable(context)
    }

    fun setDrawable(mDrawable: Drawable, cropWidth: Int, cropHeight: Int) {
        this.mDrawable = mDrawable
        this.cropWidth = cropWidth
        this.cropHeight = cropHeight
        this.isFrist = true
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.pointerCount > 1) {
            if (mStatus == STATUS_SINGLE) {
                mStatus = STATUS_MULTI_START
            } else if (mStatus == STATUS_MULTI_START) {
                mStatus = STATUS_MULTI_TOUCHING
            }
        } else {
            if (mStatus == STATUS_MULTI_START || mStatus == STATUS_MULTI_TOUCHING) {
                mX_1 = event.x
                mY_1 = event.y
            }
            mStatus = STATUS_SINGLE
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mX_1 = event.x
                mY_1 = event.y
                currentEdge = getTouch(mX_1.toInt(), mY_1.toInt())
                isTouchInSquare = mDrawableFloat.contains(event.x.toInt(),
                        event.y.toInt())
            }
            MotionEvent.ACTION_UP -> checkBounds()
            MotionEvent.ACTION_POINTER_UP -> currentEdge = EDGE_NONE
            MotionEvent.ACTION_MOVE -> if (mStatus == STATUS_MULTI_TOUCHING) {
            } else if (mStatus == STATUS_SINGLE) {
                val dx = (event.x - mX_1).toInt()
                val dy = (event.y - mY_1).toInt()
                mX_1 = event.x
                mY_1 = event.y
                // 根據得到的那一个角，并且变换Rect
                if (!(dx == 0 && dy == 0)) {
                    when (currentEdge) {
                        EDGE_LT -> mDrawableFloat.set(mDrawableFloat.left + dx,
                                mDrawableFloat.top + dy, mDrawableFloat.right,
                                mDrawableFloat.bottom)
                        EDGE_RT -> mDrawableFloat.set(mDrawableFloat.left,
                                mDrawableFloat.top + dy, mDrawableFloat.right + dx, mDrawableFloat.bottom)
                        EDGE_LB -> mDrawableFloat.set(mDrawableFloat.left + dx,
                                mDrawableFloat.top, mDrawableFloat.right, mDrawableFloat.bottom + dy)

                        EDGE_RB -> mDrawableFloat.set(mDrawableFloat.left, mDrawableFloat.top,
                                mDrawableFloat.right + dx, mDrawableFloat.bottom + dy)
                        EDGE_MOVE_IN -> if (isTouchInSquare) {
                            mDrawableFloat.offset(dx, dy)
                        }
                        EDGE_MOVE_OUT -> {
                        }
                    }
                    mDrawableFloat.sort()
                    invalidate()
                }
            }
        }
        return true
    }

    // 根据初触摸点判断是触摸的Rect哪一个角
    fun getTouch(eventX: Int, eventY: Int): Int {
        if (mFloatDrawable!!.bounds.left <= eventX && eventX < mFloatDrawable!!.bounds.left
                + mFloatDrawable!!.borderWidth && mFloatDrawable!!.bounds.top <= eventY
                && eventY < mFloatDrawable!!.bounds.top + mFloatDrawable!!.borderHeight) {
            return EDGE_LT
        } else if (mFloatDrawable!!.bounds.right - mFloatDrawable!!.borderWidth <= eventX
                && eventX < mFloatDrawable!!.bounds.right && mFloatDrawable!!.bounds.top <= eventY
                && eventY < mFloatDrawable!!.bounds.top + mFloatDrawable!!.borderHeight) {
            return EDGE_RT
        } else if (mFloatDrawable!!.bounds.left <= eventX
                && eventX < mFloatDrawable!!.bounds.left + mFloatDrawable!!.borderWidth
                && mFloatDrawable!!.bounds.bottom - mFloatDrawable!!.borderHeight <= eventY
                && eventY < mFloatDrawable!!.bounds.bottom) {
            return EDGE_LB
        } else if (mFloatDrawable!!.bounds.right - mFloatDrawable!!.borderWidth <= eventX
                && eventX < mFloatDrawable!!.bounds.right
                && mFloatDrawable!!.bounds.bottom - mFloatDrawable!!.borderHeight <= eventY
                && eventY < mFloatDrawable!!.bounds.bottom) {
            return EDGE_RB
        } else if (mFloatDrawable!!.bounds.contains(eventX, eventY)) {
            return EDGE_MOVE_IN
        }
        return EDGE_MOVE_OUT
    }

    override fun onDraw(canvas: Canvas) {
        if (mDrawable == null) {
            return
        }
        if (mDrawable!!.intrinsicWidth == 0 || mDrawable!!.intrinsicHeight == 0) {
            return
        }
        configureBounds()
        // 在画布上花图片
        mDrawable!!.draw(canvas)
        canvas.save()
        // 在画布上画浮层FloatDrawable,Region.Op.DIFFERENCE是表示Rect交集的补集
        canvas.clipRect(mDrawableFloat, Region.Op.DIFFERENCE)
        // 在交集的补集上画上灰色用来区分
        canvas.drawColor(Color.parseColor("#a0000000"))
        canvas.restore()
        // 画浮层
        mFloatDrawable!!.draw(canvas)
    }

    protected fun configureBounds() {
        // configureBounds在onDraw方法中调用
        // isFirst的目的是下面对mDrawableSrc和mDrawableFloat只初始化一次，
        // 之后的变化是根据touch事件来变化的，而不是每次执行重新对mDrawableSrc和mDrawableFloat进行设置
        if (isFrist) {
            oriRationWH = mDrawable!!.intrinsicWidth.toFloat() / mDrawable!!.intrinsicHeight.toFloat()
            val scale = mContext!!.resources.displayMetrics.density
            val w = Math.min(width, (mDrawable!!.intrinsicWidth * scale + 0.5f).toInt())
            val h = (w / oriRationWH).toInt()
            val left = (width - w) / 2
            val top = (height - h) / 2
            val right = left + w
            val bottom = top + h
            mDrawableSrc.set(left, top, right, bottom)
            mDrawableDst.set(mDrawableSrc)
            var floatWidth = dipTopx(mContext!!, cropWidth.toFloat())
            var floatHeight = dipTopx(mContext!!, cropHeight.toFloat())
            if (floatWidth > width) {
                floatWidth = width
                floatHeight = cropHeight * floatWidth / cropWidth
            }
            if (floatHeight > height) {
                floatHeight = height
                floatWidth = cropWidth * floatHeight / cropHeight
            }
            val floatLeft = (width - floatWidth) / 2
            val floatTop = (height - floatHeight) / 2
            mDrawableFloat.set(floatLeft, floatTop, floatLeft + floatWidth, floatTop + floatHeight)
            isFrist = false
        }
        mDrawable!!.bounds = mDrawableDst
        mFloatDrawable!!.bounds = mDrawableFloat
    }

    // 在up事件中调用了该方法，目的是检查是否把浮层拖出了屏幕
    protected fun checkBounds() {
        var newLeft = mDrawableFloat.left
        var newTop = mDrawableFloat.top
        var isChange = false
        if (mDrawableFloat.left < left) {
            newLeft = left
            isChange = true
        }
        if (mDrawableFloat.top < top) {
            newTop = top
            isChange = true
        }
        if (mDrawableFloat.right > right) {
            newLeft = right - mDrawableFloat.width()
            isChange = true
        }
        if (mDrawableFloat.bottom > bottom) {
            newTop = bottom - mDrawableFloat.height()
            isChange = true
        }
        mDrawableFloat.offsetTo(newLeft, newTop)
        if (isChange) {
            invalidate()
        }
    }

    fun dipTopx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}