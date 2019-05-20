package com.tson.utils.view.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;

import com.tson.utils.view.R;

/**
 * Created by tangxin on 2017/4/20.
 */
public class DrawableTextView extends android.support.v7.widget.AppCompatTextView {

    private static final int WIDTH_TYPE = 1;

    private static final int HEIGHT_TYPE = 2;

    public DrawableTextView(Context context) {
        this(context, null);
        onDrawable(context, null);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        onDrawable(context, attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onDrawable(context, attrs);
    }

    @SuppressLint("RestrictedApi")
    private void onDrawable(Context context, AttributeSet attrs) {
        if (null == attrs) {
            new Throwable("drawableTextView attrs is null");
            return;
        }
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
        AppCompatDrawableManager drawableManager = AppCompatDrawableManager.get();

        int drawableWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableWidth, -1);
        int drawableHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableHeight, -1);

        Drawable drawableLeft = null;
        int drawableLeftWidth = -1;
        int drawableLeftHeight = -1;
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableLeft, 0)) {
            drawableLeft = drawableManager.getDrawable(getContext(), ta.getResourceId(R.styleable.DrawableTextView_drawableLeft, 0));
            drawableLeftWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableLeftWidth, -1);
            drawableLeftHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableLeftHeight, -1);
            drawableLeftWidth = getNumValue(drawableLeftWidth, drawableLeft, WIDTH_TYPE);
            drawableLeftHeight = getNumValue(drawableLeftHeight, drawableLeft, HEIGHT_TYPE);
        }

        Drawable drawableTop = null;
        int drawableTopWidth = -1;
        int drawableTopHeight = -1;
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableTop, 0)) {
            drawableTop = drawableManager.getDrawable(getContext(), ta.getResourceId(R.styleable.DrawableTextView_drawableTop, 0));
            drawableTopWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableTopWidth, -1);
            drawableTopHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableTopHeight, -1);
            drawableTopWidth = getNumValue(drawableTopWidth, drawableTop, WIDTH_TYPE);
            drawableTopHeight = getNumValue(drawableTopHeight, drawableTop, HEIGHT_TYPE);
        }

        Drawable drawableRight = null;
        int drawableRightWidth = -1;
        int drawableRightHeight = -1;
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableRight, 0)) {
            drawableRight = drawableManager.getDrawable(getContext(), ta.getResourceId(R.styleable.DrawableTextView_drawableRight, 0));
            drawableRightWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableRightWidth, -1);
            drawableRightHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableRightHeight, -1);
            drawableRightWidth = getNumValue(drawableRightWidth, drawableRight, WIDTH_TYPE);
            drawableRightHeight = getNumValue(drawableRightHeight, drawableRight, HEIGHT_TYPE);
        }

        Drawable drawableBottom = null;
        int drawableBottomWidth = -1;
        int drawableBottomHeight = -1;
        if (0 != ta.getResourceId(R.styleable.DrawableTextView_drawableBottom, 0)) {
            drawableBottom = drawableManager.getDrawable(getContext(), ta.getResourceId(R.styleable.DrawableTextView_drawableBottom, 0));
            drawableBottomWidth = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableBottomWidth, -1);
            drawableBottomHeight = ta.getDimensionPixelSize(R.styleable.DrawableTextView_drawableBottomHeight, -1);
            drawableBottomWidth = getNumValue(drawableBottomWidth, drawableBottom, WIDTH_TYPE);
            drawableBottomHeight = getNumValue(drawableBottomHeight, drawableBottom, HEIGHT_TYPE);
        }

        if (drawableLeft != null && drawableLeftWidth != -1 && drawableLeftHeight != -1) {
            drawableLeft.setBounds(0, 0, drawableLeftWidth, drawableLeftHeight);
        }

        if (drawableTop != null && drawableTopWidth != -1 && drawableTopHeight != -1) {
            drawableTop.setBounds(0, 0, drawableTopWidth, drawableTopHeight);
        }

        if (drawableRight != null && drawableRightWidth != -1 && drawableRightHeight != -1) {
            drawableRight.setBounds(0, 0, drawableRightWidth, drawableRightHeight);
        }

        if (drawableBottom != null && drawableBottomWidth != -1 && drawableBottomHeight != -1) {
            drawableBottom.setBounds(0, 0, drawableBottomWidth, drawableBottomHeight);
        }

        Drawable[] drawables = getCompoundDrawables();
        Drawable textDrawable = null;
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                textDrawable = drawable;
            }
        }

        if (textDrawable != null && drawableWidth != -1 && drawableHeight != -1) {
            textDrawable.setBounds(0, 0, drawableWidth, drawableHeight);
        }
        setCompoundDrawables(drawableLeft != null ? drawableLeft : drawables[0], drawableTop != null ? drawableTop : drawables[1], drawableRight != null ? drawableRight : drawables[2], drawableBottom != null ? drawableBottom : drawables[3]);
        ta.recycle();
    }

    private int getNumValue(int num, Drawable drawable, int mType) {
        return num == -1 ? (mType == WIDTH_TYPE ? drawable.getIntrinsicWidth() : drawable.getIntrinsicHeight()) : num;
    }

}
