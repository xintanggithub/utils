package com.tson.utils.view.tab.entity;

import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;

/**
 * Date 2019/5/17 6:59 PM
 *
 * @author tangxin
 */
public class Button {

    @IdRes
    private int id;
    private Drawable defaultIcon;
    private Drawable selectIcon;
    private String defTextColor;
    private String selectTextColor;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getDefaultIcon() {
        return defaultIcon;
    }

    public void setDefaultIcon(Drawable defaultIcon) {
        this.defaultIcon = defaultIcon;
    }

    public Drawable getSelectIcon() {
        return selectIcon;
    }

    public void setSelectIcon(Drawable selectIcon) {
        this.selectIcon = selectIcon;
    }

    public String getDefTextColor() {
        return defTextColor;
    }

    public void setDefTextColor(String defTextColor) {
        this.defTextColor = defTextColor;
    }

    public String getSelectTextColor() {
        return selectTextColor;
    }

    public void setSelectTextColor(String selectTextColor) {
        this.selectTextColor = selectTextColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
