package com.newsuper.t.juejinbao.ui.home.entity;

public class WelfareEntity {
    /**
     * code : 0
     * message : success
     * data : {"reward_num":2,"got_total_num":5100}
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
         * reward_num : 2
         * got_total_num : 5100
         */

        private int reward_num;
        private int got_total_num;
        private String gif_url;
        private int finish_app_task_status;//app任务完成状态：0 全部完成; 1 将要完成任务1; 2 将要完成任务2

        public int getReward_num() {
            return reward_num;
        }

        public void setReward_num(int reward_num) {
            this.reward_num = reward_num;
        }

        public int getGot_total_num() {
            return got_total_num;
        }

        public void setGot_total_num(int got_total_num) {
            this.got_total_num = got_total_num;
        }

        public String getGif_url() {
            return gif_url;
        }

        public void setGif_url(String gif_url) {
            this.gif_url = gif_url;
        }

        public int getFinish_app_task_status() {
            return finish_app_task_status;
        }

        public void setFinish_app_task_status(int finish_app_task_status) {
            this.finish_app_task_status = finish_app_task_status;
        }
    }
}
