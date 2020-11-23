package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;


public class CustomRecyclerview extends RecyclerView {

    boolean interceptTouch;

    public CustomRecyclerview(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        interceptTouch = true;
        // interceptTouch是自定义属性控制是否拦截事件
        if (interceptTouch){
            ViewParent parent =this;
            // 循环查找ViewPager, 请求ViewPager不拦截触摸事件
            while(true) {
                if(parent == null || parent.getParent() == null) {
                    break;
                }

                if ((parent = parent.getParent()) instanceof ViewPager) {
                    break;
                }
            }
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);

    }
}
