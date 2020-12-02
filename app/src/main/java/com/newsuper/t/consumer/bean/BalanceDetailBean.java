package com.newsuper.t.consumer.bean;

import java.util.List;



public class BalanceDetailBean {

    /**
     * error_code : 0
     * error_msg :
     * data : {"historylist":[{"id":"1860","member_id":"97","lewaimai_customer_id":"4","admin_id":"1","employee_id":"0","old_balance":"22661.90","new_balance":"22651.90","change_date":"2017-04-27 15:20:57","change_type":"2","change_order_id":"749","memo":""},{"id":"1857","member_id":"0","lewaimai_customer_id":"4","admin_id":"1","employee_id":"0","old_balance":"20441.80","new_balance":"22661.90","change_date":"2017-04-26 23:11:39","change_type":"5","change_order_id":"383","memo":""}]}
     */

    public String error_code;
    public String error_msg;
    public BalanceDetailData data;


    public static class BalanceDetailData {
        public List<HistoryListBean> historylist;
    }
    public static class HistoryListBean {
        /**
         * id : 1860
         * member_id : 97
         * lewaimai_customer_id : 4
         * admin_id : 1
         * employee_id : 0
         * old_balance : 22661.90
         * new_balance : 22651.90
         * change_date : 2017-04-27 15:20:57
         * change_type : 2
         * change_order_id : 749
         * memo :
         */

        public String id;
        public String member_id;
        public String lewaimai_customer_id;
        public String admin_id;
        public String employee_id;
        public double old_balance;
        public double new_balance;
        public String change_date;
        public int change_type;
        public String change_order_id;
        public String memo;

    }
}
