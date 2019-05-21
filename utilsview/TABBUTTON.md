![示例](https://github.com/xintanggithub/utils/blob/master/utilsview/tabButton_png/device-2019-05-20-162657.png?raw=true)

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
         
- tab_text_def_color 字体默认颜色

- tab_text_select_color 字体选择颜色

- tab_icon_height icon高度

- tab_icon_width icon宽度

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

#### 1.3 方法说明

##### 1.3.1  绑定tabButton数据

- List<Button> buttons：
    将需要展示的自定义参数以及标题icon等传入
  
- defaultIndex：
    默认选择位置

```
public TabButton bindData(int defaultIndex, @NonNull List<Button> buttons)
```

##### 1.3.2 加载绑定的的数据

- 需要在bindData之后调用 

```
public TabButton initView()
```

##### 1.3.3 选中某一个tabButton

- index:
    tabButton的位置，下标
    
- initView完成之后调用

```
public void setIndex(int index)
```

##### 1.3.4 替换某个tabButton

- index：
    同上描述
    
- Button：
    需要替换的参数以及标题icon等传入

```
public void replace(int index, Button button)
```

##### 1.3.5 删除某个tabButton

- index：
    需要删除button的位置

```
public void remove(int index)
```

##### 1.3.6  添加一个tabButton到最后的位置

- 添加一个button，这里默认是添加到最后一个

- Button:
    同上描述

```
public void addButton(Button button) 
```

##### 1.3.7 添加一个tabButton到指定位置

- index：
    需要添加的button的位置，小于0时添加到第一个，大于当前存在最大位置时加入最后一个位置，其他情况添加到对应位置
 
- Button:
    同上描述

```
public void addButton(int index, Button button)
```

##### 1.3.8 添加点击事件监听

- 绑定监听事件，包括点击 和 长按事件

```
public void setOnclickListener(TabButtonListener tabButtonListener)
```

- TabButtonListener 说明

```

public interface OnclickListener {

    /**
     * 点击事件回调
     *
     * @param index  点击的button位置
     * @param button button数据
     */
    void onclick(int index, Button button);

    /**
     * 长按事件回调
     *
     * @param index  点击的button位置
     * @param button button数据
     */
    void onLongClick(int index, Button button);

}

```

