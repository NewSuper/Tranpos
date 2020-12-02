package com.newsuper.t.consumer.bean;

import com.newsuper.t.consumer.base.BaseApplication;
import com.newsuper.t.consumer.utils.FormatUtil;
import com.newsuper.t.consumer.utils.LogUtil;
import com.newsuper.t.consumer.utils.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartGoodsInfoBean {
    public boolean isVip;
    public int item_type;

    public String id;
    public String shopname;
    public String shopimage;

    public CartGoodsModel model;

    public boolean is_first_discount;
    public String first_discount;
    public String first_discount_fee;

    public boolean is_shop_first_discount;
    public String shop_first_discount;
    public String shop_first_discount_fee;

    public boolean is_discount;
    public String discount;
    public String discount_fee;

    public boolean is_promotion;
    public String promotion;
    public String promotionFee;

    public boolean is_member;
    public String memberFee;

    public String worktime;
    public String status;
    public double allPrice;
    public double discountPrice;
    public double basePrice;
    public String outtime_info;


    public static ArrayList<CartGoodsInfoBean> getCartGoodsInfo(ShopCartListBean.ShopCartData data) {
        ArrayList<CartGoodsInfoBean> list = new ArrayList<>();
        if (data.shoplist != null && data.shoplist.size() > 0) {
            for (ShopCartListBean.ShopCartData.ShopListBean shopListBean : data.shoplist) {
                ArrayList<CartGoodsModel> models = CartGoodsModel.getModels(BaseApplication.greenDaoManager.getGoodsListByShopId(shopListBean.id));
                if (models.size() > 0) {
                    CartGoodsInfoBean shopBean = new CartGoodsInfoBean();
                    shopBean.isVip = ("1".equals(data.IsShopMember) && "0".equals(data.memberFreeze));
                    shopBean.shopname = shopListBean.shopname;
                    shopBean.shopimage = shopListBean.shopimage;
                    shopBean.outtime_info = shopListBean.outtime_info;
                    shopBean.worktime = shopListBean.worktime;
                    shopBean.id = shopListBean.id;
                    shopBean.item_type = 0;
                    list.add(shopBean);

                    boolean isHasSpecialGoods = CartGoodsModel.isHasSpecialGoods(models);
                    double allPrice = 0;
                    double discountPrice = 0;
                    double vipPriceFee = 0;
                    double basePrice = shopListBean.basicprice;

                    for (CartGoodsModel m : models) {
                        CartGoodsInfoBean goodsBean = new CartGoodsInfoBean();
                        goodsBean.shopname = shopListBean.shopname;
                        shopBean.shopimage = shopListBean.shopimage;
                        goodsBean.isVip = ("1".equals(data.IsShopMember) && "0".equals(data.memberFreeze));
                        goodsBean.id = shopListBean.id;
                        goodsBean.item_type = 1;
                        goodsBean.model = m;
                        list.add(goodsBean);
                        //计算商品（商品价格 + 属性价格）* 个数
                        //非特价商品
                        if ("1".equals(m.switch_discount)) {
                            if (m.isSpecialGoods) {
                                allPrice += (FormatUtil.numDouble(m.price) + m.natruePrice) * (m.count == 0 ? 1 : m.count);
                            } else {
                                //超过限购数，按原价
                                allPrice += (FormatUtil.numDouble(m.formerprice) + m.natruePrice) * (m.count == 0 ? 1 : m.count);
                            }
                        } else {
                            allPrice += (FormatUtil.numDouble(m.price) + m.natruePrice) * (m.count == 0 ? 1 : m.count);
                        }
                        if (m.type == 0 && "1".equals(m.member_price_used) && "0".equals(m.switch_discount)) {
                            vipPriceFee += (Double.parseDouble(m.price) - Double.parseDouble(m.member_price)) * (m.count == 0 ? 1 : m.count);
                        }
                    }

                    CartGoodsInfoBean actBean = new CartGoodsInfoBean();
                    actBean.isVip = ("1".equals(data.IsShopMember) && "0".equals(data.memberFreeze));
                    actBean.shopname = shopListBean.shopname;
                    shopBean.shopimage = shopListBean.shopimage;
                    actBean.id = shopListBean.id;
                    actBean.item_type = 2;
                    LogUtil.log("getCartGoodsInfo", "dis 000 == " + discountPrice);
                    //会员优惠
                    if (actBean.isVip && vipPriceFee > 0) {
                        actBean.is_member = true;
                        discountPrice += vipPriceFee;
                        actBean.memberFee = ("-￥" + FormatUtil.numFormat("" + getFormatData(vipPriceFee)));
                    }
                    LogUtil.log("getCartGoodsInfo", "dis 111 == " + discountPrice + "   vipPriceFee ==  " + vipPriceFee);
                    boolean isFirst = false;
                    //首单立减
                    if (shopListBean.is_first_discount.equals("1") && shopListBean.is_first_order.equals("1") && !isHasSpecialGoods) {
                        actBean.is_first_discount = true;
                        String first_discount = shopListBean.first_discount;
                        float firstFee = StringUtils.isEmpty(first_discount) ? 0 : Float.parseFloat(first_discount);
                        discountPrice += firstFee;
                        actBean.first_discount = "首单立减";
                        actBean.first_discount_fee = "-￥" + FormatUtil.numFormat(first_discount);
                        isFirst = true;
                    }

                    //新客立减，不可与首单同享，默认首单优先
                    if (!isFirst && "1".equals(shopListBean.shop_first_order) && "1".equals(shopListBean.is_shop_first_discount)){
                        actBean.is_shop_first_discount = true;
                        String shop_first_discount = shopListBean.shop_first_discount;
                        float firstFee = StringUtils.isEmpty(shop_first_discount) ? 0 : Float.parseFloat(shop_first_discount);
                        discountPrice += firstFee;
                        actBean.shop_first_discount = "门店新客立减";
                        actBean.shop_first_discount_fee = "-￥" + FormatUtil.numFormat(shop_first_discount);
                    }


                    LogUtil.log("getCartGoodsInfo", "dis 111 == " + discountPrice + "   first_discount_fee ==  " + actBean.first_discount_fee);
                    double disFee = 0;
                    //首单和满减不可同享
                    if (shopListBean.open_promotion.equals("1") && !isHasSpecialGoods && !isFirst) {
                        double cha = 0;
                        if (actBean.isVip) {
                            cha = allPrice - disFee - vipPriceFee;
                        } else {
                            cha = allPrice - disFee;
                        }
                        LogUtil.log("promotionBean", "cha == " + cha);
                        double dis = 0;
                        if (shopListBean.promotion != null && shopListBean.promotion.size() > 0) {
                            for (ShopCartListBean.ShopCartData.ShopListBean.PromotionBean promotionBean : shopListBean.promotion) {
                                LogUtil.log("promotionBean", "discount == " + promotionBean.discount);
                                if (cha >= promotionBean.amount) {
                                    dis = promotionBean.discount;
                                    actBean.promotion = "满" + FormatUtil.numFormat(promotionBean.amount + "") + "减" + FormatUtil.numFormat(promotionBean.discount + "");
                                    actBean.promotionFee = "-￥" + promotionBean.discount;
                                    break;
                                }
                            }
                        }
                        if (dis > 0) {
                            actBean.is_promotion = true;
                            discountPrice += dis;
                        }
                        LogUtil.log("getCartGoodsInfo", "dis 333 == " + discountPrice + "   promotion ==  " + dis);
                    }

                    LogUtil.log("getCartGoodsInfo", "dis 444 == " + discountPrice);
                    allPrice = getFormatData(allPrice);
                    actBean.worktime = shopBean.worktime;
                    actBean.outtime_info = shopBean.outtime_info;
                    actBean.allPrice = allPrice;
                    actBean.discountPrice = discountPrice;
                    actBean.basePrice = basePrice;
                    list.add(actBean);
                }
            }
        }
        return list;
    }

    public static double getFormatData(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return Double.parseDouble(df.format(d));
    }

}
