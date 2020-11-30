package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21 0021.
 * 购物车数据
 */

public class ShoppingCartBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public boolean IsShopMember;
        public String memberBalance;
        public String memberFreeze;
        public boolean memberDiscountType;
        public boolean IsShopDiscount;
        public boolean IsOnlyPromotion;
        public boolean IsOnlyDiscount;
        public boolean isAddService;
        public ArrayList<AddserviceArrayBean> addserviceArray;
        public boolean shangjia;
        public boolean selftake;
        public String is_openselftake;
        public String is_only_online;
        public ShopModelBean shopModel;
        public String basicprice;
        public String delivery_fee;
        public String first_order;
        public String is_first_discount;
        public String first_order_discount;
        public AreaJsonBean areaJson;
        public ArrayList<String> selftake_nowtime;
        public ArrayList<String> selftake_shoptime;
        public ArrayList<String> shopdelivery_day;
        public ArrayList<Integer> shopdelivery_day_num;
        public ArrayList<PromotionsBean> promotions;
        public ArrayList<CouponListBean> couponList;
        public ArrayList<String> todaytime;
        public ArrayList<String> todaydeliveryfee;
        public ArrayList<String> othertime;
        public ArrayList<String> otherdeliveryfee;
        public ArrayList<String> paytype;
        public ArrayList<OrderFieldBean> order_field;
        public ArrayList<RangeDeliveryFeeJsonBean> range_delivery_fee_json;
        public AddressBean.AddressList address;
        public String outtime_info;
        public String weixinzhifu_type;
        public String zhifubaozhifu_type;


        //满赠
        public String is_manzeng;
        public String manzeng_is_communion;//满赠优惠是否跟满减同享 1同享 0不同享
        public ArrayList<ManzengBean> manzeng_rule;
        //新客立减
        public String shop_first_order;//是不是店铺首单，1是，0否
        public String is_shop_first_discount;//是否可以享受新客立减，1是，0否
        public String shop_first_discount;//新客立减金额
        public ArrayList<GoodsListBean.GoodsInfo> foodInfo;

        public static class ShopModelBean implements Serializable{

            public String id;
            public String admin_id;
            public String tag;
            public String shopname;
            public String type_id;
            public String shopdes;
            public String shopaddress;
            public String coordinate_x;
            public String coordinate_y;
            public String shopimage;
            public double basicprice;
            public double delivery_fee;
            public float no_delivery_fee_value;
            public String is_coupon;
            public float coupon_max;
            public String is_discount;
            public float discount_value;
            public String discountlimitmember;
            public String opendelivertime;
            public String is_delivery_free;
            public String open_promotion;
            public String is_pay_offline_limit;
            public float pay_offline_limit;
            public String delivery_fee_mode;
            public String full_free_delivery_fee_byrange;
            public String open_full_free_delivery_fee_byrange;
            public String open_full_free_delivery_fee;
            public String is_open_shopactive;
            public String is_free_delivery_fee_for_yue;
            public String is_vip_price_for_yue;
            public String is_discount_for_yue;
            public ArrayList<String> remark_label;
            public String delivery_mode;
            public String worktime;
        }

        public static class LewaimaicustomerModelBean {

            public String id;
            public String appid;
            public String name;
            public String phone;
            public String address;
            public String lng;
            public String lat;
            public String from_type;
        }


        public static class AreaJsonBean {
            public ArrayList<AreaDataBean> data;

            public static class AreaDataBean {

                public String name;
                public String bg_color;
                public float basic_price;
                public float delivery_price;
                public float no_delivery_fee_value;
                public String tag;
                public String editor;
                public String map_path;
                public String area_str;
                public String delivery_radius;
                public String edit_type;
                public String open_full_free_delivery_fee;
                public String circle_x;
                public String circle_y;
            }
        }

        public static class CouponListBean implements Serializable{
            /**
             * id : 580
             * coupon_name : 商品优惠券
             * coupon_des : 11
             * coupon_value : 5.0
             * coupon_basic_price : 1.0
             * coupon_deadline : 2017-06-23
             * coupon_status : OPEN
             * shop_ids : [0]
             * shop_titles : ["全部店铺"]
             * foodids : ["361","1884","2451","66226","66229"]
             * food_titles : ["红提*火龙111果","鱼香:肉丝","香椰牛奶","示例商品名(23/个)","酸辣土豆丝"]
             * rand_number : 2017052498504898t7X0
             */

            public String id;
            public String coupon_name;
            public String coupon_des;
            public double coupon_value;
            public double coupon_basic_price;
            public String coupon_deadline;
            public String coupon_status;
            public String rand_number;
            public List<String> shop_ids;
            public List<String> shop_titles;
            public List<String> foodids;
            public List<String> food_titles;

        }

        public static class RangeDeliveryFeeJsonBean {
            public float start;
            public float stop;
            public float value;
            public float minvalue;

        }

        public static class PromotionsBean {


            public int amount;
            public double discount;

        }
        public static class AddserviceArrayBean{
            public String name;
            public String price;
        }

        public static class OrderFieldBean{
            public String type;
            public String name;
            public String new_version;
            public ArrayList<ValueBean> value;
            public int selectItem;
            public String presetValue;
        }
        public static class ValueBean{
            public String name;
            public double price;
            public String is_open;
            public String parent_name;

        }
        public static class ManzengBean{
            /*"amount": 10,
                    "name": "果汁"，
                    "stock": 1000*/

            public String name;
            public long stock;
            public float amount;

        }
    }

}
