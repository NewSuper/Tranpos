package com.newsuper.t.consumer.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.newsuper.t.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/30 0030.
 * 商品详情
 */

public class GoodsDetailPopupWindow extends PopupWindow {
    private Context context;
    private View view;
    private ViewHolder holder;
    public GoodsDetailPopupWindow(Context context,int w,int h) {
        super(context);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_goods_detail, null);
        holder = new ViewHolder(view);
        //设置PopupWindow的View
        this.setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(w * 5 / 6);
        //设置PopupWindow弹出窗体的高
        this.setHeight(h * 3 / 4);
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
    private void initView(final ViewHolder holder){
        holder.llTimeLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.llRule.getVisibility() == View.VISIBLE){
                    holder.llRule.setVisibility(View.GONE);
                    holder.llGoodsInfo.setVisibility(View.VISIBLE);
                    holder.ivPull.setImageResource(R.mipmap.icon_cart_pull_up);
                }else {
                    holder.llRule.setVisibility(View.VISIBLE);
                    holder.llGoodsInfo.setVisibility(View.GONE);
                    holder.ivPull.setImageResource(R.mipmap.icon_cart_pull_down);
                }
            }
        });
    }
    public void show(View view){
        backggroundAlpha(0.5f);
        showAtLocation(view, Gravity.CENTER,0,0);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        backggroundAlpha(1f);
    }

    static class ViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.iv_pull)
        ImageView ivPull;
        @BindView(R.id.ll_time_limit)
        LinearLayout llTimeLimit;
        @BindView(R.id.textView3)
        TextView textView3;
        @BindView(R.id.tv_activity_limit)
        TextView tvActivityLimit;
        @BindView(R.id.tv_limit)
        TextView tvLimit;
        @BindView(R.id.tv_activity_time)
        TextView tvActivityTime;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_rule)
        LinearLayout llRule;
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
        @BindView(R.id.tv_goods_info)
        TextView tvGoodsInfo;
        @BindView(R.id.ll_goods_info)
        LinearLayout llGoodsInfo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backggroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)context).getWindow().setAttributes(lp);
    }
}
