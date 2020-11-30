package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VipPayStatusBean extends BaseBean{
    public StatusData data;
    public class StatusData{
        public int status;//status=0表示未使用，status=1表示已使用
    }

}
