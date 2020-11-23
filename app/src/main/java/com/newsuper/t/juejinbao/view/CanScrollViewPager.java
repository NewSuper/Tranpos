package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanScrollViewPager extends ViewPager {
    private boolean mNoScroll = true;//标记不允许左右滚动

    /**
     * @param context
     * @param attrs
     */
    public CanScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public CanScrollViewPager(Context context) {
        super(context);
    }


    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
//        if (v != this && (v instanceof PageWebView || v instanceof HorizontalGalleryView)) {
//            requestDisallowInterceptTouchEvent(true);
//            return true;
//        }
        return super.canScroll(v, checkV, dx, x, y);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    //设置不允许左右滚动
    public void setNoScroll(boolean noScroll) {
        this.mNoScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (mNoScroll) {
            return false;
        }
        return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mNoScroll) {
            return false;
        }

        return super.onInterceptTouchEvent(arg0);
    }
}