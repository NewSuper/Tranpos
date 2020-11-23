package com.newsuper.t.juejinbao.ui.movie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tencent.smtt.sdk.WebView;

import java.util.Map;

public class TouchWebView extends WebView {


    public TouchWebView(Context context) {
        super(context);
    }

    public TouchWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public TouchWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public TouchWebView(Context context, AttributeSet attributeSet, int i, boolean b) {
        super(context, attributeSet, i, b);
    }

    public TouchWebView(Context context, AttributeSet attributeSet, int i, Map<String, Object> map, boolean b) {
        super(context, attributeSet, i, map, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.getScrollY() <= 0)
                    this.scrollTo(0, 1);
                break;
            case MotionEvent.ACTION_UP:
                //                if(this.getScrollY() == 0)
                //                this.scrollTo(0,-1);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


}
