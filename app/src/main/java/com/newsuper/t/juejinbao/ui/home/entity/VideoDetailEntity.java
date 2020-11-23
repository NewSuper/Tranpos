package com.newsuper.t.juejinbao.ui.home.entity;

import com.google.gson.annotations.SerializedName;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class VideoDetailEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"id":130133,"url":"http://toutiao.com/group/6637814887084982791/","title":"小伙整车豪车出门并不快乐，大师点火烧了对方领带，男子恍然大悟","author":"失主得熊","author_logo":"http://p3.pstatp.com/thumb/fe8f000013a50c4e8e9f","publish_time":1545486712,"source":"今日头条","create_time":1547624072,"category_cn":"搞笑","category_en":"subv_funny","img_url":"http://p1-tt.bytecdn.cn/list/300x196/pgc-image/6f1ab8e95c0a41e4ae65d1ce0aafca45","large_img_url":"http://p3-tt.bytecdn.cn/video1609/pgc-image/6f1ab8e95c0a41e4ae65d1ce0aafca45","video_watch_count":1254,"video_like_count":5,"comment_count":1,"video_id":"v020041d0000bgf3va2gd9fjkmfs824g","abstract":"小伙整车豪车出门并不快乐，大师点火烧了对方领带，男子恍然大悟，男子拥有豪车却感觉不快乐，大师几句话就指点迷津，小伙鸦雀无声","video_duration":54,"is_fabulous":0}
     * time : 1568787657
     * vsn : 1.8.9.10
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
         * id : 130133
         * url : http://toutiao.com/group/6637814887084982791/
         * title : 小伙整车豪车出门并不快乐，大师点火烧了对方领带，男子恍然大悟
         * author : 失主得熊
         * author_logo : http://p3.pstatp.com/thumb/fe8f000013a50c4e8e9f
         * publish_time : 1545486712
         * source : 今日头条
         * create_time : 1547624072
         * category_cn : 搞笑
         * category_en : subv_funny
         * img_url : http://p1-tt.bytecdn.cn/list/300x196/pgc-image/6f1ab8e95c0a41e4ae65d1ce0aafca45
         * large_img_url : http://p3-tt.bytecdn.cn/video1609/pgc-image/6f1ab8e95c0a41e4ae65d1ce0aafca45
         * video_watch_count : 1254
         * video_like_count : 5
         * comment_count : 1
         * video_id : v020041d0000bgf3va2gd9fjkmfs824g
         * abstract : 小伙整车豪车出门并不快乐，大师点火烧了对方领带，男子恍然大悟，男子拥有豪车却感觉不快乐，大师几句话就指点迷津，小伙鸦雀无声
         * video_duration : 54
         * is_fabulous : 0
         */

        private int id;
        private String url;
        private String title;
        private String author;
        private String author_logo;
        private int publish_time;
        private String source;
        private int create_time;
        private String category_cn;
        private String category_en;
        private String img_url;
        private String large_img_url;
        private int video_watch_count;
        private int video_like_count;
        private int comment_count;
        private String video_id;
        @SerializedName("abstract")
        private String abstractX;
        private int video_duration;
        private int is_fabulous;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor_logo() {
            return author_logo;
        }

        public void setAuthor_logo(String author_logo) {
            this.author_logo = author_logo;
        }

        public int getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(int publish_time) {
            this.publish_time = publish_time;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getCategory_cn() {
            return category_cn;
        }

        public void setCategory_cn(String category_cn) {
            this.category_cn = category_cn;
        }

        public String getCategory_en() {
            return category_en;
        }

        public void setCategory_en(String category_en) {
            this.category_en = category_en;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getLarge_img_url() {
            return large_img_url;
        }

        public void setLarge_img_url(String large_img_url) {
            this.large_img_url = large_img_url;
        }

        public int getVideo_watch_count() {
            return video_watch_count;
        }

        public void setVideo_watch_count(int video_watch_count) {
            this.video_watch_count = video_watch_count;
        }

        public int getVideo_like_count() {
            return video_like_count;
        }

        public void setVideo_like_count(int video_like_count) {
            this.video_like_count = video_like_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public int getVideo_duration() {
            return video_duration;
        }

        public void setVideo_duration(int video_duration) {
            this.video_duration = video_duration;
        }

        public int getIs_fabulous() {
            return is_fabulous;
        }

        public void setIs_fabulous(int is_fabulous) {
            this.is_fabulous = is_fabulous;
        }
    }
}
