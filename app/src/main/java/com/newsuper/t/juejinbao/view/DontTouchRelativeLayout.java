package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class DontTouchRelativeLayout extends RelativeLayout {
    public DontTouchRelativeLayout(Context context) {
        super(context);
    }

    public DontTouchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DontTouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
