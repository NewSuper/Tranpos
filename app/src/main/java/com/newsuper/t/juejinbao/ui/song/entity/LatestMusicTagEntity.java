package com.newsuper.t.juejinbao.ui.song.entity;

import java.util.List;

public class LatestMusicTagEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"en":"latest","cn":"最新"},{"en":"china","cn":"内地"},{"en":"hk","cn":"港台"},{"en":"eaa","cn":"欧美"},{"en":"korea","cn":"韩国"}]
     */

    private int code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * en : latest
         * cn : 最新
         */

        private String en;
        private String cn;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }
    }
}
