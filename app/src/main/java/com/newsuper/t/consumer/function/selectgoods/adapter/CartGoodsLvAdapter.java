package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.newsuper.t.R;
import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.bean.CartGoodsInfoBean;
import com.newsuper.t.consumer.bean.CartGoodsModel;
import com.newsuper.t.consumer.bean.GoodsListBean;
import com.newsuper.t.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.newsuper.t.consumer.function.selectgoods.activity.ShoppingCartActivity2;
import com.newsuper.t.consumer.function.selectgoods.inter.ICheckGoodsMustView;
import com.newsuper.t.consumer.function.selectgoods.presenter.CheckGoodsMustPresenter;
import com.newsuper.t.consumer.function.selectgoods.request.IsCollectRequest;
import com.newsuper.t.consumer.manager.RetrofitManager;
import com.newsuper.t.consumer.utils.Const;
import com.newsuper.t.consumer.utils.DialogUtils;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.SharedPreferencesUtil;
import com.newsuper.t.consumer.utils.StringUtils;
import com.newsuper.t.consumer.utils.ToastUtil;
import com.newsuper.t.consumer.utils.UIUtils;
import com.newsuper.t.consumer.utils.UrlConst;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.newsuper.t.consumer.function.selectgoods.activity.ShoppingCartListActivity.ACTIVITY_RESULT;

/**
 * Created by Administrator on 2018/6/22 0022.
 * 多店购物车
 */

public class CartGoodsLvAdapter extends BaseAdapter implements ICheckGoodsMustView {
    private static final int ITEM_SHOP = 0;
    private static final int ITEM_GOODS = 1;
    private static final int ITEM_ACTIVITY = 2;
    private Context context;
    DecimalFormat df = new DecimalFormat("#0.00");
    private ArrayList<CartGoodsInfoBean> list;
    private CheckGoodsMustPresenter presenter;
    public CartGoodsLvAdapter(Context context, ArrayList<CartGoodsInfoBean> list) {
        this.context = context;
        this.list = list;
        presenter = new CheckGoodsMustPresenter(this);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).item_type == 0) {
            return ITEM_SHOP;
        } else if (list.get(position).item_type == 1) {
            return ITEM_GOODS;
        } else {
            return ITEM_ACTIVITY;
        }
    }

    @Override
    public int getCount() {
        return list.size();
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
        final CartGoodsInfoBean bean = list.get(position);
        //店铺
        if (getItemViewType(position) == ITEM_SHOP) {
            ShopViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_cart_list_shop, null);
                holder = new ShopViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ShopViewHolder) convertView.getTag();
            }
            holder.tvShopName.setText(bean.shopname);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog(context, bean.id);
                }
            });
            holder.llShopName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SelectGoodsActivity3.class);
                    intent.putExtra("from_page", "cartList");
                    intent.putExtra("shop_id", bean.id);
                    intent.putExtra("shop_name", bean.shopname);
                    ((Activity) context).startActivityForResult(intent, ACTIVITY_RESULT);
                }
            });
            String url = bean.shopimage;
            if (StringUtils.isEmpty(url)){
                holder.ivShop.setImageResource(R.mipmap.store_logo_default);
            }else {
                if (!url.startsWith("http")) {
                    url = RetrofitManager.BASE_IMG_URL_SMALL + url;
                }
                Picasso.with(context).load(url).error(R.mipmap.store_logo_default).into(holder.ivShop);
            }
        }
        //店铺商品
        else if (getItemViewType(position) == ITEM_GOODS) {
            GoodsViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_cart_list_goods, null);
                holder = new GoodsViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (GoodsViewHolder) convertView.getTag();
            }
            CartGoodsModel info = bean.model;
            String name = info.name;
            if (!StringUtils.isEmpty(info.natureName)){
                holder.tvGoodsNature.setText(info.natureName);
            }else {
                holder.tvGoodsNature.setText("");
            }
            holder.tvGoodsName.setText(name);
            holder.tvGoodsCount.setText("x" + info.count);
            holder.tvGoodsDis.setVisibility(View.GONE);
            //非折扣商品
            if ("1".equals(info.switch_discount)){
                if (info.isSpecialGoods){
                    holder.tvGoodsDis.setVisibility(View.VISIBLE);
                    holder.tvGoodsName.setText(name+" >");
                    double d = FormatUtil.numDouble(info.price);
                    holder.tvGoodsPrice.setText(FormatUtil.numFormat(df.format(d)));
                    if (info.natruePrice > 0){
                        String s = getFormatData((d + info.natruePrice))+ "";
                        holder.tvGoodsPrice.setText(FormatUtil.numFormat(s) );
                    }
                }else {
                    double d = FormatUtil.numDouble(info.formerprice);
                    holder.tvGoodsPrice.setText(FormatUtil.numFormat(df.format(d)));
                    if (info.natruePrice > 0){
                        String s = getFormatData((d + info.natruePrice))+ "";
                        holder.tvGoodsPrice.setText(FormatUtil.numFormat(s) );
                    }
                }
            }else {
               /* //显示会员价
                if (bean.isVip && 0 == info.type && "1".equals(info.member_price_used)){
                    holder.tvGoodsPrice.setText("￥"+ FormatUtil.numFormat(info.member_price));
                    double d = FormatUtil.numDouble(info.member_price);
                    if (info.natruePrice > 0){
                        String s = getFormatData((d + info.natruePrice))+ "";
                        holder.tvGoodsPrice.setText("￥" + FormatUtil.numFormat(s) );
                    }
                }else {

                }*/
                double d = FormatUtil.numDouble(info.price);
                holder.tvGoodsPrice.setText(FormatUtil.numFormat(df.format(d)));
                if (info.natruePrice > 0){
                    String s = getFormatData((d + info.natruePrice))+ "";
                    holder.tvGoodsPrice.setText(FormatUtil.numFormat(s) );
                }
            }
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
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SelectGoodsActivity3.class);
                    intent.putExtra("from_page", "cartList");
                    intent.putExtra("shop_id", bean.id);
                    intent.putExtra("shop_name", bean.shopname);
                    ((Activity) context).startActivityForResult(intent, ACTIVITY_RESULT);
                }
            });
        }
        //店铺活动
        else if (getItemViewType(position) == ITEM_ACTIVITY) {
            ActivityViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_cart_list_shop_act, null);
                holder = new ActivityViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ActivityViewHolder) convertView.getTag();
            }
            boolean flag = false;
            //会员优惠
            if (bean.is_member){
                flag = true;
                holder.llVip.setVisibility(View.VISIBLE);
                holder.tvVipFee.setText(bean.memberFee);
            } else {
                holder.llVip.setVisibility(View.GONE);
            }
            //首单立减
            if (bean.is_first_discount) {
                flag = true;
                holder.llNewUser.setVisibility(View.VISIBLE);
                holder.tvNew.setText("首单立减");
                holder.tvNewFee.setText(bean.first_discount_fee);
            } else {
                holder.llNewUser.setVisibility(View.GONE);
            }
            //店铺折扣
            //2.8 需求去掉
            holder.llDis.setVisibility(View.GONE);
           /* if (bean.is_discount) {
                holder.tvDis.setText(bean.discount);
                holder.tvDisFee.setText(bean.discount_fee);
                holder.llDis.setVisibility(View.VISIBLE);
            }*/
            //满减
            holder.llJian.setVisibility(View.GONE);
            if (bean.is_promotion) {
                flag = true;
                holder.tvJian.setText(bean.promotion);
                holder.tvJianFee.setText(bean.promotionFee);
                holder.llJian.setVisibility(View.VISIBLE);
            }
            //新客立减
            holder.ll_xinke.setVisibility(View.GONE);
            if(bean.is_shop_first_discount){
                flag = true;
                holder.tv_xinke.setText(bean.shop_first_discount);
                holder.tv_xinke_fee.setText(bean.shop_first_discount_fee);
                holder.ll_xinke.setVisibility(View.VISIBLE);
            }
            if (flag){
                holder.ll_act.setVisibility(View.VISIBLE);
            }else {
                holder.ll_act.setVisibility(View.GONE);
            }
            holder.tvCouponPrice.setVisibility(View.VISIBLE);
            holder.ivCouponTip.setVisibility(View.GONE);
            double allPrice = bean.allPrice;
            double discountPrice = bean.discountPrice;
            double basePrice = bean.basePrice;
            double payPrice = 0;
            boolean isCanOrder = false;
            LogUtil.log("CartGoodsLvAdapter","worktime  == "+bean.worktime);
            LogUtil.log("CartGoodsLvAdapter","outtime_info  == "+bean.outtime_info);
            if (!"1".equals(bean.worktime)) {
                holder.btnPay.setText("休息中");
                holder.btnPay.setTextColor(Color.parseColor("#FFFFFF"));
                holder.btnPay.setBackgroundColor(Color.parseColor("#CCCCCC"));
                holder.btnPay.setOnClickListener(null);
                holder.tvPayPrice.setText(FormatUtil.numFormat("" + getFormatData(allPrice - discountPrice)));
                if (!StringUtils.isEmpty(bean.outtime_info)){
                    isCanOrder = true;
                }
            } else {
                isCanOrder = true;
            }
            if (isCanOrder){
                if (allPrice - basePrice >= 0) {
                    discountPrice = getFormatData(discountPrice);
                    holder.tvCouponPrice.setText("已优惠￥" + FormatUtil.numFormat("" + discountPrice));
                    payPrice = allPrice - discountPrice;
                    payPrice = getFormatData(payPrice);
                    holder.tvPayPrice.setText(FormatUtil.numFormat("" + payPrice));
                    if (payPrice <= 0) {
                        holder.tvPayPrice.setText("0.01");
                    }
                    holder.btnPay.setText("去结算");
                    holder.btnPay.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.btnPay.setBackgroundColor(Color.parseColor("#FB797B"));
                    holder.btnPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkOrder(bean.id);
                        }
                    });
                    holder.ivCouponTip.setVisibility(View.VISIBLE);
                    holder.ivCouponTip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.showToastOnCenter(context, "具体优惠金额以订单结算为准~");
                        }
                    });
                    if (discountPrice <= 0) {
                        holder.tvCouponPrice.setVisibility(View.INVISIBLE);
                        holder.ivCouponTip.setVisibility(View.INVISIBLE);
                    } else {
                        holder.tvCouponPrice.setVisibility(View.VISIBLE);
                        holder.ivCouponTip.setVisibility(View.VISIBLE);
                    }
                } else {
                    double d = getFormatData(basePrice - allPrice);
//                    holder.tvCouponPrice.setText("还差" + FormatUtil.numFormat(d + "") + "元");
                    holder.tvCouponPrice.setText("");
                    holder.llPriceInfo.setOnClickListener(null);
//                    holder.btnPay.setText("去凑单");
                    holder.btnPay.setText("差" + FormatUtil.numFormat(d + "") +"起送");
                    holder.btnPay.setTextColor(Color.parseColor("#FB797B"));
                    holder.btnPay.setBackgroundResource(R.drawable.shape_cart_list_pay);
                    holder.btnPay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, SelectGoodsActivity3.class);
                            intent.putExtra("from_page", "cartList");
                            intent.putExtra("shop_id", bean.id);
                            intent.putExtra("shop_name", bean.shopname);
                            context.startActivity(intent);
                            ((Activity) context).startActivityForResult(intent, ACTIVITY_RESULT);
                        }
                    });
                    holder.tvPayPrice.setText(FormatUtil.numFormat("" + getFormatData(allPrice)));
                }
            }
        }
        return convertView;
    }

    public double getFormatData(double d) {
        return Double.parseDouble(df.format(d));
    }

    AlertDialog dialog = null;

    private void showDeleteDialog(Context context,final String id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cart_delete, null);
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                BaseApplication.greenDaoManager.deleteGoodsByShopId(id);
                ArrayList<CartGoodsInfoBean> beens = new ArrayList<>();
                beens.addAll(list);
                list.clear();
                for (CartGoodsInfoBean infoBean : beens ){
                    if (!infoBean.id.equals(id)){
                        list.add(infoBean);
                    }
                }
                if (list.size() == 0){
                    if (deleteGoodsListener != null){
                        deleteGoodsListener.delete();
                    }
                }
                notifyDataSetChanged();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private DeleteGoodsListener deleteGoodsListener;

    public void setDeleteGoodsListener(DeleteGoodsListener deleteGoodsListener) {
        this.deleteGoodsListener = deleteGoodsListener;
    }

    //必选商品检测
    private void checkOrder(String shop_id) {
        String admin_id = Const.ADMIN_ID;
        String token = SharedPreferencesUtil.getToken();
        ArrayList<String> goodList = new ArrayList<>();
        List<GoodsListBean.GoodsInfo> goodsInfos = BaseApplication.greenDaoManager.getGoodsListByShopId(shop_id);
        for (GoodsListBean.GoodsInfo goodsInfo : goodsInfos) {
            goodList.add(goodsInfo.original_type_id);
        }
        String food_type = new Gson().toJson(goodList);
        HashMap<String, String> params = IsCollectRequest.checkOrder(admin_id, token, shop_id,food_type);
        presenter.check(UrlConst.CHECK_ORDER, params, shop_id);
    }

    //必选分类提示
    private Dialog goodsTypeNeedDialog;
    private TextView tvError;
    private void showGoodsTypeNeedDialog(String msg) {
        if (goodsTypeNeedDialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_goods_type_need, null);
            tvError = (TextView) view.findViewById(R.id.tv_error);
            view.findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsTypeNeedDialog.dismiss();
                }
            });
            goodsTypeNeedDialog = DialogUtils.centerDialog(context, view);
        }
        tvError.setText(msg);
        goodsTypeNeedDialog.show();
    }

    @Override
    public void showSuccessVIew(String shop_id) {
        Intent intent = new Intent(context, ShoppingCartActivity2.class);
        intent.putExtra("shop_id", shop_id);
        ((Activity) context).startActivityForResult(intent, ACTIVITY_RESULT);
    }

    @Override
    public void showCheckOrderView(String msg) {
        showGoodsTypeNeedDialog(msg);
    }

    @Override
    public void dialogDismiss() {

    }

    @Override
    public void showToast(String s) {
        UIUtils.showToast(s);
    }

    public interface DeleteGoodsListener {
        void delete();
    }

    static class ShopViewHolder {
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.ll_shop_name)
        LinearLayout llShopName;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.iv_shop)
        ImageView ivShop;

        ShopViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    static class GoodsViewHolder {
        @BindView(R.id.iv_goods)
        ImageView ivGoods;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_nature)
        TextView tvGoodsNature;
        @BindView(R.id.tv_goods_count)
        TextView tvGoodsCount;
        @BindView(R.id.tv_goods_dis)
        TextView tvGoodsDis;


        GoodsViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ActivityViewHolder {
        @BindView(R.id.tv_new)
        TextView tvNew;
        @BindView(R.id.tv_new_fee)
        TextView tvNewFee;
        @BindView(R.id.ll_new_user)
        LinearLayout llNewUser;
        @BindView(R.id.tv_jian)
        TextView tvJian;
        @BindView(R.id.tv_jian_fee)
        TextView tvJianFee;
        @BindView(R.id.ll_jian)
        LinearLayout llJian;

        @BindView(R.id.tv_xinke_fee)
        TextView tv_xinke_fee;
        @BindView(R.id.tv_xinke)
        TextView tv_xinke;
        @BindView(R.id.ll_xinke)
        LinearLayout ll_xinke;

        @BindView(R.id.ll_act)
        LinearLayout ll_act;
        @BindView(R.id.tv_dis)
        TextView tvDis;
        @BindView(R.id.tv_dis_fee)
        TextView tvDisFee;
        @BindView(R.id.ll_dis)
        LinearLayout llDis;
        @BindView(R.id.tv_vip_fee)
        TextView tvVipFee;
        @BindView(R.id.ll_vip)
        LinearLayout llVip;
        @BindView(R.id.tv_coupon_price)
        TextView tvCouponPrice;
        @BindView(R.id.iv_coupon_tip)
        ImageView ivCouponTip;
        @BindView(R.id.ll_price_info)
        LinearLayout llPriceInfo;
        @BindView(R.id.tv_pay_price)
        TextView tvPayPrice;
        @BindView(R.id.btn_pay)
        Button btnPay;
        ActivityViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
