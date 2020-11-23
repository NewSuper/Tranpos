package com.newsuper.t.juejinbao.ui.login.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class SetAndChangePswEntity extends Entity {

    /**
     * code : 0
     * msg : 验证码已发送
     * data : []
     * time : 1562579238
     * vsn : 1.8.2
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    /**
     * data : {}
     */

//    private DataBean data;


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



    public static class DataBean {
    }
}
