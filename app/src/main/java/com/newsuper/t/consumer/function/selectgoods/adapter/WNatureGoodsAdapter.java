package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.newsuper.t.consumer.bean.WShopCart2;
import com.newsuper.t.consumer.function.selectgoods.inter.IWDeleteNatureGoods;
import com.newsuper.t.consumer.utils.FormatUtil;
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

public class WNatureGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<GoodsListBean.GoodsInfo> data;
    private DecimalFormat df=new DecimalFormat("#0.00");
    private TextView tvTotalPrice;
    private WShopCart2 shopCart;
    private IWDeleteNatureGoods iwDeleteNatureGoods;
    private int goodsPosition;
    private String flag;
    private boolean isEffectiveVip;

    public WNatureGoodsAdapter(Context context, ArrayList<GoodsListBean.GoodsInfo> data, WShopCart2 shopCart, TextView tvTotalPrice,String flag) {
        this.context = context;
        this.data = data;
        this.tvTotalPrice = tvTotalPrice;
        this.shopCart=shopCart;
        this.flag=flag;
        isEffectiveVip= SharedPreferencesUtil.getIsEffectVip();
    }
    public void setGoodsPosition(int position){
        this.goodsPosition=position;
    }

    public void setDeletNatureListener(IWDeleteNatureGoods iwDeleteNatureGoods){
        this.iwDeleteNatureGoods=iwDeleteNatureGoods;
    }

    public void updateTotalPrice(){
        //判断是否是套餐集合
        if(data.size()>0){
            GoodsListBean.GoodsInfo goodsInfo=data.get(0);
            if (null!=goodsInfo.packageNature&&goodsInfo.packageNature.size()>0){
                float totalPrice=0;
                if ("1".equals(goodsInfo.switch_discount)){
                    if(!TextUtils.isEmpty(goodsInfo.num_discount)&&Integer.parseInt(goodsInfo.num_discount)>0){
                        if(data.size()<=Integer.parseInt(goodsInfo.num_discount)){
                            totalPrice = Float.parseFloat(goodsInfo.price) * data.size();
                        }else{
                            totalPrice = Float.parseFloat(goodsInfo.price) * Integer.parseInt(goodsInfo.num_discount)+Float.parseFloat(goodsInfo.formerprice) *(data.size()-Integer.parseInt(goodsInfo.num_discount));
                        }
                    }else{
                        totalPrice = Float.parseFloat(goodsInfo.price) * data.size();
                    }
                }else{
                    totalPrice = Float.parseFloat(goodsInfo.price) * data.size();
                }
                if (totalPrice > 0) {
                    tvTotalPrice.setText("￥" + FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(totalPrice)))));
                } else {
                    tvTotalPrice.setText("");
                }
            }else{
                for(GoodsListBean.GoodsInfo goods:data){
                    if(shopCart.natureTotalPrice.size()>0){
                        if(shopCart.natureTotalPrice.containsKey(goods.id)){
                            float totalPrice=shopCart.natureTotalPrice.get(goods.id);
                            if(totalPrice>0){
                                tvTotalPrice.setText("￥"+FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(totalPrice)))));
                            }else{
                                tvTotalPrice.setText("");
                            }
                        }else{
                            tvTotalPrice.setText("");
                        }
                    }else{
                        tvTotalPrice.setText("");
                    }
                }
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
        if(null!=natureHolder){
            final GoodsListBean.GoodsInfo goods=data.get(position);
            natureHolder.ll_layout.setContentDescription(position + "");
            natureHolder.tvNum.setText("第"+(position+1)+"份价格：");
            if(TextUtils.isEmpty(goods.index)){
                natureHolder.tvDelete.setVisibility(View.GONE);
            }else{
                natureHolder.tvDelete.setVisibility(View.VISIBLE);
            }
            natureHolder.natureContainer.removeAllViews();
            if (null != goods.packageNature && goods.packageNature.size() > 0) {
                for (final GoodsListBean.PackageNature packageNature : goods.packageNature) {
                    final View valueView = View.inflate(context, R.layout.item_nature_value, null);
                    TextView valueName = (TextView) valueView.findViewById(R.id.valueName);
                    valueName.setText(packageNature.name);
                    TagFlowLayout valueFlowLayoutvalue = (TagFlowLayout) valueView.findViewById(R.id.valueFlowLayout);
                    TagAdapter<GoodsListBean.PackageNatureValue> valueAdapter = new TagAdapter<GoodsListBean.PackageNatureValue>(packageNature.value) {
                        @Override
                        public View getView(FlowLayout parent, int position, GoodsListBean.PackageNatureValue packageNatureValue) {
                            TextView textView = (TextView) View.inflate(context, R.layout.item_tv, null);
                            textView.setText(packageNatureValue.name);
                            textView.setTextSize(14);
                            if ("1".equals(packageNatureValue.stockvalid)) {
                                if (packageNatureValue.stock <= 0) {
                                    textView.setBackgroundResource(R.drawable.shape_tag_no_stock);
                                    textView.setTextColor(Color.parseColor("#c6c6c6"));
                                }
                            }
                            if ("CLOSED".equals(packageNatureValue.status)) {
                                textView.setBackgroundResource(R.drawable.shape_tag_no_stock);
                                textView.setTextColor(Color.parseColor("#c6c6c6"));

                            }
                            return textView;
                        }
                    };
                    valueFlowLayoutvalue.setAdapter(valueAdapter);
                    valueFlowLayoutvalue.setMaxSelectCount(1);
                    valueFlowLayoutvalue.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                        @Override
                        public void onSelected(Set<Integer> selectPosSet) {
                            //选中属性；
                            for (int i = 0; i < packageNature.value.size(); i++) {
                                if (selectPosSet.contains(i)) {
                                    packageNature.value.get(i).is_selected = true;
                                } else {
                                    packageNature.value.get(i).is_selected = false;
                                }
                            }
                            if(!TextUtils.isEmpty(goods.index)){
                                BaseApplication.greenDaoManager.updateNatureGoods(goods);//从本地中更新属性
                                updateTotalPrice();
                            }
                        }
                    });

                    valueFlowLayoutvalue.setOnIsCanSelectListener(new TagFlowLayout.OnIsCanSelectListener() {
                        @Override
                        public boolean isCanSelect(int position) {
                            GoodsListBean.PackageNatureValue selectValue = packageNature.value.get(position);
                            if ("1".equals(selectValue.stockvalid)) {
                                if (selectValue.stock <= 0) {
                                    return false;
                                }
                            }
                            if ("CLOSED".equals(selectValue.status)) {
                                return false;
                            }
                            return true;
                        }
                    });

                    for(int i=0;i<packageNature.value.size();i++){
                        if(packageNature.value.get(i).is_selected){
                            ((TagView)valueFlowLayoutvalue.getChildAt(i)).setChecked(true);
                            valueFlowLayoutvalue.mSelectedView.add(i);
                        }
                    }
                    natureHolder.natureContainer.addView(valueView);
                }
                if (TextUtils.isEmpty(goods.index)) {
                    natureHolder.tv_begin_price.setText(FormatUtil.numFormat(df.format(Float.parseFloat(goods.price))));
                }else{
                    if ("1".equals(goods.switch_discount)){
                        if(goods.isUseDiscount){
                            natureHolder.tvPrice.setText(FormatUtil.numFormat(df.format(Float.parseFloat(goods.price))));
                        }else{
                            natureHolder.tvPrice.setText(FormatUtil.numFormat(df.format(Float.parseFloat(goods.formerprice))));
                        }
                    }else{
                        natureHolder.tvPrice.setText(FormatUtil.numFormat(df.format(Float.parseFloat(goods.price))));
                    }
                }
            } else {
                for (final GoodsListBean.GoodsNature goodsNature : goods.nature) {
                    final View valueView = View.inflate(context, R.layout.item_nature_value, null);
                    TextView valueName = (TextView) valueView.findViewById(R.id.valueName);
                    valueName.setText(goodsNature.naturename);
                    TagFlowLayout valueFlowLayoutvalue = (TagFlowLayout) valueView.findViewById(R.id.valueFlowLayout);
                    TagAdapter<GoodsListBean.GoodsNatureData> valueAdapter = new TagAdapter<GoodsListBean.GoodsNatureData>(goodsNature.data) {
                        @Override
                        public View getView(FlowLayout parent, int position, GoodsListBean.GoodsNatureData goodsNatureData) {
                            TextView textView = (TextView) View.inflate(context, R.layout.item_tv, null);
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
                            int valuePrice=0;
                            //选中属性；
                            for (int i = 0; i < goodsNature.data.size(); i++) {
                                if (selectPosSet.contains(i)) {
                                    if(!goodsNature.data.get(i).is_selected){
                                        valuePrice+=Float.parseFloat(goodsNature.data.get(i).price);
                                    }
                                    goodsNature.data.get(i).is_selected = true;
                                } else {
                                    if(goodsNature.data.get(i).is_selected){
                                        valuePrice-=Float.parseFloat(goodsNature.data.get(i).price);
                                    }
                                    goodsNature.data.get(i).is_selected = false;
                                }
                            }

                            if(!TextUtils.isEmpty(goods.index)){
                                if(shopCart.natureTotalPrice.containsKey(goods.id)){
                                    float total=shopCart.natureTotalPrice.get(goods.id);
                                    total+=valuePrice;
                                    shopCart.natureTotalPrice.put(goods.id,total);
                                }
                                BaseApplication.greenDaoManager.updateNatureGoods(goods);//从本地中更新属性
                                updateTotalPrice();
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
                                        price = Float.parseFloat(goods.member_price);
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
                            natureHolder.tvPrice.setText(FormatUtil.numFormat(df.format(Float.parseFloat(String.valueOf(price)))));

                        }
                    });
                    for(int i=0;i<goodsNature.data.size();i++){
                        if(goodsNature.data.get(i).is_selected){
                            ((TagView)valueFlowLayoutvalue.getChildAt(i)).setChecked(true);
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
                            unintPrice = Float.parseFloat(goods.member_price);
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
            }
            natureHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=iwDeleteNatureGoods){
                        iwDeleteNatureGoods.deleteNatureGoods(goods,position,goodsPosition,flag);
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
