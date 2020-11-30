package com.newsuper.t.consumer.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;


public class LoadingDialog3 extends AlertDialog {

    private TextView tips;
    private ImageView iv_route;
    private AnimationDrawable mAnimation;

    public LoadingDialog3(Context context) {
        super(context, R.style.transparentDialog);
        this.setCancelable(false);
    }

    public LoadingDialog3(Context context, int theme) {
        super(context, theme);
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.pop_loading_3);
        iv_route = (ImageView) findViewById(R.id.iv_route);
        tips = (TextView)findViewById(R.id.tv_tip);
        mAnimation = (AnimationDrawable) iv_route.getBackground();
    }

    @Override
    public void show() {
        super.show();
        initAnim();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    private void initAnim() {
        iv_route.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
    }

    @Override
    public void dismiss() {
        try {
            mAnimation.stop();
            super.dismiss();
        }catch (Exception e){
            super.dismiss();
        }
    }

}
