package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

public class OrderAgainBean extends BaseBean{
    /**
     * data : {"cart":[{"foodid":"19442561","foodnum":1,"foodname":"分组2_商品2_5折_多属性","foodprice":"5","member_price_used":"0","member_price":"0.00","unit":"","typeid":"-1","is_dabao":"0","dabao_money":"0","is_nature":"1","natureArray":[{"data":[{"name":"份量","value":"大份2元","naturevalueprice":2}],"selectedNaturePrice":7}],"foodInfo":{"foodtype":[]}},{"foodid":"19449358","foodnum":2,"foodname":"分组3_商品2","foodprice":"10","member_price_used":"0","member_price":"0.00","unit":"","typeid":"1491994","is_dabao":"0","dabao_money":"0","is_nature":"1","natureArray":[{"data":[{"name":"属性1","value":"大","naturevalueprice":1},{"name":"属性2","value":"高","naturevalueprice":1},{"name":"属性3","value":"黑","naturevalueprice":1},{"name":"属性4","value":"红、蓝","naturevalueprice":5},{"name":"属性5","value":"1111、2222、3333、4444、5555","naturevalueprice":5}],"selectedNaturePrice":23},{"data":[{"name":"属性1","value":"大","naturevalueprice":1},{"name":"属性2","value":"高","naturevalueprice":1},{"name":"属性3","value":"黑","naturevalueprice":1},{"name":"属性4","value":"红","naturevalueprice":2},{"name":"属性5","value":"1111","naturevalueprice":1}],"selectedNaturePrice":16}],"foodInfo":{"foodtype":{"id":"1491994","admin_id":"1","shop_id":"308570","name":"分组3","tag":"0","is_weekshow":"0","week":"1,2,3","supporttype":"0","is_show":"1","showtime":"a:2:{s:5:\"start\";s:5:\"00:00\";s:4:\"stop\";s:5:\"00:00\";}","is_waimai_show":"1","is_tangshi_show":"1","is_shouyinji_show":"1","is_zhengcan_show":"1"}}},{"foodid":"19449477","foodnum":1,"foodname":"会员价-多属性","foodprice":"10","member_price_used":"1","member_price":"1","unit":"","typeid":"1495372","is_dabao":"0","dabao_money":"0","is_nature":"1","natureArray":[{"data":[{"name":"份量","value":"小份","naturevalueprice":1}],"selectedNaturePrice":11}],"foodInfo":{"foodtype":{"id":"1495372","admin_id":"1","shop_id":"308570","name":"会员价","tag":"-10","is_weekshow":"0","week":"","supporttype":"0","is_show":"1","showtime":"a:2:{s:5:\"start\";s:5:\"00:00\";s:4:\"stop\";s:5:\"00:00\";}","is_waimai_show":"1","is_tangshi_show":"1","is_shouyinji_show":"1","is_zhengcan_show":"1"}}}],"foodInfo":[]}
     */

    public OrderAgainData data;

    public static class OrderAgainData {
        public ArrayList<CartGoodsData> cart;
        public ArrayList<FoodInfoBean> foodInfo;
    }
    public static class CartGoodsData {
        /**
         * foodid : 19442561
         * foodnum : 1
         * foodname : 分组2_商品2_5折_多属性
         * foodprice : 5
         * member_price_used : 0
         * member_price : 0.00
         * unit :
         * typeid : -1
         * is_dabao : 0
         * dabao_money : 0
         * is_nature : 1
         * natureArray : [{"data":[{"name":"份量","value":"大份2元","naturevalueprice":2}],"selectedNaturePrice":7}]
         * foodInfo : {"foodtype":[]}
         */

        public String foodid;
        public String foodnum;
        public String foodname;
        public String foodprice;
        public String member_price_used;
        public String member_price;
        public String unit;
        public String typeid;
        public String is_dabao;
        public String dabao_money;
        public String is_nature;
        public GoodsListBean.GoodsInfo foodInfo;
        public ArrayList<NatureArrayBean> natureArray;
    }
    public static class FoodInfoBean {
        public GoodsListBean.GoodsInfo foodInfo;

    }
    public static class FoodTypeData{

        /**
         * id : 1491994
         * admin_id : 1
         * shop_id : 308570
         * name : 分组3
         * tag : 0
         * is_weekshow : 0
         * week : 1,2,3
         * supporttype : 0
         * is_show : 1
         * showtime : a:2:{s:5:"start";s:5:"00:00";s:4:"stop";s:5:"00:00";}
         * is_waimai_show : 1
         * is_tangshi_show : 1
         * is_shouyinji_show : 1
         * is_zhengcan_show : 1
         */

        public String id;
        public String admin_id;
        public String shop_id;
        public String name;
        public String tag;
    }

    public static class NatureArrayBean {
        /**
         * data : [{"name":"份量","value":"大份2元","naturevalueprice":2}]
         * selectedNaturePrice : 7
         */
        public ArrayList<NatureDataBean> data;
        public String selectedNaturePrice;
    }
    public static class NatureDataBean {
        /**
         * name : 份量
         * value : 大份2元
         * naturevalueprice : 2
         */

        public String name;
        public String value;
        public String naturevalueprice;
    }
}
