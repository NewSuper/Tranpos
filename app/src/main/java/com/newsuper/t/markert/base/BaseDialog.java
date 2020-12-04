package com.newsuper.t.markert.base;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FixDialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.newsuper.t.R;

public abstract class BaseDialog extends FixDialogFragment {
    private Builder mBuilder;

    public void setmBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
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
        setCancelable(true);
    }

    public static class Builder {
        private int withParams = ViewGroup.LayoutParams.MATCH_PARENT;
        private int heightParams = ViewGroup.LayoutParams.MATCH_PARENT;
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

        public BaseDialog build(Class<?> clazz) {
            BaseDialog dialog = null;
            try {
                dialog = (BaseDialog) clazz.newInstance();
                dialog.setmBuilder(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
            return dialog;
        }
    }
}
