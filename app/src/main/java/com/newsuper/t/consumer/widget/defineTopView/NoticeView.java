package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.widget.HorizontalMarqueeView;


public class NoticeView extends LinearLayout {

    public HorizontalMarqueeView tv_notice;
    private Context context;

    public NoticeView(Context context){
        super(context);
        this.context=context;
        initView();
    }

    public NoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_notice, null);
        tv_notice = (HorizontalMarqueeView) view.findViewById(R.id.tv_notice);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }


    public void setNotice(String notice){
        tv_notice.setText(notice);
    }

}
