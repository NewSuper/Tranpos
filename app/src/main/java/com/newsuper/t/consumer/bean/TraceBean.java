package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Create by Administrator on 2019/4/25 0025
 */
public class TraceBean {

    /**
     * error_msg :
     * error_code : 0
     * data : {"footprintlist":[{"date":"今天","items":[{"id":"66","admin_id":"1","area_id":"4672","shop_id":"3450","init_date":"2019-04-26","shopinfo":{"id":"3450","tag":"-1006","admin_id":"1","shopname":"分区活动","type_id":"991","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"","dis":"1.7km","range":"1676","shopdes":"","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}},{"id":"65","admin_id":"1","area_id":"1","shop_id":"3281","init_date":"2019-04-26","shopinfo":{"id":"3281","tag":"0","admin_id":"1","shopname":"Aaron 专卖点点滴滴","type_id":"988","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"","dis":"14.3km","range":"14348.6","shopdes":"12121212","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"满122.5元免配送费","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}},{"id":"64","admin_id":"1","area_id":"4679","shop_id":"3852","init_date":"2019-04-26","shopinfo":{"id":"3852","tag":"-99999999","admin_id":"1","shopname":"Aaron专属店铺","type_id":"0","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"https://img.lewaimai.com/upload_files/image/20190328/1553742273647d50544h70wm72G0Zc9b.jpg","dis":"0 m","range":"0","shopdes":"","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"0.00折起","shop_discount_for_h5":"0.00折起","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}}]}],"pagesize":20,"page":1,"totalpage":1,"counts":3}
     */

    public String error_msg;
    public int error_code;
    public DataBean data;

    public static class DataBean {
        /**
         * footprintlist : [{"date":"今天","items":[{"id":"66","admin_id":"1","area_id":"4672","shop_id":"3450","init_date":"2019-04-26","shopinfo":{"id":"3450","tag":"-1006","admin_id":"1","shopname":"分区活动","type_id":"991","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"","dis":"1.7km","range":"1676","shopdes":"","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}},{"id":"65","admin_id":"1","area_id":"1","shop_id":"3281","init_date":"2019-04-26","shopinfo":{"id":"3281","tag":"0","admin_id":"1","shopname":"Aaron 专卖点点滴滴","type_id":"988","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"","dis":"14.3km","range":"14348.6","shopdes":"12121212","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"满122.5元免配送费","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}},{"id":"64","admin_id":"1","area_id":"4679","shop_id":"3852","init_date":"2019-04-26","shopinfo":{"id":"3852","tag":"-99999999","admin_id":"1","shopname":"Aaron专属店铺","type_id":"0","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"https://img.lewaimai.com/upload_files/image/20190328/1553742273647d50544h70wm72G0Zc9b.jpg","dis":"0 m","range":"0","shopdes":"","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"0.00折起","shop_discount_for_h5":"0.00折起","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}}]}]
         * pagesize : 20
         * page : 1
         * totalpage : 1
         * counts : 3
         */
        public int pagesize;
        public int page;
        public int totalpage;
        public int counts;
        public List<FootprintlistBean> footprintlist;

        public static class FootprintlistBean {
            /**
             * date : 今天
             * items : [{"id":"66","admin_id":"1","area_id":"4672","shop_id":"3450","init_date":"2019-04-26","shopinfo":{"id":"3450","tag":"-1006","admin_id":"1","shopname":"分区活动","type_id":"991","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"","dis":"1.7km","range":"1676","shopdes":"","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}},{"id":"65","admin_id":"1","area_id":"1","shop_id":"3281","init_date":"2019-04-26","shopinfo":{"id":"3281","tag":"0","admin_id":"1","shopname":"Aaron 专卖点点滴滴","type_id":"988","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"","dis":"14.3km","range":"14348.6","shopdes":"12121212","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"满122.5元免配送费","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}},{"id":"64","admin_id":"1","area_id":"4679","shop_id":"3852","init_date":"2019-04-26","shopinfo":{"id":"3852","tag":"-99999999","admin_id":"1","shopname":"Aaron专属店铺","type_id":"0","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"https://img.lewaimai.com/upload_files/image/20190328/1553742273647d50544h70wm72G0Zc9b.jpg","dis":"0 m","range":"0","shopdes":"","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"0.00折起","shop_discount_for_h5":"0.00折起","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}}]
             */
            public String date;
            public List<ItemsBean> items;

            public static class ItemsBean {
                /**
                 * id : 66
                 * admin_id : 1
                 * area_id : 4672
                 * shop_id : 3450
                 * init_date : 2019-04-26
                 * shopinfo : {"id":"3450","tag":"-1006","admin_id":"1","shopname":"分区活动","type_id":"991","is_first_discount":"0","first_discount":"0","ordervalid":"1","work_logo":"","xiaoliang":"0","commentgrade":"0","commentnum":"0","open_promotion":"0","promotion":"","is_coupon":"1","is_delivery_free":"0","is_discount":"0","discount_value":"10.0","discountlimitmember":"0","shopimage":"","dis":"1.7km","range":"1676","shopdes":"","condition":"all","worktime":"1","outtime_info":"","is_open_shopactive":"1","is_only_promotion":"0","is_only_discount":"0","is_only_online":"0","label":"0","activity_info":{"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""},"is_show_expected_delivery":"1","expected_delivery_times":"35"}
                 */
                public String id;
                public String admin_id;
                public String area_id;
                public String shop_id;
                public String init_date;
                public ShopinfoBean shopinfo;

                public static class ShopinfoBean implements Serializable {
                    /**
                     * id : 3450
                     * tag : -1006
                     * admin_id : 1
                     * shopname : 分区活动
                     * type_id : 991
                     * is_first_discount : 0
                     * first_discount : 0
                     * ordervalid : 1
                     * work_logo :
                     * xiaoliang : 0
                     * commentgrade : 0
                     * commentnum : 0
                     * open_promotion : 0
                     * promotion :
                     * is_coupon : 1
                     * is_delivery_free : 0
                     * is_discount : 0
                     * discount_value : 10.0
                     * discountlimitmember : 0
                     * shopimage :
                     * dis : 1.7km
                     * range : 1676
                     * shopdes :
                     * condition : all
                     * worktime : 1
                     * outtime_info :
                     * is_open_shopactive : 1
                     * is_only_promotion : 0
                     * is_only_discount : 0
                     * is_only_online : 0
                     * label : 0
                     * activity_info : {"first_order":"","consume":"","consume_arr":[],"member":"","delivery_fee":"","coupon":"","coupon_package":"","shop_discount":"","shop_discount_for_h5":"","manzeng":""}
                     * is_show_expected_delivery : 1
                     * expected_delivery_times : 35
                     */
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
                    public ActivityInfoBean activity_info;
                    public String label;//店铺标签 0无 1品牌 2新店
                    public String shop_label;
                    public String range;
                    public String open_selftake;
                    public String expected_delivery_times;
                    public String is_show_delivery_service;
                    public String outtime_info;
                    public String is_show_sales_volume;
                    public String is_show_expected_delivery;
                    //满赠
                    public String is_manzeng;

                    public static class ActivityInfoBean {
                        /**
                         * first_order :
                         * consume :
                         * consume_arr : []
                         * member :
                         * delivery_fee :
                         * coupon :
                         * coupon_package :
                         * shop_discount :
                         * shop_discount_for_h5 :
                         * manzeng :
                         */
                        public String first_order;
                        public String consume;
                        public String member;
                        public String delivery_fee;
                        public String coupon;
                        public String coupon_package;
                        public String shop_discount;
                        public String shop_discount_for_h5;
                        public String manzeng;
                        public String shop_first_discount;//新客立减
                        public List<String> consume_arr;
                    }
                }
            }
        }
    }
}
