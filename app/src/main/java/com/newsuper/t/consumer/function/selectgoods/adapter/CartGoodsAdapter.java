package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.bean.CartGoodsModel;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.manager.RetrofitManager;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/13 0013.
 * 购物车商品
 */

public class CartGoodsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CartGoodsModel> models;
    private boolean isShowMemberPrice;
    public CartGoodsAdapter(Context context,ArrayList<CartGoodsModel> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodsViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_cart_goods, null);
            holder = new GoodsViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (GoodsViewHolder)convertView.getTag();
        }
        CartGoodsModel info = models.get(position);
        String name =  info.name;
        if (!StringUtils.isEmpty(info.natureName)){
            name = info.name +"("+ info.natureName+")";
        }
        holder.tvGoodsName.setText(name);
        LogUtil.log("isSpecialGoods","position == "+position  +" formerprice" + info.formerprice +" price" + info.price);
        //非折扣商品
        holder.tvGoodsDis.setVisibility(View.GONE);
        if ("1".equals(info.switch_discount)){
            if (info.isSpecialGoods){
                holder.tvGoodsName.setText(name);
                holder.tvGoodsDis.setVisibility(View.VISIBLE);
                double d = FormatUtil.numDouble(info.price);
                holder.tvGoodsPrice.setText("￥"+ FormatUtil.numFormat(df.format(d)));
                if (info.natruePrice > 0){
                    String s = getFormatData((d + info.natruePrice))+ "";
                    holder.tvGoodsPrice.setText("￥" + FormatUtil.numFormat(s) );
                }
            }else {
                double d = FormatUtil.numDouble(info.formerprice);
                holder.tvGoodsPrice.setText("￥"+ FormatUtil.numFormat(df.format(d)));
                if (info.natruePrice > 0){
                    String s = getFormatData((d + info.natruePrice))+ "";
                    holder.tvGoodsPrice.setText("￥" + FormatUtil.numFormat(s) );
                }
            }
        }else {
            //显示会员价
            if (isShowMemberPrice && 0 == info.type && "1".equals(info.member_price_used)){
                holder.tvGoodsPrice.setText("￥"+ FormatUtil.numFormat(info.member_price));
                double d = FormatUtil.numDouble(info.member_price);
                if (info.natruePrice > 0){
                    String s = getFormatData((d + info.natruePrice))+ "";
                    holder.tvGoodsPrice.setText("￥" + FormatUtil.numFormat(s) );
                }
            }else {
                double d = FormatUtil.numDouble(info.price);
                holder.tvGoodsPrice.setText("￥"+ FormatUtil.numFormat(df.format(d)));
                if (info.natruePrice > 0){
                    String s = getFormatData((d + info.natruePrice))+ "";
                    holder.tvGoodsPrice.setText("￥" + FormatUtil.numFormat(s) );
                }
            }
        }
        holder.tvGoodsCount.setText("x"+info.count);
        String url = info.img;
        if (!StringUtils.isEmpty(url)){
            if (!url.startsWith("http")){
                url = RetrofitManager.BASE_IMG_URL + url;
            }
            Picasso.with(context).load(url).error(R.mipmap.common_def_food).into(holder.ivGoods);
        }else {
            holder.ivGoods.setImageResource(R.mipmap.common_def_food);
        }
        return convertView;
    }
    public void showMemberPrice(boolean isShowMemberPrice){
        this.isShowMemberPrice = isShowMemberPrice;
        notifyDataSetChanged();
    }
    DecimalFormat df = new DecimalFormat("#0.00");
    public double getFormatData(double d) {
        return Double.parseDouble(df.format(d));
    }

    static class GoodsViewHolder {
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_dis)
        TextView tvGoodsDis;

        GoodsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
