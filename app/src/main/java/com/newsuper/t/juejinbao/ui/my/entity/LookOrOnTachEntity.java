package com.newsuper.t.juejinbao.ui.my.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class LookOrOnTachEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : 操作成功
     * time : 1563454448
     * vsn : 1.8.4
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
