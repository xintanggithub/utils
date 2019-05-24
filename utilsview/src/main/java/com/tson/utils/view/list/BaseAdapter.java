package com.tson.utils.view.list;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Time 2019/3/26 2:52 PM
 *
 * @author tangxin
 */
public abstract class BaseAdapter<T, E extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mData;
    private int itemLayoutId;
    protected Context context;
    protected OnclickListener onclickListener;

    /**
     * 普通布局
     */
    private final int TYPE_ITEM = 1;
    /**
     * 脚布局
     */
    private final int TYPE_FOOTER = 2;
    /**
     * 当前加载状态，默认为加载完成
     */
    private int loadState = 2;
    private CallBack mCallBack;
    /**
     * 正在加载
     */
    public static final int LOADING = 1;
    /**
     * 加载完成
     */
    public static final int LOADING_COMPLETE = 2;
    /**
     * 加载到底
     */
    public static final int LOADING_END = 3;

    private boolean showFooter = false;

    OnclickListener getOnclickListener() {
        if (null == onclickListener) {
            onclickListener = view -> {
            };
        }
        return onclickListener;
    }

    public void setOnclickListener(OnclickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

    public BaseAdapter(List<T> mData, int itemLayoutId) {
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
    }

    public BaseAdapter(List<T> mData, int itemLayoutId, CallBack callBack) {
        this.mData = mData;
        this.itemLayoutId = itemLayoutId;
        setShowFooter();
        mCallBack = callBack;
        setGridLayoutManager();
    }

    public void setData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void replace(int index, T data) {
        this.mData.set(index, data);
        notifyItemChanged(index);
    }

    public T getData(int position) {
        if (mData != null) {
            return mData.get(position);
        } else {
            return null;
        }
    }

    public List<T> getAllData() {
        return mData;
    }

    public void addAll(List<T> mData) {
        boolean isEmpty = mData.isEmpty();
        this.mData.addAll(mData);
        //防止从0到有，并且为第一页加载数据时，自动滚动到最底部
        if (!isEmpty) {
            loadComplete();
            notifyItemRangeInserted(this.mData.size() - mData.size(), this.mData.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public void add(T data) {
        boolean isEmpty = mData.isEmpty();
        this.mData.add(data);
        if (!isEmpty) {
            loadComplete();
            notifyItemInserted(this.mData.size() - 1);
        } else {
            this.mData.add(data);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (mData != null) {
            this.mData.clear();
            notifyDataSetChanged();
//            notifyItemRangeInserted(this.mData.size(), this.mData.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            E itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), itemLayoutId,
                    parent, false);
            return new BaseViewHolder<>(itemBinding);
        } else {
            if (null == mCallBack) {
                Log.e("BaseAdapter", "设置了显示footer,但是缺少footer的必要参数");
            }
            return new FooterViewHolder<>(mCallBack.dataBinding(parent));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof BaseViewHolder) {
            BaseViewHolder baseViewHolder = (BaseViewHolder) viewHolder;
            this.onBindViewHolders(i, baseViewHolder);
        } else {
            FooterViewHolder baseViewHolder = (FooterViewHolder) viewHolder;
            mCallBack.footerHolder(baseViewHolder, mData, loadState);
        }
    }

    public abstract void onBindViewHolders(int position, @NonNull BaseViewHolder holder);

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为FooterView
        if (showFooter) {
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        } else {
            return TYPE_ITEM;
        }
    }

    public static class BaseViewHolder<E extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public E itemDataBinding;

        BaseViewHolder(E itemDataBinding) {
            super(itemDataBinding.getRoot());
            this.itemDataBinding = itemDataBinding;
        }
    }

    public static class FooterViewHolder<E extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public E itemDataBinding;

        FooterViewHolder(E itemDataBinding) {
            super(itemDataBinding.getRoot());
            this.itemDataBinding = itemDataBinding;
        }
    }

    public void loadMore() {
        if (loadState != LOADING) {
            loadState = LOADING;
            setShowFooter();
            notifyItemRangeChanged(mData.size(), mData.size() + 1);
        }
    }

    public void loadComplete() {
        loadState = LOADING_COMPLETE;
        if (showFooter) {
            if (!mData.isEmpty()) {
                notifyItemRemoved(mData.size());
            } else {
                notifyDataSetChanged();
            }
        }
        setHideFooter();
    }

    public void noneMore() {
        loadState = LOADING_END;
        if (showFooter) {
            if (!mData.isEmpty()) {
                notifyItemRemoved(mData.size());
            } else {
                notifyDataSetChanged();
            }
        }
        setHideFooter();
    }

    private void setShowFooter() {
        this.showFooter = true;
    }

    private void setHideFooter() {
        this.showFooter = false;
    }

    @Override
    public int getItemCount() {
        if (null == mData) {
            return 0;
        }
        return showFooter ? (mData.size() + 1) : mData.size();
    }

    public interface OnclickListener {
        /**
         * 点击事件 回调方法
         *
         * @param view 点击的控件对象
         */
        void onclick(View view);
    }

    /**
     * 将RecyclerView的网格布局中的某个item设置为独占一行、只占一列，只占两列、等等
     */
    private void setGridLayoutManager() {
        if (null != mCallBack) {
            RecyclerView.LayoutManager layout = mCallBack.layoutManager();
            if (layout instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int type = getItemViewType(position);
                        if (type == TYPE_ITEM) {
                            //只占一行中的一列
                            return 1;
                        } else {
                            //独占一行
                            return gridLayoutManager.getSpanCount();
                        }
                    }
                });
            }
        }
    }

}
