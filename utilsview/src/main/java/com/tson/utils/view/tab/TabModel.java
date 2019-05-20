package com.tson.utils.view.tab;

/**
 * Date 2019/5/17 6:08 PM
 * <p>
 * 1： 文字在上 icon在下
 * <p>
 * TEXT_TOP_ICON_BOTTOM
 * <p>
 * 2： 文字在左 icon在右
 * <p>
 * TEXT_LEFT_ICON_RIGHT
 * <p>
 * 3： 文字在下 icon在上
 * <p>
 * TEXT_BOTTOM_ICON_TOP
 * <p>
 * 4： 文字在右 icon在左
 * <p>
 * TEXT_RIGHT_ICON_LEFT
 *
 * @author tangxin
 */
public enum TabModel {
    /**
     * 文字在上 icon在下
     */
    TEXT_TOP_ICON_BOTTOM(1, "TEXT_TOP_ICON_BOTTOM"),
    /**
     * 文字在左 icon在右
     */
    TEXT_LEFT_ICON_RIGHT(2, "TEXT_LEFT_ICON_RIGHT"),
    /**
     * 文字在下 icon在上
     */
    TEXT_BOTTOM_ICON_TOP(3, "TEXT_BOTTOM_ICON_TOP"),
    /**
     * 文字在右 icon在左
     */
    TEXT_RIGHT_ICON_LEFT(4, "TEXT_RIGHT_ICON_LEFT");

    private int type;
    private String typeName;

    TabModel(int type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public static TabModel getModel(int type) {
        for (TabModel enums : TabModel.values()) {
            if (enums.getType() == type) {
                return enums;
            }
        }
        return null;
    }
}
