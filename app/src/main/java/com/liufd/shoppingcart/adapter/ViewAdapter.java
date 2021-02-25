package com.liufd.shoppingcart.adapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {
    /**
     * view的显示隐藏
     */
    @BindingAdapter(value = {"isVisible"}, requireAll = false)
    public static void isVisible(View view, final Integer visibility) {
        view.setVisibility(visibility);
    }

}
