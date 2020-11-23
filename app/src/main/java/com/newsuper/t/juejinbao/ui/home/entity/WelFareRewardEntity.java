package com.newsuper.t.juejinbao.ui.home.entity;

public class WelFareRewardEntity {

    /**
     * code : 0
     * msg : success
     * data : {"reward_value":20}
     * time : 1584534111
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
         * reward_value : 20
         */

        private int reward_value;
        private int ad_show_apart_time;

        public int getAd_show_apart_time() {
            return ad_show_apart_time;
        }

        public void setAd_show_apart_time(int ad_show_apart_time) {
            this.ad_show_apart_time = ad_show_apart_time;
        }

        public int getReward_value() {
            return reward_value;
        }

        public void setReward_value(int reward_value) {
            this.reward_value = reward_value;
        }
    }
}
