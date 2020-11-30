package com.newsuper.t.consumer.function.top.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
//
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.TopBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.widget.ActivityLabelLayout;
import com.xunjoy.lewaimai.consumer.widget.NoticeTextView;
import com.xunjoy.lewaimai.consumer.widget.RatingBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import static com.xunjoy.lewaimai.consumer.utils.Const.STRING_DIVER;


/**
 * 首页店铺列表
 */
public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 1;
    private boolean isLoadAll = false;
    private Context context;
    private ArrayList<TopBean.ShopList> shopLists;
    public ShopOnClickListener shopOnClickListener;

    public ShopAdapter(Context context, ArrayList<TopBean.ShopList> shopLists) {
        super();
        this.context = context;
        this.shopLists = shopLists;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() > 0 && getItemCount() == position + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return shopLists.size() > 0 ? shopLists.size() + 1 : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = LayoutInflater.from(context).inflate(R.layout.adapter_shop_list, parent, false);
            return new ShopViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_footer_load_more, parent, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if ( holder instanceof FooterViewHolder){
            FooterViewHolder holder1 = (FooterViewHolder)holder;
            if (getItemCount() >= 10){
                if (isLoadAll){
                    holder1.tvLoadMore.setText("已全部加载完");
                    holder1.llLogo.setVisibility(View.VISIBLE);
                    holder1.tvLoadMore.setVisibility(View.INVISIBLE);
                }else {
                    holder1.tvLoadMore.setText("加载中...");
                    holder1.llLogo.setVisibility(View.GONE);
                    holder1.tvLoadMore.setVisibility(View.VISIBLE);
                }
            }else {
                holder1.tvLoadMore.setText("");
                holder1.llLogo.setVisibility(View.VISIBLE);
                holder1.tvLoadMore.setVisibility(View.INVISIBLE);
            }
            holder1.llLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shopOnClickListener != null){
                        shopOnClickListener.onLogoClick();
                    }
                }
            });
            return;
        }
        final ShopViewHolder mHolder = (ShopViewHolder) holder;
        final TopBean.ShopList shop = shopLists.get(position);
        loadImageView(shop.shopimage, mHolder.ivStoreLogo);
        if (!StringUtils.isEmpty(shop.shop_label)){
            mHolder.tvLabel.setVisibility(View.VISIBLE);
            mHolder.tvLabel.setText(shop.shop_label);
            mHolder.tvLabel.setBackgroundColor(ContextCompat.getColor(context,R.color.label_green));
        }else {
            mHolder.tvLabel.setVisibility(View.GONE);
        }

        //店铺描述
        if (StringUtils.isEmpty(shop.shopdes)){
            mHolder.ll_gonggao.setVisibility(View.GONE);
        }else {
            mHolder.ll_gonggao.setVisibility(View.VISIBLE);
            mHolder.tv_gonggao.setText("“"+shop.shopdes+"”");
        }


        boolean isTop = false;
        //已打烊
        if (shop.worktime.equals("-1")){
            isTop = true;
            mHolder.ivShopStatus.setVisibility(View.VISIBLE);
            mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_out_time);
        }
        //休息中
        else if (shop.worktime.equals("0")){
            isTop = true;
            mHolder.ivShopStatus.setVisibility(View.VISIBLE);
            mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_rest);
        }
        //营业中
        else if (shop.worktime.equals("1")){
            mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
        }
        //暂停营业
        else if (shop.worktime.equals("-2")){
            isTop = true;
            mHolder.ivShopStatus.setVisibility(View.VISIBLE);
            mHolder.ivShopStatus.setImageResource(R.mipmap.storelist_icon_stop);
        }
        //停止营业
        else if (shop.worktime.equals("2")){
            isTop = true;
            mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
        }else {
            mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
        }
        if (isTop){
            mHolder.ll_main.setAlpha(0.5f);
        }else {
            mHolder.ll_main.setAlpha(1);
        }
        //预订单
        if (!"1".equals(shop.worktime)){
            if (StringUtils.isEmpty(shop.outtime_info)){
                mHolder.llTip.setVisibility(View.GONE);
            }else {
                mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
                mHolder.ll_main.setAlpha(1);
                mHolder.llTip.setVisibility(View.VISIBLE);
                if (shop.outtime_info.contains(",")){
                    String s[] = shop.outtime_info.split(",");
                    mHolder.tvYu.setText(s[0]);
                    mHolder.tvTip.setText(s[1]);
                }else {
                    if (shop.outtime_info.contains("，")){
                        String s[] = shop.outtime_info.split("，");
                        mHolder.tvYu.setText(s[0]);
                        mHolder.tvTip.setText(s[1]);
                    }else {
                        mHolder.tvYu.setText("现在预定");
                        mHolder.tvTip.setText(shop.outtime_info);
                    }
                }
            }
        }else {
            mHolder.llTip.setVisibility(View.GONE);
        }
        mHolder.tvStoreName.setText(shop.shopname);
        UIUtils.setTextViewFakeBold(mHolder.tvStoreName,true);
        if (!StringUtils.isEmpty(shop.expected_delivery_times) && "1".equals(shop.is_show_expected_delivery)){
            String s = shop.expected_delivery_times +"分钟";
            if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)){
                s = s +" | "+shop.dis;
            }
            mHolder.tvDistance.setText(s);
        }else {
            if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)){
                mHolder.tvDistance.setText(shop.dis);
            }else {
                mHolder.tvDistance.setText("");
            }

        }

        mHolder.tvStarCount.setText(shop.commentgrade);
        if ("1".equals(shop.is_show_sales_volume)){
            mHolder.tvSalesCount.setText("已售" + shop.xiaoliang);
            mHolder.tvSalesCount.setVisibility(View.VISIBLE);
        }else {
            mHolder.tvSalesCount.setVisibility(View.GONE);
        }
        if (StringUtils.isEmpty(shop.delivery_service)) {
            mHolder.tvSpecialDelivery.setVisibility(View.GONE);
        } else {
            mHolder.tvSpecialDelivery.setVisibility(View.VISIBLE);
            mHolder.tvSpecialDelivery.setText(shop.delivery_service);
        }
        String delivery_fee = shop.delivery_fee;
        if (StringUtils.isEmpty(delivery_fee)){
            delivery_fee = "0";
        }
        String price = "起送￥" +  FormatUtil.numFormat(shop.basicprice) + "  配送￥" +  FormatUtil.numFormat(delivery_fee);
        mHolder.tvPrice.setText(price);

        if (!StringUtils.isEmpty(shop.commentgrade)) {
            mHolder.ratingBar.setStar(Float.parseFloat(shop.commentgrade));
        }
        mHolder.llActivity.removeAllViews();
        String first_order = shop.activity_info.first_order;
        String shop_first_order = shop.activity_info.shop_first_discount;
        String consume = shop.activity_info.consume;
        String coupon = shop.activity_info.coupon;
        String shop_discount = shop.activity_info.shop_discount;
        String deliveryFee = shop.activity_info.delivery_fee;
        String member = shop.activity_info.member;
        String manzeng = shop.activity_info.manzeng;
        String open_selftake = shop.open_selftake;
        final ArrayList<String> act = new ArrayList<>();
        if (!StringUtils.isEmpty(consume)){
            act.addAll(shop.activity_info.consume_arr);
        }
        if (!StringUtils.isEmpty(shop_first_order)){
            act.add(shop_first_order);
        }
        if (!StringUtils.isEmpty(first_order)){
            act.add(first_order);
        }
        if (!StringUtils.isEmpty(coupon)){
            act.add(coupon);
        }
        if (!StringUtils.isEmpty(shop_discount)){
            act.add(shop_discount);
        }
        if (!StringUtils.isEmpty(deliveryFee)){
            act.add(deliveryFee);
        }
        if (!StringUtils.isEmpty(member)){
            act.add(member);
        }
        if ( !StringUtils.isEmpty(manzeng)){
            act.add(manzeng);
        }
        if ("1".equals(open_selftake)){
            act.add("到店自取");
        }

        mHolder.llActivity.setGuideView(mHolder.ivMore);
        mHolder.ivMore.setVisibility(View.GONE);
        mHolder.llActivity.setRowListener(new ActivityLabelLayout.RowListener() {
            @Override
            public void onRow(int r) {
                if (r > 0){
                    mHolder.ivMore.setVisibility(View.VISIBLE);
                }else {
                    mHolder.ivMore.setVisibility(View.GONE);
                }
            }
        });
        mHolder.llActivity.setActivityLabelView(act,false);
        mHolder.isMore = false;
        mHolder.ivMore.setImageResource(R.mipmap.push);
        mHolder.ll_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHolder.isMore){
                    mHolder.isMore = true;
                    mHolder.ivMore.setImageResource(R.mipmap.pull);
                    mHolder.llActivity.setActivityLabelView(act,true);
                }else {
                    mHolder.isMore = false;
                    mHolder.ivMore.setImageResource(R.mipmap.push);
                    mHolder.llActivity.setActivityLabelView(act,false);
                }
            }
        });
        mHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopOnClickListener != null){
                    shopOnClickListener.onShopOnClick(shop);
                }
            }
        });
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_store_logo)
        ImageView ivStoreLogo;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tv_star_count)
        TextView tvStarCount;
        @BindView(R.id.tv_sales_count)
        TextView tvSalesCount;
        @BindView(R.id.tv_special_delivery)
        TextView tvSpecialDelivery;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.iv_shop_status)
        ImageView ivShopStatus;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.tv_time)
        TextView tvTime;
       /* @BindView(R.id.ll_activity)
        LinearLayout llActivity;*/
        @BindView(R.id.ll_activity)
        ActivityLabelLayout llActivity;
        @BindView(R.id.ll_act)
        LinearLayout ll_act;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        @BindView(R.id.ll_tip)
        LinearLayout llTip;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.ll_yu)
        TextView tvYu;
        @BindView(R.id.ll_gonggao)
        LinearLayout ll_gonggao;
        @BindView(R.id.tv_gonggao)
        NoticeTextView tv_gonggao;
        boolean isMore = false;
        View mView;

        public ShopViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
    private void loadImageView(String url, final ImageView view) {
        if (StringUtils.isEmpty(url)){
            view.setImageResource(R.mipmap.store_logo_default);
        }else {
            if (!url.startsWith("http")) {
                url = RetrofitManager.BASE_IMG_URL_SMALL + url;
            }
            //加载网络图片
            UIUtils.glideAppLoadShopImg(context,url,R.mipmap.store_logo_default,view);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_load_more)
        TextView tvLoadMore;
        @BindView(R.id.ll_logo)
        LinearLayout llLogo;


        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
    public void setIsLoadAll(boolean b){
        this.isLoadAll = b;
        notifyDataSetChanged();
    }

    public void setShopOnClickListener(ShopOnClickListener shopOnClickListener) {
        this.shopOnClickListener = shopOnClickListener;
    }

    public interface ShopOnClickListener{
        void onShopOnClick(TopBean.ShopList shopList);
        void onLogoClick();
    }
}