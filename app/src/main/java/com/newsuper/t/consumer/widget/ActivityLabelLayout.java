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

public class ActivityLabelLayout extends ViewGroup {
    private int horizontalSpace  = 15;
    private int verticalSpace = 15;
    private Context context;
    private boolean isShowAll;
    private int row;
    public ActivityLabelLayout(Context context) {
        super(context);
        this.context = context;
    }
    public ActivityLabelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelLayout);
        horizontalSpace = (int) a.getDimension(R.styleable.LabelLayout_horizontalSpace,0);
        verticalSpace = (int) a.getDimension(R.styleable.LabelLayout_verticalSpace,0);
    }

    public int getRow() {
        return row;
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
                    if (rowListener != null){
                        if (x > maxWidth){
                            rowListener.onRow(1);
                        }else {
                            rowListener.onRow(0);
                        }
                    }
                }else {
                    y = row * height + height + row * verticalSpace;
                }
                if (x > maxWidth) {
                    if (!isShowAll){
                        break;
                    }
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

    private View guideView;

    public void setGuideView(View guideView) {
        this.guideView = guideView;
    }

    public void setActivityLabelView(ArrayList<String> labels, boolean b){
        isShowAll = b;
        removeAllViews();
        if (labels == null || labels.size() == 0){
            return;
        }
        int allWidth = 0;
        for (int i = 0;i < labels.size() ;i++){
            TextView textView = (TextView)View.inflate(context, R.layout.layout_shop_activity_red, null);
            textView.setText(labels.get(i));
            if (labels.get(i).contains("折") ){
                textView.setBackgroundResource(R.drawable.shape_act_bg_green);
                textView.setTextColor(Color.parseColor("#11C977"));
            }else if (labels.get(i).contains("会员") || labels.get(i).contains("赠") || labels.get(i).contains("自取")){
                textView.setBackgroundResource(R.drawable.shape_act_bg_yellow);
                textView.setTextColor(Color.parseColor("#FBA608"));
            }
            allWidth += textView.getMeasuredWidth();
            addView(textView);
        }
    }
    private RowListener rowListener;

    public void setRowListener(RowListener rowListener) {
        this.rowListener = rowListener;
    }

    public interface RowListener{
        void onRow(int r);
    }
}

