### 1. TabButton的使用

#### 1.1 添加布局

```
        <com.tson.utils.view.tab.TabButton
            android:id="@+id/tabButton"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="#789789"
            app:tab_button_height="200dp"
            app:tab_button_icon_visible="true"
            app:tab_button_text_size="16sp"
            app:tab_button_text_visible="true"
            app:tab_dividing_line_visible="true"
            app:tab_gravity="1"
            app:tab_icon_text_interval="-8dp"
            app:tab_icon_text_model="1"
            app:tab_orientation="2" />

```

##### 属性说明

- tab_button_height TabButton高度

- tab_button_width  TabButton宽度

- tab_button_icon_visible 是否显示图标

- tab_button_text_size 字体大小

- tab_button_text_visible 是否显示文字标题

- tab_dividing_line_visible 是否显示分割线

- tab_gravity 内部对齐方式
                             1. center
                             2. left
                             3. right
                             4. top
                             5. bottom

- tab_icon_text_interval icon和text的间隔，大于0增加间隔，小于0减少间隔

- tab_icon_text_model 显示模式 
         1 文字在上  icon 在下
         2 文字在左  icon 在右
         3 文字在下  icon 在上
         4 文字在右  icon 在左
         
- tab_orientation 对齐方式
         1横向 
         2纵向

#### 1.2 使用

```
        //默认icon数组
        val defIcon: MutableList<Int> = mutableListOf(
                R.drawable.ic_customer, R.drawable.ic_houses,
                R.drawable.ic_main, R.drawable.ic_mine
        )
        //选择的icon数组
        val selectIcon: MutableList<Int> = mutableListOf(
                R.drawable.ic_customer_up, R.drawable.ic_house_up,
                R.drawable.ic_main_up, R.drawable.ic_mine_up
        )

        val a = mutableListOf<Button>()

        for (i in 0..3) {
            val button = Button()
            button.id = i
            //转换为drawble
            button.defaultIcon = ContextCompat.getDrawable(this, defIcon[i])
            button.selectIcon = ContextCompat.getDrawable(this, selectIcon[i])
            //默认字体颜色
            button.defTextColor = "#000000"
            //选中字体颜色
            button.selectTextColor = "#ec7d28"
            //tab名称
            button.name = "tab-$i"
            a.add(button)
        }
        //添加数据并显示
        tabButton.bindData(0, a).initView()
        //绑定点击事件
        tabButton.setOnclickListener(onclick)


    val onclick = object : TabButtonListener() {
        override fun onclick(index: Int, button: Button?) {
            Toast.makeText(this@DemoUtilsActivity, "onclick:: ${button?.name}", Toast.LENGTH_LONG).show()
        }

        override fun onLongClick(index: Int, button: Button?) {
            Toast.makeText(this@DemoUtilsActivity, "onLongClick:: ${button?.name}", Toast.LENGTH_LONG).show()
        }
    }

```
