package com.newsuper.t.juejinbao.ui.home.entity;

public class FinishTaskEntity {
    /**
     * code : 0
     * message : success
     * data : {"type":1,"reward_value":2,"finished_num":1,"total_task_num":2}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : 1
         * reward_value : 2
         * finished_num : 1
         * total_task_num : 2
         */

        private int type;
        private int reward_value;
        private int finished_num;
        private int total_task_num;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getReward_value() {
            return reward_value;
        }

        public void setReward_value(int reward_value) {
            this.reward_value = reward_value;
        }

        public int getFinished_num() {
            return finished_num;
        }

        public void setFinished_num(int finished_num) {
            this.finished_num = finished_num;
        }

        public int getTotal_task_num() {
            return total_task_num;
        }

        public void setTotal_task_num(int total_task_num) {
            this.total_task_num = total_task_num;
        }
    }
}
