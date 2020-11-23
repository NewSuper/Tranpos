package com.newsuper.t.juejinbao.ui.movie.entity;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class UploadMovieDetailBean extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"mdid":"d639e4e967ad91dad653b9df33274c93"}
     * time : 1579091782
     * vsn : 1.8.8
     */

    private int code;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * mdid : d639e4e967ad91dad653b9df33274c93
         */

        private String mdid;

        public String getMdid() {
            return mdid;
        }

        public void setMdid(String mdid) {
            this.mdid = mdid;
        }
    }
}
