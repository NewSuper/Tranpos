package com.newsuper.t.juejinbao.ui.movie.bean;

import java.io.Serializable;
import java.util.List;

public class UpdateEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"is_upgrade":0,"versionName":"1.0.0","versionCode":"100000","liteTitle":"闪电视频加速全新上线","content":["1.精选实时视频，随时随地发现生活中的趣事。","2.自动检测视频极速下载，让你有更 流畅的观影体验。","3.检索本地视频功能，可以自动扫描 您手机内的视频文件，提供更高速的 视频播放器。"]}
     * time : 1569222320
     * vsn : 1.8.8
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
         * is_upgrade : 0
         * versionName : 1.0.0
         * versionCode : 100000
         * liteTitle : 闪电视频加速全新上线
         * content : ["1.精选实时视频，随时随地发现生活中的趣事。","2.自动检测视频极速下载，让你有更 流畅的观影体验。","3.检索本地视频功能，可以自动扫描 您手机内的视频文件，提供更高速的 视频播放器。"]
         */

        private int is_upgrade;
        private String versionName;
        private String versionCode;
        private String liteTitle;
        private List<String> content;
        private String downloadUrl;
        private int is_screen;

        public int getIs_screen() {
            return is_screen;
        }

        public void setIs_screen(int is_screen) {
            this.is_screen = is_screen;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public int getIs_upgrade() {
            return is_upgrade;
        }

        public void setIs_upgrade(int is_upgrade) {
            this.is_upgrade = is_upgrade;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }

        public String getLiteTitle() {
            return liteTitle;
        }

        public void setLiteTitle(String liteTitle) {
            this.liteTitle = liteTitle;
        }

        public List<String> getContent() {
            return content;
        }

        public void setContent(List<String> content) {
            this.content = content;
        }
    }
}
