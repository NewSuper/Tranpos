package com.newsuper.t.juejinbao.bean;

public class BaseEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"ad_info":[{"id":343,"uid":1,"username":"yn123","ad_name":"开屏GIF","max_day":10000,"max_all":10000,"category":"","view_count":0,"click_count":0,"state":1,"cost":0,"create_time":1562323625,"update_time":1562323648,"type":100,"pid":0,"ptitle":"","directional_packet":0,"region":"{\"provinces\":[],\"citys\":[]}","sex":0,"device":"[]","online_date_start":1556380800,"online_date_end":1588003199,"online_time_start":0,"online_time_end":86399,"price_type":1,"price":100,"link":"http://www.baidu.com","style_type":17,"images":["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/07/05/R2y7xmeHjs.gif"],"title":"掘金宝","sub_title":"看资讯影视赚车子房子","download":0,"download_type":0,"download_ios":"","download_android":"","ad_position":"1","keywords":"[]","ad_sign":"广告","ad_type":2,"ad_position_json":"[\"1\"]"}],"ad_platform_type":2}
     * time : 1563163151
     * vsn : 1.8.2
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }
}
