package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2018/7/19 0019.
 */

public class SetTopPayBean extends BaseBean {
    public SetTopPayData data;
    public static class SetTopPayData{
        public String status;
        public String info_id;
        public ZhiFuParameters zhifuParameters;

    }
    public static class ZhiFuParameters{
        public String appid;
        public String partnerid;
        public String prepayid;
        public String timestamp;
        public String noncestr;
        public String sign;
    }
}
