package com.newsuper.t.juejinbao.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.newsuper.t.R;


public class CustomLayout extends FrameLayout {

    private Context mContext;
    private CustomDrawable background;

    public CustomLayout(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public CustomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initView(context, attrs, 0);
    }

    public CustomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @SuppressLint("NewApi")
    private void initView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        background = new CustomDrawable(getBackground());
        setBackground(background);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resetBackgroundHoleArea();
    }

    @SuppressLint("NewApi")
    private void resetBackgroundHoleArea() {
        Path path = null;
        // 以子View为范围构造需要透明显示的区域
        View view = findViewById(R.id.iv_scan);
        if (view != null) {
            path = new Path();
            // 矩形透明区域
            path.addRoundRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), dp2Px(mContext,5), dp2Px(mContext,5), Path.Direction.CW);
        }
        if (path != null) {
            background.setSrcPath(path);
        }
    }

    public int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
