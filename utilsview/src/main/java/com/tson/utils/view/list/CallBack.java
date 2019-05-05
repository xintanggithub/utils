package com.tson.utils.view.list;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created tangxin
 * Time 2019/5/5 6:21 PM
 */
public interface CallBack<T, E extends ViewDataBinding> {

    E dataBinding(@NonNull ViewGroup parent);

    void footerHolder(BaseAdapter.FooterViewHolder holder, List<T> mData, int loadState);

}
