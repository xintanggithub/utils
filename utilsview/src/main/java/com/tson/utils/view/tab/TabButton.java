package com.tson.utils.view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tson.utils.view.DisplayUtils;
import com.tson.utils.view.R;
import com.tson.utils.view.tab.callback.TabButtonListener;
import com.tson.utils.view.tab.entity.Button;
import com.tson.utils.view.tab.entity.Tab;

import java.util.ArrayList;
import java.util.List;

/**
 * Date 2019/5/17 3:39 PM
 *
 * @author tangxin
 */
public class TabButton extends LinearLayout {

    private static final String TAG = TabButton.class.getName();

    /**
     * 是否有文字标题 true  有 false无
     */
    private boolean mTabButtonTextVisible = false;
    /**
     * 是否有 icon  true有 false 无
     */
    private boolean mTabButtonIconVisible = false;
    /**
     * 文字标题大小
     */
    private float mTabButtonTextSize;
    /**
     * 字体 默认大小
     */
    private float defaultTextSize = 13;
    /**
     * icon宽度
     */
    private float mTabButtonWidth;
    /**
     * icon高度
     */
    private float mTabButtonHeight;
    private float defaultIconWidth = 20;
    private float defaultIconHeight = 20;
    private int defColor;
    private int selectColor;
    private int interval;
    /**
     * 显示模型
     * {@link TabModel}
     */
    private int mTabIconTextModel;
    /**
     * tab对齐方式<p/>
     * 1. center<p/>
     * 2. left<p/>
     * 3. right<p/>
     * 4. top<p/>
     * 5. bottom
     */
    private int mTabGravity;
    /**
     * 是否显示分割线
     */
    private boolean mTabDividingLineVisible;
    /**
     * 排列方式  1 横向  2纵向
     */
    private int mTabOrientation;
    /**
     * 按钮属性列表
     */
    private List<Button> mButtons = new ArrayList<>();
    private List<Tab> tabs = new ArrayList<>();
    /**
     * 点击事件回调
     */
    private TabButtonListener onclickListener;
    /**
     * 默认选择位置,后面选中的位置也更新到这个值
     */
    private int defaultIndex = 0;

    public TabButton(Context context) {
        this(context, null);
    }

    public TabButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
    }

    private void getAttr(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            new Throwable("isInEditMode is true");
            return;
        }
        if (null == attrs) {
            new Throwable("attrs is null, please setting attr");
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabButton);
        try {
            mTabButtonTextVisible = typedArray.getBoolean(R.styleable
                    .TabButton_tab_button_text_visible, false);
            mTabButtonIconVisible = typedArray.getBoolean(R.styleable
                    .TabButton_tab_button_icon_visible, false);
            mTabButtonTextSize = typedArray.getDimension(R.styleable.TabButton_tab_button_text_size,
                    DisplayUtils.Companion.sp2px(context, defaultTextSize));
            mTabIconTextModel = typedArray.getInteger(R.styleable.TabButton_tab_icon_text_model,
                    TabModel.TEXT_BOTTOM_ICON_TOP.getType());
            mTabGravity = typedArray.getInteger(R.styleable.TabButton_tab_gravity,
                    TabGravity.CENTER.getGravity());
            mTabDividingLineVisible = typedArray.getBoolean(R.styleable
                    .TabButton_tab_dividing_line_visible, false);
            mTabOrientation = typedArray.getInteger(R.styleable.TabButton_tab_orientation,
                    TabOrientation.HORIZONTAL.getOrientation());
            if (mTabOrientation == TabOrientation.HORIZONTAL.getOrientation()) {
                mTabButtonWidth = typedArray.getDimension(R.styleable
                                .TabButton_tab_button_width,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                mTabButtonHeight = typedArray.getDimension(R.styleable
                                .TabButton_tab_button_height,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                mTabButtonWidth = typedArray.getDimension(R.styleable
                                .TabButton_tab_button_width,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                mTabButtonHeight = typedArray.getDimension(R.styleable
                                .TabButton_tab_button_height,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
            interval = (int) typedArray.getDimension(R.styleable.TabButton_tab_icon_text_interval,
                    -(DisplayUtils.Companion.dp2px(context, 20)));
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * 如果不设置ID数组，则直接去数组下标加hash生成
     *
     * @param defaultIndex 默认选择的按钮
     * @param buttons      按钮数据
     * @return {@link TabButton}
     */
    public TabButton bindData(int defaultIndex, @NonNull List<Button> buttons) {
        if (null == buttons || buttons.size() == 0) {
            new Throwable("binData error, buttons is null");
        }
        this.defaultIndex = defaultIndex;
        mButtons.clear();
        mButtons.addAll(buttons);
        createIds();
        return this;
    }

    private void createIds() {
        if (null != mButtons && !mButtons.isEmpty()) {
            for (int i = 0; i < mButtons.size(); i++) {
                Button button = mButtons.get(i);
                if (0 == button.getId()) {
                    button.setId(i);
                }
            }
        }
    }

    /**
     * 开始加载bind数据
     */
    public TabButton initView() {
        tabs.clear();
        //先绘制外部容器
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = (int) mTabButtonWidth;
        layoutParams.height = (int) mTabButtonHeight;
        setLayoutParams(layoutParams);
        //设置内容排列方向
        setOrientation(mTabOrientation == TabOrientation.HORIZONTAL
                .getOrientation() ? HORIZONTAL : VERTICAL);
        if (null != mButtons && !mButtons.isEmpty()) {
            for (int i = 0; i < mButtons.size(); i++) {
                Button item = mButtons.get(i);
                int id = item.getId();
                TextView textView = null;
                ImageView imageView = null;
                if (mTabButtonTextVisible) {
                    textView = drawTextView();
                }
                if (mTabButtonIconVisible) {
                    imageView = drawImageView();
                }
                //绘制好tab
                LinearLayout linearLayout = drawTab(textView, imageView);
                //加入tablist，再统一设置点击事件
                tabs.add(tab(id, imageView, textView, linearLayout, item));
                //第一个不添加分隔线，后面的都需要先加分割线
                TextView tvLines = null;
                if (mTabDividingLineVisible) {
                    tvLines = drawLines();
                }
                if (i != 0 && null != tvLines) {
                    addView(tvLines);
                }
                addView(linearLayout);
            }
        } else {
            new Throwable("buttons is empty");
        }
        loadData();
        return this;
    }

    /**
     * 加载数据和绑定监听
     */
    private void loadData() {
        if (null == tabs || tabs.isEmpty()) {
            new Throwable("tab is null");
            return;
        }
        for (int i = 0; i < tabs.size(); i++) {
            Tab tab = tabs.get(i);
            Button button = tab.getButton();
            int finalI = i;
            tab.getLinearLayout().setOnClickListener(v -> {
                myListener.onclick(finalI, tab.getButton());
            });
            tab.getLinearLayout().setOnLongClickListener(v -> {
                myListener.onLongClick(finalI, tab.getButton());
                return true;
            });
            if (defaultIndex == i) {
                ImageView imageView = tab.getImageView();
                if (null != imageView) {
                    imageView.setImageDrawable(button.getSelectIcon());
                }
                TextView textView = tab.getTextView();
                if (null != textView) {
                    textView.setText(button.getName());
                    textView.setTextSize(DisplayUtils.Companion.px2sp(getContext(), mTabButtonTextSize));
                    textView.setTextColor(Color.parseColor(button.getSelectTextColor()));
                }
            } else {
                ImageView imageView = tab.getImageView();
                if (null != imageView) {
                    imageView.setImageDrawable(button.getDefaultIcon());
                }
                TextView textView = tab.getTextView();
                if (null != textView) {
                    textView.setText(button.getName());
                    textView.setTextSize(DisplayUtils.Companion.px2sp(getContext(), mTabButtonTextSize));
                    textView.setTextColor(Color.parseColor(button.getDefTextColor()));
                }
            }
        }
    }

    private Tab tab(int id, ImageView imageView, TextView textView, LinearLayout linearLayout,
                    Button button) {
        Tab tab = new Tab();
        tab.setId(id);
        tab.setImageView(imageView);
        tab.setTextView(textView);
        tab.setLinearLayout(linearLayout);
        tab.setButton(button);
        return tab;
    }

    private LinearLayout drawTab(TextView textView, ImageView imageView) {
        TabModel tabModel = TabModel.getModel(mTabIconTextModel);
        LinearLayout linearLayout = new LinearLayout(getContext());
        //横向
        if (mTabOrientation == TabOrientation.HORIZONTAL.getOrientation()) {
            linearLayout.setLayoutParams(new LayoutParams(((mTabButtonWidth == -1 ?
                    DisplayUtils.Companion.getScreenWidthSize() :(int)mTabButtonWidth) /
                    mButtons.size()) - (mTabDividingLineVisible ? mButtons.size() - 1 : 0),
                    LayoutParams.MATCH_PARENT));
        } else {
            //纵向
            linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ((mTabButtonHeight == -1 ? DisplayUtils.Companion.getScreenHeightSize()
                            : (int)mTabButtonHeight) / mButtons.size()) - (mTabDividingLineVisible ?
                            mButtons.size() - 1 : 0)));
        }
        linearLayout.setGravity(getGravityType());
        switch (tabModel) {
            case TEXT_TOP_ICON_BOTTOM:
                linearLayout.setOrientation(VERTICAL);
                LayoutParams layoutParams1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams1.setMargins(0, 0, 0, interval);
                textView.setLayoutParams(layoutParams1);
                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
                linearLayout.addView(textView);
                linearLayout.addView(imageView);
                break;
            case TEXT_BOTTOM_ICON_TOP:
                LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams2.setMargins(0, interval, 0, 0);
                textView.setLayoutParams(layoutParams2);
                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
                linearLayout.setOrientation(VERTICAL);
                linearLayout.addView(imageView);
                linearLayout.addView(textView);
                break;
            case TEXT_LEFT_ICON_RIGHT:
                linearLayout.setOrientation(HORIZONTAL);
                LayoutParams layoutParams3 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams3.setMargins(0, 0, interval, 0);
                textView.setLayoutParams(layoutParams3);
                imageView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                linearLayout.addView(textView);
                linearLayout.addView(imageView);
                break;
            case TEXT_RIGHT_ICON_LEFT:
                linearLayout.setOrientation(HORIZONTAL);
                LayoutParams layoutParams4 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams4.setMargins(interval, 0, 0, 0);
                textView.setLayoutParams(layoutParams4);
                imageView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                linearLayout.addView(imageView);
                linearLayout.addView(textView);
                break;
            default:
                LayoutParams layoutParams5 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams5.setMargins(0, interval, 0, 0);
                textView.setLayoutParams(layoutParams5);
                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f));
                linearLayout.setOrientation(VERTICAL);
                linearLayout.addView(imageView);
                linearLayout.addView(textView);
        }
        return linearLayout;
    }

    private int getGravityType() {
        int gravity = Gravity.CENTER;
        //设置内容对齐模式
        TabGravity tabGravity = TabGravity.getGravity(mTabGravity);
        switch (tabGravity) {
            case TOP:
                gravity = Gravity.TOP;
                break;
            case LEFT:
                gravity = Gravity.START;
                break;
            case RIGHT:
                gravity = Gravity.END;
                break;
            case BOTTOM:
                gravity = Gravity.BOTTOM;
                break;
            case CENTER:
                gravity = Gravity.CENTER;
                break;
            default:
                gravity = Gravity.CENTER;
        }
        return gravity;
    }

    private TextView drawLines() {
        TextView textView = new TextView(getContext());
        //横向
        if (mTabOrientation == TabOrientation.HORIZONTAL.getOrientation()) {
            textView.setLayoutParams(new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            //纵向
            textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        }
        textView.setBackgroundResource(R.color.lines_color);
        return textView;
    }

    private TextView drawTextView() {
        TextView textView = new TextView(getContext());
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setBackgroundResource(R.color.translation);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private ImageView drawImageView() {
        ImageView imageView = new ImageView(getContext());
        return imageView;
    }

    private String getName(Button item) {
        //是否显示text
        if (mTabButtonTextVisible) {
            return item.getName();
        }
        return null;
    }

    /**
     * 选中button，指向下标
     */
    public void setIndex(int id) {
        notifyTabButton(getButtonById(id));
    }

    private Button getButtonById(int id) {
        if (null != tabs && !tabs.isEmpty()) {
            for (Tab tab : tabs) {
                if (id == tab.getId()) {
                    return tab.getButton();
                }
            }
        }
        return null;
    }

    /**
     * 替换某个按钮
     *
     * @param index  按钮位置
     * @param button 按钮数据
     */
    public void replace(int index, Button button) {

    }

    /**
     * 隐藏某个按钮
     *
     * @param index 按钮位置
     */
    public void hideIndex(int index) {

    }

    /**
     * 添加一个按钮(添加到第一个)
     *
     * @param button 按钮数据
     */
    public void addButton(Button button) {

    }

    /**
     * 添加一个按钮到指定位置
     *
     * @param index  需要添加到的位置
     * @param button 按钮数据
     */
    public void addButton(int index, Button button) {

    }

    /**
     * 设置点击事件回调
     *
     * @param tabButtonListener 回调方法
     */
    public void setOnclickListener(TabButtonListener tabButtonListener) {
        this.onclickListener = tabButtonListener;
    }

    TabButtonListener myListener = new TabButtonListener() {
        @Override
        public void onclick(int index, Button button) {
            if (null != onclickListener) {
                onclickListener.onclick(index, button);
            }
            defaultIndex = index;
            notifyTabButton(button);
        }

        @Override
        public void onLongClick(int index, Button button) {
            super.onLongClick(index, button);
            if (null != onclickListener) {
                onclickListener.onLongClick(index, button);
            }
            defaultIndex = index;
            notifyTabButton(button);
        }
    };

    private void notifyTabButton(Button button) {
        for (Tab tab : tabs) {
            Button finalButton = tab.getButton();
            if (finalButton.getId() != button.getId()) {
                ImageView imageView = tab.getImageView();
                if (null != imageView) {
                    imageView.setImageDrawable(finalButton.getDefaultIcon());
                }
                TextView textView = tab.getTextView();
                if (null != textView) {
                    textView.setText(finalButton.getName());
                    textView.setTextSize(DisplayUtils.Companion.px2sp(getContext(), mTabButtonTextSize));
                    textView.setTextColor(Color.parseColor(finalButton.getDefTextColor()));
                }
            } else {
                ImageView imageView = tab.getImageView();
                if (null != imageView) {
                    imageView.setImageDrawable(finalButton.getSelectIcon());
                }
                TextView textView = tab.getTextView();
                if (null != textView) {
                    textView.setText(finalButton.getName());
                    textView.setTextSize(DisplayUtils.Companion.px2sp(getContext(), mTabButtonTextSize));
                    textView.setTextColor(Color.parseColor(finalButton.getSelectTextColor()));
                }
            }
        }
    }

}
