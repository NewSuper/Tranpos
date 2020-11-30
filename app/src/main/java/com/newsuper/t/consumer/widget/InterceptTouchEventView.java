package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class InterceptTouchEventView extends View {
    public InterceptTouchEventView(Context context) {
        super(context);
    }

    public InterceptTouchEventView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptTouchEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    boolean isIntercept;

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }
    float mX,mY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = getX();
                float y = getY();
                if (Math.abs(mX - x) > 20 || Math.abs(mY - y) > 20){
                    if (isIntercept){
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
