package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopSearchBean extends BaseBean {
    public DataBean data;
    public static class DataBean{
        public ArrayList<TopBean.ShopType> shoptype;
        public ArrayList<ShopList> shoplist;
        public String is_show_expected_delivery;
        public String is_show_sales_volume;
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
        public String is_collect;
        public String is_open_shopactive;
        public String is_show_sales_volume;
        public String is_only_promotion;
        public ActivityInfo activity_info;
        public String label;//店铺标签 0无 1品牌 2新店
        public String expected_delivery_times;
        public String outtime_info;
        public String shop_label;
        public String open_selftake;
        //满赠
        public String is_manzeng;
        public ArrayList<FoodBean> foodlist;
        public boolean isShowAll;
        public boolean isMore;
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
        public String shop_first_discount;//新客立减
        public List<String> consume_arr;
    }

    public static class FoodBean implements Serializable{
        public String name;
        public String img;
        public String discount_price;
        public String switch_discount;
        public String food_id;
        public String shop_id;
        public String price;
        public String type_id;
        public String second_type_id;
    }
}
