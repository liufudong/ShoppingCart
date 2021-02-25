package com.liufd.shoppingcart.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.liufd.shoppingcart.R;


public class BaseBottomView<T extends ViewDataBinding> extends Dialog {

    public T mBinding;
    int layoutId;
    int gravity = Gravity.BOTTOM;
    View mView;
    private int width = -1;
    private int height = -1;
    int style;


    public BaseBottomView(Context context, int layoutId) {
        super(context, R.style.MyDialog);
        this.layoutId = layoutId;
        mView = LayoutInflater.from(context).inflate(layoutId, null, false);
        mBinding = DataBindingUtil.bind(mView);
        getWindow().setWindowAnimations(getStyle());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(mView);//这行一定要写在前面

        setCancelable(true);//点击外部不可dismiss
//        setCanceledOnTouchOutside(isBackCanCelable);
        Window window = this.getWindow();
        window.setGravity(getGravity());
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = getWidth();
        params.height = getHeight();
        window.setAttributes(params);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        if (height == -1) {
            height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        return height;
    }

    public int getWidth() {
        if (width == -1) {
            width = WindowManager.LayoutParams.MATCH_PARENT;
        }
        return width;
    }

    public int getGravity() {
        return gravity;
    }

    public int getStyle() {
        if (style == -1) {
            style = R.style.my_pickerview_dialogAnim;
        }
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}