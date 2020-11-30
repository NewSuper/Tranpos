package com.newsuper.t.consumer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ListView;

/**
 * Created by Administrator on 2018/8/6 0006.
 */

public class DirectionListView extends ListView {
    private float startY = 0;//按下时y值
    private int mTouchSlop;//系统值
    public DirectionListView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public DirectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public DirectionListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    @SuppressLint("NewApi")
    public DirectionListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getY() - startY) > mTouchSlop) {
                    if (ev.getY() - startY >= 0) {
                        mListener.onScrollDown(Math.abs(ev.getY() - startY));
                    } else {
                        mListener.onScrollUp(Math.abs(ev.getY() - startY));
                    }
                }
                startY = ev.getY();
                break;
        }
        return super.onTouchEvent(ev);
    }
    private ScrollDirectionListener mListener;

    public void setScrollDirectionListener(ScrollDirectionListener mListener) {
        this.mListener = mListener;
    }

    public interface ScrollDirectionListener{
        void onScrollDown(float y);
        void onScrollUp(float y);
    }
}
