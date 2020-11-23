package com.newsuper.t.juejinbao.ui.my.entity;

public class InviteFriendRewardValueEntity {

    /**
     * code : 0
     * msg : success
     * data : {"inviting_reward_first":25,"inviting_reward":12.5,"inviting_by_mobile":25}
     * time : 1574070636
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
         * inviting_reward_first : 25
         * inviting_reward : 12.5
         * inviting_by_mobile : 25
         */

        private double inviting_reward_first;
        private double inviting_reward;
        private double inviting_by_mobile;

        public double getInviting_reward_first() {
            return inviting_reward_first;
        }

        public void setInviting_reward_first(double inviting_reward_first) {
            this.inviting_reward_first = inviting_reward_first;
        }

        public double getInviting_reward() {
            return inviting_reward;
        }

        public void setInviting_reward(double inviting_reward) {
            this.inviting_reward = inviting_reward;
        }

        public double getInviting_by_mobile() {
            return inviting_by_mobile;
        }

        public void setInviting_by_mobile(double inviting_by_mobile) {
            this.inviting_by_mobile = inviting_by_mobile;
        }
    }
}
