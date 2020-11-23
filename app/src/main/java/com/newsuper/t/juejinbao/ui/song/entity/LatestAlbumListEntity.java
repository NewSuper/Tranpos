package com.newsuper.t.juejinbao.ui.song.entity;

import com.juejinchain.android.module.movie.adapter.EasyAdapter;

import java.util.List;

public class LatestAlbumListEntity {


    /**
     * code : 0
     * msg : success
     * data : [{"title":"礼貌","subtitle":"三更半夜 / 余泽雅","thumbnail":"","id":"https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1039158327,4018984203&fm=26&gp=0.jpg"}]
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

    public static class DataBean extends EasyAdapter.TypeBean {
        /**
         * title : 礼貌
         * subtitle : 三更半夜 / 余泽雅
         * thumbnail :
         * id : https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1039158327,4018984203&fm=26&gp=0.jpg
         */

        private String title;
        private String subtitle;
        private String thumbnail;
        private String id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
