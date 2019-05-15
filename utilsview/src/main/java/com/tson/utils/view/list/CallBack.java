package com.tson.utils.view.list;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created tangxin
 * Time 2019/5/5 6:21 PM
 */
public interface CallBack<T, E extends ViewDataBinding> {

    /**
     * 获取dataBinding
     *
     * @param parent viewGroup
     * @return dataBinding
     */
    E dataBinding(@NonNull ViewGroup parent);

    /**
     * 返回foot view
     *
     * @param holder    holder
     * @param mData     数据
     * @param loadState 状态
     */
    void footerHolder(BaseAdapter.FooterViewHolder holder, List<T> mData, int loadState);

    /**
     * 获取layoutManager
     *
     * @return layoutManager
     */
    RecyclerView.LayoutManager layoutManager();

}
