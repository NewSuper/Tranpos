package com.newsuper.t.juejinbao.ui.task.entity;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class SleepEarningEntity2 extends Entity {
    private int code;
    private String msg;
    private int time;
    private String vsn;
  //  private DataBean data;
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

    }

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
