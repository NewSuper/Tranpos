package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import java.util.Random;

public class MyHeader extends LinearLayout implements RefreshHeader {

    public static String REFRESH_HEADER_PULLING = "下拉可以刷新";//"下拉可以刷新";
    public static String REFRESH_HEADER_LOADING = "正在加载...";//"正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_FINISH = "刷新成功";//"刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";//"刷新失败";

    private TextView mTitleText;
    private ImageView mRotateView;
    private int mRotateAniTime = 300;
    Random mRandom = new Random();
    /**
     * 旋转动画
     */
    private RotateAnimation mRotateAnimation;
    private static final int[] IMG_RES = {
            R.mipmap.ic_refresh_01,
            R.mipmap.ic_refresh_02,
            R.mipmap.ic_refresh_03,
            R.mipmap.ic_refresh_04,
            R.mipmap.ic_refresh_05,
            R.mipmap.ic_refresh_06,
            R.mipmap.ic_refresh_07,
            R.mipmap.ic_refresh_08,
            R.mipmap.ic_refresh_09,
            R.mipmap.ic_refresh_10,
            R.mipmap.ic_refresh_11,
            R.mipmap.ic_refresh_12,
            R.mipmap.ic_refresh_13,
            R.mipmap.ic_refresh_14
    };

    public MyHeader(Context context) {
        this(context, null);
    }

    public MyHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        buildAnimation();
        View view = LayoutInflater.from(context).inflate(R.layout.model_refresh_my_header, this);
        mTitleText = view.findViewById(R.id.model_refresh_my_header_state);
        mRotateView = view.findViewById(R.id.model_refresh_my_header_img);

    }


    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh: //下拉过程
                mTitleText.setText(REFRESH_HEADER_PULLING);
                break;
            case ReleaseToRefresh: //松开刷新
                mTitleText.setText(REFRESH_HEADER_RELEASE);
                break;
            case Refreshing: //loading中
                mTitleText.setText(REFRESH_HEADER_LOADING);
                break;
        }
    }

    @NonNull
    public View getView() {
        return this;//真实的视图就是自己，不能返回null
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
        //开始动画
        mRotateView.clearAnimation();
        mRotateView.startAnimation(mRotateAnimation);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        //停止动画
        mRotateAnimation.cancel();
        if (success) {
            mTitleText.setText("刷新完成");
        } else {
            mTitleText.setText("刷新失败");
        }

        return 500; //延迟500毫秒之后再弹回
    }

    private void buildAnimation() {
        mRotateAnimation = new RotateAnimation(0, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(mRotateAniTime);
        mRotateAnimation.setFillAfter(true);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (offset == 0) {
            mRotateView.setImageResource(IMG_RES[mRandom.nextInt(IMG_RES.length)]);
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {
    }
}