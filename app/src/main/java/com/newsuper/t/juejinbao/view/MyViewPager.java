package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 重写onInterceptTouchEvent()方法来解决图片点击缩小时候的Crash问题
     * 解决bugly错误：java.lang.IllegalArgumentException: pointerIndex out of range pointerIndex=-1 pointerCount=1
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException  e) {
            e.printStackTrace();
        }
        return false ;
    }
}
