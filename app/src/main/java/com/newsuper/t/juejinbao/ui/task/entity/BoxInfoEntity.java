package com.newsuper.t.juejinbao.ui.task.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class BoxInfoEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"coin":37.5,"reward_type":1,"treasure_box_share_coin":75,"treasure_box_share_reward_type":1,"inviting_friend_first_coin":25,"inviting_friend_first_reward_type":2}
     * time : 1573641749
     * vsn : 1.9.1.06
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
         * coin : 37.5
         * reward_type : 1
         * treasure_box_share_coin : 75
         * treasure_box_share_reward_type : 1
         * inviting_friend_first_coin : 25
         * inviting_friend_first_reward_type : 2
         */

        private double coin;
        private int reward_type;
        private int treasure_box_share_coin;
        private int treasure_box_share_reward_type;
        private int inviting_friend_first_coin;
        private int inviting_friend_first_reward_type;

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

        public int getTreasure_box_share_coin() {
            return treasure_box_share_coin;
        }

        public void setTreasure_box_share_coin(int treasure_box_share_coin) {
            this.treasure_box_share_coin = treasure_box_share_coin;
        }

        public int getTreasure_box_share_reward_type() {
            return treasure_box_share_reward_type;
        }

        public void setTreasure_box_share_reward_type(int treasure_box_share_reward_type) {
            this.treasure_box_share_reward_type = treasure_box_share_reward_type;
        }

        public int getInviting_friend_first_coin() {
            return inviting_friend_first_coin;
        }

        public void setInviting_friend_first_coin(int inviting_friend_first_coin) {
            this.inviting_friend_first_coin = inviting_friend_first_coin;
        }

        public int getInviting_friend_first_reward_type() {
            return inviting_friend_first_reward_type;
        }

        public void setInviting_friend_first_reward_type(int inviting_friend_first_reward_type) {
            this.inviting_friend_first_reward_type = inviting_friend_first_reward_type;
        }
    }
}
