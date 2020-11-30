package com.newsuper.t.consumer.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class OrderPayResultBean extends BaseBean {
    public PayResultData data;
    public static class PayResultData{
        public String status;
        public String paytype;
        public String order_no;
        public String order_id;
        public ZhiFuParameters zhifuParameters;
        public String zhifubaoParameters;

    }
    public static class ZhiFuParameters implements Serializable{
        public String appid;
        public String partnerid;
        public String prepayid;
        public String timestamp;
        public String noncestr;
        public String sign;
    }

}
