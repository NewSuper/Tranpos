package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.newsuper.t.consumer.utils.UIUtils;


public class MaxHeightScrollView_520 extends ScrollView {
    private Context mContext;

    public MaxHeightScrollView_520(Context context) {
        super(context);
        init(context);
    }

    public MaxHeightScrollView_520(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }


    public MaxHeightScrollView_520(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(UIUtils.dip2px(520), MeasureSpec.AT_MOST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
