package com.newsuper.t.juejinbao.ui.share.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class ShortUrlEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : [{"url_short":"http://t.cn/Ailc6a2N","url_long":"http://transit1.dev.juejinchain.cn/encode.php?og=aHR0cDovL3RyYW5zaXQxLmRldi5qdWVqaW5jaGFpbi5jbi8/cGF0aD0vQXJ0aWNsZURldGFpbHMvNTU3Mzk3OCZpbnZpdGVDb2RlPTExMjQyMiZzaGFyZV90eXBlPXdlY2hhdA==","type":0}]
     * time : 1563789946
     * vsn : 1.8.4
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
         * url_short : http://t.cn/Ailc6a2N
         * url_long : http://transit1.dev.juejinchain.cn/encode.php?og=aHR0cDovL3RyYW5zaXQxLmRldi5qdWVqaW5jaGFpbi5jbi8/cGF0aD0vQXJ0aWNsZURldGFpbHMvNTU3Mzk3OCZpbnZpdGVDb2RlPTExMjQyMiZzaGFyZV90eXBlPXdlY2hhdA==
         * type : 0
         */

        private String url_short;
        private String url_long;
        private int type;

        public String getUrl_short() {
            return url_short;
        }

        public void setUrl_short(String url_short) {
            this.url_short = url_short;
        }

        public String getUrl_long() {
            return url_long;
        }

        public void setUrl_long(String url_long) {
            this.url_long = url_long;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
