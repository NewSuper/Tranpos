package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/8/26 0026.
 */

public class ClickableRelativeLayout extends RelativeLayout {

    public ClickableRelativeLayout(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
    
}
