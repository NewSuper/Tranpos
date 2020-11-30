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


public class ShopTitleView extends LinearLayout {
    private LinearLayout llShop;
    private TextView tvShop;
    public ShopTitleView(Context context) {
        super(context);
        initView(context);
    }
    public ShopTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ShopTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    public void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_go_to_shop, null);
        tvShop = (TextView)view.findViewById(R.id.tv_shop_name) ;
        llShop = (LinearLayout) view.findViewById(R.id.ll_shop_name) ;
        addView(view, new LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
    }
    public void setShopName(String name) {
        if (tvShop != null){
            tvShop.setText(name);
        }
    }
    public void setOnClickListener(OnClickListener listener){
        if (llShop != null){
            llShop.setOnClickListener(listener);
        }
    }
}
