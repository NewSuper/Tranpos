package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class ExChangeMiniProgramEntity extends Entity {

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private DataBean data;
    public static class DataBean {
        private int is_new;
        private double use_money;
        private double exchange_vcoin;
        private double price_of_jjb;

        public double getPrice_of_jjb() {
            return price_of_jjb;
        }

        public void setPrice_of_jjb(double price_of_jjb) {
            this.price_of_jjb = price_of_jjb;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public double getUse_money() {
            return use_money;
        }

        public void setUse_money(double use_money) {
            this.use_money = use_money;
        }

        public double getExchange_vcoin() {
            return exchange_vcoin;
        }

        public void setExchange_vcoin(double exchange_vcoin) {
            this.exchange_vcoin = exchange_vcoin;
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
