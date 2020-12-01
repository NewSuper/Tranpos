package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.ShopSearchBean;
import com.newsuper.t.consumer.function.selectgoods.activity.GoodsDetailActivity2;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopSearchGoodsAdapter extends BaseAdapter {
    private ArrayList<ShopSearchBean.FoodBean> food;
    private Context context;
    private boolean isShowAll = false;
    private String searchWord = "";
    private int color = Color.parseColor("#FB797B");
    private ShopSearchBean.ShopList shop;
    public ShopSearchGoodsAdapter(Context context,ShopSearchBean.ShopList shop) {
        this.context = context;
        this.shop = shop;
        this.food = shop.foodlist;
    }
    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
    public void setShowAll(boolean showAll) {
        isShowAll = showAll;
        notifyDataSetChanged();
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    @Override
    public int getCount() {
        if (food.size() > 2) {
            if (isShowAll) {
                return food.size();
            } else {
                return 2;
            }
        }
        return food.size();
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
        ShopSearchGoodsViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adpater_shop_search_goods, null);
            holder = new ShopSearchGoodsViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ShopSearchGoodsViewHolder)convertView.getTag();
        }
        final ShopSearchBean.FoodBean bean = food.get(position);

        if (!StringUtils.isEmpty(bean.name)){
            SpannableString ss = StringUtils.matcherSearchWord(color,bean.name,searchWord);
            if (ss != null){
                holder.tvGoodsName.setText(ss);
            }else {
                holder.tvGoodsName.setText(bean.name);
            }
        }
        if ("1".equals(bean.switch_discount)){
            if (!StringUtils.isEmpty(bean.discount_price)){
                holder.tvGoodsPrice.setText(bean.discount_price);
                holder.tvGoodsPrice.setVisibility(View.VISIBLE);
            }
            if (!StringUtils.isEmpty(bean.price)){
                holder.tvGoodsPrice2.setVisibility(View.VISIBLE);
                holder.tvGoodsPrice2.setText("￥"+bean.price);
            }else {
                holder.tvGoodsPrice2.setVisibility(View.INVISIBLE);
            }
        }else {
            if (!StringUtils.isEmpty(bean.price)){
                holder.tvGoodsPrice.setText(bean.price);
                holder.tvGoodsPrice.setVisibility(View.VISIBLE);
            }
        }

        String url = bean.img;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).error(R.mipmap.common_def_food).into(holder.ivGoods);
        }else {
            holder.ivGoods.setImageResource(R.mipmap.common_def_food);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, GoodsDetailActivity2.class);
//                intent.putExtra("goods_id",bean.food_id);
//                intent.putExtra("from_page","searchShop");
//                intent.putExtra("shop_id",bean.shop_id);
//               /* intent.putExtra("shop_info",shop);
//                intent.putExtra("type_id",bean.type_id);
//                intent.putExtra("second_type_id",bean.second_type_id);*/
//                context.startActivity(intent);
                Intent intent = new Intent(context, SelectGoodsActivity3.class);
                intent.putExtra("from_page","searchShop");
                intent.putExtra("shop_info",shop);
                if (shop.foodlist != null && shop.foodlist.size() > 0){
                    intent.putExtra("goods_id",bean.food_id);
                    intent.putExtra("type_id",shop.foodlist.get(0).type_id);
                    intent.putExtra("second_type_id",shop.foodlist.get(0).second_type_id);
                } else {
                    intent.putExtra("goods_id","");
                    intent.putExtra("type_id","");
                    intent.putExtra("second_type_id","");
                }
                intent.putExtra("keyWord",searchWord);
                LogUtil.log("searchGoodsType","1、传递keyword== "+searchWord+"; goods_id== "+bean.food_id);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ShopSearchGoodsViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_price_2)
        TextView tvGoodsPrice2;

        ShopSearchGoodsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
