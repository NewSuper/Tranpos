package com.newsuper.t.consumer.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/20 0020.
 */

public class MyPublishListBean extends BaseBean implements Serializable{
    public ArrayList<MyPublishListData> data;
    public static class MyPublishListData implements Serializable{
        /**
         * id : 59
         * month : 五月
         * day : 22
         * category : 招聘求职-全职招聘
         * is_topping : 0
         * top_rest :
         * status : 1
         * content : 梅西梅西梅西梅西梅西梅西梅西梅西梅西梅西……
         * images : ["http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png","http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png","http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png","http://img.lewaimai.com/upload_files/image/20180523/h0WWghhcCPctwdRVOYTfcjsNC9hUkZgC.png"]
         */

        public String id;
        public String month;
        public String day;
        public String category;
        public String is_topping;
        public String top_rest;
        public String status;
        public String content;
        public ArrayList<String> images;
        public boolean isShowAll;

    }

}
