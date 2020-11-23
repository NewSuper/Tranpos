package com.newsuper.t.juejinbao.ui.song.entity;

import java.util.List;

public class MusicCollectionEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"cid":2,"id":2,"name":"著名戏曲","singer":"","thumbnail":"http://jjlmobile.juejinchain.com/images/music/著名戏曲.png","play_count":201}]
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
         * cid : 2
         * id : 2
         * name : 著名戏曲
         * singer :
         * thumbnail : http://jjlmobile.juejinchain.com/images/music/著名戏曲.png
         * play_count : 201
         */

        private int cid;
        private int id;
        private String name;
        private String singer;
        private String thumbnail;
        private int play_count;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSinger() {
            return singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getPlay_count() {
            return play_count;
        }

        public void setPlay_count(int play_count) {
            this.play_count = play_count;
        }
    }
}
