package com.newsuper.t.juejinbao.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;


import com.newsuper.t.juejinbao.view.SlidingLayout;

import butterknife.ButterKnife;


public abstract class DataBindingActivity<B extends ViewDataBinding> extends AppCompatActivity {
    protected B mViewBinding = null;
    protected Activity mActivity;

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        //所有Activity头部全屏
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
////            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
////                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
////            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
////            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
////            window.setStatusBarColor(Color.TRANSPARENT);
                View decorView = window.getDecorView();
                decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
                    @Override
                    public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                        WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
                        return defaultInsets.replaceSystemWindowInsets(
                                defaultInsets.getSystemWindowInsetLeft(),
                                0,
                                defaultInsets.getSystemWindowInsetRight(),
                                defaultInsets.getSystemWindowInsetBottom());
                    }
                });
                ViewCompat.requestApplyInsets(decorView);
                //将状态栏设成透明，如不想透明可设置其他颜色
                window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
//        rootView.setFitsSystemWindows(true);
        setContentView(rootView);
        ButterKnife.bind(this);
        mViewBinding = DataBindingUtil.bind(rootView);
//        mApplication = (BaseApplication) getApplication();
//        if (setStatusBarColor()) {
//            if (Build.VERSION.SDK_INT > 21) {
//                getWindow().setStatusBarColor(Color.parseColor("#21277B"));
//            }
//        }
        initView();
        initData();
        if (isSupportSwipeBack()) {
            SlidingLayout slidingLayout = new SlidingLayout(this);
            slidingLayout.bindActivity(this);
        }

        //状态栏透明
//        StatusBarUtil.setImmersiveStatusBar(this, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BaseApplication.watch(this, this);
    }

    /**
     *
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    public boolean isSupportSwipeBack() {
        return true;
    }

    public abstract boolean setStatusBarColor();

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();
}
