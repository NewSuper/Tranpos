package com.transpos.market.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View

class GridSpacingItemDecoration : RecyclerView.ItemDecoration {

    private var spanCount = 0 //列数

    private var spacing = 0 //间隔

    constructor(spanCount : Int,spacing : Int){
        this.spanCount = spanCount
        this.spacing = spacing
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position

        val column = position % spanCount // item column

        if(column == 0){
            outRect.left = 0
        } else{
            outRect.left = spacing
        }

        if (position >= spanCount) {
            outRect.top = spacing // item top
        }
    }
}