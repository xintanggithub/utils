package com.tson.utils.view.tab.callback;

import com.tson.utils.view.tab.entity.Button;

/**
 * Date 2019/5/17 7:12 PM
 *
 * @author tangxin
 */
public interface OnclickListener {

    /**
     * 点击事件回调
     *
     * @param index  点击的button位置
     * @param button button数据
     */
    void onclick(int index, Button button);

    /**
     * 点击事件回调
     *
     * @param index  点击的button位置
     * @param button button数据
     */
    void onLongClick(int index, Button button);

}
