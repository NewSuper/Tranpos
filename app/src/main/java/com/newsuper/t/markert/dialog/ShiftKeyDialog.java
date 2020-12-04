package com.newsuper.t.markert.dialog;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShiftKeyDialog extends FixDialogFragment {
    @BindView(R.id.tv_dialog_title)
    TextView tv_dialog_title;
    private Builder mBuilder;
    private ShiftKeyDialog setmBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
        return this;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mBuilder == null){
            mBuilder = new Builder();
        }
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = mBuilder.gravity;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable());
        window.setWindowAnimations(mBuilder.animations);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(mBuilder.withParams, mBuilder.heightParams);
        setCancelable(false);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_shift_key, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        tv_dialog_title.setText("功能快捷键");
    }
    @OnClick({R.id.iv_cancel})
    public void onViewClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_cancel:
                dismiss();
                break;
        }
    }
    public static class Builder {
        private int withParams = ViewGroup.LayoutParams.MATCH_PARENT;
        private int heightParams = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int gravity = Gravity.CENTER;
        private int animations = R.style.dialog_animation_fade;

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

        public ShiftKeyDialog build(){
            ShiftKeyDialog dialog = new ShiftKeyDialog();
            dialog.setmBuilder(this);
            return dialog;
        }
    }
}
