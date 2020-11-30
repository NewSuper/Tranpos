package com.newsuper.t.consumer.bean;

public class GrayBean {
    /*"error_msg": "",
            "error_code": 0,
            "data": {
        "gray_upgrade": 1   // 1 代表灰度升级，  0 代表默认请求生产环境*/
    public String error_msg;
    public String error_code;
    public GrayData data;
    public static class GrayData{
        public String gray_upgrade;
    }
}
