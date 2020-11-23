package com.newsuper.t.juejinbao.ui.task.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;


public class SleepEarningEntity extends Entity {
    private int code;
    private String msg;
    private int time;
    private String vsn;
    private DataBean data;

    public static class DataBean {
        private int reward_value;//奖励金币数
        private int record_id;//记录id(用于后面观看视频奖励)

        public int getReward_value() {
            return reward_value;
        }

        public void setReward_value(int reward_value) {
            this.reward_value = reward_value;
        }

        public int getRecord_id() {
            return record_id;
        }

        public void setRecord_id(int record_id) {
            this.record_id = record_id;
        }

        private int coin_value;// 可领取金币

        private int slept_time;// 已睡眠时间

        private int slept_status;// 是否已开启睡眠 0 否，1 是

        private int distance_start_sleep_time;// 距离可开启睡眠剩余时间(单位:秒)

        private String desc;// 文案描述

        private int timestamp;// 当前时间戳(单位:秒)

        private int projected_income;//一个晚上预计收入(获得最大金币数)

        public int getProjected_income() {
            return projected_income;
        }

        public void setProjected_income(int projected_income) {
            this.projected_income = projected_income;
        }

        public int getCoin_value() {
            return coin_value;
        }

        public void setCoin_value(int coin_value) {
            this.coin_value = coin_value;
        }

        public int getSlept_status() {
            return slept_status;
        }

        public void setSlept_status(int slept_status) {
            this.slept_status = slept_status;
        }

        public int getDistance_start_sleep_time() {
            return distance_start_sleep_time;
        }

        public void setDistance_start_sleep_time(int distance_start_sleep_time) {
            this.distance_start_sleep_time = distance_start_sleep_time;
        }

        public int getSlept_time() {
            return slept_time;
        }

        public void setSlept_time(int slept_time) {
            this.slept_time = slept_time;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
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
