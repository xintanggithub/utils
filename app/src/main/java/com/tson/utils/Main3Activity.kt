package com.tson.utils

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tson.utils.fr.MarketAppAdapter
import kotlinx.android.synthetic.main.test_001.*

class Main3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_001)
        viewPager.offscreenPageLimit = 3
        viewPager.adapter = MarketAppAdapter(supportFragmentManager)
    }
}
