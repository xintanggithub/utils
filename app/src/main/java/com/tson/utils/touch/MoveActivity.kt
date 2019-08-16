package com.tson.utils.touch

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import com.tson.utils.R
import kotlinx.android.synthetic.main.activity_move.*


class MoveActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move)
        initView()
    }

    private fun initView() {
        drawImg()
        bgImg.alpha = 0.8F
        bgImg.setOnTouchListener(OnMultiTouchListener(Handler(), bgImg, object : OnLongClick {
            override fun long() {
                drawImg()
                bgRoot.alpha = 0F
                bgImg.alpha = 0.8F
            }

            override fun up() {
                bgRoot.alpha = 1F
                bgImg.alpha = 0F
                drawImg()
            }

        }, 800))
    }

    fun drawImg() {
        bgRoot.buildDrawingCache()
        bgImg.setImageBitmap(bgRoot.getDrawingCache())
    }

}
