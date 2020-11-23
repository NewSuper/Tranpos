package com.newsuper.t.juejinbao.ui.home.entity;

import java.util.List;

public class HomeSearchResultEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"id":4546242,"title":"何炅爆料白敬亭为戏严格自控，聚餐3h只跟好友互动，全程不摘口罩"}]
     * time : 1582510829
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
         * id : 4546242
         * title : 何炅爆料白敬亭为戏严格自控，聚餐3h只跟好友互动，全程不摘口罩
         */

        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
