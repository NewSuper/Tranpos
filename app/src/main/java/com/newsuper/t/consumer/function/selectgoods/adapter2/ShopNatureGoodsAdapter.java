package com.newsuper.t.consumer.function.selectgoods.adapter2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.bean.ShopCart2;
import com.newsuper.t.consumer.function.selectgoods.inter.IDeleteNatureGoods;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.MemberUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.widget.FlowLayout.FlowLayout;
import com.newsuper.t.consumer.widget.FlowLayout.TagAdapter;
import com.newsuper.t.consumer.widget.FlowLayout.TagFlowLayout;
import com.newsuper.t.consumer.widget.FlowLayout.TagView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopNatureGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private TextView tvTotalPrice;
    private ShopCart2 shopCart;
    private IDeleteNatureGoods iDeleteNatureGoods;
    private int goodsPosition;
    private boolean isEffectiveVip;

    public ShopNatureGoodsAdapter(Context context, ArrayList<GoodsListBean.GoodsInfo> data, ShopCart2 shopCart, TextView tvTotalPrice) {
        this.context = context;
        this.data = data;
        this.tvTotalPrice = tvTotalPrice;
        this.shopCart = shopCart;
        isEffectiveVip = SharedPreferencesUtil.getIsEffectVip();
    }

    public void setGoodsPosition(int position) {
        this.goodsPosition = position;
    }

    public void setDeletNatureListener(IDeleteNatureGoods iDeleteNatureGoods) {
        this.iDeleteNatureGoods = iDeleteNatureGoods;
    }

    public void updateTotalPrice() {
        //判断是否是套餐集合
        if (data.size() > 0) {
            GoodsListBean.GoodsInfo goodsInfo = data.get(0);
            if (shopCart.natureTotalPrice.size() > 0) {
                if (shopCart.natureTotalPrice.containsKey(goodsInfo.id)) {
                    float totalPrice = shopCart.natureTotalPrice.get(goodsInfo.id);
                    if (totalPrice > 0) {
                        tvTotalPrice.setText("￥" + FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(totalPrice)))));
                    } else {
                        tvTotalPrice.setText("");
                    }
                } else {
                    tvTotalPrice.setText("");
                }
            } else {
                tvTotalPrice.setText("");
            }
        }
    }

    public class NatureViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_num)
        public TextView tvNum;
        @BindView(R.id.tv_price)
        public TextView tvPrice;
        @BindView(R.id.tv_delete)
        public TextView tvDelete;
        @BindView(R.id.tv_begin_price)
        public TextView tv_begin_price;
        @BindView(R.id.ll_nature_container)
        public LinearLayout natureContainer;
        @BindView(R.id.ll_layout)
        public LinearLayout ll_layout;
        @BindView(R.id.ll_tip)
        public LinearLayout ll_tip;
        @BindView(R.id.rl_header)
        public RelativeLayout rl_header;

        public NatureViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nature_goods, parent, false);
        return new NatureViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final NatureViewHolder natureHolder = (NatureViewHolder) holder;
        if (null != natureHolder) {
            final GoodsListBean.GoodsInfo goods = data.get(position);
            natureHolder.ll_layout.setContentDescription(position + "");
            natureHolder.tvNum.setText("第" + (position + 1) + "份价格：");
            LogUtil.log("addGoodsList","addGoodsList == " + goods.index);
            if (TextUtils.isEmpty(goods.index)) {
                natureHolder.ll_tip.setVisibility(View.VISIBLE);
                natureHolder.rl_header.setVisibility(View.GONE);
            } else {
                natureHolder.ll_tip.setVisibility(View.GONE);
                natureHolder.rl_header.setVisibility(View.VISIBLE);
            }
            natureHolder.natureContainer.removeAllViews();
            for (final GoodsListBean.GoodsNature goodsNature : goods.nature) {
                final View valueView = View.inflate(context, R.layout.item_nature_value, null);
                TextView valueName = (TextView) valueView.findViewById(R.id.valueName);
                valueName.setText(goodsNature.naturename);
                TagFlowLayout valueFlowLayoutvalue = (TagFlowLayout) valueView.findViewById(R.id.valueFlowLayout);
                TagAdapter<GoodsListBean.GoodsNatureData> valueAdapter = new TagAdapter<GoodsListBean.GoodsNatureData>(goodsNature.data) {
                    @Override
                    public View getView(FlowLayout parent, int position, GoodsListBean.GoodsNatureData goodsNatureData) {
                        TextView textView = (TextView) View.inflate(context, R.layout.item_tv_nature, null);
                        textView.setText(goodsNatureData.naturevalue);
                        textView.setTextSize(14);
                        return textView;
                    }
                };
                valueFlowLayoutvalue.setAdapter(valueAdapter);
                if (!TextUtils.isEmpty(goodsNature.maxchoose)) {
                    valueFlowLayoutvalue.setMaxSelectCount(Integer.parseInt(goodsNature.maxchoose));
                } else {
                    valueFlowLayoutvalue.setMaxSelectCount(1);
                }
                valueFlowLayoutvalue.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                    @Override
                    public void onSelected(Set<Integer> selectPosSet) {
                        //记录属性变化的价格
                        float valuePrice = 0;
                        //选中属性；
                        for (int i = 0; i < goodsNature.data.size(); i++) {
                            if (selectPosSet.contains(i)) {
                                if (!goodsNature.data.get(i).is_selected) {
                                    valuePrice += Float.parseFloat(goodsNature.data.get(i).price);
                                }
                                goodsNature.data.get(i).is_selected = true;
                            } else {
                                if (goodsNature.data.get(i).is_selected) {
                                    valuePrice -= Float.parseFloat(goodsNature.data.get(i).price);
                                }
                                goodsNature.data.get(i).is_selected = false;
                            }
                        }
                        if (!TextUtils.isEmpty(goods.index)) {
                            if (shopCart.natureTotalPrice.containsKey(goods.id)) {
                                float total = shopCart.natureTotalPrice.get(goods.id);
                                total += valuePrice;
                                shopCart.natureTotalPrice.put(goods.id, total);
                            }
                            shopCart.updateGoods(goods);
                            BaseApplication.greenDaoManager.updateNatureGoods(goods);//从本地中更新属性
                            updateTotalPrice();
                            shopCart.shoppingTotalPrice += valuePrice;
                            if (null != iDeleteNatureGoods) {
                                iDeleteNatureGoods.updateTotalPrice();
                            }
                        }
                        //重新计算商品的价格
                        float price = 0;
                        if ("1".equals(goods.switch_discount)) {
                            if(goods.isUseDiscount){
                                price=Float.parseFloat(goods.price);
                            }else{
                                price=Float.parseFloat(goods.formerprice);
                            }
                        }else{
                            if ("1".equals(goods.member_price_used)) {
                                if (isEffectiveVip) {
                                    price = MemberUtil.getMemberPriceFloat(goods.member_grade_price);
                                } else {
                                    price = Float.parseFloat(goods.price);
                                }
                            } else {
                                price = Float.parseFloat(goods.price);
                            }
                        }
                        for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                            for (GoodsListBean.GoodsNatureData natureData : goodsNature.data) {
                                if (natureData.is_selected) {
                                    if (!TextUtils.isEmpty(natureData.price)) {
                                        price += Float.parseFloat(natureData.price);
                                    }
                                }
                            }
                        }
                        if (TextUtils.isEmpty(goods.index)) {
                            natureHolder.tv_begin_price.setText(FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(price)))));
                        }
                        natureHolder.tvPrice.setText(FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(price)))));
                    }
                });
                for (int i = 0; i < goodsNature.data.size(); i++) {
                    if (goodsNature.data.get(i).is_selected) {
                        ((TagView) valueFlowLayoutvalue.getChildAt(i)).setChecked(true);
                        valueFlowLayoutvalue.mSelectedView.add(i);
                    }
                }
                natureHolder.natureContainer.addView(valueView);
            }


            float unintPrice = 0;
            if ("1".equals(goods.switch_discount)) {
                if(goods.isUseDiscount){
                    unintPrice=Float.parseFloat(goods.price);
                }else{
                    unintPrice=Float.parseFloat(goods.formerprice);
                }
            }else{
                if ("1".equals(goods.member_price_used)) {
                    if (isEffectiveVip) {
                        unintPrice = MemberUtil.getMemberPriceFloat(goods.member_grade_price);
                    } else {
                        unintPrice = Float.parseFloat(goods.price);
                    }
                } else {
                    unintPrice = Float.parseFloat(goods.price);
                }
            }
            if (null != goods.nature && goods.nature.size() > 0) {
                for (GoodsListBean.GoodsNature goodsNature : goods.nature) {
                    for (GoodsListBean.GoodsNatureData data : goodsNature.data) {
                        if (data.is_selected) {
                            unintPrice += Float.parseFloat(data.price);
                        }
                    }
                }
            }
            if (TextUtils.isEmpty(goods.index)) {
                natureHolder.tv_begin_price.setText(FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(unintPrice)))));
            }
            natureHolder.tvPrice.setText(FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(unintPrice)))));
            natureHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != iDeleteNatureGoods) {
                        iDeleteNatureGoods.deleteNatureGoods(goods, position, goodsPosition);
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

