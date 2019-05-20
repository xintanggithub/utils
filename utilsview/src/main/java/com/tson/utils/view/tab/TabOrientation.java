package com.tson.utils.view.tab;

/**
 * Date 2019/5/17 6:30 PM
 *
 * @author tangxin
 */
public enum TabOrientation {
    /**
     * tab 模式:<p/>1 horizontal 横向<p/> 2 vertical 纵向
     */
    HORIZONTAL(1, "HORIZONTAL"),
    VERTICAL(2, "VERTICAL");

    private int orientation;
    private String orientationName;

    TabOrientation(int orientation, String orientationName) {
        this.orientation = orientation;
        this.orientationName = orientationName;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public String getOrientationName() {
        return orientationName;
    }

    public void setOrientationName(String orientationName) {
        this.orientationName = orientationName;
    }
}
