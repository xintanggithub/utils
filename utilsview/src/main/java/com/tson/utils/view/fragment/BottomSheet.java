package com.tson.utils.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tson.utils.view.DisplayUtils;
import com.tson.utils.view.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author tangxin
 */
public class BottomSheet<T> extends BottomSheetDialogFragment {

    private static final String TAG = "BottomSheet";

    private static final int ITEM_SIZE = 50;
    private static final int LINES_HEIGHT = 9;

    private int MIX_RELEVANT_SIZE = 1;
    private List<T> mData;
    private ChooseItemListener<T> result;
    private BindTextListener bindTextListener;
    private String mTitle;
    private RelativeLayout rlBgLayout;
    private RecyclerView mRecyclerView;
    private boolean isEmptyTitle = true;//是否有title
    private ListAdapter listAdapter;
    private int mSelectIndex = -1;

    public int getSelectIndex() {
        return mSelectIndex;
    }

    public void setSelectIndex(int mSelectIndex) {
        this.mSelectIndex = mSelectIndex;
    }

    public BottomSheet() {
        listAdapter = new ListAdapter();
    }

    public interface ChooseItemListener<T> {
        void onResult(int position, T result);

    }

    public interface BindTextListener {
        void bindText(TextView tv, int position);
    }

    public BindTextListener getBindTextListener() {
        if (null == bindTextListener) {
            bindTextListener = (tv, position) -> {
            };
        }
        return bindTextListener;
    }

    public void setData(T[] data, String title, ChooseItemListener mResult, BindTextListener bindTextListener) {
        setData(Arrays.asList(data), title, mResult, bindTextListener);
    }

    public void setData(List<T> data, String title, ChooseItemListener mResult, BindTextListener bindTextListener) {
        this.bindTextListener = bindTextListener;
        mData = data;
        result = mResult;
        mTitle = title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        AppCompatTextView mTitleView = view.findViewById(R.id.tv_title);
        isEmptyTitle = isNotEmpty(mTitle);
        if (isEmptyTitle) {
            mTitleView.setText(mTitle);
        } else {
            mTitleView.setVisibility(View.GONE);
        }
        rlBgLayout = view.findViewById(R.id.bg_layout);
        setHeight();
        initListAdapter();
    }

    public void addMore(List<T> data) {
        listAdapter.notifyDataSetChanged();
    }

    private void setHeight() {
        ViewGroup.LayoutParams lp = rlBgLayout.getLayoutParams();
        rlBgLayout.getLayoutParams();
        if (DisplayUtils.Companion.getScreenHeightSize() <= (DisplayUtils.Companion.dp2px(getContext(), ITEM_SIZE * mData.size() + ITEM_SIZE * (isEmptyTitle ? 3 : 2)))) {
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            lp.height = DisplayUtils.Companion.dp2px(getContext(), ITEM_SIZE * mData.size() + ITEM_SIZE * (isEmptyTitle ? 2 : 1) + LINES_HEIGHT);
        }
        rlBgLayout.setLayoutParams(lp);
    }


    private void initListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(listAdapter);
        setFooterView(mRecyclerView);
    }


    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(getActivity()).inflate(R.layout.item_cancel, view, false);
        listAdapter.setFooterView(footer);
        footer.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private final class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
        public static final int TYPE_FOOTER = 1;  //带有Footer
        public static final int TYPE_NORMAL = 2;
        private View mFooterView;

        public void setFooterView(View footerView) {
            mFooterView = footerView;
            notifyItemInserted(getItemCount() - MIX_RELEVANT_SIZE);
        }

        @Override
        public int getItemViewType(int position) {
            if (position == getItemCount() - MIX_RELEVANT_SIZE) {
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mFooterView != null && viewType == TYPE_FOOTER) {
                return new ViewHolder(mFooterView);
            }
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottom_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (getItemViewType(position) == TYPE_NORMAL) {
                final T itemData = mData.get(position);
                if (itemData instanceof String) {
                    holder.tvContent.setText((String) itemData);
                } else {
                    getBindTextListener().bindText(holder.tvContent, position);
                }
                if (position == mSelectIndex) {
                    holder.tvContent.setTextColor(getContext().getResources().getColor(R.color.select_color));
                } else {
                    holder.tvContent.setTextColor(getContext().getResources().getColor(R.color.content_color));
                }
                holder.tvContent.setOnClickListener(v -> {
                    if (null != result) {
                        result.onResult(position, itemData);
                    }
                    mSelectIndex = position;
                    dismiss();
                });
            }
        }

        @Override
        public int getItemCount() {
            if (mFooterView == null) {
                return mData.size();
            } else {
                return mData.size() + MIX_RELEVANT_SIZE;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView tvContent;

            public ViewHolder(View itemView) {
                super(itemView);
                if (itemView != mFooterView) {
                    tvContent = itemView.findViewById(R.id.tv_content);
                }
            }
        }
    }

    /**
     * 判String断是否为空
     */
    public boolean isNotEmpty(String str) {
        if (null == str || "".equals(str) || TextUtils.isEmpty(str) || "null".equals(str)) {
            return false;
        } else {
            return true;
        }
    }

}
