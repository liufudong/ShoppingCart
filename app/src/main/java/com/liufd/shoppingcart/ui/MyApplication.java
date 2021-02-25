package com.liufd.shoppingcart.ui;

import android.app.Application;
import android.content.Context;

import com.liufd.shoppingcart.util.ToastUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this);
    }
}
