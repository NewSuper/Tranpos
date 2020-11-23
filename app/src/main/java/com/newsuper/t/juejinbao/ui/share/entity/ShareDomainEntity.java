package com.newsuper.t.juejinbao.ui.share.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class ShareDomainEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"domain":"http://transit1.dev.juejinchain.cn/","third_domain":"http://wechat4.dev.juejinchain.cn/","php_domain":"","lock":1,"time":5000}
     * time : 1563778187
     * vsn : 1.8.4
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
         * domain : http://transit1.dev.juejinchain.cn/
         * third_domain : http://wechat4.dev.juejinchain.cn/
         * php_domain :
         * lock : 1
         * time : 5000
         */

        private String domain;
        private String third_domain;
        private String php_domain;
        private int lock;
        private int time;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getThird_domain() {
            return third_domain;
        }

        public void setThird_domain(String third_domain) {
            this.third_domain = third_domain;
        }

        public String getPhp_domain() {
            return php_domain;
        }

        public void setPhp_domain(String php_domain) {
            this.php_domain = php_domain;
        }

        public int getLock() {
            return lock;
        }

        public void setLock(int lock) {
            this.lock = lock;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
