package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;

public class BindThirdEntity implements Serializable {

    /**
     * code : 0
     * msg : success
     * data : {"coin":500,"reward_type":1,"user_token":""}
     * time : 1563857407
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
        /**+
         * coin : 500
         * reward_type : 1
         * user_token :
         */

        private double coin;
        private int reward_type;
        private String user_token;

        public double getCoin() {
            return coin;
        }

        public void setCoin(double coin) {
            this.coin = coin;
        }

        public int getReward_type() {
            return reward_type;
        }

        public void setReward_type(int reward_type) {
            this.reward_type = reward_type;
        }

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }
    }

    @Override
    public String toString() {
        return "BindThirdEntity{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", time=" + time +
                ", vsn='" + vsn + '\'' +
                '}';
    }
}
