package com.liufd.shoppingcart.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.liufd.shoppingcart.BR;
import com.liufd.shoppingcart.R;
import com.liufd.shoppingcart.adapter.MultipleChoiceAdapter;
import com.liufd.shoppingcart.bean.ShoppingCartBean;
import com.liufd.shoppingcart.databinding.ActivityMainBinding;
import com.liufd.shoppingcart.databinding.ItemMultipleChoiceBinding;
import com.liufd.shoppingcart.dialog.CountDialogCenter;
import com.liufd.shoppingcart.model.ShoppingCartModel;
import com.liufd.shoppingcart.util.KeyboardAction;
import com.liufd.shoppingcart.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements KeyboardAction {
    ShoppingCartModel mViewModel;
    ActivityMainBinding mBinding;

    private MultipleChoiceAdapter mAdapter;
    List<ShoppingCartBean.ItemsBean> dates = new ArrayList<>();
    Boolean checkAllBoolean = false;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViewModel();
        initDates();
        initListener();
    }

    public void initViewModel() {
        mViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ShoppingCartModel.class);
        mBinding.setVariable(BR.mModel, mViewModel);
        mBinding.setLifecycleOwner(this);
    }

    private void initDates() {
        mBinding.mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mAdapter = new MultipleChoiceAdapter(this.dates) {
            @Override
            protected void onBindItem(ItemMultipleChoiceBinding binding, ShoppingCartBean.ItemsBean item, int position) {
                onBindItems(binding, item, position);
            }

            @Override
            protected void checkAll(Boolean flag) {
                setCheckAllBoolean(flag);
            }

            @Override
            protected void uploadUI() {
                mViewModel.totalNumber(mAdapter.getList());
            }
        };
        mBinding.mRecyclerView.setAdapter(mAdapter);
        mViewModel.getDate();
        mViewModel.dates.observe(this, date -> {
            this.dates.clear();
            this.dates.addAll(date);
            mAdapter.notifyDataSetChangers();
        });

    }

    private void onBindItems(ItemMultipleChoiceBinding binding, ShoppingCartBean.ItemsBean item, int position) {
        binding.titleTextView.setText(item.getName());
        binding.priceTextView.setText("￥" + item.getPrice());
        binding.numberTextView.setText(String.valueOf(item.getCount()));
        //-
        binding.cutImageView.setOnClickListener(v -> {
            mViewModel.upload(binding.numberTextView.getText().toString(), binding.numberTextView, item, mAdapter, dates, position, true);
        });
        //+
        binding.plusImageView.setOnClickListener(v -> {
            mViewModel.upload(binding.numberTextView.getText().toString(), binding.numberTextView, item, mAdapter, dates, position, false);
        });
        //数量
        binding.numberTextView.setOnClickListener(v -> {
            showNumberDialog(item, position);
        });

    }

    private void showNumberDialog(ShoppingCartBean.ItemsBean item, int position) {
        showKeyboard(MainActivity.this.getCurrentFocus());
        new CountDialogCenter(MainActivity.this).setContent(String.valueOf(item.getCount())).setOnClickListener(count -> {
            mAdapter.setCount(position, count);
        }).showView();

    }

    private void initListener() {
        //全选
        mViewModel.checkAllLiveData.observe(this, flag -> {
            checkAllBoolean = (!checkAllBoolean);
            setCompoundDrawables();
            mAdapter.setSelectAll(checkAllBoolean);
        });
        //删除 或 去结算
        mViewModel.deleteLiveData.observe(this, flag -> {
            if (getResources().getString(R.string.delete).equals(mViewModel.toSettleAccountsString.getValue())) {
                mAdapter.delete();
            } else {//尚未完成
                mAdapter.getList();//数据在这
                ToastUtil.show("去结算");
            }
        });
    }

    private void setCompoundDrawables() {
        if (checkAllBoolean) {
            drawable = ContextCompat.getDrawable(mBinding.mAllTextView.getContext(), R.mipmap.round_check_active);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        } else {
            drawable = ContextCompat.getDrawable(mBinding.mAllTextView.getContext(), R.mipmap.round_check_selected);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        mBinding.mAllTextView.setCompoundDrawables(drawable,
                null, null, null);
    }

    public void setCheckAllBoolean(Boolean checkAllBoolean) {
        if (this.checkAllBoolean != checkAllBoolean) {
            this.checkAllBoolean = checkAllBoolean;
            setCompoundDrawables();
        }
    }
}