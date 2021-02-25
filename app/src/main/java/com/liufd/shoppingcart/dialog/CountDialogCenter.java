package com.liufd.shoppingcart.dialog;


import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.liufd.shoppingcart.R;
import com.liufd.shoppingcart.databinding.DialogCountBinding;
import com.liufd.shoppingcart.util.ToastUtil;

public class CountDialogCenter {
    private BaseBottomView<DialogCountBinding> mBaseBottomView;
    Context context;
    CountDialogClickListener mOnClickListener;

    public interface CountDialogClickListener {
        void onClick(int count);
    }

    public CountDialogCenter setOnClickListener(CountDialogClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        return this;
    }

    public CountDialogCenter setSubmitText(String text) {
        mBaseBottomView.mBinding.submit.setText(text);
        return this;
    }

    public CountDialogCenter setContent(String text) {
        mBaseBottomView.mBinding.lookOverTv.setText(text);
        return this;
    }

    public CountDialogCenter setContent(Spanned text) {
        mBaseBottomView.mBinding.lookOverTv.setText(text);
        return this;
    }

    public CountDialogCenter setContentColor(int color) {
        mBaseBottomView.mBinding.lookOverTv.setTextColor(color);
        return this;
    }


    public CountDialogCenter setCancelGone() {
        mBaseBottomView.mBinding.cancel.setVisibility(View.GONE);
        mBaseBottomView.mBinding.cancelView.setVisibility(View.GONE);
        return this;
    }

    private void setOnClickListener() {
        mBaseBottomView.mBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseBottomView.dismiss();
            }
        });
        mBaseBottomView.mBinding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mBaseBottomView.mBinding.lookOverTv.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(Integer.parseInt(text));
                    }
                    mBaseBottomView.dismiss();
                } else {
                    ToastUtil.show("输入不能为空");
                }
            }
        });
    }


    public CountDialogCenter(Context context) {
        this.context = context;
        mBaseBottomView = new BaseBottomView(context, R.layout.dialog_count);
        mBaseBottomView.setStyle(R.style.my_center_dialogAnim);
        mBaseBottomView.setGravity(Gravity.CENTER);
//        mBaseBottomView.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mBaseBottomView.setWidth(dip2px(context, 280));

    }


    public CountDialogCenter showView() {
        setOnClickListener();
        mBaseBottomView.show();

        return this;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
