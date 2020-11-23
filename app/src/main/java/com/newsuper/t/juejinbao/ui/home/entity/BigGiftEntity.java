package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class BigGiftEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"vcoin":5,"vcoin_to_rmb":10.35,"user_count":229834}
     * time : 1565076548
     * vsn : 1.8.4
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
         * vcoin : 5
         * vcoin_to_rmb : 10.35
         * user_count : 229834
         */

        private double vcoin;
        private double vcoin_to_rmb;
        private int user_count;

        public double getVcoin() {
            return vcoin;
        }

        public void setVcoin(double vcoin) {
            this.vcoin = vcoin;
        }

        public double getVcoin_to_rmb() {
            return vcoin_to_rmb;
        }

        public void setVcoin_to_rmb(double vcoin_to_rmb) {
            this.vcoin_to_rmb = vcoin_to_rmb;
        }

        public int getUser_count() {
            return user_count;
        }

        public void setUser_count(int user_count) {
            this.user_count = user_count;
        }
    }
}
