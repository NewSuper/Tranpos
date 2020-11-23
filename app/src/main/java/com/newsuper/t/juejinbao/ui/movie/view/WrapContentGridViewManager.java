package com.newsuper.t.juejinbao.ui.movie.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

public class WrapContentGridViewManager extends GridLayoutManager {


    public WrapContentGridViewManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public WrapContentGridViewManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public WrapContentGridViewManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            Log.e("zy" , "grid_IndexOutOfBoundsException");
        }
    }

}
