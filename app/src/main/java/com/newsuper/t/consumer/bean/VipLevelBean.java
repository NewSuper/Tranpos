package com.newsuper.t.consumer.bean;

import java.util.List;

/**
 * Create by Administrator on 2019/7/1 0001
 */
public class VipLevelBean {

    /**
     * error_msg :
     * error_code : 0
     * data : {"gradelist":[{"id":"1","admin_id":"1","name":"VIP1","nickname":"超级会员","status":"1","create_time":"2019-06-21 14:46:24","is_upbyrecharge":"1","recharge_money":"1000.00","privilege":""},{"id":"2","admin_id":"1","name":"VIP2","nickname":"黄泥会员","status":"1","create_time":"2019-06-21 14:46:51","is_upbyrecharge":"0","recharge_money":"0.00","privilege":""}],"is_member":1,"grade_id":"1"}
     */

    public String error_msg;
    public int error_code;
    public DataBean data;

    public static class DataBean {
        /**
         * gradelist : [{"id":"1","admin_id":"1","name":"VIP1","nickname":"超级会员","status":"1","create_time":"2019-06-21 14:46:24","is_upbyrecharge":"1","recharge_money":"1000.00","privilege":""},{"id":"2","admin_id":"1","name":"VIP2","nickname":"黄泥会员","status":"1","create_time":"2019-06-21 14:46:51","is_upbyrecharge":"0","recharge_money":"0.00","privilege":""}]
         * is_member : 1
         * grade_id : 1
         */

        public int is_member;
        public String grade_id;
        public List<GradelistBean> gradelist;

        public static class GradelistBean {
            /**
             * id : 1
             * admin_id : 1
             * name : VIP1
             * nickname : 超级会员
             * status : 1
             * create_time : 2019-06-21 14:46:24
             * is_upbyrecharge : 1
             * recharge_money : 1000.00
             * privilege :
             */

            public String id;
            public String admin_id;
            public String name;
            public String nickname;
            public String status;
            public String create_time;
            public String is_upbyrecharge;
            public String recharge_money;
            public String privilege;
        }
    }
}
