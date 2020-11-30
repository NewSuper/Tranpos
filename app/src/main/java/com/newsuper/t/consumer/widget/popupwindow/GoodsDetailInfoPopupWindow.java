package com.newsuper.t.consumer.widget.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.newsuper.t.R;
import com.newsuper.t.consumer.widget.HeaderChangeScrollview;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

public class GoodsDetailInfoPopupWindow extends PopupWindow {
    private Context context;
    private View view;
    private ViewHolder holder;
    private int alpha = 255;
    public GoodsDetailInfoPopupWindow(Context context) {
        super(context);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_goods_info, null);
        holder = new ViewHolder(view);
        //设置PopupWindow的View
        this.setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        initView(holder);
    }

    private void initView(final ViewHolder holder) {
        holder.nestedScrollView.setOnTouchEventMoveListenre(new HeaderChangeScrollview.OnTouchEventMoveListenre() {
            @Override
            public void onSlideUp(int mOriginalHeaderHeight, int mHeaderHeight) {

            }

            @Override
            public void onSlideDwon(int mOriginalHeaderHeight, int mHeaderHeight) {

            }

            @Override
            public void onSlide(int alpha) {
                Log.i("GoodsDetailInfo","alpha == "+alpha);
            }
        });
    }

    public void show(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        showAtLocation(v, Gravity.NO_GRAVITY,location[0], location[1] - getHeight());

    }

    static class ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_today_price)
        TextView tvTodayPrice;
        @BindView(R.id.tv_sale_count)
        TextView tvSaleCount;
        @BindView(R.id.tv_now_price)
        TextView tvNowPrice;
        @BindView(R.id.tv_before_price)
        TextView tvBeforePrice;
        @BindView(R.id.iv_delete_goods)
        ImageView ivDeleteGoods;
        @BindView(R.id.iv_add_goods)
        ImageView ivAddGoods;
        @BindView(R.id.ll_add_goods)
        LinearLayout llAddGoods;
        @BindView(R.id.ll_add_cart)
        LinearLayout llAddCart;
        @BindView(R.id.tv_vip_price)
        TextView tvVipPrice;
        @BindView(R.id.tv_goods_info)
        TextView tvGoodsInfo;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.ll_shop)
        LinearLayout llShop;
        @BindView(R.id.nestedScrollView)
        HeaderChangeScrollview nestedScrollView;
        @BindView(R.id.iv_back)
        ImageView ivBack;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rl_toolbar)
        RelativeLayout rlToolbar;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
