package com.newsuper.t.juejinbao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.newsuper.t.R;


public class RecommendRoundImageView extends android.support.v7.widget.AppCompatImageView {
    float width,height;
    int radius = 0;

    public RecommendRoundImageView(Context context) {
        this(context, null);
    }

    public RecommendRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecommendRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        radius = (int) context.getResources().getDimension(R.dimen.ws5dp);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width > radius && height > radius) {
            Path path = new Path();
            path.moveTo(radius, 0);
            path.lineTo(width - radius, 0);
            path.quadTo(width, 0, width, radius);
            path.lineTo(width, height);
            path.lineTo(0, height);
            path.lineTo(0, radius);
            path.quadTo(0, 0, radius, 0);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }
}

