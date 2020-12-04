package com.newsuper.t.markert.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FixDialogFragment;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.markert.dialog.vm.InputDialogVM;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VipCancelDialog extends FixDialogFragment {

    @BindView(R.id.tv_dialog_title)
    TextView tv_dialog_title;
    @BindView(R.id.tv_tips)
    TextView tv_tips;
    private Builder mBuilder;

    private VipCancelDialog setmBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBuilder == null) {
            mBuilder = new Builder();
        }
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = mBuilder.gravity;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable());
        window.setWindowAnimations(mBuilder.animations);
        //设置边距
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(mBuilder.withParams, mBuilder.heightParams);
//        window.getDecorView().setPadding(UiUtils.dp2px(mBuilder.leftMargin,getContext()),0,UiUtils.dp2px(mBuilder.rightMargin,getContext()),0);
        setCancelable(false);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_guadan_tips, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tv_dialog_title.setText("取消会员");
        tv_tips.setText("您确定要取消会员吗？");
    }

    @OnClick({R.id.iv_cancel, R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                InputDialogVM vm = ViewModelProviders.of(getActivity()).get(InputDialogVM.class);
                vm.getVipCancelData().postValue("");
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    public static class Builder {
        private int withParams = ViewGroup.LayoutParams.MATCH_PARENT;
        private int heightParams = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int gravity = Gravity.CENTER;
        private int animations = R.style.dialog_animation_fade;
        private int leftMargin = 0;
        private int rightMargin = 0;

        public Builder setLeftMargin(int leftMargin) {
            this.leftMargin = leftMargin;
            return this;
        }

        public Builder setRightMargin(int rightMargin) {
            this.rightMargin = rightMargin;
            return this;
        }

        public Builder setWithParams(int withParams) {
            this.withParams = withParams;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setAnimations(int animations) {
            this.animations = animations;
            return this;
        }

        public Builder setHeightParams(int heightParams) {
            this.heightParams = heightParams;
            return this;
        }

        public VipCancelDialog build() {
            VipCancelDialog dialog = new VipCancelDialog();
            dialog.setmBuilder(this);
            return dialog;
        }
    }
}
