package com.newsuper.t.consumer.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PayOrderBean extends BaseBean {

    /**
     * data : {"order_no":"P201706079797499952367229","order_id":"43919415","paytype":"weixinzhifu","status":"paying","order_pay_id":"598809"}
     */

    public DataBean data;

    public static class DataBean {
        /**
         * order_no : P201706079797499952367229
         * order_id : 43919415
         * paytype : weixinzhifu
         * status : paying
         * order_pay_id : 598809
         */

        public String order_no;
        public String order_id;
        public String paytype;
        public String status;
        public String order_pay_id;
        public OrderPayResultBean.ZhiFuParameters zhifuParameters;
        public String zhifubaoParameters;
    }
    static class ZhiFuParameters implements Serializable {
        public String appid;
        public String partnerid;
        public String prepayid;
        public String timestamp;
        public String noncestr;
        public String sign;
    }
}
