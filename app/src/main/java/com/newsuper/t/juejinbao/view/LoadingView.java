package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newsuper.t.R;


public class LoadingView extends FrameLayout {
    public View mEmptyView, mErrorView, mLoadingView;
    private OnClickListener onErrorClickListener;
    private OnClickListener onEmptyClickListener;
    private LayoutInflater mLayoutInflater;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout, 0, 0);

        try {
            int emptyView = a.getResourceId(R.styleable.LoadingLayout_emptyView, R.layout.empty_view);
            int errorView = a.getResourceId(R.styleable.LoadingLayout_errorView, R.layout.error_view);
            int loadingView = a.getResourceId(R.styleable.LoadingLayout_loadingView, R.layout.loading_view);
            mLayoutInflater = LayoutInflater.from(getContext());
            mEmptyView = mLayoutInflater.inflate(emptyView, this, true);
            mErrorView = mLayoutInflater.inflate(errorView, this, true);
            mLoadingView = mLayoutInflater.inflate(loadingView, this, true);
        }finally {
            a.recycle();
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Glide.with(this).load(R.drawable.ic_top_loading).into((ImageView)mLoadingView.findViewById(R.id.progress));

        for (int i = 0; i < getChildCount() - 1; i++) {
            getChildAt(i).setVisibility(GONE);
        }

        findViewById(R.id.tv_msg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onEmptyClickListener) {
                    onEmptyClickListener.onClick(v);
                }
            }
        });

        findViewById(R.id.tv_error_msg).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onErrorClickListener) {
                    onErrorClickListener.onClick(v);
                }
            }
        });
    }

    public void showEmpty() {
        if(refreshingAnim!=null){
            refreshingAnim.stop();
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    /**
     * 暂无数据
     * @param msg
     */
    public void showEmpty(String msg) {
        if(refreshingAnim!=null){
            refreshingAnim.stop();
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 0) {
                child.setVisibility(VISIBLE);
                ((TextView)child.findViewById(R.id.tv_msg)).setText(msg);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    /**
     * 展示错误布局
     * @param msg
     */
    public void showError(String msg) {
        if(refreshingAnim!=null){
            refreshingAnim.stop();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(msg).append("，点击重试");
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
                ((TextView)child.findViewById(R.id.tv_error_msg)).setText(stringBuffer.toString());
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    /**
     * 展示错误布局
     */
    public void showError() {
        if(refreshingAnim!=null){
            refreshingAnim.stop();
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }
    private AnimationDrawable refreshingAnim;

    /**
     * 展示loading
     */
    public void showLoading() {
        try {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = this.getChildAt(i);
                if(child != null) {
                    if (i == 2) {
                        child.setVisibility(VISIBLE);
//                ImageView loadImg = child.findViewById(R.id.loading_imge);
//                loadImg.setImageResource(com.ys.network.R.drawable.pulltorefresh_anim);
//                refreshingAnim = (AnimationDrawable) loadImg.getDrawable();
//                refreshingAnim.start();
                    } else {
                        child.setVisibility(GONE);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 展示内容
     */
    public void showContent() {
        if(refreshingAnim!=null){
            refreshingAnim.stop();
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i > 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public LoadingView setOnEmptyClickListener(OnClickListener onEmptyClickListener) {
        this.onEmptyClickListener = onEmptyClickListener;
        return this;
    }

    public LoadingView setOnErrorClickListener(OnClickListener onErrorClickListener) {
        this.onErrorClickListener = onErrorClickListener;

        findViewById(R.id.tv_error_msg).setOnClickListener(onErrorClickListener);

        return this;
    }

    public LoadingView setEmptyView(@LayoutRes int layout) {
        removeView(getChildAt(0));
        mEmptyView = mLayoutInflater.inflate(layout, null, true);
        addView(mEmptyView, 0);
        onFinishInflate();
        return this;
    }

    public LoadingView setErrorView(@LayoutRes int layout) {
        removeView(getChildAt(1));
        mErrorView = mLayoutInflater.inflate(layout, null, true);
        addView(mErrorView, 1);
        onFinishInflate();
        return this;
    }

    public LoadingView setLoadingView(@LayoutRes int layout) {
        removeView(getChildAt(2));
        mLoadingView = mLayoutInflater.inflate(layout, null, true);
        addView(mLoadingView, 2);
        return this;
    }

    public LoadingView setEmptyText(String text) {
        ((TextView) findViewById(R.id.tv_msg)).setText(text);
        return this;
    }

    public LoadingView setErrorText(String text) {
        ((TextView) findViewById(R.id.tv_error_msg)).setText(text);
        return this;
    }

    @Override
    protected void onDetachedFromWindow() {
        if(refreshingAnim!=null){
            refreshingAnim.stop();
        }
        super.onDetachedFromWindow();
    }
}

