package com.newsuper.t.consumer.widget.defineTopView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.WTopBean;
import com.newsuper.t.consumer.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyAgainView extends LinearLayout {
    private BuyAgainViewHolder viewHolder;
    private Context context;
    public BuyAgainView(Context context) {
        super(context);
        initView(context);
    }

    public BuyAgainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BuyAgainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_buy_again, null);
        viewHolder = new BuyAgainViewHolder(view);
        addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void setData(OnClickListener onClickListener, WTopBean.AgainOrderData orderData){
        viewHolder.tvBuyAgain.setText(orderData.text);
        viewHolder.tvBuyAgain.setOnClickListener(onClickListener);
    }
    public void setGoodsData(String url,String shop_name,String goods){
        viewHolder.tvShopName.setText(shop_name);
        viewHolder.tvGoodsName.setText(goods);
        UIUtils.glideAppLoad2(context,url,R.mipmap.store_logo_default,viewHolder.ivGoods);
    }

    static class BuyAgainViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_buy_again)
        TextView tvBuyAgain;

        BuyAgainViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
