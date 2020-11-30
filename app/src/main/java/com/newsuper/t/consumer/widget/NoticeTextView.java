package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class NoticeTextView extends AppCompatTextView{

    public NoticeTextView(Context context) {
        super(context);
    }

    public NoticeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoticeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getLineCount() == 0) {
            return;
        }
        if(getLayout()==null) {
            return;
        }
        int ellipsisCount = getLayout().getEllipsisCount(getLineCount() - 1);
        if (ellipsisCount == 0) {
            return;
        }
        String content = getText().toString();
        String lastChar = content.substring(content.length() - 1, content.length());
        int measuredWidth = getMeasuredWidth();
        int lineCount = getLineCount();
        int maxMW = measuredWidth * lineCount;
        while (getPaint().measureText(content + "..." + lastChar) > maxMW) {
            content = content.substring(0, content.length() - 1);
        }
        setText(content + "..." + lastChar);
    }
}
