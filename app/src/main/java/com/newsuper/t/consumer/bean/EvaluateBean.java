package com.newsuper.t.consumer.bean;

import java.util.List;

/**
 * Create by Administrator on 2019/5/5 0005
 */
public class EvaluateBean {

    /**
     * error_msg :
     * error_code : 0
     * data : {"pagesize":20,"page":1,"totalpage":2,"counts":28,"comments":[{"comment_id":"574","admin_id":"1","shop_id":"3304","grade":"5","time":"2018-01-15 14:41:18","customer_name":"小幸福(匿名评价）","imgurl":["/upload_files/image/20180115/151599852590808nZ13eU6978B6oD64H.jpg","/upload_files/image/20180115/1515998534077826879I05F31qZ408S4.jpg","/upload_files/image/20180115/151599853922803g7r28dhT030I6F3c9.jpg","/upload_files/image/20180115/1515998545198zlPWvf159L83554JKTZ.jpg"],"content":"评价内容","creplycontent":"","creplytime":"2018-09-26 17:56:14","shopname":"店铺名称","shopimage":"/uploads/admin_33/shop_16/shopimage_16.jpg"}]}
     */

    public String error_msg;
    public int error_code;
    public DataBean data;

    public static class DataBean {
        /**
         * pagesize : 20
         * page : 1
         * totalpage : 2
         * counts : 28
         * comments : [{"comment_id":"574","admin_id":"1","shop_id":"3304","grade":"5","time":"2018-01-15 14:41:18","customer_name":"小幸福(匿名评价）","imgurl":["/upload_files/image/20180115/151599852590808nZ13eU6978B6oD64H.jpg","/upload_files/image/20180115/1515998534077826879I05F31qZ408S4.jpg","/upload_files/image/20180115/151599853922803g7r28dhT030I6F3c9.jpg","/upload_files/image/20180115/1515998545198zlPWvf159L83554JKTZ.jpg"],"content":"评价内容","creplycontent":"","creplytime":"2018-09-26 17:56:14","shopname":"店铺名称","shopimage":"/uploads/admin_33/shop_16/shopimage_16.jpg"}]
         */

        public int pagesize;
        public int page;
        public int totalpage;
        public int counts;
        public List<CommentsBean> comments;

        public static class CommentsBean {
            /**
             * comment_id : 574
             * admin_id : 1
             * shop_id : 3304
             * grade : 5
             * time : 2018-01-15 14:41:18
             * customer_name : 小幸福(匿名评价）
             * imgurl : ["/upload_files/image/20180115/151599852590808nZ13eU6978B6oD64H.jpg","/upload_files/image/20180115/1515998534077826879I05F31qZ408S4.jpg","/upload_files/image/20180115/151599853922803g7r28dhT030I6F3c9.jpg","/upload_files/image/20180115/1515998545198zlPWvf159L83554JKTZ.jpg"]
             * content : 评价内容
             * creplycontent :
             * creplytime : 2018-09-26 17:56:14
             * shopname : 店铺名称
             * shopimage : /uploads/admin_33/shop_16/shopimage_16.jpg
             */

            public String comment_id;
            public String admin_id;
            public String shop_id;
            public String grade;
            public String time;
            public String customer_name;
            public String content;
            public String creplycontent;
            public String creplytime;
            public String shopname;
            public String shopimage;
            public List<String> imgurl;
        }
    }
}
