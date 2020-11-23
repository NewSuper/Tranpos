package com.newsuper.t.juejinbao.ui.song.entity;

import java.util.List;

public class ClassAreaDetailEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"id":17,"thumbnail":"http://jjlmobile.juejinchain.com/images/music/华语新歌.png","desc":"","play_count":0,"cid":17,"title":"慵懒小调"}]
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
         * id : 17
         * thumbnail : http://jjlmobile.juejinchain.com/images/music/华语新歌.png
         * desc :
         * play_count : 0
         * cid : 17
         * title : 慵懒小调
         */

        private int id;
        private String thumbnail;
        private String desc;
        private int play_count;
        private int cid;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getPlay_count() {
            return play_count;
        }

        public void setPlay_count(int play_count) {
            this.play_count = play_count;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
