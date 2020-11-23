package com.newsuper.t.juejinbao.ui.home.entity;

public class UpAppEntity {


    /**
     * code : 0
     * msg : success
     * data : {"content":"<p style=\"font-size: 14px;font-weight: bold;\">★1.8.6更新说明:★<\/p>1.恢复微信锁粉功能<br>2.优化影视功能,并过滤大量垃圾资源<br>3.优化VIP影视,彻底去除广告<br>4.新增电视直播功能<br>","appstore":0,"download_url":"","h5_download_url":null,"is_mandatory_update":0}
     * time : 1563848882
     * vsn : 1.8.2
     */

    private int code;
    private String msg;
    private DataBean data;
    private int time;
    private String vsn;
    private String urlHeader;

    public String getUrlHeader() {
        return urlHeader;
    }

    public void setUrlHeader(String urlHeader) {
        this.urlHeader = urlHeader;
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
         * content : <p style="font-size: 14px;font-weight: bold;">★1.8.6更新说明:★</p>1.恢复微信锁粉功能<br>2.优化影视功能,并过滤大量垃圾资源<br>3.优化VIP影视,彻底去除广告<br>4.新增电视直播功能<br>
         * appstore : 0
         * download_url :
         * h5_download_url : null
         * is_mandatory_update : 0
         */

        private String content;
        //0本地更新（链接是拼出来的先拿到域名然后拼接+（path=/）） 1是跳转浏览器更新
        private int appstore;
        //下载地址
        private String download_url;
        private Object h5_download_url;
        private int is_mandatory_update=0;
        private int code = 0;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAppstore() {
            return appstore;
        }

        public void setAppstore(int appstore) {
            this.appstore = appstore;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public Object getH5_download_url() {
            return h5_download_url;
        }

        public void setH5_download_url(Object h5_download_url) {
            this.h5_download_url = h5_download_url;
        }

        public int getIs_mandatory_update() {
            return is_mandatory_update;
        }

        public void setIs_mandatory_update(int is_mandatory_update) {
            this.is_mandatory_update = is_mandatory_update;
        }

        //是否强制更新
        public boolean isForce() {
//        return is_mandatory_update == 0; //测试
            return is_mandatory_update == 1;
        }
    }
}
