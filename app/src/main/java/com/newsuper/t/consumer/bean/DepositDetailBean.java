package com.newsuper.t.consumer.bean;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositDetailBean {

    /**
     * error_msg :
     * error_code : 0
     * data : {"id":"5","admin_id":"1","area_id":"3304","change_type":"1","change_date":"2019-07-01 14:21:18","money":"+10","deposit":"50","area_name":"南山分区","remark":"押了1个桶"}
     */

    public String error_msg;
    public int error_code;
    public DataBean data;

    public static class DataBean {
        /**
         * id : 5
         * admin_id : 1
         * area_id : 3304
         * change_type : 1
         * change_date : 2019-07-01 14:21:18
         * money : +10
         * deposit : 50
         * area_name : 南山分区
         * remark : 押了1个桶
         */

        public String id;
        public String admin_id;
        public String area_id;
        public String change_type;
        public String change_date;
        public String money;
        public String deposit;
        public String area_name;
        public String remark;
    }
}
