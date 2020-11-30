package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页数据
 */

public class TopBean extends BaseBean {
    public DataBean data;

    public static class DataBean{
        public Lunboimg lunboimg;
        public ArrayList<ShopType> shoptype;
        public ArrayList<ShopList> shoplist;
        public String share_title;
        public String share_image;
        public String is_show_expected_delivery;

    }
    public static class Lunboimg {
        public ArrayList<String> img_array;
        public ArrayList<String> links_array;
    }

    public static class ShopType {
        public String id;
        public String admin_id;
        public String name;
        public String tag;
        public String icon;
        public String is_show;
    }

    public static class ShopList implements Serializable{
        public String is_show_distance;
        public String id;
        public String tag;
        public String admin_id;
        public String shopname;
        public String type_id;
        public String is_first_discount;
        public String first_discount;
        public String ordervalid;
        public String worktime;
        public String work_logo;
        public String basicprice;
        public String delivery_fee;
        public String xiaoliang;
        public String commentgrade;
        public String commentnum;
        public String open_promotion;
        public String promotion;
        public String is_coupon;
        public String is_delivery_free;
        public String is_discount;
        public String discount_value;
        public String discountlimitmember;
        public String shopimage;
        public String dis;
        public String shopdes;
        public String delivery_service;
        public String condition;
        public String open_full_free_delivery_fee;
        public String no_delivery_fee_value;
        public String is_collect;
        public String shop_notice;
        public String shop_notice_used;
        public String full;
        public String is_open_shopactive;
        public String is_only_promotion;
        public String is_only_discount;
        public String is_only_online;
        public ActivityInfo activity_info;
        public String label;//店铺标签 0无 1品牌 2新店
        public String shop_label;
        public String range;
        public String expected_delivery_times;
        public String is_show_delivery_service;
        public String outtime_info;
        public String is_show_expected_delivery;
        public String is_show_sales_volume;
        public String open_selftake;
        //满赠
        public String is_manzeng;
        public String is_shop_first_discount;//是否开启新客立减
    }
    public static class ActivityInfo implements Serializable{
        public String first_order;
        public String consume;
        public String member;
        public String delivery_fee;
        public String coupon;
        public String coupon_package;
        public String shop_discount;
        public String manzeng;
        public String shop_first_discount;//新客立减shop_first_discount
        public List<String> consume_arr;
    }
}
