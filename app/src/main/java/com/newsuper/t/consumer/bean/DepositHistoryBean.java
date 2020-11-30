package com.newsuper.t.consumer.bean;

import java.util.List;

/**
 * Create by Administrator on 2019/6/17 0017
 */
public class DepositHistoryBean {

    /**
     * error_msg :
     * error_code : 0
     * data : {"pagesize":20,"page":1,"totalpage":2,"counts":28,"datas":[{"id":"5","admin_id":"1","area_id":"3304","change_type":"1","change_date":"2019-07-01 14:21:18","money":"+10","deposit":"50","area_name":"南山分区"}]}
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
         * datas : [{"id":"5","admin_id":"1","area_id":"3304","change_type":"1","change_date":"2019-07-01 14:21:18","money":"+10","deposit":"50","area_name":"南山分区"}]
         */

        public int pagesize;
        public int page;
        public int totalpage;
        public int counts;
        public List<DatasBean> datas;

        public static class DatasBean {
            /**
             * id : 5
             * admin_id : 1
             * area_id : 3304
             * change_type : 1
             * change_date : 2019-07-01 14:21:18
             * money : +10
             * deposit : 50
             * area_name : 南山分区
             */

            public String id;
            public String admin_id;
            public String area_id;
            public String change_type;
            public String change_date;
            public String money;
            public String deposit;
            public String area_name;
        }
    }
}
