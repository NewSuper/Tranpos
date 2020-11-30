package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.newsuper.t.R;

import java.util.ArrayList;

public class CustomTabLayout extends ViewGroup {
    private int horizontalSpace  = 15;
    private int verticalSpace = 15;
    private Context context;
    public CustomTabLayout(Context context) {
        super(context);
        this.context = context;
    }
    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelLayout);
        horizontalSpace = (int) a.getDimension(R.styleable.LabelLayout_horizontalSpace,0);
        verticalSpace = (int) a.getDimension(R.styleable.LabelLayout_verticalSpace,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;
        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                if (index == 0){
                    x += width ;
                }else {
                    x += width + horizontalSpace;
                }
                if (row == 0){
                    y = row * height + height ;
                }else {
                    y = row * height + height + row * verticalSpace;
                }
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * height + height + row * verticalSpace;
                }

            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                if (i == 0){
                    x += width;
                }else {
                    x += width + horizontalSpace;
                }
                if (row == 0){
                    y = row * height + height ;
                }else {
                    y = row * height + height + row * verticalSpace;
                }
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * height + height + row * verticalSpace;
                }
                child.layout(x - width , y - height , x , y);
            }
        }
    }

    public void setTabView(final ArrayList<String> labels,final TabClickListener tabClickListener){
        removeAllViews();
        if (labels == null || labels.size() == 0){
            return;
        }
        for (int i = 0;i < labels.size() ;i++){
            final TextView textView = (TextView)View.inflate(context, R.layout.textview_tab, null);
            textView.setText(labels.get(i));
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tabClickListener != null){
                        tabClickListener.onClick(textView.getText().toString());
                    }
                }
            });
            addView(textView);
        }
    }
    public interface TabClickListener{
        void onClick(String s);
    }
}