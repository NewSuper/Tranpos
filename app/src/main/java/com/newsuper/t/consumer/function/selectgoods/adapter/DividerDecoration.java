package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.newsuper.t.consumer.base.BaseApplication;


public class DividerDecoration extends RecyclerView.ItemDecoration {

    private float mDividerHeight;
    private Paint mPaint;

    public DividerDecoration(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setDividerColor(int color){
        mPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //第一个ItemView不需要在上面绘制分割线
        if (parent.getChildAdapterPosition(view) != 0) {
            //这里直接硬编码为1px
            outRect.top = 1;
            mDividerHeight =dip2px(0.5f);
        }
    }

    public static float dip2px(float dip) {
        final float scale = BaseApplication.getApplication().getResources().getDisplayMetrics().density;
        return (dip * scale + 0.5f);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);
            //第一个ItemView不需要绘制
            if (index == 0) {
                continue;
            }

            float dividerTop = view.getTop() - mDividerHeight;
            float dividerLeft = parent.getPaddingLeft();
            float dividerBottom = view.getTop();
            float dividerRight = parent.getWidth() - parent.getPaddingRight();

            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
        }
    }
}