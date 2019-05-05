package com.tson.utils;

import android.support.annotation.NonNull;

import com.tson.utils.databinding.ItemLayoutBinding;
import com.tson.utils.view.list.BaseAdapter;
import com.tson.utils.view.list.CallBack;

import java.util.List;

/**
 * Created tangxin
 * Time 2019/5/5 6:58 PM
 */
public class MyAdapter extends BaseAdapter<String, ItemLayoutBinding> {

    MyAdapter(List<String> mData, int itemLayoutId, CallBack callBack) {
        super(mData, itemLayoutId, callBack);
    }

    @Override
    public void onBindViewHolders(int position, @NonNull BaseViewHolder holder) {
        ItemLayoutBinding itemLayoutBinding = (ItemLayoutBinding) holder.itemDataBinding;
        itemLayoutBinding.setVm(getData(position));
    }

}
