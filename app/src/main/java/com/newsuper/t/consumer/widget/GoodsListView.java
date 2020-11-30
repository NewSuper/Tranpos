package com.newsuper.t.consumer.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.CartGoodsModel;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class GoodsListView extends LinearLayout {
    private Context context;
    DecimalFormat df = new DecimalFormat("#0.00");
    public GoodsListView(Context context) {
        super(context);
        this.context = context;

    }

    public GoodsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public GoodsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setGoods(ArrayList<CartGoodsModel> models, boolean isShowMemberPrice) {
        if (models == null){
            return;
        }
        setOrientation(VERTICAL);
        removeAllViews();
        for(int i = 0 ; i < models.size();i++){
            View view = LayoutInflater.from(context).inflate(R.layout.layout_goods_list, null);
            CartGoodsModel info = models.get(i);
            GoodsViewHolder holder = new GoodsViewHolder(view);
            String name = info.name;
            if (!StringUtils.isEmpty(info.natureName)){
               holder.tvGoodsNature.setText(info.natureName);
            }else {
                holder.tvGoodsNature.setText("");
            }
            holder.tvGoodsName.setText(name);
            holder.tvGoodsCount.setText("x" + info.count);
            //非折扣商品
            if ("1".equals(info.switch_discount)){
                if (info.isSpecialGoods){
                    holder.tvGoodsName.setText(name);
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
           /* double allPrice = (StringUtils.isEmpty(info.price)? 0:Float.parseFloat(info.price) + info.natruePrice);
            allPrice = getFormatData(allPrice);
            holder.tvGoodsPrice.setText("￥" + FormatUtil.numFormat(allPrice+""));*/
            String url = info.img;
            if (url != null){
                if (!url.startsWith("http")){
                    url = RetrofitManager.BASE_IMG_URL + url;
                }
                LogUtil.log("GoodsListView","url == "+url);
                Picasso.with(context).load(url).error(R.mipmap.common_def_food).into(holder.ivGoods);
            }else {
                holder.ivGoods.setImageResource(R.mipmap.common_def_food);
            }

            addView(view,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
            if (i < models.size() - 1){
                TextView textView = new TextView(context);
                textView.setHeight(1);
                textView.setWidth(view.getWidth());
                textView.setBackgroundColor(Color.parseColor("#EBEBEB"));
                addView(textView);
            }
        }
        LogUtil.log("GoodsListView","goods ---- ");
    }

    static class GoodsViewHolder {
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.tv_goods_nature)
        TextView tvGoodsNature;
        @BindView(R.id.iv_goods)
        ImageView ivGoods;

        GoodsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public double getFormatData( double d){
        return Double.parseDouble(df.format(d));
    }
}
