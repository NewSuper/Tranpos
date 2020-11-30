package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;

import java.util.ArrayList;


//自定义滑动标签控件
public class SlidTabView extends HorizontalScrollView {
    private LinearLayout tabsContainer;
    private LayoutInflater mLayoutInflater;
    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private int currentPosition = 0;
    private ArrayList<String> typeList;
    private OnCheckTypeListener mCheckTableListener;

    public SlidTabView(Context context) {
        this(context, null);
    }

    public SlidTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        //创建线性布局的标签容器
        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(tabsContainer);
        //默认参数
        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

    }

    public void setTypeList(ArrayList<String> typeList) {
        tabsContainer.removeAllViews();
        this.typeList = typeList;
        for (int i = 0; i < typeList.size(); i++) {
            addTab(i, typeList.get(i));
        }
    }

    //设置一个选中分类的监听
    public void setCheckTypeListener(OnCheckTypeListener checkTableListener) {
        this.mCheckTableListener = checkTableListener;
    }

    private void addTab(final int position, String type) {
        ViewGroup tab = (ViewGroup) mLayoutInflater.inflate(R.layout.item_second_type, this, false);
        TextView tv_second_type = (TextView) tab.findViewById(R.id.tv_second_type);
        tv_second_type.setText(type);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = position;
                updateView();
                if (null != mCheckTableListener) {
                    mCheckTableListener.check(position);
                }
            }
        });
        tabsContainer.addView(tab, position, defaultTabLayoutParams);
    }

    private void updateView(){
        int i=0;
        while (i < tabsContainer.getChildCount()) {
            ViewGroup tab = (ViewGroup) tabsContainer.getChildAt(i);
            TextView tv_second_type = (TextView) tab.findViewById(R.id.tv_second_type);
            View red_line = (View) tab.findViewById(R.id.red_line);
            if(i==currentPosition){
                red_line.setVisibility(View.VISIBLE);
                tv_second_type.setTextColor(Color.parseColor("#FB797B"));
            }else{
                red_line.setVisibility(View.INVISIBLE);
                tv_second_type.setTextColor(Color.parseColor("#333333"));
            }
            i++;
        }
    }

    public interface OnCheckTypeListener {
        void check(int position);
    }

    public void setCheck(int pos){
        if(pos<tabsContainer.getChildCount()){
            tabsContainer.getChildAt(pos).performClick();
        }
    }
}
