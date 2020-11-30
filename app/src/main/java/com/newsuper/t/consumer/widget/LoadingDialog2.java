package com.newsuper.t.consumer.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsuper.t.R;


/**
 * 加载中Dialog
 */
public class LoadingDialog2 extends AlertDialog {

    private TextView tips_loading_msg;
    private ImageView iv_route;
    private AnimationDrawable mAnimation;

    public LoadingDialog2(Context context) {
        super(context, R.style.transparentDialog);
        this.setCancelable(false);
    }

    public LoadingDialog2(Context context, int theme) {
        super(context, theme);
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);

        iv_route = (ImageView) findViewById(R.id.iv_route);
        mAnimation = (AnimationDrawable) iv_route.getBackground();
        initAnim();
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

        }
    }

}
