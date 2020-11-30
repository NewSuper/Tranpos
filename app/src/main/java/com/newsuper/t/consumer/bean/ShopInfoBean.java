package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopInfoBean extends BaseBean implements Serializable{
    public ShopInfoData data;
    public class ShopInfoData{
        public ShopInfo shop;
        public String basicprice;
        public String delivery_fee;
        public String commentgrade;
        public String is_collect;
        public String is_show_logo; 	//1需要显示乐外卖logo，0不显示
        public CommentGrades commentgradePeople;
        public ArrayList<TagInfo> tag_num;
        public String is_show_sales_volume;
        public String is_show_expected_delivery;
    }

    public class CommentGrades{
        public String grade_1;
        public String grade_2;
        public String grade_3;
        public String grade_4;
        public String grade_5;
    }

    public class ShopInfo implements Serializable{

        public String id;
        public String admin_id;
        public String init_date;
        public String tag;
        public String shopname;
        public String is_first_discount;
        public String first_discount;
        public String basicprice;
        public String delivery_fee;
        public String open_promotion; //string 	是否开启满减活动，1开启，0不开启
        public ArrayList<ManJian> promotion_rule;
        public String is_coupon;
        public String is_delivery_free;
        public String is_discount;
        public String discount_value;
        public String discountlimitmember;
        public String open_full_free_delivery_fee;
        public String no_delivery_fee_value;
        public String shop_notice;
        public String dis;
        public String shopdes;
        public String delivery_service;
        public String type_id;
        public String shopaddress;
        public String QQ;
        public String coordinate_x;
        public String coordinate_y;
        public String orderphone;
        public ArrayList<String> shopimage;
        public String delivery_radius;
        public String comment_num;
        public AreaData area_data;
        public String delivery_fee_mode;//起送价、配送费模式，1=固定配送费、起送价，2=按区域，3＝按距离，4=按配送时间
        public ArrayList<DeliveryFee> range_delivery_fee;//按距离配送的费用
        public int worktime;
        public String outtime_info;
        public String weeks;
        public String work_week;
        public String closeinfo;//店铺下线提醒
        public String ordervalid_remind;//顾客下单关闭提醒
        public String is_show_basicprice;//店铺信息是否显示起送价，0显示，1不显示
        public String is_show_delivery;//店铺信息是否显示外送费，0显示，1不显示
        public ArrayList<SaleTime> business_hours;
        public String is_open_shopactive;
        public String is_only_promotion;
        public String is_only_online;
        public String is_only_discount;
        public String order_num;
        public ActInfo activity_info;
        public String unitshow;
        public String showsales;
        public String is_opencomment;
        public String showtype;
        public ArrayList<String> quality_img;
        public String show_food_license;
        public String food_showtype;//0弹框显示1新页面显示
        public String switch_advertising;
        public String image_advertising;
        //满赠
        public String is_manzeng;
        public String is_shop_first_discount;//是否开启新客立减
        public String shop_first_discount;
        public CouponInfo exclusivecoupon;
        public String expected_delivery_times;

    }

    public class ActInfo implements Serializable{
        public String first_order;//"新用户下单立减10元（不与满减同享）",
        public String consume;//满20减5;满50减20;满100减50;（在线支付专享）",
        public String member;//会员免配送费（余额支付专享）",
        public String delivery_fee;//,
        public String coupon;//可使用优惠券",
        public String coupon_package;//"消费满100元送优惠券礼包",
        public String shop_discount;//"全场可享受全店9折（在线支付专享）",
        public String shop_discount_for_h5;//"全场可享受全店9折（余额支付专享）"
        public String manzeng;
        public String shop_first_discount;//新客立减
    }

    public class CouponInfo implements Serializable{
        public ArrayList<GoodsListBean.Coupon> list;
    }

    public class SaleTime implements Serializable{
        public String start;
        public String stop;
    }

    public class AreaData implements Serializable{
        public ArrayList<ServiceArea> data;
    }

    public class ServiceArea implements Serializable{
        public String name;
        public String area_str;
        public String bg_color;
        public String edit_type;
        public String delivery_radius;
        public String circle_x;
        public String circle_y;
    }

    public class TagInfo{
        public int id;
        public String name;
        public String num;
    }

    public class DeliveryFee implements Serializable{
        public String start;
        public String stop;
        public String value;
        public String minvalue;

    }
}
