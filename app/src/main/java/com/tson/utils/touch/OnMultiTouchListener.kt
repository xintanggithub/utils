package com.tson.utils.touch

import android.graphics.Matrix
import android.graphics.PointF
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView


/**
 *  Date 2019-08-16 14:00
 *
 * @author tangxin
 */
class OnMultiTouchListener(var handler: Handler, var longClickView: View,
                           var longListener: OnLongClick,
                           var delayMillis: Long) : OnTouchListener {

    private val NONE = 0
    private val MOVE = 1
    private val ZOOM = 2
    private val DRAG = 3

    private var mode = NONE
    private val matrix = Matrix()
    private val savedMatrix = Matrix()
    private val start = PointF()
    private val mid = PointF()
    private var oldDistance = 0f
    private var isLongTouch = false

    override fun onTouch(v: View, event: MotionEvent): Boolean {

        val view = v as ImageView

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                handler.removeCallbacks(r)
                matrix.set(view.imageMatrix)
                savedMatrix.set(matrix)
                start.set(event.x, event.y)
                handler.postDelayed(r, delayMillis)
                mode = DRAG
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                if (isLongTouch) {
                    oldDistance = spacing(event)
                    if (oldDistance > 1f) {
                        savedMatrix.set(matrix)
                        midPoint(mid, event)

                        mode = ZOOM
                    }
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                handler.removeCallbacks(r)
                longListener.up()
                if (isLongTouch)
                    mode = NONE
            }

            MotionEvent.ACTION_MOVE -> {
                if (isLongTouch) {
                    if (mode == DRAG) {
                        matrix.set(savedMatrix)
                        matrix.postTranslate(event.x - start.x, event.y - start.y)
                    } else if (mode == ZOOM) {
                        val newDist = spacing(event)
                        if (newDist > 1f) {
                            matrix.set(savedMatrix)
                            val scale = newDist / oldDistance
                            matrix.postScale(scale, scale, mid.x, mid.y)
                        }
                    }
                }
            }
        }

        view.setImageMatrix(matrix)
        view.setScaleType(ImageView.ScaleType.MATRIX)
        view.setPadding(3, 5, 3, 5)

        return true
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)

        return 1F
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)

        point.set(x / 2, y / 2)
    }

    private val r = Runnable {
        isLongTouch = true
        longListener.long()
    }

}

interface OnLongClick {

    fun long()

    fun up()

}
