package com.tson.utils.view.tab

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.tson.utils.view.DisplayUtils
import com.tson.utils.view.R
import com.tson.utils.view.tab.callback.TabButtonListener
import com.tson.utils.view.tab.entity.Button
import com.tson.utils.view.tab.entity.Tab
import java.util.*

/**
 * Date 2019/5/17 3:39 PM
 *
 * @author tangxin
 */
class TabButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        LinearLayout(context, attrs, defStyleAttr) {

    /**
     * 是否有文字标题 true  有 false无
     */
    private var mTabButtonTextVisible = false
    /**
     * 是否有 icon  true有 false 无
     */
    private var mTabButtonIconVisible = false
    /**
     * 文字标题大小
     */
    private var mTabButtonTextSize: Float = 0.toFloat()
    /**
     * icon宽度
     */
    private var mTabButtonWidth: Float = 0.toFloat()
    /**
     * icon高度
     */
    private var mTabButtonHeight: Float = 0.toFloat()
    private var defaultIconWidth = -1
    private var defaultIconHeight = -1
    private var defColor: Int = 0
    private var lineColor: Int = 0
    private var selectColor: Int = 0
    private var interval: Int = 0
    private var foregroundDrawable: Drawable? = null
    private var foregroundId: Int = 0
    private var paddingDrawableLeft = 0
    private var paddingDrawableRight = 0
    private var paddingDrawableTop = 0
    private var paddingDrawableBottom = 0
    private var padding = 0

    private var isSelect = true

    /**
     * 显示模型
     * [TabModel]
     */
    private var mTabIconTextModel: Int = 0
    /**
     * tab对齐方式
     *
     *
     * 1. center
     *
     *
     * 2. left
     *
     *
     * 3. right
     *
     *
     * 4. top
     *
     *
     * 5. bottom
     */
    private var mTabGravity: Int = 0
    /**
     * 是否显示分割线
     */
    private var mTabDividingLineVisible: Boolean = false
    /**
     * 排列方式  1 横向  2纵向
     */
    private var mTabOrientation: Int = 0
    /**
     * 按钮属性列表
     */
    private val mButtons = ArrayList<Button>()
    private val tabs = ArrayList<Tab>()
    /**
     * 点击事件回调
     */
    private var onclickListener: TabButtonListener? = null
    /**
     * 默认选择位置,后面选中的位置也更新到这个值
     */
    private var defaultIndex = 0

    private//设置内容对齐模式
    val gravityType: Int
        get() {
            return when (TabGravity.getGravity(mTabGravity)) {
                TabGravity.TOP -> Gravity.TOP
                TabGravity.LEFT -> Gravity.START
                TabGravity.RIGHT -> Gravity.END
                TabGravity.BOTTOM -> Gravity.BOTTOM
                TabGravity.CENTER -> Gravity.CENTER
                else -> Gravity.CENTER
            }
        }

    internal var myListener: TabButtonListener = object : TabButtonListener() {
        override fun onclick(index: Int, button: Button) {
            if (null != onclickListener) {
                onclickListener!!.onclick(index, button)
            }
            defaultIndex = index
            notifyTabButton(button)
        }

        override fun onLongClick(index: Int, button: Button) {
            super.onLongClick(index, button)
            if (null != onclickListener) {
                onclickListener!!.onLongClick(index, button)
            }
            defaultIndex = index
            notifyTabButton(button)
        }
    }

    init {
        getAttr(context, attrs)
    }

    private fun getAttr(context: Context, attrs: AttributeSet?) {
        if (isInEditMode) {
            Throwable("isInEditMode is true")
            return
        }
        if (null == attrs) {
            Throwable("attrs is null, please setting attr")
            return
        }
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabButton)
        try {
            mTabButtonTextVisible = typedArray.getBoolean(
                    R.styleable
                            .TabButton_tab_button_text_visible, false
            )
            mTabButtonIconVisible = typedArray.getBoolean(
                    R.styleable
                            .TabButton_tab_button_icon_visible, false
            )
            isSelect = typedArray.getBoolean(R.styleable.TabButton_tab_button_select, true)
            val defaultTextSize = 13f
            mTabButtonTextSize = typedArray.getDimension(
                    R.styleable.TabButton_tab_button_text_size,
                    DisplayUtils.sp2px(context, defaultTextSize).toFloat()
            )
            mTabIconTextModel = typedArray.getInteger(
                    R.styleable.TabButton_tab_icon_text_model,
                    TabModel.TEXT_BOTTOM_ICON_TOP.type
            )
            mTabGravity = typedArray.getInteger(
                    R.styleable.TabButton_tab_gravity,
                    TabGravity.CENTER.gravity
            )
            mTabDividingLineVisible = typedArray.getBoolean(
                    R.styleable
                            .TabButton_tab_dividing_line_visible, false
            )
            mTabOrientation = typedArray.getInteger(
                    R.styleable.TabButton_tab_orientation,
                    TabOrientation.HORIZONTAL.orientation
            )
            if (mTabOrientation == TabOrientation.HORIZONTAL.orientation) {
                mTabButtonWidth = typedArray.getDimension(
                        R.styleable
                                .TabButton_tab_button_width,
                        ViewGroup.LayoutParams.MATCH_PARENT.toFloat()
                )
                mTabButtonHeight = typedArray.getDimension(
                        R.styleable
                                .TabButton_tab_button_height,
                        ViewGroup.LayoutParams.WRAP_CONTENT.toFloat()
                )
            } else {
                mTabButtonWidth = typedArray.getDimension(
                        R.styleable
                                .TabButton_tab_button_width,
                        ViewGroup.LayoutParams.WRAP_CONTENT.toFloat()
                )
                mTabButtonHeight = typedArray.getDimension(
                        R.styleable
                                .TabButton_tab_button_height,
                        ViewGroup.LayoutParams.MATCH_PARENT.toFloat()
                )
            }
            interval = typedArray.getDimension(
                    R.styleable.TabButton_tab_icon_text_interval,
                    (-DisplayUtils.dp2px(context, 20f)).toFloat()
            ).toInt()
            foregroundDrawable = typedArray.getDrawable(R.styleable.TabButton_foreground)
            foregroundId =
                    typedArray.getResourceId(R.styleable.TabButton_foreground, R.drawable.touchable_background_white)
            defColor = typedArray.getResourceId(R.styleable.TabButton_tab_text_def_color, R.color.def_color)
            selectColor = typedArray.getResourceId(R.styleable.TabButton_tab_text_select_color, R.color.select_color)
            defaultIconWidth = typedArray.getDimension(R.styleable.TabButton_tab_icon_width, -1f).toInt()
            defaultIconHeight = typedArray.getDimension(R.styleable.TabButton_tab_icon_height, -1f).toInt()
            padding = typedArray.getDimension(R.styleable.TabButton_tab_button_padding, 0f).toInt()
            if (padding == 0) {
                padding = 0
                paddingDrawableLeft = typedArray.getDimension(R.styleable.TabButton_tab_button_padding_left, 0f).toInt()
                paddingDrawableRight =
                        typedArray.getDimension(R.styleable.TabButton_tab_button_padding_right, 0f).toInt()
                paddingDrawableTop = typedArray.getDimension(R.styleable.TabButton_tab_button_padding_top, 0f).toInt()
                paddingDrawableBottom =
                        typedArray.getDimension(R.styleable.TabButton_tab_button_padding_bottom, 0f).toInt()
            }
            lineColor = typedArray.getResourceId(R.styleable.TabButton_tab_dividing_line_color, R.color.lines_color)
        } finally {
            typedArray.recycle()
        }
    }

    /**
     * 如果不设置ID数组，则直接去数组下标加hash生成
     *
     * @param defaultIndex 默认选择的按钮
     * @param buttons      按钮数据
     * @return [TabButton]
     */
    fun bindData(defaultIndex: Int, buttons: List<Button>): TabButton {
        if (buttons.isEmpty()) {
            Throwable("binData error, buttons is null")
        }
        this.defaultIndex = defaultIndex
        mButtons.clear()
        mButtons.addAll(buttons)
        createIds()
        return this
    }

    private fun createIds() {
        if (mButtons.isNotEmpty()) {
            for (i in mButtons.indices) {
                val button = mButtons[i]
                if (0 == button.id) {
                    button.id = i
                }
            }
        }
    }

    /**
     * 开始加载bind数据
     */
    fun initView(): TabButton {
        tabs.clear()
        //先绘制外部容器
        val layoutParams = layoutParams
        layoutParams.width = mTabButtonWidth.toInt()
        layoutParams.height = mTabButtonHeight.toInt()
        setLayoutParams(layoutParams)
        //设置内容排列方向
        orientation = if (mTabOrientation == TabOrientation.HORIZONTAL
                        .orientation
        )
            HORIZONTAL
        else
            VERTICAL
        if (!mButtons.isEmpty()) {
            for (i in mButtons.indices) {
                val item = mButtons[i]
                val id = item.id
                var textView: TextView? = null
                var imageView: ImageView? = null
                if (mTabButtonTextVisible) {
                    textView = drawTextView()
                }
                if (mTabButtonIconVisible) {
                    imageView = drawImageView()
                }
                //绘制好tab
                val linearLayout = drawTab(textView, imageView)
                //加入tablist，再统一设置点击事件
                tabs.add(tab(id, imageView, textView, linearLayout, item))
                //第一个不添加分隔线，后面的都需要先加分割线
                var tvLines: TextView? = null
                if (mTabDividingLineVisible) {
                    tvLines = drawLines()
                }
                if (i != 0 && null != tvLines) {
                    addView(tvLines)
                }
                addView(linearLayout)
            }
        } else {
            Throwable("buttons is empty")
        }
        loadData()
        return this
    }

    /**
     * 加载数据和绑定监听
     */
    private fun loadData() {
        if (tabs.isEmpty()) {
            Throwable("tab is null")
            return
        }
        for (i in tabs.indices) {
            val tab = tabs[i]
            val button = tab.button
            tab.linearLayout!!.setOnClickListener { v -> myListener.onclick(i, tab.button!!) }
            tab.linearLayout!!.setOnLongClickListener { v ->
                myListener.onLongClick(i, tab.button!!)
                true
            }
            if (defaultIndex == i && isSelect) {
                val imageView = tab.imageView
                imageView?.setImageDrawable(button!!.selectIcon)
                val textView = tab.textView
                if (null != textView) {
                    textView.run {
                        text = button!!.name
                        textSize = DisplayUtils.px2sp(context, mTabButtonTextSize).toFloat()
                        setTextColor(resources.getColor(selectColor))
                    }
                }
            } else {
                val imageView = tab.imageView
                imageView?.setImageDrawable(button!!.defaultIcon)
                val textView = tab.textView
                if (null != textView) {
                    textView.run {
                        text = button!!.name
                        textSize = DisplayUtils.px2sp(context, mTabButtonTextSize).toFloat()
                        setTextColor(resources.getColor(defColor))
                    }
                }
            }
        }
    }

    private fun tab(
            id: Int, imageView: ImageView?, textView: TextView?, linearLayout: LinearLayout,
            button: Button
    ): Tab {
        val tab = Tab()
        tab.id = id
        tab.imageView = imageView
        tab.textView = textView
        tab.linearLayout = linearLayout
        tab.button = button
        return tab
    }

    private fun drawTab(textView: TextView?, imageView: ImageView?): LinearLayout {
        val tabModel = TabModel.getModel(mTabIconTextModel)
        val linearLayout = LinearLayout(context)
        //横向
        if (mTabOrientation == TabOrientation.HORIZONTAL.orientation) {
            linearLayout.layoutParams = LayoutParams(
                    (if (mTabButtonWidth == -1f)
                        DisplayUtils.getScreenWidthSize()
                    else
                        mTabButtonWidth.toInt()) / mButtons.size,
                    LayoutParams.MATCH_PARENT
            )
        } else {
            //纵向
            linearLayout.layoutParams = LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (if (mTabButtonHeight == -1f)
                        DisplayUtils.getScreenHeightSize()
                    else
                        mTabButtonHeight.toInt()) / mButtons.size
            )
        }
        linearLayout.gravity = gravityType
        if (padding == 0) {
            linearLayout.setPadding(
                    paddingDrawableLeft, paddingDrawableTop,
                    paddingDrawableRight, paddingDrawableBottom
            )
        } else {
            linearLayout.setPadding(padding, padding, padding, padding)
        }
        when (tabModel) {
            TabModel.TEXT_TOP_ICON_BOTTOM -> {
                linearLayout.orientation = VERTICAL
                val layoutParams1 = LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParams1.setMargins(0, 0, 0, interval)
                textView!!.layoutParams = layoutParams1
                imageView?.layoutParams = LayoutParams(
                        if (defaultIconWidth == -1)
                            ViewGroup.LayoutParams.MATCH_PARENT
                        else
                            defaultIconWidth, 0, 1.0f
                )
                linearLayout.addView(textView)
                if (null != imageView)
                    linearLayout.addView(imageView)
            }
            TabModel.TEXT_BOTTOM_ICON_TOP -> {
                val layoutParams2 = LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParams2.setMargins(0, interval, 0, 0)
                textView!!.layoutParams = layoutParams2
                imageView?.layoutParams = LayoutParams(
                        if (defaultIconWidth == -1)
                            ViewGroup.LayoutParams.MATCH_PARENT
                        else
                            defaultIconWidth, 0, 1.0f
                )
                linearLayout.orientation = VERTICAL
                if (null != imageView)
                    linearLayout.addView(imageView)
                linearLayout.addView(textView)
            }
            TabModel.TEXT_LEFT_ICON_RIGHT -> {
                linearLayout.orientation = HORIZONTAL
                val layoutParams3 = LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
                layoutParams3.setMargins(0, 0, interval, 0)
                textView!!.layoutParams = layoutParams3
                imageView?.layoutParams = LayoutParams(
                        0, if (defaultIconHeight == -1)
                    ViewGroup.LayoutParams.MATCH_PARENT
                else
                    defaultIconHeight, 1.0f
                )
                linearLayout.addView(textView)
                if (null != imageView)
                    linearLayout.addView(imageView)
            }
            TabModel.TEXT_RIGHT_ICON_LEFT -> {
                linearLayout.orientation = HORIZONTAL
                val layoutParams4 = LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
                layoutParams4.setMargins(interval, 0, 0, 0)
                textView!!.layoutParams = layoutParams4
                imageView?.layoutParams = LayoutParams(
                        0, if (defaultIconHeight == -1)
                    ViewGroup.LayoutParams.MATCH_PARENT
                else
                    defaultIconHeight, 1.0f
                )
                if (null != imageView)
                    linearLayout.addView(imageView)
                linearLayout.addView(textView)
            }
            else -> {
                val layoutParams5 = LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
                layoutParams5.setMargins(0, interval, 0, 0)
                textView!!.layoutParams = layoutParams5
                imageView?.layoutParams = LayoutParams(
                        if (defaultIconWidth == -1)
                            ViewGroup.LayoutParams.MATCH_PARENT
                        else
                            defaultIconWidth, 0, 1.0f
                )
                linearLayout.orientation = VERTICAL
                if (null != imageView)
                    linearLayout.addView(imageView)
                linearLayout.addView(textView)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (null == foregroundDrawable) {
                linearLayout.foreground = resources.getDrawable(R.drawable.touchable_background_white)
            } else {
                linearLayout.foreground = resources.getDrawable(foregroundId)
            }
        }
        return linearLayout
    }

    private fun drawLines(): TextView {
        return TextView(context).also {
            it.setBackgroundResource(lineColor)
            if (mTabOrientation == TabOrientation.HORIZONTAL.orientation) {
                it.layoutParams = LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT)
            } else {
                //纵向
                it.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
            }
        }
    }

    private fun drawTextView(): TextView {
        return TextView(context).also {
            it.maxLines = 1
            it.ellipsize = TextUtils.TruncateAt.END
            it.setBackgroundResource(R.color.translation)
            it.gravity = Gravity.CENTER
        }
    }

    private fun drawImageView(): ImageView {
        return ImageView(context)
    }

    /**
     * 选中button，指向下标
     */
    fun setIndex(index: Int) {
        notifyTabButton(tabs[index].button)
        defaultIndex = index
    }

    /**
     * 替换某个按钮
     *
     * @param index  按钮位置
     * @param button 按钮数据
     */
    fun replace(index: Int, button: Button) {
        mButtons[index] = button
        removeAllViews()
        initView()
    }

    /**
     * 删除button
     *
     * @param index 需要删除的button位置
     */
    fun remove(index: Int) {
        try {
            mButtons.removeAt(index)
        } catch (e: Exception) {
            Throwable(e)
            return
        }

        if (defaultIndex == index) {
            defaultIndex = -1
        }
        removeAllViews()
        initView()
    }

    /**
     * 添加一个按钮
     *
     * @param button 按钮数据
     */
    fun addButton(button: Button) {
        mButtons.add(button)
        removeAllViews()
        initView()
    }

    /**
     * 添加一个按钮到指定位置
     *
     * @param index  需要添加到的位置
     * @param button 按钮数据
     */
    fun addButton(index: Int, button: Button) {
        //说明index是在当前tab范围以内
        if (mButtons.size >= index + 1) {
            mButtons.add(index, button)
        } else {
            //说明不在范围以内
            //添加到第一个
            if (index < 0) {
                mButtons.add(0, button)
            } else {
                //添加到最后一个
                mButtons.add(button)
            }
        }
        removeAllViews()
        initView()
    }

    /**
     * 设置点击事件回调
     *
     * @param tabButtonListener 回调方法
     */
    fun setOnclickListener(tabButtonListener: TabButtonListener) {
        this.onclickListener = tabButtonListener
    }

    private fun notifyTabButton(button: Button?) {
        for (tab in tabs) {
            val finalButton = tab.button
            if (finalButton?.id != button?.id || !isSelect) {
                val imageView = tab.imageView
                imageView?.setImageDrawable(finalButton?.defaultIcon)
                val textView = tab.textView
                textView?.run {
                    text = finalButton?.name
                    textSize = DisplayUtils.px2sp(context, mTabButtonTextSize).toFloat()
                    setTextColor(resources.getColor(defColor))
                }
            } else {
                val imageView = tab.imageView
                imageView?.setImageDrawable(finalButton?.selectIcon)
                val textView = tab.textView
                textView?.run {
                    text = finalButton?.name
                    textSize = DisplayUtils.px2sp(context, mTabButtonTextSize).toFloat()
                    setTextColor(resources.getColor(selectColor))
                }
            }
        }
    }

    companion object {
        private val TAG = TabButton::class.java.name
    }

}
