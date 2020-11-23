package com.newsuper.t.juejinbao.ui.song.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class MusicDataListEntity extends Entity {


    /**
     * code : 0
     * msg : success
     * data : {"categorys":[{"thumbnail":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg","title":"经典歌曲","alias":"111"}],"songs":[{"songname":"晴天","singer":"周杰伦","image":"http://p2.music.126.net/sbw-bwlP3w1oygtuZHEHEQ==/119846767443488.jpg?param=130y130","lyric":"http://music.163.com/api/song/lyric?id=5285160&lv=1&kv=1&tv=-1","special_name":"超级歌单 第五期","song_url":"http://music.163.com/song/media/outer/url?id=5285160.mp3"}],"latest_album":[{"id":14,"thumbnail":"http://jjlmobile.juejinchain.com/images/music/华语新歌.png","desc":"三更半夜 / 余泽雅","play_count":3,"cid":14,"title":"礼貌"}],"class_area":[{"id":1,"en":1,"cn":null,"title":"怀旧经典","thumbnail":null}]}
     * time : 1575711206
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
        private List<CategorysBean> categorys;
        private List<SongBean> songs;
        private List<LatestAlbumBean> latest_album;
        private List<ClassAreaBean> class_area;

        public List<CategorysBean> getCategorys() {
            return categorys;
        }

        public void setCategorys(List<CategorysBean> categorys) {
            this.categorys = categorys;
        }

        public List<SongBean> getSongs() {
            return songs;
        }

        public void setSongs(List<SongBean> songs) {
            this.songs = songs;
        }

        public List<LatestAlbumBean> getLatest_album() {
            return latest_album;
        }

        public void setLatest_album(List<LatestAlbumBean> latest_album) {
            this.latest_album = latest_album;
        }

        public List<ClassAreaBean> getClass_area() {
            return class_area;
        }

        public void setClass_area(List<ClassAreaBean> class_area) {
            this.class_area = class_area;
        }


        public static class LatestAlbumBean {
            /**
             * id : 14
             * thumbnail : http://jjlmobile.juejinchain.com/images/music/华语新歌.png
             * desc : 三更半夜 / 余泽雅
             * play_count : 3
             * cid : 14
             * title : 礼貌
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

        public static class ClassAreaBean {
            /**
             * id : 1
             * en : 1
             * cn : null
             * title : 怀旧经典
             * thumbnail : null
             */

            private int id;
            private int en;
            private Object cn;
            private String title;
            private Object thumbnail;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getEn() {
                return en;
            }

            public void setEn(int en) {
                this.en = en;
            }

            public Object getCn() {
                return cn;
            }

            public void setCn(Object cn) {
                this.cn = cn;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(Object thumbnail) {
                this.thumbnail = thumbnail;
            }
        }
    }
}
