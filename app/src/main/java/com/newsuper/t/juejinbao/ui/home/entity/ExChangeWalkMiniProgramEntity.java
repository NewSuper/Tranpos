package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class ExChangeWalkMiniProgramEntity extends Entity {

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private DataBean data;
    public static class DataBean {
        private String vcoin;
        private String thumb;

        public String getVcoin() {
            return vcoin;
        }

        public void setVcoin(String vcoin) {
            this.vcoin = vcoin;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

}
