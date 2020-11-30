package com.newsuper.t.consumer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;


/**
 * Created by Administrator on 2017/7/17 0017.
 * 加载动画
 */

public class LoadingAnimatorView extends RelativeLayout{
    private AnimationDrawable animationDrawable;
    private Context mContext;
    private TextView tvLoading;
    private RefreshThirdStepView loadingView;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public LoadingAnimatorView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public LoadingAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }

    public LoadingAnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
    }

    @SuppressLint("NewApi")
    public LoadingAnimatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initView(context);
    }


    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_view,null);
        tvLoading = view.findViewById(R.id.tv_loading);
        loadingView = view.findViewById(R.id.loading_view);
        //动画
        animationDrawable = (AnimationDrawable)loadingView.getBackground();
        addView(view,new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
    }

    Runnable anim = new Runnable() {
        @Override
        public void run() {
            animationDrawable.start();
        }
    };

    public void showView(){
        this.setVisibility(VISIBLE);
        handler.post(anim);
    }
    public void showView(String loading){
        this.setVisibility(VISIBLE);
        this.tvLoading.setText(loading);
        handler.post(anim);
    }
    public void dismissView(){
        this.setVisibility(GONE);
        animationDrawable.stop();
        handler.removeCallbacks(anim);
    }
    public  void tryRecycleAnimationDrawable() {
        if (animationDrawable != null) {
            animationDrawable.stop();
            for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
                Drawable frame = animationDrawable.getFrame(i);
                if (frame instanceof BitmapDrawable) {
                    ((BitmapDrawable) frame).getBitmap().recycle();
                }
                frame.setCallback(null);
            }
            animationDrawable.setCallback(null);
        }
    }
}
