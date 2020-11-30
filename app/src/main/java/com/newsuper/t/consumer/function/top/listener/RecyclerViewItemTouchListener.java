package com.newsuper.t.consumer.function.top.listener;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/5/23 0023.
 * RecyclerView item 点击事件
 */

public class RecyclerViewItemTouchListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat gestureDetector;
    private RecyclerViewItemClickListener itemClickListener;
    private RecyclerView recyclerView;
    public RecyclerViewItemTouchListener(Context context,final RecyclerViewItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        gestureDetector = new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (recyclerView != null){
                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null && itemClickListener != null ) {
                        itemClickListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                    }
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        this.recyclerView = rv;
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.recyclerView = rv;
        gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    public interface RecyclerViewItemClickListener {
        void onItemClick(View v, int position);
    }
}
