package com.newsuper.t.juejinbao.base;

import android.app.ActivityManager;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;
import com.squareup.otto.Bus;


import java.util.List;


public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> {

    protected P mPresenter;
    protected Bus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = CreateUtil.getT(this, 0);
        mPresenter.attachModelView(this);
        super.onCreate(savedInstanceState);
//        ActivityUtils.add(this);
        ActivityCollector.addActivity(this, getClass());
        //注册otto
        bus = BusProvider.getInstance();
        if (bus != null) {
            bus.register(this);
        }
        //设置状态栏&刘海高度
        try {
            View statusText = findViewById(getResources().getIdentifier("tv_status", "id", getPackageName()));
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) statusText.getLayoutParams();
            param.height = getStateBarHeight();
            statusText.setLayoutParams(param);
        } catch (Exception e) {
        }
    }

    /**
     * 获取状态栏&刘海高度
     */
    protected int getStateBarHeight() {

        //安卓9.0自带方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

        }
        //厂商方法
        else {
            switch (NotchScreenUtil.getDeviceBrand()) {
                //华为
                case NotchScreenUtil.DEVICE_BRAND_HUAWEI:
                    //存在刘海屏
                    if (NotchScreenUtil.hasNotchInScreenAtHuawei(this)) {
                        return NotchScreenUtil.getNotchSizeAtHuawei(this);
                    }
                    break;
                //OPPO
                case NotchScreenUtil.DEVICE_BRAND_OPPO:
                    //存在刘海屏
                    if (NotchScreenUtil.hasNotchInScreenAtOppo(this)) {
                        return NotchScreenUtil.getNotchSizeAtOppo(this);
                    }
                    break;
                //VIVO
                case NotchScreenUtil.DEVICE_BRAND_VIVO:
                    //存在刘海屏
                    if (NotchScreenUtil.hasNotchInScreenAtVivo(this)) {
                        return NotchScreenUtil.getNotchSizeAtVivo(this);
                    }
                    break;
            }
        }

        //普通状态栏高度
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return this.getResources().getDimensionPixelSize(resourceId);
        }
        return dip2px(20);
    }

    //dp转像素
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    protected void remove() {
//        ActivityUtils.exitWithoutMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ActivityUtils.remove(this);
        ActivityCollector.removeActivity(this);
        //注销监听订阅者
        if (bus != null) {
            bus.unregister(this);
            bus = null;
        }
        //取消网络的订阅者
        if (mPresenter != null) {
            mPresenter.unSubscribe();
            mPresenter = null;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //取消网络的订阅者
            if (mPresenter != null) {
                mPresenter.unSubscribe();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public abstract int getLayoutId();

    @Override
    public abstract void initView();

    @Override
    public abstract void initData();

//    /**
//     * 防止连点导致的多个重复页面
//     * @param ev
//     * @return
//     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            if (isFastDoubleClick()) {
//                return true;
//            }
//        }
//        return super.dispatchTouchEvent(ev);
//    }
//    private static long lastClickTime;
//    private long DELAY_TIME = 100; //默认间隔时间
//
//    public boolean isFastDoubleClick() {
//        long time = System.currentTimeMillis();
//        long timeD = time - lastClickTime;
//        if (0 < timeD && timeD < DELAY_TIME) {
//            return true;
//        }
//        lastClickTime = time;
//        return false;
//    }

    //用来控制应用前后台切换的逻辑
    private boolean isCurrentRunningForeground = true;

    @Override
    protected void onStart() {
        super.onStart();
//        if (!isCurrentRunningForeground) {
//            isCurrentRunningForeground=true;
//            //处理跳转到广告页逻辑
//            ToastUtils.getInstance().show(mActivity,"跳转至广告页");
////            Intent intennt=new Intent(this,SplashActivity.class);
////            startActivity(intennt);
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
        }
    }

    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        if (appProcessInfos == null)
            return false;
        // 枚举进程,查看该应用是否在运行
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 点击空白区域隐藏键盘.
     */
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent me) {
//        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
//            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
//            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
//                hideKeyboard(v.getWindowToken());   //收起键盘
//            }
//        }
//        return super.dispatchTouchEvent(me);
//    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
