package com.newsuper.t.consumer.bean;

public class ShopCommentInfo extends BaseBean {


    /**
     * data : {"order_no":"W201903199757101488154855","admin_id":"1","shop_id":"3456","shop_name":"店铺名称","shop_image":"/uploads/admin_33/shop_16/shopimage_16.jpg","courier_id":"158","courier_name":"28配送员2","courier_pic":""}
     */

    public DataBean data;
    

    public static class DataBean {
        /**
         * order_no : W201903199757101488154855
         * admin_id : 1
         * shop_id : 3456
         * shop_name : 店铺名称
         * shop_image : /uploads/admin_33/shop_16/shopimage_16.jpg
         * courier_id : 158
         * courier_name : 28配送员2
         * courier_pic : 
         */

        public String order_no;
        public String admin_id;
        public String shop_id;
        public String shop_name;
        public String shop_image;
        public String courier_id;
        public String courier_name;
        public String courier_pic;
    }
}
