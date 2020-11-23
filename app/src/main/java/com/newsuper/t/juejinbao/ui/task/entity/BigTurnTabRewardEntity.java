package com.newsuper.t.juejinbao.ui.task.entity;

public class BigTurnTabRewardEntity {

    /**
     * code : 0
     * msg : success
     * data : {"coin":45}
     * time : 1574236963
     * vsn : 1.8.8.2
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
         * coin : 45
         */

        private double coin;

        public double getCoin() {
            return coin;
        }

        public void setCoin(double coin) {
            this.coin = coin;
        }
    }
}
