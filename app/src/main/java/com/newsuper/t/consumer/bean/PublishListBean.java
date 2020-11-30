package com.newsuper.t.consumer.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19 0019.
 */

public class PublishListBean extends BaseBean {

    public ArrayList<PublishList> data;


    public static class PublishList {
        /**
         * id : 9
         * headimgurl :
         * customer_name :
         * first_category_name : 招聘求职
         * category_name : 求职
         * application_business : 互联网/IT
         * labs : ["好品牌","值得信赖"]
         * content : test9
         * images : ["http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png","http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png","http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png","http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png","http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png"]
         * is_top : 0
         * publish_date : 05-18
         * contact_name : 小李
         * contact_tel : 13888888888
         * is_colleted : 1
         * collected_id : 2
         */

        public String id;
        public String headimgurl;
        public String customer_name;
        public String first_category_name;
        public String category_name;
        public String application_business;
        public String content;
        public String is_top;
        public String publish_date;
        public String contact_name;
        public String contact_tel;
        public String is_colleted;
        public String collected_id;
        public ArrayList<String> labs;
        public ArrayList<String> images;
        public boolean isShowAll;
    }
}
