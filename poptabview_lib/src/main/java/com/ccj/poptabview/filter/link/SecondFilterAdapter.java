package com.ccj.poptabview.filter.link;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.ccj.poptabview.FilterConfig;
import com.ccj.poptabview.R;
import com.ccj.poptabview.base.BaseFilterTabBean;
import com.ccj.poptabview.listener.OnHolderClickedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 右侧二级筛选adapter
 *
 * @author ccj on 17/3/23.
 */
public class SecondFilterAdapter extends RecyclerView.Adapter implements OnHolderClickedListener {

    private int singleOrMultiply;
    private OnMSecondItemClickListener listener;
    private List<BaseFilterTabBean> mData;

    private ArrayList<Integer> mSelectedList = new ArrayList<>();

    private int firstPosition; //一级菜单 选中

    public SecondFilterAdapter(OnMSecondItemClickListener listener, int singleOrMultiply) {
        this.listener = listener;
        this.singleOrMultiply = singleOrMultiply;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_second_filter, parent, false);
        return new FilterViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mData != null && position < mData.size()) {
            FilterViewHolder viewHolder = (FilterViewHolder) holder;
            viewHolder.tv_filter.setText(mData.get(position).getTab_name());
            if (mSelectedList.contains(position)) {
                viewHolder.tv_filter.setChecked(true);
            } else {
                viewHolder.tv_filter.setChecked(false);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    public void setData(int firstPosition, List<BaseFilterTabBean> data) {

        this.firstPosition = firstPosition;
        mData = new ArrayList<>();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mData != null) {
            mData.clear();
            mSelectedList.clear();
            notifyDataSetChanged();
        }
    }


    public void setCheckedItem(List<Integer> secondFilterBean) {
        mSelectedList.clear();
        if (secondFilterBean != null && !secondFilterBean.isEmpty()) {
            mSelectedList.addAll(secondFilterBean);
        }

        notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int pos) {
        if (pos >= 0 && pos < mData.size()) {
            Integer position= Integer.valueOf(pos);
            if (mSelectedList.contains(position)) {
                mSelectedList.remove(position);
            } else if (singleOrMultiply == FilterConfig.FILTER_TYPE_SINGLE) {
                mSelectedList.clear();
                mSelectedList.add(position);
            } else {
                mSelectedList.add(position);

            }

            listener.onSecondItemClick(firstPosition, mData.get(position), mSelectedList);
        }
        //dismiss();
        notifyDataSetChanged();
    }

    public static class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckedTextView tv_filter;
        OnHolderClickedListener mListener;

        public FilterViewHolder(View itemView, OnHolderClickedListener listener) {
            super(itemView);
            tv_filter = (CheckedTextView) itemView.findViewById(R.id.tv_filter);
            tv_filter.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v instanceof CheckedTextView) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }


    public interface OnMSecondItemClickListener {
        void onSecondItemClick(int secondPos, BaseFilterTabBean filterTabBean, ArrayList<Integer> secondFilterBean);
    }

}
