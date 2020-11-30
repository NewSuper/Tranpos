package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 获取收藏店铺数据返回
 */

public class CollectionBean extends BaseBean {
    public CollectionData data;
    /**
     * is_show_distance : 1
     * id : 3
     * tag : -3
     * admin_id : 1
     * shopname : 乐外卖测试店铺
     * type_id : 988
     * is_first_discount : 1
     * first_discount : 11.0
     * ordervalid : 1
     * worktime : 1
     * work_logo : 
     * xiaoliang : 5570
     * commentgrade : 4.3
     * commentnum : 261
     * open_promotion : 1
     * promotion : 满10元减9元；满80元减10元；满100元减30元；
     * is_coupon : 1
     * is_delivery_free : 1
     * is_discount : 1
     * discount_value : 9.0
     * discountlimitmember : 0
     * shopimage : http://img.lewaimai.com/upload_files/image/20170826/2txph7wk5zIJuJdSc2CbwMmBYphYR4z0.jpg
     * dis : 7672.5千米
     * range : 7672520
     * shopdes : 测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述

     * condition : all
     */




    public class CollectionData {
        public ArrayList<ShopList> shoplist;
        public ArrayList<ShopType>shoptype;
    }
    public class ShopType{
        public String id;//	string	店铺分类id
        public String admin_id;//	string	乐外卖账号id
        public String  name	;//string	店铺分类名称
        public String  tag;//	string	店铺分类序号
        public String  icon;//	string	店铺分类图标URL
        public String is_show;//	string	店铺分类是否显示， 默认1：显示 0：不显示
    }
    public class ShopList implements Serializable{
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
        public String range;
        public String shopdes;
        public String condition;
        public String is_open_shopactive;
        public String is_only_promotion;
        public String is_only_discount;
        public String is_only_online;
        public TopBean.ActivityInfo activity_info;
        public String open_selftake;
        public String label;//店铺标签 0无 1品牌 2新店
        public String shop_label;
        public String outtime_info;
        public String expected_delivery_times;
        public String is_show_delivery_service;
        public String is_show_sales_volume;
        public String is_show_expected_delivery;
        //满赠
        public String is_manzeng;
    }
}
