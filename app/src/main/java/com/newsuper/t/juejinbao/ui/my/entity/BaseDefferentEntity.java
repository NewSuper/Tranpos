package com.newsuper.t.juejinbao.ui.my.entity;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class BaseDefferentEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : 操作成功
     * time : 1565837033
     * vsn : 1.8.8.3
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
