package com.newsuper.t.consumer.function.selectgoods.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xunjoy.lewaimai.consumer.R;
import com.xunjoy.lewaimai.consumer.application.BaseApplication;
import com.xunjoy.lewaimai.consumer.bean.CartGoodsModel;
import com.xunjoy.lewaimai.consumer.bean.GoodsListBean;
import com.xunjoy.lewaimai.consumer.bean.ShopCartListBean;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.SelectGoodsActivity3;
import com.xunjoy.lewaimai.consumer.function.selectgoods.activity.ShoppingCartActivity2;
import com.xunjoy.lewaimai.consumer.function.selectgoods.inter.ICheckGoodsMustView;
import com.xunjoy.lewaimai.consumer.function.selectgoods.presenter.CheckGoodsMustPresenter;
import com.xunjoy.lewaimai.consumer.function.selectgoods.request.IsCollectRequest;
import com.xunjoy.lewaimai.consumer.utils.Const;
import com.xunjoy.lewaimai.consumer.utils.DialogUtils;
import com.xunjoy.lewaimai.consumer.utils.FormatUtil;
import com.xunjoy.lewaimai.consumer.utils.LogUtil;
import com.xunjoy.lewaimai.consumer.utils.SharedPreferencesUtil;
import com.xunjoy.lewaimai.consumer.utils.StringUtils;
import com.xunjoy.lewaimai.consumer.utils.ToastUtil;
import com.xunjoy.lewaimai.consumer.utils.UIUtils;
import com.xunjoy.lewaimai.consumer.utils.UrlConst;
import com.xunjoy.lewaimai.consumer.widget.GoodsListView;
import com.xunjoy.lewaimai.consumer.widget.ListViewForScrollView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.xunjoy.lewaimai.consumer.function.selectgoods.activity.ShoppingCartListActivity.ACTIVITY_RESULT;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 购物车列表商品显示
 */

public class CartGoodsListAdapter extends RecyclerView.Adapter<CartGoodsListAdapter.CartGoodsListViewHolder> implements ICheckGoodsMustView {

    private Context context;
    private ShopCartListBean.ShopCartData data;
    public static String IsShopMember;
    public static String memberFreeze;
    public static String discountlimitmember;
    private boolean isVip;
    DecimalFormat df = new DecimalFormat("#0.00");
    private CheckGoodsMustPresenter presenter;
    public CartGoodsListAdapter(Context context, ShopCartListBean.ShopCartData data, boolean isVip) {
        super();
        this.context = context;
        this.data = data;
        this.isVip = isVip;
        presenter = new CheckGoodsMustPresenter(this);
    }

    @Override
    public CartGoodsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_cart_list, parent,false);
        return new CartGoodsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartGoodsListViewHolder holder, final int position) {
        final ShopCartListBean.ShopCartData.ShopListBean shopListBean = data.shoplist.get(position);
        ArrayList<CartGoodsModel> models = shopListBean.models;
        boolean isHasSpecialGoods = CartGoodsModel.isHasSpecialGoods(models);
        double allPrice = 0;
        double payPrice = 0;
        double discountPrice = 0;
        double vipPriceFee = 0;
        double basePrice = shopListBean.basicprice;
        if (models != null) {
            holder.goodsList.setGoods(models,isVip);
            CartGoodsAdapter adapter = new CartGoodsAdapter(context,models);
            holder.lvGoods.setAdapter(adapter);
            for (CartGoodsModel model : models) {
                //计算商品（商品价格 + 属性价格）* 个数
                //非特价商品
                if ("1".equals(model.switch_discount)){
                    if (model.isSpecialGoods){
                        allPrice += (FormatUtil.numDouble(model.price) + model.natruePrice ) * (model.count == 0 ? 1 : model.count);
                    }else {
                        //超过限购数，按原价
                        allPrice += (FormatUtil.numDouble(model.formerprice) + model.natruePrice ) * (model.count == 0 ? 1 : model.count);
                    }
                }else {
                    allPrice += (FormatUtil.numDouble(model.price) + model.natruePrice ) * (model.count == 0 ? 1 : model.count);
                }
                 if (model.type == 0 && "1".equals(model.member_price_used) && "0".equals(model.switch_discount)) {
                    vipPriceFee += (Double.parseDouble(model.price) - Double.parseDouble(model.member_price)) * (model.count == 0 ? 1 : model.count);
                }
            }
        }
        holder.tvShopName.setText(shopListBean.shopname);
        //会员优惠
        if (isVip && vipPriceFee > 0) {
            discountPrice += vipPriceFee;
            holder.llVip.setVisibility(View.VISIBLE);
            holder.tvVipFee.setText("-￥"+ FormatUtil.numFormat("" + getFormatData(vipPriceFee)));
        }else {
            holder.llVip.setVisibility(View.GONE);
        }
        boolean is_first_discount = false;
        //首单立减
        if (shopListBean.is_first_discount.equals("1") && shopListBean.is_first_order.equals("1") && !isHasSpecialGoods) {
            is_first_discount = true;
            holder.llNewUser.setVisibility(View.VISIBLE);
            String first_discount = shopListBean.first_discount;
            discountPrice += StringUtils.isEmpty(first_discount) ? 0 : Float.parseFloat(first_discount);
            holder.tvNew.setText("首单立减" );
            holder.tvNewFee.setText("-￥"+ FormatUtil.numFormat(first_discount));
        }else {
            holder.llNewUser.setVisibility(View.GONE);
        }
        double disFee = 0;
        //店铺折扣
        holder.llDis.setVisibility(View.GONE);
        if (shopListBean.is_discount.equals("1") && !isHasSpecialGoods) {
            double allDisFee = allPrice;
            if (IsShopMember.equals("1") && memberFreeze.equals("0")) {
                allDisFee = allPrice - vipPriceFee;
            }
            holder.tvDis.setText("店铺"+shopListBean.discount_value+"折");
            //会员才能打折
            if (shopListBean.discountlimitmember.equals("1")) {
                if (IsShopMember.equals("1") && memberFreeze.equals("0")) {
                    disFee = (allDisFee * (10 - shopListBean.discount_value)) / 10;
                    discountPrice += disFee;
                    disFee = getFormatData(disFee);
                    holder.tvDisFee.setText("-￥"+ FormatUtil.numFormat("" + disFee));
                    holder.llDis.setVisibility(View.VISIBLE);
                }
            } else {
                disFee = (allDisFee * (10 - shopListBean.discount_value)) / 10;
                discountPrice += disFee;
                disFee = getFormatData(disFee);
                holder.tvDisFee.setText("-￥"+ FormatUtil.numFormat("" + disFee));
                holder.llDis.setVisibility(View.VISIBLE);
            }
        }
        LogUtil.log("promotionBean", "shopListBean.open_promotion == " + shopListBean.open_promotion);
        //满减
        holder.llJian.setVisibility(View.GONE);
        if (shopListBean.open_promotion.equals("1") && !isHasSpecialGoods && !(shopListBean.is_open_shopactive.equals("1") && is_first_discount) ) {
            double cha = 0;
            if (isVip) {
                cha = allPrice - disFee - vipPriceFee;
            } else {
                cha = allPrice - disFee;
            }
            LogUtil.log("promotionBean", "cha == " + cha);
            if (shopListBean.promotion != null && shopListBean.promotion.size() > 0) {
                for (ShopCartListBean.ShopCartData.ShopListBean.PromotionBean promotionBean : shopListBean.promotion) {
                    LogUtil.log("promotionBean", "discount == " + promotionBean.discount);
                    if (cha >= promotionBean.amount) {
                        discountPrice += promotionBean.discount;
                        holder.tvJian.setText("满"+FormatUtil.numFormat(promotionBean.amount +"") + "减" + FormatUtil.numFormat(promotionBean.discount+""));
                        holder.tvJianFee.setText("-￥"+ promotionBean.discount);
                        holder.llJian.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        }
        allPrice = getFormatData(allPrice);
        holder.tvCouponPrice.setVisibility(View.VISIBLE);
        holder.ivCouponTip.setVisibility(View.GONE);
        if ("-2".equals(shopListBean.worktime) || "-1".equals(shopListBean.worktime) || "0".equals(shopListBean.worktime)) {
            holder.btnPay.setText("休息中");
            holder.btnPay.setTextColor(Color.parseColor("#FFFFFF"));
            holder.btnPay.setBackgroundColor(Color.parseColor("#CCCCCC"));
            holder.btnPay.setOnClickListener(null);
            holder.tvPayPrice.setText("￥" + FormatUtil.numFormat("" + getFormatData(allPrice  - discountPrice)));
        } else {
            if (allPrice - basePrice >= 0) {
                discountPrice = getFormatData(discountPrice);
                holder.tvCouponPrice.setText("已优惠￥" + FormatUtil.numFormat("" + discountPrice));
                payPrice = allPrice - discountPrice ;
                payPrice = getFormatData(payPrice);
                holder.tvPayPrice.setText("￥" + FormatUtil.numFormat("" + payPrice));
                if (payPrice <= 0) {
                    holder.tvPayPrice.setText("￥0.01");
                }
                holder.btnPay.setText("去结算");
                holder.btnPay.setTextColor(Color.parseColor("#FFFFFF"));
                holder.btnPay.setBackgroundColor(Color.parseColor("#FB797B"));
                holder.btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkOrder(shopListBean.id);
                    }
                });
                holder.ivCouponTip.setVisibility(View.VISIBLE);
                holder.ivCouponTip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      /*  if (popupWindow == null){
                            popupWindow = new CouponInfoPopupWindow(context,holder.vvMore.getMeasuredWidth());
                        }
                        if (popupWindow.isShowing()){
                            popupWindow.dismiss();
                        }else {
                            popupWindow.showUpView(holder.vvMore);
                        }*/
                        ToastUtil.showToastOnCenter(context,"具体优惠金额以订单结算为准~");
                    }
                });
                if (discountPrice <= 0){
                    holder.tvCouponPrice.setVisibility(View.INVISIBLE);
                    holder.ivCouponTip.setVisibility(View.INVISIBLE);
                }else {
                    holder.tvCouponPrice.setVisibility(View.VISIBLE);
                    holder.ivCouponTip.setVisibility(View.VISIBLE);
                }
            } else {
                double d = getFormatData(basePrice - allPrice);
                holder.tvCouponPrice.setText("还差" + FormatUtil.numFormat(d + "") + "元");
                holder.llPriceInfo.setOnClickListener(null);
                holder.btnPay.setText("去凑单");
                holder.btnPay.setTextColor(Color.parseColor("#FB797B"));
                holder.btnPay.setBackgroundResource(R.drawable.shape_cart_list_pay);
                holder.btnPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, SelectGoodsActivity3.class);
                        intent.putExtra("from_page", "cartList");
                        intent.putExtra("shop_id", shopListBean.id);
                        intent.putExtra("shop_name", shopListBean.shopname);
                        context.startActivity(intent);
                        ((Activity) context).startActivityForResult(intent, ACTIVITY_RESULT);
                    }
                });
                holder.tvPayPrice.setText("￥" + FormatUtil.numFormat("" +getFormatData(allPrice)));
            }
        }
        if (position == getItemCount() - 1) {
            holder.vvMore.setVisibility(View.VISIBLE);
        } else {
            holder.vvMore.setVisibility(View.GONE);
        }

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(context, shopListBean);

            }
        });

        holder.llShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SelectGoodsActivity3.class);
                intent.putExtra("from_page", "cartList");
                intent.putExtra("shop_id", shopListBean.id);
                intent.putExtra("shop_name", shopListBean.shopname);
                ((Activity) context).startActivityForResult(intent, ACTIVITY_RESULT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.shoplist.size();
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


    static class CartGoodsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.ll_shop_name)
        LinearLayout llShopName;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.lv_goods)
        ListViewForScrollView lvGoods;
        @BindView(R.id.goods_list)
        GoodsListView goodsList;
        @BindView(R.id.tv_jian)
        TextView tvJian;
        @BindView(R.id.tv_jian_fee)
        TextView tvJianFee;
        @BindView(R.id.ll_jian)
        LinearLayout llJian;
        @BindView(R.id.tv_new)
        TextView tvNew;
        @BindView(R.id.tv_new_fee)
        TextView tvNewFee;
        @BindView(R.id.ll_new_user)
        LinearLayout llNewUser;
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
        @BindView(R.id.ll_price_info)
        LinearLayout llPriceInfo;
        @BindView(R.id.tv_pay_price)
        TextView tvPayPrice;
        @BindView(R.id.btn_pay)
        Button btnPay;
        @BindView(R.id.vv_more)
        View vvMore;
        @BindView(R.id.iv_coupon_tip)
        ImageView ivCouponTip;

        CartGoodsListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public double getFormatData(double d) {
        return Double.parseDouble(df.format(d));
    }

    AlertDialog dialog;

    private void showDeleteDialog(Context context, final ShopCartListBean.ShopCartData.ShopListBean shopListBean) {
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
                BaseApplication.greenDaoManager.deleteGoodsByShopId(shopListBean.id);
                data.shoplist.remove(shopListBean);
                notifyDataSetChanged();
                if (data.shoplist.size() == 0) {
                    if (deleteGoodsListener != null) {
                        deleteGoodsListener.delete();
                    }
                }
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

    public interface DeleteGoodsListener {
        void delete();
    }
}
