package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/24 0024.
 */

public class HelpBean extends BaseBean {

    public HelpData data;


    public static class HelpData {
        public String title;
        public String delivery_fee_type;
        public String delivery_fee;
        public String delivery_radius;
        public String offline_limit;
        public ArrayList<String> delivery_day;
        public ArrayList<String> time_today;
        public ArrayList<String> time_otherday;
        public ArrayList<String> minute_end;
        public ArrayList<String> minute_start_today;
        public ArrayList<String> minute_start_otherday;
        public ArrayList<SpecialFeeBean> special_fee;
        public ArrayList<AddserviceBean> addservice;
        public ArrayList<IndividuationBean> individuation_fee;
        public ArrayList<ServiceContentBean> service_content;
        public ArrayList<DeliveryFeeByDistanceBean> delivery_fee_by_distance;
        public ArrayList<String> paytype;
        public String zhifubaozhifu_type;
        public String weixinzhifu_type;
        public String member_notice;
        public String member_balance;
        public CustomAddress nearest_info;

    }
    public static class SpecialFeeBean {
        /**
         * title : 保温箱
         * fee : 5.00
         */

        public String title;
        public String fee;

    }

    public static class AddserviceBean {
        /**
         * title : 高温补贴
         * fee : 2.50
         */

        public String title;
        public String fee;

    }
    public static class IndividuationBean {
        /**
         * title : 高温补贴
         * fee : 2.50
         */

        public String title;
        public String fee;

    }

    public static class DeliveryFeeByDistanceBean {
        /**
         * start : 1km
         * stop : 3km
         * long :
         * fee : 2.00
         */

        public String start;
        public String stop;
        public String fee;
        public String length;

    }
    public static class ServiceContentBean{
        public String name;
        public String type;
        public String content;
        public String sort;
        public int key;
        public ArrayList<String> value;
        public String currentValue;
    }
    public static class CustomAddress {
        /**
         * name : 刘先生
         * phone : 18688712411
         * address : 哈哈哈
         * address_name :
         * lng : 0.000000
         * lat : 0.000000
         */

        public String name;
        public String phone;
        public String address;
        public String address_name;
        public String lng;
        public String lat;
}
}
