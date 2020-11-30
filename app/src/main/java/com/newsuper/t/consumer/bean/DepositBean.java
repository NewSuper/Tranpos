package com.newsuper.t.consumer.bean;

import java.util.List;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositBean {

    /**
     * error_msg :
     * error_code : 0
     * data : [{"area_id":"1","area_name":"南山分区","summoney":50}]
     */

    public String error_msg;
    public int error_code;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * area_id : 1
         * area_name : 南山分区
         * summoney : 50
         */

        public String area_id;
        public String area_name;
        public String summoney;
    }
}
