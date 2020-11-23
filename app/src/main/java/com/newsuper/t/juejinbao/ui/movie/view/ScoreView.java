package com.newsuper.t.juejinbao.ui.movie.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.juejinchain.android.R;

public class ScoreView extends View {
    private Context context;

    //控件的宽高
    private int viewWidth = 0;
    private int viewHight = 0;

    private Paint paint;

    private float f = 1;


    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        LLog.e("ds3 onMeasure");
        // 宽度测量
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                viewWidth = Math.min(widthSize, 100);
                break;
            case MeasureSpec.EXACTLY:
                viewWidth = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                viewWidth = 100;
                break;
        }

        // 高度测量
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                viewHight = Math.min(heightSize, 100);
                break;
            case MeasureSpec.EXACTLY:
                viewHight = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                viewHight = 100;
                break;
        }

        setMeasuredDimension(viewWidth, viewHight);




    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextScaleX(1.4f);
//        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setColor(context.getResources().getColor(R.color.app_color));
    }

    public void setRate(float f){
        this.f = f / 10;
        invalidate();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, viewWidth * f, viewHight, paint);
    }
}
