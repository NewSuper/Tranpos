package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class GetCoinEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"wealth_plan":0,"unsigned":0,"msg":0,"reward_coefficient":5}
     * time : 1562749619
     * vsn : 1.8.2
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
        private double coin;
        private int extra_coin;

        public double getCoin() {
            return coin;
        }

        public void setCoin(double coin) {
            this.coin = coin;
        }

        public int getExtra_coin() {
            return extra_coin;
        }

        public void setExtra_coin(int extra_coin) {
            this.extra_coin = extra_coin;
        }
    }
}
