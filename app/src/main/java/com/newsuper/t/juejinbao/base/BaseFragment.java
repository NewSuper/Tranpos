package com.newsuper.t.juejinbao.base;

import android.annotation.SuppressLint;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;


import com.newsuper.t.juejinbao.utils.androidUtils.NotchScreenUtil;

import java.util.List;

public abstract class BaseFragment<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingFragment<B> {
    protected P mPresenter;

    protected boolean showOrHide = false;

    @Override
    public abstract void initView();

    @Override
    public abstract void initData();

    @Override
    public void initPresenter() {
        mPresenter = CreateUtil.getT(this, 0);
        mPresenter.attachModelView(this);

        //设置状态栏&刘海高度
        try {
            View statusText = getView().findViewById(getResources().getIdentifier("tv_status", "id", getActivity().getPackageName()));
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) statusText.getLayoutParams();
            param.height = getStateBarHeight();
            statusText.setLayoutParams(param);
        } catch (Exception e) {
        }
    }

    @Override
    public void onResume() {//和activity的onResume绑定，Fragment初始化的时候必调用，但切换fragment的hide和visible的时候可能不会调用！
        super.onResume();
        if (isAdded() && !isHidden()) {//用isVisible此时为false，因为mView.getWindowToken为null
            inThis();
        }
    }

    @Override
    public void onPause() {
        if (isVisible())
            outThis();
        super.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {//默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
        super.onHiddenChanged(hidden);
        if (!hidden) {
            inThis();
        } else {
            outThis();
        }
    }

    protected void inThis() {
        showOrHide = true;
    }

    protected void outThis() {
        showOrHide = false;
    }

    /**
     * 获取状态栏&刘海高度
     */
    private int getStateBarHeight() {

        //安卓9.0自带方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

        }
        //厂商方法
        else {
            switch (NotchScreenUtil.getDeviceBrand()) {
                //华为
                case NotchScreenUtil.DEVICE_BRAND_HUAWEI:
                    //存在刘海屏
                    if (NotchScreenUtil.hasNotchInScreenAtHuawei(getActivity())) {
                        return NotchScreenUtil.getNotchSizeAtHuawei(getActivity());
                    }
                    //OPPO
                case NotchScreenUtil.DEVICE_BRAND_OPPO:
                    //存在刘海屏
                    if (NotchScreenUtil.hasNotchInScreenAtOppo(getActivity())) {
                        return NotchScreenUtil.getNotchSizeAtOppo(getActivity());
                    }
                    //VIVO
                case NotchScreenUtil.DEVICE_BRAND_VIVO:
                    //存在刘海屏
                    if (NotchScreenUtil.hasNotchInScreenAtVivo(getActivity())) {
                        return NotchScreenUtil.getNotchSizeAtVivo(getActivity());
                    }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bus != null) {
            bus.unregister(this);
        }
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        }
    }

    @Override
    public void initImmersionBar() {
    }
}
