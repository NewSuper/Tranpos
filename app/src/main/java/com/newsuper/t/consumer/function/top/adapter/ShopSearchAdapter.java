package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.ShopSearchBean;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.widget.ActivityLabelLayout;
import com.newsuper.t.consumer.widget.ListViewForScrollView;
import com.newsuper.t.consumer.widget.NoticeTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newsuper.t.consumer.utils.Const.STRING_DIVER;

public class ShopSearchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ShopSearchBean.ShopList> shopLists;
    private String searchWord = "";
    private int color = Color.parseColor("#FB797B");
    public ShopSearchAdapter(Context context, ArrayList<ShopSearchBean.ShopList> shopLists) {
        super();
        this.context = context;
        this.shopLists = shopLists;
    }
    private String is_show_expected_delivery;
    public void setIs_show_expected_delivery(String is_show_expected_delivery) {
        this.is_show_expected_delivery = is_show_expected_delivery;
    }
    private String is_show_sales_volume;

    public void setIs_show_sales_volume(String is_show_sales_volume) {
        this.is_show_sales_volume = is_show_sales_volume;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
    @Override
    public int getCount() {
        return shopLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_shop_search2, null);
            mHolder = new MyViewHolder(convertView);
            convertView.setTag(mHolder);
        }else {
            mHolder = (MyViewHolder)convertView.getTag();
        }

        final  ShopSearchBean.ShopList shop = shopLists.get(position);
        loadImg(shop.shopimage,mHolder.ivStoreLogo);
        UIUtils.setTextViewFakeBold(mHolder.tvStoreName,true);
        if (!StringUtils.isEmpty(shop.shopname)){
            SpannableString ss = StringUtils.matcherSearchWord(color,shop.shopname,searchWord);
            if (ss != null){
                mHolder.tvStoreName.setText(ss);
            }else {
                mHolder.tvStoreName.setText(shop.shopname);
            }
        }
        if (!StringUtils.isEmpty(shop.shop_label)){
            mHolder.tvLabel.setVisibility(View.VISIBLE);
            mHolder.tvLabel.setText(shop.shop_label);
            mHolder.tvLabel.setBackgroundColor(ContextCompat.getColor(context,R.color.label_green));
        }else {
            mHolder.tvLabel.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(shop.expected_delivery_times) && "1".equals(is_show_expected_delivery)){
            String s = shop.expected_delivery_times +"分钟";
            if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)){
                s = s +"  "+shop.dis;
            }
            mHolder.tvDistance.setText(s);
        }else {
            if (!StringUtils.isEmpty(shop.dis) && "1".equals(shop.is_show_distance)){
                mHolder.tvDistance.setText(shop.dis);
            }else {
                mHolder.tvDistance.setText("");
            }

        }
        if ("1".equals(is_show_sales_volume)){
            mHolder.tvSalesCount.setText("已售" + shop.xiaoliang+STRING_DIVER);
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
        String price = "起送￥" +  FormatUtil.numFormat(shop.basicprice) +STRING_DIVER+ "配送￥" +  FormatUtil.numFormat(delivery_fee);
        mHolder.tvPrice.setText(price);
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
        final  MyViewHolder holder = mHolder;
        mHolder.llActivity.setGuideView(mHolder.ivMore);
        mHolder.ivMore.setVisibility(View.GONE);
        mHolder.llActivity.setRowListener(new ActivityLabelLayout.RowListener() {
            @Override
            public void onRow(int r) {
                if (r > 0){
                    holder.ivMore.setVisibility(View.VISIBLE);
                }else {
                    holder.ivMore.setVisibility(View.GONE);
                }
            }
        });
        mHolder.llActivity.setActivityLabelView(act,false);
        mHolder.isMore = false;
        mHolder.ivMore.setImageResource(R.mipmap.push);
        mHolder.ll_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.isMore){
                    holder.isMore = true;
                    holder.ivMore.setImageResource(R.mipmap.pull);
                    holder.llActivity.setActivityLabelView(act,true);
                }else {
                    holder.isMore = false;
                    holder.ivMore.setImageResource(R.mipmap.push);
                    holder.llActivity.setActivityLabelView(act,false);
                }
            }
        });
        //预订单
        if (!"1".equals(shop.worktime)){
            if (StringUtils.isEmpty(shop.outtime_info)){
                mHolder.llTip.setVisibility(View.GONE);
                mHolder.ll_shop_info.setVisibility(View.GONE);
                mHolder.tv_work_time.setVisibility(View.VISIBLE);
                switch (shop.worktime){
                    //已打烊
                    case "-1":
                        mHolder.tv_work_time.setText("店铺打烊");
                        break;
                    //休息中
                    case "0":
                        mHolder.tv_work_time.setText("店铺休息");
                        break;
                    //暂停营业
                    case "-2":
                        mHolder.tv_work_time.setText("暂停营业");
                        break;
                    //停止营业
                    case "2":
                        mHolder.tv_work_time.setText("停止营业");
                        break;
                }
            }else {
                mHolder.ll_shop_info.setVisibility(View.GONE);
                mHolder.tv_work_time.setVisibility(View.GONE);
                mHolder.ivShopStatus.setVisibility(View.INVISIBLE);
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
            mHolder.tv_work_time.setVisibility(View.GONE);
            mHolder.llTip.setVisibility(View.GONE);
            mHolder.ll_shop_info.setVisibility(View.VISIBLE);
        }
        String food_id = "";
        if (shop.foodlist != null && shop.foodlist.size() > 0 ){
            food_id = shop.foodlist.get(0).food_id;
            final ShopSearchGoodsAdapter goodsAdapter = new ShopSearchGoodsAdapter(context,shop);
            goodsAdapter.setSearchWord(searchWord);
            mHolder.lvGoods.setAdapter(goodsAdapter);
            if (shop.foodlist.size() > 2){
                mHolder.llStorePublicity.setVisibility(View.VISIBLE);
                mHolder.ivFood.setImageResource(R.mipmap.push);
                final TextView textView = mHolder.tvFood;
                final ImageView imageView = mHolder.ivFood;
                final String count = "查看其他"+(shop.foodlist.size() - 2)+"个商品";
                mHolder.tvFood.setText(count);
                mHolder.llStorePublicity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (shop.isShowAll){
                                goodsAdapter.setShowAll(false);
                            shop.isShowAll = false;
                            textView.setText(count);
                            imageView.setImageResource(R.mipmap.push);
                        }else {
                                goodsAdapter.setShowAll(true);
                            shop.isShowAll = true;
                            textView.setText("收起");
                            imageView.setImageResource(R.mipmap.pull);
                        }

                    }
                });
            }else {
                mHolder.llStorePublicity.setVisibility(View.GONE);
            }
        }else {
            mHolder.llStorePublicity.setVisibility(View.GONE);
        }

        final String id = food_id;
        mHolder.ll_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectGoodsActivity3.class);
                intent.putExtra("from_page","searchShop");
                intent.putExtra("shop_info",shop);
                if (shop.foodlist != null && shop.foodlist.size() > 0){
                    intent.putExtra("goods_id",id);
                    intent.putExtra("type_id",shop.foodlist.get(0).type_id);
                    intent.putExtra("second_type_id",shop.foodlist.get(0).second_type_id);
                }else {
                    intent.putExtra("goods_id","");
                    intent.putExtra("type_id","");
                    intent.putExtra("second_type_id","");
                }
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class MyViewHolder {
        @BindView(R.id.iv_store_logo)
        ImageView ivStoreLogo;
        @BindView(R.id.tv_store_name)
        TextView tvStoreName;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        /* @BindView(R.id.ratingBar)
        RatingBar ratingBar;
       @BindView(R.id.tv_star_count)
        TextView tvStarCount;*/
        @BindView(R.id.tv_sales_count)
        TextView tvSalesCount;
        @BindView(R.id.tv_special_delivery)
        TextView tvSpecialDelivery;
        @BindView(R.id.ll_activity)
        ActivityLabelLayout llActivity;
        @BindView(R.id.ll_act)
        LinearLayout ll_act;
        @BindView(R.id.tv_food)
        TextView tvFood;
        @BindView(R.id.iv_food)
        ImageView ivFood;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.ll_store_publicity)
        LinearLayout llStorePublicity;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.tv_label)
        TextView tvLabel;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_shop_status)
        ImageView ivShopStatus;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        @BindView(R.id.ll_tip)
        LinearLayout llTip;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.ll_shop)
        LinearLayout ll_shop;
        @BindView(R.id.ll_yu)
        TextView tvYu;
        @BindView(R.id.tv_work_time)
        TextView tv_work_time;
        @BindView(R.id.ll_gonggao)
        LinearLayout ll_gonggao;
        @BindView(R.id.ll_shop_info)
        LinearLayout ll_shop_info;
        @BindView(R.id.tv_gonggao)
        NoticeTextView tv_gonggao;
        @BindView(R.id.lv_goods)
        ListViewForScrollView lvGoods;
        boolean isMore = false;
        View mView;
        public MyViewHolder(View view) {
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
    private void loadImg(String url, final ImageView view) {
        if (StringUtils.isEmpty(url)){
            view.setImageResource(R.mipmap.store_logo_default);
        }else {
            if (!url.startsWith("http")) {
                url = RetrofitManager.BASE_IMG_URL_SMALL + url;
            }
            //加载网络图片
//            UIUtils.loadImageView(context,url,view);
            Picasso.with(context).load(url).into(view);
        }
    }
}
