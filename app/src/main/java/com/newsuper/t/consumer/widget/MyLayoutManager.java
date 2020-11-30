package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/1/27 0027.
 */

public class MyLayoutManager extends LinearLayoutManager {

    public MyLayoutManager(Context context) {
        super(context);
    }


    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        View view = recycler.getViewForPosition(0);
        if(view != null){
            measureChild(view, widthSpec, heightSpec);
            int measuredHeight = view.getMeasuredHeight();
            if(measuredHeight>260){
                setMeasuredDimension(widthSpec, 260);
            }else{
                setMeasuredDimension(widthSpec, measuredHeight);
            }
        }
    }
}
