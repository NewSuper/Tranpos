package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class MessageHintEntity extends Entity {


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
        /**
         * wealth_plan : 0
         * unsigned : 0
         * msg : 0
         * reward_coefficient : 5
         */

        private int wealth_plan;
        private int unsigned;
        private int msg;
        private double reward_coefficient;

        public int getWealth_plan() {
            return wealth_plan;
        }

        public void setWealth_plan(int wealth_plan) {
            this.wealth_plan = wealth_plan;
        }

        public int getUnsigned() {
            return unsigned;
        }

        public void setUnsigned(int unsigned) {
            this.unsigned = unsigned;
        }

        public int getMsg() {
            return msg;
        }

        public void setMsg(int msg) {
            this.msg = msg;
        }

        public double getReward_coefficient() {
            return reward_coefficient;
        }

        public void setReward_coefficient(double reward_coefficient) {
            this.reward_coefficient = reward_coefficient;
        }
    }
}
