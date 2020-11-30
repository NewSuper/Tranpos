package com.newsuper.t.consumer.bean;


import java.util.List;


public class TestModle extends BaseBean{
    public DataBean data;


    /**
     * lunboimg : {"img_array":["/uploads/publicprice/admin_1/max/c63edde.jpg","/uploads/publicprice/admin_1/max/fashibocuimianbaopeizhengui(bishengke).jpg","/upload_files/image/20161102/UdEi6zDJSViwvIOnBziP2TbQ0S9MP8L1.jpg"],"links_array":["11167","22267","3367","4467","5567"]}
     * shoptype : [{"id":"973","admin_id":"1","name":"123","tag":"0","icon":"","is_show":"1"}]
     * shoplist : [{"is_show_distance":1,"id":"3","tag":"0","admin_id":"1","shopname":"乐外卖测试店铺","type_id":"960","is_first_discount":"1","first_discount":"11.0","ordervalid":"1","worktime":"1","work_logo":"","basicprice":"20","delivery_fee":"1.0","xiaoliang":"4443","commentgrade":"4.4","commentnum":"109","open_promotion":"1","promotion":"消费满8元减2元；","is_coupon":"1","is_delivery_free":"1","is_discount":"1","discount_value":"9.0","discountlimitmember":"1","shopimage":"http://img.lewaimai.com/uploads/publicprice/admin_1/max/96m58PICHux_1024.jpg","dis":"0 米","shopdes":"测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述2222212342141242134","delivery_service":"乐外卖专送wer","condition":"all"}]
     */
    public static  class  DataBean{
        public LunboimgBean lunboimg;
        public List<ShoptypeBean> shoptype;
        public List<ShoplistBean> shoplist;
    }
    
  


    public static class LunboimgBean {
        public List<String> img_array;
        public List<String> links_array;
    }

    public static class ShoptypeBean {
        /**
         * id : 973
         * admin_id : 1
         * name : 123
         * tag : 0
         * icon :
         * is_show : 1
         */

        public String id;
        public String admin_id;
        public String name;
        public String tag;
        public String icon;
        public String is_show;

    }

    public static class ShoplistBean {
        /**
         * is_show_distance : 1
         * id : 3
         * tag : 0
         * admin_id : 1
         * shopname : 乐外卖测试店铺
         * type_id : 960
         * is_first_discount : 1
         * first_discount : 11.0
         * ordervalid : 1
         * worktime : 1
         * work_logo :
         * basicprice : 20
         * delivery_fee : 1.0
         * xiaoliang : 4443
         * commentgrade : 4.4
         * commentnum : 109
         * open_promotion : 1
         * promotion : 消费满8元减2元；
         * is_coupon : 1
         * is_delivery_free : 1
         * is_discount : 1
         * discount_value : 9.0
         * discountlimitmember : 1
         * shopimage : http://img.lewaimai.com/uploads/publicprice/admin_1/max/96m58PICHux_1024.jpg
         * dis : 0 米
         * shopdes : 测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述测试店铺描述2222212342141242134
         * delivery_service : 乐外卖专送wer
         * condition : all
         */

        public int is_show_distance;
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
    }
}
