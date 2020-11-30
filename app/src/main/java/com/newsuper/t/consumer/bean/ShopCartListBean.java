package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class ShopCartListBean extends BaseBean {

    /**
     * data : {"IsShopMember":"0","memberFreeze":"0","shoplist":[{"id":"3","shopname":"乐外卖测试店铺","is_first_discount":"1","first_discount":"11.0","is_open_shopactive":"1","open_promotion":"1","promotion":[{"amount":100,"discount":30},{"amount":80,"discount":10},{"amount":20,"discount":5}],"is_discount":"1","discount_value":"9.0","discountlimitmember":"1","is_first_order":"1"}],"foodlist":[{"id":"5409282","status":"CLOSED","price":"3.00","buying_price":"123.32","is_dabao":"0","dabao_money":"0.00","stockvalid":"0","stock":"20","open_autostock":"1","autostocknum":"20","member_price_used":"0","member_price":"0.00","memberlimit":"0","has_formerprice":"0","formerprice":"0.00","is_nature":"1","nature":[{"naturename":"啊","data":[{"naturevalue":"啊","price":"1","is_open":"0"},{"naturevalue":"不","price":"10","is_open":"0"},{"naturevalue":"去","price":"10","is_open":"1"},{"naturevalue":"在","price":"1","is_open":"1"},{"naturevalue":"下次vzx","price":"0","is_open":"1"},{"naturevalue":"vxzcv下次","price":"0","is_open":"1"}],"maxchoose":"1"},{"naturename":"小分","data":[{"naturevalue":"加小碗饭","price":"2","is_open":"0"},{"naturevalue":"加大碗饭","price":"3","is_open":"0"}],"maxchoose":"2"}]}],"food_package":[]}
     */

    public ShopCartData data;


    public static class ShopCartData {
        public String IsShopMember;
        public String memberFreeze;
        public ArrayList<ShopListBean> shoplist;
        public ArrayList<FoodListBean> foodlist;
        public static class ShopListBean {

            public String id;
            public String shopname;
            public String shopimage;
            public String is_first_discount;
            public String first_discount;
            public String is_open_shopactive;
            public String open_promotion;
            public String is_discount;
            public float discount_value;
            public String discountlimitmember;
            public String is_first_order;
            public ArrayList<PromotionBean> promotion;
            public ArrayList<CartGoodsModel> models;
            public String worktime;
            public double basicprice;
            public String outtime_info;

            public String shop_first_order;//(是否是当前店铺首单),
            public String is_shop_first_discount;//（店铺是否开启新客立减）,
            public String shop_first_discount;

            public static class PromotionBean {
                public int amount;
                public int discount;

            }
        }

        public static class FoodListBean {
            /**
             * id : 5409282
             * status : CLOSED
             * price : 3.00
             * buying_price : 123.32
             * is_dabao : 0
             * dabao_money : 0.00
             * stockvalid : 0
             * stock : 20
             * open_autostock : 1
             * autostocknum : 20
             * member_price_used : 0
             * member_price : 0.00
             * memberlimit : 0
             * has_formerprice : 0
             * formerprice : 0.00
             * is_nature : 1
             * nature : [{"naturename":"啊","data":[{"naturevalue":"啊","price":"1","is_open":"0"},{"naturevalue":"不","price":"10","is_open":"0"},{"naturevalue":"去","price":"10","is_open":"1"},{"naturevalue":"在","price":"1","is_open":"1"},{"naturevalue":"下次vzx","price":"0","is_open":"1"},{"naturevalue":"vxzcv下次","price":"0","is_open":"1"}],"maxchoose":"1"},{"naturename":"小分","data":[{"naturevalue":"加小碗饭","price":"2","is_open":"0"},{"naturevalue":"加大碗饭","price":"3","is_open":"0"}],"maxchoose":"2"}]
             */

            public String id;
            public String status;
            public String price;
            public String buying_price;
            public String is_dabao;
            public String dabao_money;
            public String stockvalid;
            public int stock;
            public String open_autostock;
            public String autostocknum;
            public String member_price_used;
            public String member_price;
            public String memberlimit;
            public String has_formerprice;
            public String formerprice;
            public String is_nature;
            public ArrayList<NatureBean> nature;
            public String start_time;
            public String stop_time;
            public ArrayList<LimitTimeBean> limit_time;
            public String is_limitfood;
            public String is_all_limit;
            public String is_customerday_limit;
            public String datetage;
            public String timetage;
            public String hasBuyNumByDay;
            public String hasBuyNum;
            public int is_all_limit_num;
            public int day_foodnum;

            public static class LimitTimeBean{
                public String start;
                public String stop;
            }
            public static class NatureBean {
                /**
                 * naturename : 啊
                 * data : [{"naturevalue":"啊","price":"1","is_open":"0"},{"naturevalue":"不","price":"10","is_open":"0"},{"naturevalue":"去","price":"10","is_open":"1"},{"naturevalue":"在","price":"1","is_open":"1"},{"naturevalue":"下次vzx","price":"0","is_open":"1"},{"naturevalue":"vxzcv下次","price":"0","is_open":"1"}]
                 * maxchoose : 1
                 */

                public String naturename;
                public String maxchoose;
                public ArrayList<NatureData> data;
                public static class NatureData {
                    /**
                     * naturevalue : 啊
                     * price : 1
                     * is_open : 0
                     */

                    public String naturevalue;
                    public String price;
                    public String is_open;

                }
            }
        }
    }
    public static class FoodPackageBean{
        public String id;
        public String name;
        public String price;
        public String shop_id;
        public ArrayList<GoodsListBean.PackageNature> nature;
    }
}
