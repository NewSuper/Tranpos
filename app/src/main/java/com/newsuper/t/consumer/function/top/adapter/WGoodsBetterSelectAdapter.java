package com.newsuper.t.consumer.function.top.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
//
import com.newsuper.t.R;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.WShopCart2;
import com.newsuper.t.consumer.function.selectgoods.inter.IGoodsToDetailPage;
import com.newsuper.t.consumer.function.selectgoods.inter.IWShopCart;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.UIUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WGoodsBetterSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private DecimalFormat df;
    private IGoodsToDetailPage iToGoodsDetailPage;
    private WShopCart2 wShopCart;
    private IWShopCart iWShopCart;
    private String flag;

    public WGoodsBetterSelectAdapter(Context mContext, ArrayList<GoodsListBean.GoodsInfo> data, WShopCart2 wShopCart, String flag) {
        this.mContext = mContext;
        this.data = data;
        this.wShopCart = wShopCart;
        this.flag = flag;
        df = new DecimalFormat("#0.00");
    }

    public void setWShopCartListener(IWShopCart iWShopCart) {
        this.iWShopCart = iWShopCart;
    }

    public void setIToDetailPage(IGoodsToDetailPage iToGoodsDetailPage) {
        this.iToGoodsDetailPage = iToGoodsDetailPage;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewGoods = LayoutInflater.from(mContext).inflate(R.layout.item_goods_youxian, parent, false);
        GoodsViewHolder goodsHolder = new GoodsViewHolder(viewGoods);
        return goodsHolder;
    }

    public class GoodsViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tvPrice;
        public TextView tvQi;
        public TextView tvCount;
        public TextView tvShowCount;
        public TextView tv_limit;
        public TextView tv_min_buy;
        public LinearLayout ll_edit_goods;
        public LinearLayout llMinus;
        public LinearLayout llAdd;
        public LinearLayout llTip;
        public LinearLayout ll_stop_sale;
        public LinearLayout ll_sale_out;
        public LinearLayout item_root;
        public RelativeLayout rl_select_nature;

        public GoodsViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvQi = (TextView) itemView.findViewById(R.id.tvQi);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            tv_limit = (TextView) itemView.findViewById(R.id.tv_limit);
            tvShowCount = (TextView) itemView.findViewById(R.id.tvShowCount);
            tv_min_buy = (TextView) itemView.findViewById(R.id.tv_min_buy);
            ll_sale_out = (LinearLayout) itemView.findViewById(R.id.ll_sale_out);
            ll_stop_sale = (LinearLayout) itemView.findViewById(R.id.ll_stop_sale);
            ll_edit_goods = (LinearLayout) itemView.findViewById(R.id.ll_edit_goods);
            llMinus = (LinearLayout) itemView.findViewById(R.id.llMinus);
            llAdd = (LinearLayout) itemView.findViewById(R.id.llAdd);
            llTip = (LinearLayout) itemView.findViewById(R.id.llTip);
            item_root = (LinearLayout) itemView.findViewById(R.id.item_root);
            rl_select_nature = (RelativeLayout) itemView.findViewById(R.id.rl_select_nature);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            GoodsViewHolder goodsHolder = (GoodsViewHolder) holder;
            GoodsListBean.GoodsInfo goods = data.get(position);
            int count = wShopCart.getGoodsCount(goods.id);
            if (count < 1) {
                goodsHolder.llMinus.setVisibility(View.GONE);
                goodsHolder.tvCount.setVisibility(View.GONE);
            } else {
                goodsHolder.llMinus.setVisibility(View.VISIBLE);
                goodsHolder.tvCount.setVisibility(View.VISIBLE);
                goodsHolder.tvCount.setText(count + "");
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GoodsViewHolder goodsHolder = (GoodsViewHolder) holder;
        if (goodsHolder != null) {
            final GoodsListBean.GoodsInfo goods = data.get(position);
            goodsHolder.tv_name.setText(goods.name);
            //加载网络图片
            UIUtils.glideAppLoad(mContext,goods.img,R.mipmap.common_def_food,goodsHolder.iv_img);
            goodsHolder.item_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != iToGoodsDetailPage) {
                        iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                    }
                }
            });

            int count = wShopCart.getGoodsCount(goods.id);

            String price = goods.price ;
            if ("1".equals(goods.member_price_used)) {
                price = MemberUtil.getMemberPriceStringWPage(goods);
            }
            if (null != goods.nature && goods.nature.size() > 0) {
                //商品默认属性价格
                if (!TextUtils.isEmpty(goods.min_price)) {
                    goodsHolder.tvPrice.setText(FormatUtil.numFormat(Float.parseFloat(price) + Float.parseFloat(goods.min_price) + ""));
                } else {
                    goodsHolder.tvPrice.setText(FormatUtil.numFormat(price));
                }
                goodsHolder.tvQi.setVisibility(View.VISIBLE);
            } else {
                goodsHolder.tvQi.setVisibility(View.GONE);
                goodsHolder.tvPrice.setText(FormatUtil.numFormat(price));
            }
            //先判断店铺是否正常营业
            if (1 == goods.worktime) {
                goodsHolder.llTip.setVisibility(View.GONE);
                //判断限购活动是否开启
                if ("1".equals(goods.is_limitfood)) {
                    goodsHolder.tv_limit.setVisibility(View.VISIBLE);
                    goodsHolder.rl_select_nature.setVisibility(View.GONE);
                    goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                } else {
                    goodsHolder.tv_limit.setVisibility(View.GONE);
                    if (null != goods.nature && goods.nature.size() > 0 && goods.is_nature.equals("1")) {
                        goodsHolder.rl_select_nature.setVisibility(View.VISIBLE);
                        goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                        if (count < 1) {
                            goodsHolder.tvShowCount.setVisibility(View.GONE);
                        } else {
                            goodsHolder.tvShowCount.setVisibility(View.VISIBLE);
                            goodsHolder.tvShowCount.setText(count + "");
                        }
                    } else {
                        goodsHolder.rl_select_nature.setVisibility(View.GONE);
                        goodsHolder.ll_edit_goods.setVisibility(View.VISIBLE);
                        if (count < 1) {
                            goodsHolder.llMinus.setVisibility(View.GONE);
                            goodsHolder.tvCount.setVisibility(View.GONE);
                        } else {
                            goodsHolder.llMinus.setVisibility(View.VISIBLE);
                            goodsHolder.tvCount.setVisibility(View.VISIBLE);
                            goodsHolder.tvCount.setText(count + "");
                        }
                    }
                }
                //判断商品状态
                if (goods.status.equals("NORMAL")) {
                    goodsHolder.ll_stop_sale.setVisibility(View.GONE);
                } else {
                    goodsHolder.ll_stop_sale.setVisibility(View.VISIBLE);
                    goodsHolder.rl_select_nature.setVisibility(View.GONE);
                    goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                }
            } else {
                goodsHolder.tv_limit.setVisibility(View.GONE);
                goodsHolder.rl_select_nature.setVisibility(View.GONE);
                goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                goodsHolder.llTip.setVisibility(View.VISIBLE);
                //判断商品状态
                if (goods.status.equals("NORMAL")) {
                    goodsHolder.ll_stop_sale.setVisibility(View.GONE);
                }else{
                    goodsHolder.ll_stop_sale.setVisibility(View.VISIBLE);
                }
            }
            if ("1".equals(goods.stockvalid)) {
                if (goods.stock > 0) {
                    goodsHolder.ll_sale_out.setVisibility(View.GONE);
                } else {
                    goodsHolder.ll_sale_out.setVisibility(View.VISIBLE);
                    goodsHolder.rl_select_nature.setVisibility(View.GONE);
                    goodsHolder.ll_edit_goods.setVisibility(View.GONE);
                    goodsHolder.tv_limit.setVisibility(View.GONE);
                }
            } else {
                goodsHolder.ll_sale_out.setVisibility(View.GONE);
            }
            //最小起购数
            if (!StringUtils.isEmpty(goods.min_buy_count) && Integer.parseInt(goods.min_buy_count) > 1){
                int c = Integer.parseInt(goods.min_buy_count);
                goodsHolder.tv_min_buy.setText(c + "份起购");
                goodsHolder.tv_min_buy.setVisibility(View.VISIBLE);
            }else {
                goodsHolder.tv_min_buy.setVisibility(View.INVISIBLE);
            }
            goodsHolder.llTip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(goods.worktime == 2){
                        if(null != iWShopCart){
                            iWShopCart.showTipInfo("店铺暂停营业，该商品暂停购买");
                        }
                    }else{
                        if(null!=iWShopCart){
                            iWShopCart.showTipInfo("店铺休息中，该商品暂停购买");
                        }
                    }
                }
            });
            goodsHolder.rl_select_nature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1".equals(goods.stockvalid)) {
                        if (goods.stock > 0) {
                            //显示属性弹框
                            if (iWShopCart != null) {
                                iWShopCart.showNatureDialog(goods, position, flag);
                            }
                        } else {
                            UIUtils.showToast("库存不足");
                        }
                    } else {
                        //显示属性弹框
                        if (iWShopCart != null) {
                            iWShopCart.showNatureDialog(goods, position, flag);
                        }
                    }
                }
            });

            goodsHolder.llAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (wShopCart.addShoppingSingle(goods)) {
                        notifyItemChanged(position, 0);
                        if (iWShopCart != null)
                            iWShopCart.add(goods);
                    }
                }
            });

            goodsHolder.llMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (wShopCart.subShoppingSingle(goods)) {
                        notifyItemChanged(position, 0);
                        if (iWShopCart != null)
                            iWShopCart.remove(goods);
                    }
                }
            });

            //点击item跳到商品详情页
            goodsHolder.item_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != iToGoodsDetailPage) {
                        iToGoodsDetailPage.toGoodsDetailPage(goods, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
