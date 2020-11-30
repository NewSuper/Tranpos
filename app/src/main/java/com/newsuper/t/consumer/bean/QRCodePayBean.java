package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class QRCodePayBean {

    public String error_code;
    public String error_msg;
    public DataBean data;
    public static class DataBean {
        public String member_id;
        public String weixin_password;
        public String qrcodeurl;
        public String imgurl;
    }
}
