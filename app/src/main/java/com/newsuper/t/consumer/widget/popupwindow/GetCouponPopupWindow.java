package com.newsuper.t.consumer.widget.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GetCouponBean;
import com.newsuper.t.consumer.function.top.adapter.GetCouponAdapter;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.widget.ListViewForScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/9 0009.
 * 首页领取优惠券
 */

public class GetCouponPopupWindow extends PopupWindow {
    private View view;
    private GetCouponPopupViewHolder holder;
    private GetCouponAdapter adapter;
    private Context context;
    public GetCouponPopupWindow(final Context context,View.OnClickListener onClickListener) {
        super(context);
       /* Fresco.initialize(context);
        setClippingEnabled(false);*/
        this.context = context;
       //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        // 设置PopupWindow弹出窗体的背景
        // 这样设置才能铺满屏幕，去掉这句话会出现缝隙
        this.setBackgroundDrawable(dw);
        view = LayoutInflater.from(context).inflate(R.layout.popupwindow_get_coupon, null);
        holder = new GetCouponPopupViewHolder(view);
        //设置PopupWindow的View
        this.setContentView(view);
        holder.ivDimiss.setOnClickListener(onClickListener);
        holder.btnLogin.setOnClickListener(onClickListener);
        adapter = new GetCouponAdapter(context);
    }
    //状态码，'OK'：正常显示且顾客已领取优惠券，'CUSTOMER_LOG_OUT':顾客未登录，
    // ’NOT_NORMAL‘:异常（未开启、不在有效时间内、顾客已领取），不需要显示优惠券
    public void showWithData(GetCouponBean.CouponInfo info){
        ArrayList<GetCouponBean.CouponInfoItem> couponInfoItems = new ArrayList<>();
        LogUtil.log("showWithData"," status == "+ info.status);
        if (!StringUtils.isEmpty(SharedPreferencesUtil.getToken())){
            holder.btnLogin.setVisibility(View.GONE);
        }else{
            holder.btnLogin.setVisibility(View.VISIBLE);
        }
        if ("1".equals(info.show_freshman_coupon) && info.freshman_coupons != null && info.freshman_coupons.size() > 0){
            couponInfoItems.addAll(info.freshman_coupons);
        }
        if (info.coupons != null && info.coupons.size() > 0){
            LogUtil.log("showWithData"," size "+ info.coupons.size());
            couponInfoItems.addAll(info.coupons);
        }
        if (couponInfoItems.size() == 0){
            return;
        }
        adapter.setCouponList(couponInfoItems);
        if (couponInfoItems.size() > 3){
            holder.lvCouponMore.setAdapter(adapter);
            holder.lvCouponMore.setVisibility(View.VISIBLE);
            holder.lvCoupon.setVisibility(View.GONE);
        }else {
            holder.lvCoupon.setAdapter(adapter);
            holder.lvCoupon.setVisibility(View.VISIBLE);
            holder.lvCouponMore.setVisibility(View.GONE);
        }
        show();

    }
    public void show(){
        showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    static class GetCouponPopupViewHolder {
      /*  @BindView(R.id.iv_title)
        SimpleDraweeView ivTitle;*/
        @BindView(R.id.lv_coupon_more)
        ListView lvCouponMore;
        @BindView(R.id.lv_coupon)
        ListViewForScrollView lvCoupon;
        @BindView(R.id.btn_login)
        Button btnLogin;
        @BindView(R.id.iv_dimiss)
        ImageView ivDimiss;
        @BindView(R.id.ll_bg)
        LinearLayout ll_bg;
        GetCouponPopupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
