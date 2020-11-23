package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.newsuper.t.R;


public class CirclePercentView extends View {

    public static final int WIDTH_RADIUS_RATIO = 8;     // 弧线半径 : 弧线线宽 (比例)
    public static final int MAX = 100; //满值（为了奖励转圈更缓和：1000）
    private float progressPercent; //当前进度
    private Paint mPaint;
    private int radius;//圆弧宽度
    private int max;//圆弧宽度
    private RectF rectF;
    private int bgColor, progressColor;
    private int startColor, endColor;
    private LinearGradient gradient;
    private boolean isGradient; //是否渐变（预留）

    public CirclePercentView(Context context) {
        super(context);
        init();
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePercentView);
        bgColor = typedArray.getColor(R.styleable.CirclePercentView_circleBgColor, getResources().getColor(R.color.yellow_more));
        progressColor = typedArray.getColor(R.styleable.CirclePercentView_circleProgressColor, getResources().getColor(R.color.red_500));
        radius = typedArray.getInt(R.styleable.CirclePercentView_circleRadius, WIDTH_RADIUS_RATIO);
        max = typedArray.getInt(R.styleable.CirclePercentView_circleProgressMax, MAX);
        isGradient = typedArray.getBoolean(R.styleable.CirclePercentView_circleIsGradient, false);
        startColor = typedArray.getColor(R.styleable.CirclePercentView_circleStartColor, getResources().getColor(R.color.red_500));
        endColor = typedArray.getColor(R.styleable.CirclePercentView_circleEndColor, getResources().getColor(R.color.red_500));
        typedArray.recycle();
        init();
    }

    public CirclePercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());//自定义的View能够使用wrap_content或者是match_parent的属性
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1、绘制背景灰色圆环
        int centerX = getWidth() / 2;
        int strokeWidth = centerX / radius;
        mPaint.setShader(null);//必须设置为null，否则背景也会加上渐变色
        mPaint.setStrokeWidth(strokeWidth); //设置画笔的大小
        mPaint.setColor(bgColor);
        canvas.drawCircle(centerX, centerX, centerX - strokeWidth / 2, mPaint);
        // 2、绘制比例弧
        if (rectF == null) {//外切正方形
            rectF = new RectF(strokeWidth / 2, strokeWidth / 2, 2 * centerX - strokeWidth / 2, 2 * centerX - strokeWidth / 2);
        }
        //3、是否绘制渐变色
        if (isGradient) {
            gradient = new LinearGradient(0, 0, rectF.right, 0, startColor, endColor, Shader.TileMode.MIRROR);
            mPaint.setShader(gradient);//设置线性渐变
        } else {
            mPaint.setColor(progressColor);
        }


        canvas.drawArc(rectF, -90, 360f * progressPercent / max, false, mPaint);   //画比例圆弧
    }

    private void init() {
        mPaint = new Paint();
        //画笔样式
        mPaint.setStyle(Paint.Style.STROKE);
        //设置笔刷的样式:圆形
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
    }

    @Keep
    public void setPercentage(float percentage) {
        this.progressPercent = percentage;
        invalidate();
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

}


