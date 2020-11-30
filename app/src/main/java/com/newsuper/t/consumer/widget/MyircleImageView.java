package com.newsuper.t.consumer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public class MyircleImageView extends AppCompatImageView {
    /**外圆的宽度*/
    private int outCircleWidth;
    /**外圆的颜色*/
    private int outCircleColor;
    private Paint outCirclePaint;
    private Paint bmPaint;
    /**图片的宽度*/
    /**图片的高度*/
    protected int bmHeight;
    /**ImageView控件的宽度*/
    private int width;
    /**ImageView控件的高度*/
    private int height;
    //基本的三个构造函数
    public MyircleImageView(Context context) {
        super(context);
        initView();
    }
    public MyircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureSize(widthMeasureSpec);
        height = measureSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
    /**
     * 测量宽和高，这一块可以是一个模板代码(Android群英传)
     * @param widthMeasureSpec
     * @return
     */
    private int measureSize(int widthMeasureSpec) {
        int result = 0;
        //从MeasureSpec对象中提取出来具体的测量模式和大小
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            //测量的模式，精确
            result = size;
        }
        return result;
    }
    @SuppressLint("ResourceAsColor")
    private void initView()
    {
        outCircleColor = Color.parseColor("#FFFFFF");
        outCircleWidth = (int) dpToPx(1);
        outCirclePaint = new Paint();
        outCirclePaint.setColor(outCircleColor);
        outCirclePaint.setStrokeWidth(outCircleWidth);
        /*消除锯齿  */
        outCirclePaint.setAntiAlias(true);
        /*绘制空心圆  */
        outCirclePaint.setStyle(Paint.Style.STROKE);
        bmPaint = new Paint();
        bmPaint.setAntiAlias(true);
        bmPaint.setFilterBitmap(true);
        bmPaint.setDither(true);
        bmPaint.setColor(Color.parseColor("#BAB399"));
//        setBackgroundColor(android.R.color.transparent);
    }
    public void setOutCircleColor(int outCircleColor){
        outCirclePaint.setColor(outCircleColor);
    }
    public void setOutCircleWidth(int outCircleWidth){
        this.outCircleWidth = (int) dpToPx(outCircleWidth);
        outCirclePaint.setStrokeWidth(this.outCircleWidth);
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if ((drawable == null) || (getWidth() == 0) || (getHeight() == 0)) {
            return;
        }
        Bitmap b = drawable2Bitmap(drawable);
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap croppedBitmap = getCroppedBitmap(bitmap);
        canvas.drawBitmap(croppedBitmap, 0, 0, null);
        canvas.drawCircle(width/ 2,width / 2, (width-outCircleWidth) / 2, outCirclePaint);
    }
    private Bitmap getCroppedBitmap(Bitmap bitmap)
    {
        Bitmap sbmp  = Bitmap.createScaledBitmap(bitmap, width, width, false);
        Bitmap outputBm = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBm);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, width);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        float scale = ((float)width - (float)outCircleWidth) / ((float)width);
        canvas.translate(outCircleWidth/2, outCircleWidth/2);
        canvas.scale(scale, scale);
        canvas.drawCircle((width)/ 2,(width) / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        return outputBm;
    }
    /**数据转换: dp---->px*/
    protected float dpToPx(float dp){
        return dp * getContext().getResources().getDisplayMetrics().density;
    }
    /**将Drawable对象转换成 Bitmap对象
     * @param drawable 可绘制图像*/
    protected  Bitmap drawable2Bitmap(Drawable drawable)
    {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
    protected String tag(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        //" getClassName = "++" getMethodName = "++" getLineNumber = "+stackTrace[i].getLineNumber());
        int index = 3;
        String fileName = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();
        for (int i = 0; i < stackTrace.length; i++){
            //Log.e("i = "+i+" TAG", "fileName = "+stackTrace[i].getFileName()+" methodName = "+stackTrace[i].getMethodName()+" lineNumber = "+stackTrace[i].getLineNumber());
        }
        return "["+fileName+":"+lineNumber+"]"+" # "+methodName;
    }
}