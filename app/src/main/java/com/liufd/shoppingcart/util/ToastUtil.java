package com.liufd.shoppingcart.util;

import android.app.Application;
import android.view.Gravity;

import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.BaseToastStyle;

public class ToastUtil {
    public static void init(Application application) {
        ToastUtils.init(application);
//        ToastUtils.getToast().setGravity(Gravity.BOTTOM,0,50);//50为距离底部多高
        BaseToastStyle mBaseToastStyle=new BaseToastStyle(application) {
            @Override
            public int getCornerRadius() {
                return  dp2px(4);
            }

            @Override
            public int getGravity() {
                return Gravity.BOTTOM;
            }

            @Override
            public int getYOffset() {
                return dp2px(50);
            }

            @Override
            public int getBackgroundColor() {
                return 0XFF000000;
            }

            @Override
            public int getTextColor() {
                return 0XFFFFFFFF;
            }

            @Override
            public float getTextSize() {
                return sp2px(14);
            }

            @Override
            public int getPaddingStart() {
                return dp2px(12);
            }

            @Override
            public int getPaddingTop() {
                return dp2px(10);
            }
        };
        ToastUtils.initStyle(mBaseToastStyle);
    }

    public static void show(String message) {
        try {
            ToastUtils.show(message);
        } catch (Exception e) {
//            if (BuildConfig.DEBUG){
//                e.printStackTrace();
//            }
        }

    }
    public static void showToast(String message) {
        show(message);
    }



    public static void show() {
        ToastUtil.show("功能尚未开发,敬请等待下一版本");
    }

    public static final void toastLongMessage(final String message) {
        showToast(message);

    }
}
