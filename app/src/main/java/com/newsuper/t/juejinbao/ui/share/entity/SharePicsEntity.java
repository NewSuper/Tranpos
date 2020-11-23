package com.newsuper.t.juejinbao.ui.share.entity;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class SharePicsEntity extends Entity {


    /**
     * code : 0
     * msg : success
     * data : {"is_update":0,"imgs":["http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style1.png","http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style2.png","http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style3.png","http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style4.png"]}
     * time : 1569049908
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
         * is_update : 0
         * imgs : ["http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style1.png","http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style2.png","http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style3.png","http://jjlmobile.juejinchain.com/share/mobile/img/poster_bg/bg_style4.png"]
         */

        private int is_update;
        private List<String> imgs;

        public int getIs_update() {
            return is_update;
        }

        public void setIs_update(int is_update) {
            this.is_update = is_update;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
