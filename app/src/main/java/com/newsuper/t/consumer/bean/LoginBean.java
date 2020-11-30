package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2017/6/1 0001.
 */

public class LoginBean extends BaseBean{
    public LoginData data;
    public static class LoginData{
        public String lwm_sess_token;
        public String customer_id;
        public String is_bind;
    }
}
