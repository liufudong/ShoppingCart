package com.liufd.shoppingcart.model;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.liufd.shoppingcart.adapter.MultipleChoiceAdapter;
import com.liufd.shoppingcart.R;
import com.liufd.shoppingcart.bean.ShoppingCartBean;
import com.liufd.shoppingcart.util.Logger;
import com.liufd.shoppingcart.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartModel extends AndroidViewModel {
    public final String TAG = ShoppingCartModel.class.getName();


    public MutableLiveData<Boolean> checkAllLiveData = new MutableLiveData<>();

    public MutableLiveData<String> editString = new MutableLiveData<String>();
    public MutableLiveData<Integer> editColor = new MutableLiveData<>();
    public MutableLiveData<Integer> totalVisibility = new MutableLiveData<>();
    public MutableLiveData<Integer> number = new MutableLiveData<>();
    public MutableLiveData<Double> price = new MutableLiveData<Double>();
    public MutableLiveData<String> toSettleAccountsString = new MutableLiveData<String>();
    public MutableLiveData<String> totalPriceString = new MutableLiveData<String>();

    public ShoppingCartModel(@NonNull Application application) {
        super(application);
        init();
    }

    public void init() {
        editString.setValue(getApplication().getResources().getString(R.string.edit));
        editColor.setValue(R.color.textColor);
        totalVisibility.setValue(View.VISIBLE);
        number.setValue(0);
        price.setValue(0.00);
        toSettleAccountsString.setValue((String.format(getApplication()
                .getResources().getString(R.string.toSettleAccounts), number.getValue().toString())));
        totalPriceString.setValue((String.format(getApplication()
                .getResources().getString(R.string.totalPrice), price.getValue().toString())));
    }

    //全选
    public MutableLiveData<List<ShoppingCartBean.ItemsBean>> dates = new MutableLiveData<List<ShoppingCartBean.ItemsBean>>();
    public void checkAllBindingCommand(){
        checkAllLiveData.setValue(true);
    }
    //编辑
    public void editBindingCommand(){
        setEditTextViewText();
    }
    //删除
    public MutableLiveData<Boolean> deleteLiveData = new MutableLiveData<>();
    public void deleteBindingCommand(){
        deleteLiveData.setValue(true);
    }

    //模拟数据
    public void getDate() {
        List<ShoppingCartBean.ItemsBean> list = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            list.add(new ShoppingCartBean.ItemsBean("进口草莓A级" + i, (float) i, 1, true));
        }
        dates.setValue(list);
    }

    private void setEditTextViewText() {
        if (editString.getValue() != null) {
            boolean equals = getApplication().getResources().getString(R.string.edit).equals(editString.getValue());
            editString.setValue(equals ? getApplication().getResources().getString(R.string.done)
                    : getApplication().getResources().getString(R.string.edit));
            editColor.setValue(equals ? ContextCompat.getColor(getApplication(), R.color.colorMain)
                    : ContextCompat.getColor(getApplication(), R.color.textColor));
            totalVisibility.setValue(equals ? View.INVISIBLE : View.VISIBLE);
            editColor.setValue(equals ? ContextCompat.getColor(getApplication(), R.color.colorMain)
                    : ContextCompat.getColor(getApplication(), R.color.textColor));
            toSettleAccountsString.setValue(equals ?
                    getApplication().getResources().getString(R.string.delete) :
                    (String.format(getApplication()
                            .getResources().getString(R.string.toSettleAccounts), number.getValue().toString()))
            );
        }
    }

    public void upload(String text, TextView mTextView, ShoppingCartBean.ItemsBean item,
                       MultipleChoiceAdapter multipleChoiceAdapter,
                       List<ShoppingCartBean.ItemsBean> dates,
                       int position, boolean cut) {
        try {
            int number = Integer.parseInt(text);
            if (cut) {
                if (number > 1) {
                    mTextView.setText(String.valueOf(number - 1));
                    item.setCount(Integer.parseInt(mTextView.getText().toString()));
                    multipleChoiceAdapter.notifyItemChangers(position);
                    dates.set(position, item);
                } else {
                    mTextView.setText("1");
                    ToastUtil.show("该宝贝不能减少了哟");
                }
            } else {
                mTextView.setText(String.valueOf(number + 1));
                item.setCount(Integer.parseInt(mTextView.getText().toString()));
                multipleChoiceAdapter.notifyItemChangers(position);
                dates.set(position, item);
            }

        } catch (Exception e) {
            Logger.e(TAG, TAG + "cut()" + e.getMessage());
        }
    }

    public void totalNumber(List<ShoppingCartBean.ItemsBean> list) {
        if (list != null) {
            int index = 0;
            double total = 0;
            for (ShoppingCartBean.ItemsBean date : list) {
                if (date.isCheck()) {//是否选中
                    index += date.getCount();
                    total += date.getPrice() * date.getCount();
                }
            }
            number.setValue(index);
            price.setValue(total);
            totalPriceString.setValue((String.format(getApplication()
                    .getResources().getString(R.string.totalPrice), price.getValue().toString())));

            boolean equals = getApplication().getResources().getString(R.string.edit).equals(editString.getValue());
            if (equals) {
                toSettleAccountsString.setValue(String.format(getApplication()
                        .getResources().getString(R.string.toSettleAccounts), number.getValue().toString()));
            }
        }
    }

}
