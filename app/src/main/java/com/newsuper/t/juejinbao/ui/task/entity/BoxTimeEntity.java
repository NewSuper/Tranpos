package com.newsuper.t.juejinbao.ui.task.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class BoxTimeEntity extends Entity {
    /**
     * code : 0
     * msg : success
     * data : 0
     * time : 1573550510
     * vsn : 1.8.8
     */

    private int code;
    private String msg;
    private long data;
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

    public long getData() {
        return data;
    }

    public void setData(long data) {
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
