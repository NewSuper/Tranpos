package com.newsuper.t.juejinbao.ui.login.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class DefaultIntviteCodeEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : ["1111","1112","1113"]
     * time : 1568099132
     * vsn : 1.8.8
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private List<String> data;

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

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
