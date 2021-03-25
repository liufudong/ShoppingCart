package com.liufd.shoppingcart.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.liufd.shoppingcart.R;
import com.liufd.shoppingcart.bean.ShoppingCartBean;
import com.liufd.shoppingcart.databinding.ItemMultipleChoiceBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MultipleChoiceAdapter extends RecyclerView.Adapter {

    private List<ShoppingCartBean.ItemsBean> mList;






    public List<ShoppingCartBean.ItemsBean> getList() {
        return mList;
    }

    public MultipleChoiceAdapter(List<ShoppingCartBean.ItemsBean> mList) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        this.mList = mList;
    }

    public MultipleChoiceAdapter(ShoppingCartBean.ItemsBean[] mList) {
        this.mList = new ArrayList<>();
        this.mList.addAll(Arrays.asList(mList));
    }


    public void setSelectAll(boolean mCheck) {
        ShoppingCartBean.ItemsBean item = null;
        for (int i = 0; i < mList.size(); i++) {
            item = mList.get(i);
            item.setCheck(mCheck);
            mList.set(i, item);
        }
        notifyDataSetChangers();
    }

    public void setCount(int position, int count) {
        if (mList.size() > position) {
            ShoppingCartBean.ItemsBean item = mList.get(position);
            item.setCount(count);
            mList.set(position, item);
            notifyItemChangers(position);
        }
    }

    public void delete() {
        List<ShoppingCartBean.ItemsBean> zList = new ArrayList<>();
        for (ShoppingCartBean.ItemsBean itemsBean : mList) {
            if (!itemsBean.isCheck()) {
                zList.add(itemsBean);
            }
        }
        mList.clear();
        mList.addAll(zList);
        notifyDataSetChangers();
    }

    public void notifyDataSetChangers() {
        notifyDataSetChanged();
        uploadUI();
        isCheckAll();
    }

    public void notifyItemChangers(int position) {
        notifyItemChanged(position);
        uploadUI();
        isCheckAll();
    }

    private void isCheckAll() {
        int check = 0;
        for (ShoppingCartBean.ItemsBean itemsBean : mList) {
            if (itemsBean.isCheck()) {
                check += 1;
            }
        }
        checkAll(check == mList.size());
    }

    public void setSelection(ShoppingCartBean.ItemsBean mItemsBean, int position) {
        mItemsBean.setCheck(!mItemsBean.isCheck());
        mList.set(position, mItemsBean);
        isCheckAll();
        notifyDataSetChangers();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMultipleChoiceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_multiple_choice, parent, false);
        return new MultipleChoiceViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MultipleChoiceViewHolder) {
            final MultipleChoiceViewHolder viewHolder = (MultipleChoiceViewHolder) holder;
            ItemMultipleChoiceBinding binding = DataBindingUtil.getBinding(holder.itemView);
            if (binding != null) {
                this.onBindItem(binding, this.mList.get(position), position);
                binding.checkbox.setBackground
                        (ContextCompat.getDrawable(binding.checkbox.getContext(), R.mipmap.round_check_selected));
                if (mList.get(position).isCheck()) {
                    binding.checkbox.setChecked(true);
                    binding.checkbox.setBackground
                            (ContextCompat.getDrawable(binding.checkbox.getContext(), R.mipmap.round_check_active));
                    viewHolder.itemView.setSelected(true);
                } else {
                    binding.checkbox.setBackground(ContextCompat.getDrawable(binding.checkbox.getContext(), R.mipmap.round_check_selected));
                    binding.checkbox.setChecked(false);
                    viewHolder.itemView.setSelected(false);
                }
                binding.mRelativeLayout.setOnClickListener(v -> setSelection(mList.get(position), position));
            }
        }
    }

    protected abstract void onBindItem(ItemMultipleChoiceBinding binding, ShoppingCartBean.ItemsBean item, int position);

    protected abstract void checkAll(Boolean flag);//是否全选

    protected abstract void uploadUI();//更新UI

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MultipleChoiceViewHolder extends RecyclerView.ViewHolder {

        public MultipleChoiceViewHolder(View itemView) {
            super(itemView);
        }
    }

}