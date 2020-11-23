package com.newsuper.t.juejinbao.ui.song.entity;

import java.util.List;

public class LatestAlbumTagEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"cn":"内地","en":"china"},{"cn":"港台","en":"hk"},{"cn":"欧美","en":"eaa"},{"cn":"韩国","en":"korea"},{"cn":"其他","en":"other"}]
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
         * cn : 内地
         * en : china
         */

        private String cn;
        private String en;

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }
    }
}
