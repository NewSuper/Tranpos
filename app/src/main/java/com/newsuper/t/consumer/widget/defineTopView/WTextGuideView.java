package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;


/**
 * Created by Administrator on 2017/8/2 0002.
 * 文本导航
 */

public class WTextGuideView  extends LinearLayout {
        private LinearLayout llText;
        private TextView tvText;
        public WTextGuideView(Context context) {
            super(context);
            initView(context);
        }
        public WTextGuideView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            initView(context);
        }

        public WTextGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView(context);
        }
        public void initView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_wei_text_guide, null);
            tvText = (TextView)view.findViewById(R.id.tv_text) ;
            llText = (LinearLayout) view.findViewById(R.id.ll_text) ;
            addView(view, new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        }
        public void setTextValue(String name) {
            if (tvText != null){
                tvText.setText(name);
            }
        }
        public void setOnClickListener(OnClickListener listener){
            if (llText != null){
                llText.setOnClickListener(listener);
            }
        }
}
