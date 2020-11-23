package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class HomeBottomTabEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : [{"txt":"首页","to":"/","show":1},{"txt":"影视","to":"/movie","show":1},{"txt":"赚车赚房","to":"/make_money","show":1},{"txt":"任务","to":"/task","show":1},{"txt":"我的","to":"/personal_center","show":1}]
     * time : 1563281337
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
         * txt : 首页
         * to : /
         * show : 1
         */

        private String txt;
        private String to;
        private int show;
        private String ext_url;

        public String getExt_url() {
            return ext_url;
        }

        public void setExt_url(String ext_url) {
            this.ext_url = ext_url;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public int getShow() {
            return show;
        }

        public void setShow(int show) {
            this.show = show;
        }
    }
}
