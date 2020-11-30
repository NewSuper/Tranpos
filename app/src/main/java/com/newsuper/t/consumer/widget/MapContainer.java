package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.ScrollView;



public class MapContainer extends RelativeLayout {
    private ScrollView scrollView;
    public MapContainer(Context context) {
        super(context);
    }

    public MapContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            scrollView.requestDisallowInterceptTouchEvent(false);
        } else {
            scrollView.requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        heightMeasureSpec = (widthMeasureSpec * 130) / 335;
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 设置容器所需的宽度和高度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (width * 130) / 335;
        LogUtil.log("MapContainer","w == "+width +"  h = "+height);
        setMeasuredDimension(width, height);
    }*/
}
