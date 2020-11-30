package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/31 0031.
 * 微页面定位
 */

public class LocationAddressView extends LinearLayout {
    ViewHolder viewHolder;
    Context context;
    int scroll_value;//偏移量
    public LocationAddressView(Context context) {
        super(context);
        initView(context);
    }

    public LocationAddressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LocationAddressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        View view = LayoutInflater.from(context).inflate(R.layout.layout_wei_address, null);
        viewHolder = new ViewHolder(view);
        scroll_value = viewHolder.llAddress.getMeasuredHeight();
        LogUtil.log("LocationAddressView","scroll_value == "+ scroll_value);
        addView(view, new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));


    }
    public void setAddressValue(String value){
        if (viewHolder != null){
            viewHolder.tvAddressWei.setText(value);
        }
    }
    public void setOnClickListener(OnClickListener listener){
        if (viewHolder != null){
            viewHolder.llAddress.setOnClickListener(listener);
            viewHolder.llSearchShop.setOnClickListener(listener);
        }
    }
    static class ViewHolder {
        @BindView(R.id.tv_address_wei)
        TextView tvAddressWei;
        @BindView(R.id.ll_search_shop)
        LinearLayout llSearchShop;
        @BindView(R.id.ll_address)
        LinearLayout llAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private int maxScrollHeight ,currentHeight;
    public int finalHeight;
    public void moveView(int y){
        if (maxScrollHeight == 0){
            maxScrollHeight = viewHolder.llAddress.getMeasuredHeight() * 3 / 4;
            finalHeight = getMeasuredHeight() - maxScrollHeight;
        }
        LogUtil.log("LocationAddressView","y == "+ y + " MeasuredHeight = "+getMeasuredHeight() +  " maxScrollHeight = " + maxScrollHeight);
        int off = (int)(y * 0.1);

        if (Math.abs(off) > maxScrollHeight){
            currentHeight =  - maxScrollHeight;
        }else {
            currentHeight = off;
        }
        changeBG(currentHeight);
    }
    private void changeBG(int scroll){
        //获取到layoutParams然后改变属性，在设置回去
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
        layoutParams.topMargin =  scroll;
        setLayoutParams(layoutParams);
        invalidate();
    }
}
