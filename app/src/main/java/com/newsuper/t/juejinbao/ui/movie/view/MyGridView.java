package com.newsuper.t.juejinbao.ui.movie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //gridview嵌套在其他view里有可能只显示一行，复写此方法解决
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    //以下是设置空白区域点击事件
    private float mTouchX;
     private float mTouchY;
     private OnTouchBlankPositionListener mTouchBlankPosListener;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTouchBlankPosListener != null) {
            if (!isEnabled()) {
                return isClickable() || isLongClickable();
            }
            int action = event.getActionMasked();
            float x = event.getX();
            float y = event.getY();
            final int motionPosition = pointToPosition((int) x, (int) y);
            if (motionPosition == INVALID_POSITION) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchX = x;
                        mTouchY = y;
//                        mTouchBlankPosListener.onTouchBlank(event);
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if (Math.abs(mTouchX - x) > 10
//                                || Math.abs(mTouchY - y) > 10) {
//                            mTouchBlankPosListener.onTouchBlank(event);
//                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mTouchX = 0;
                        mTouchY = 0;
                        mTouchBlankPosListener.onTouchBlank(event);
                        break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置GridView的空白区域的触摸事件
     *
     * @param listener
     */
    public void setOnTouchBlankPositionListener(  OnTouchBlankPositionListener listener) {
        mTouchBlankPosListener = listener;
    }

    public interface OnTouchBlankPositionListener {
        void onTouchBlank(MotionEvent event);
    }
}
