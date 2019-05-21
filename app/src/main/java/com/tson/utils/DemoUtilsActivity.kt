package com.tson.utils

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.tson.utils.view.tab.callback.TabButtonListener
import com.tson.utils.view.tab.entity.Button
import kotlinx.android.synthetic.main.activity_demo_http.*

/**
 * @author tangxin
 */
class DemoUtilsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_http)
        val defIcon: MutableList<Int> = mutableListOf(
                R.drawable.ic_customer, R.drawable.ic_houses,
                R.drawable.ic_main, R.drawable.ic_mine
        )
        val selectIcon: MutableList<Int> = mutableListOf(
                R.drawable.ic_customer_up, R.drawable.ic_house_up,
                R.drawable.ic_main_up, R.drawable.ic_mine_up
        )

        val a = mutableListOf<Button>()

        for (i in 0..3) {
            val button = Button()
            button.id = i
            button.defaultIcon = ContextCompat.getDrawable(this, defIcon[i])
            button.selectIcon = ContextCompat.getDrawable(this, selectIcon[i])
            button.name = "tab-$i"
            a.add(button)
        }
        tabButton.bindData(0, a).initView()
        tabButton3.bindData(1, a).initView()
        tabButton4.bindData(2, a).initView()
        tabButton5.bindData(3, a).initView()

        tabButton2.bindData(0, a).initView()
        tabButton6.bindData(1, a).initView()
        tabButton7.bindData(2, a).initView()
        tabButton8.bindData(3, a).initView()

        tabButton.setOnclickListener(onclick)
        tabButton3.setOnclickListener(onclick)
        tabButton4.setOnclickListener(onclick)
        tabButton5.setOnclickListener(onclick)
        tabButton2.setOnclickListener(onclick)
        tabButton6.setOnclickListener(onclick)
        tabButton7.setOnclickListener(onclick)
        tabButton8.setOnclickListener(onclick)

        replace.setOnClickListener {
            val button = Button().also {
                it.id = 2
                it.name = "007"
                it.defaultIcon = ContextCompat.getDrawable(this, defIcon[2])
                it.selectIcon = ContextCompat.getDrawable(this, selectIcon[2])
            }
            tabButton.replace(2, button)
            tabButton2.replace(2, button)
        }

        add_button.setOnClickListener {
            val button = Button().also {
                it.id = 5
                it.name = "008"
                it.defaultIcon = ContextCompat.getDrawable(this, defIcon[2])
                it.selectIcon = ContextCompat.getDrawable(this, selectIcon[2])
            }
            tabButton3.addButton(button)
            tabButton6.addButton(button)
            tabButton7.addButton(8, button)
        }

        index.setOnClickListener {
            tabButton2.setIndex(1);
        }

        remove.setOnClickListener {
            tabButton2.remove(2)
        }
    }

    val onclick = object : TabButtonListener() {
        override fun onclick(index: Int, button: Button?) {
            Toast.makeText(this@DemoUtilsActivity, "onclick:: ${button?.name}", Toast.LENGTH_LONG).show()
        }

        override fun onLongClick(index: Int, button: Button?) {
            Toast.makeText(this@DemoUtilsActivity, "onLongClick:: ${button?.name}", Toast.LENGTH_LONG).show()
        }
    }

}
