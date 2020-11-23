package com.newsuper.t.juejinbao.ui.login.entity;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class IsShowQQEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"wechat":1,"qq":1}
     * time : 1564653252
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
         * wechat : 1
         * qq : 1
         */

        private int wechat;
        private int qq;
        private int is_mobile_first;
        public int getIs_mobile_first() {
            return is_mobile_first;
        }

        public void setIs_mobile_first(int is_mobile_first) {
            this.is_mobile_first = is_mobile_first;
        }

        public int getWechat() {
            return wechat;
        }

        public void setWechat(int wechat) {
            this.wechat = wechat;
        }

        public int getQq() {
            return qq;
        }

        public void setQq(int qq) {
            this.qq = qq;
        }
    }
}
