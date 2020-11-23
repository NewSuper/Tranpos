package com.newsuper.t.juejinbao.ui.song.entity;

public class MusicSearchFromEntity {
    /**
     * code : 0
     * msg : success
     * data : {"source_url":"http://www.guqiankun.com/tools/music/","platform_type":5,"js_url":"http://luodi1.dev.juejinchain.cn/live/js/getMusicSource.js"}
     * time : 1585382274
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
         * source_url : http://www.guqiankun.com/tools/music/
         * platform_type : 5
         * js_url : http://luodi1.dev.juejinchain.cn/live/js/getMusicSource.js
         */

        private String source_url;
        private String platform_type;
        private String js_url;

        public String getSource_url() {
            return source_url;
        }

        public void setSource_url(String source_url) {
            this.source_url = source_url;
        }

        public String getPlatform_type() {
            return platform_type;
        }

        public void setPlatform_type(String platform_type) {
            this.platform_type = platform_type;
        }

        public String getJs_url() {
            return js_url;
        }

        public void setJs_url(String js_url) {
            this.js_url = js_url;
        }
    }
}
