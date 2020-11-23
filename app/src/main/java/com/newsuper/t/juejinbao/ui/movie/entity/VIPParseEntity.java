package com.newsuper.t.juejinbao.ui.movie.entity;

public class VIPParseEntity  {


    /**
     * code : 0
     * msg : success
     * data : {"云线路1":"http://www.playm3u8.cn/jiexi.php?url=","云线路2":"https://jexi.a0296.cn/?url=","云线路3":"http://www.xrunuu.top/uv/?url=","云线路4":"https://jiexi.380k.com/?url=","云线路5":"http://jx.fengmangyingshi.vip/?url=","云线路6":"http://api.jiexi.la/jiexi/?url=","云线路7":"http://www.ckmov.vip/api.php?url=","云线路8":"http://okjx.cc/?url="}
     * time : 1564574827
     * vsn : 1.8.2
     */

    private int code;
    private String msg;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
