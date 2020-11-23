package com.newsuper.t.juejinbao.ui.song.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class MusicHotListEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : [{"title":"你的答案","desc":"打破一切恐惧 我能找到答案","number":42593},{"title":"你的答案","desc":"打破一切恐惧 我能找到答案","number":23687},{"title":"你的答案","desc":"打破一切恐惧 我能找到答案","number":17922}]
     * time : 1575712212
     * vsn : 1.8.8
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 你的答案
         * desc : 打破一切恐惧 我能找到答案
         * number : 42593
         */

        private String title;
        private String desc;
        private String number;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
