package com.tson.utils.view.tab;

/**
 * Date 2019/5/17 6:19 PM
 *
 * @author tangxin
 */
public enum TabGravity {
    /**
     * <p/>
     * 1. center<p/>
     * 2. left<p/>
     * 3. right<p/>
     * 4. top<p/>
     * 5. bottom
     */
    CENTER(1, "CENTER"),
    LEFT(2, "LEFT"),
    RIGHT(3, "RIGHT"),
    TOP(4, "TOP"),
    BOTTOM(5, "BOTTOM");

    private int gravity;

    private String gravityName;

    TabGravity(int gravity, String gravityName) {
        this.gravity = gravity;
        this.gravityName = gravityName;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public String getGravityName() {
        return gravityName;
    }

    public void setGravityName(String gravityName) {
        this.gravityName = gravityName;
    }

    public static TabGravity getGravity(int gravity) {
        for (TabGravity enums : TabGravity.values()) {
            if (enums.getGravity() == gravity) {
                return enums;
            }
        }
        return null;
    }
}
