package com.newsuper.t.juejinbao.ui.song.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class MusicListEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"categorys":{"thumbnail":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg","title":"经典歌曲","desc":"8090都在听","play_count":5151,"alias":"333"},"songs":[{"songname":"晴天","singer":"周杰伦","image":"http://p2.music.126.net/sbw-bwlP3w1oygtuZHEHEQ==/119846767443488.jpg?param=130y130","lyric":"http://music.163.com/api/song/lyric?id=5285160&lv=1&kv=1&tv=-1","special_name":"超级歌单 第五期","song_url":"http://music.163.com/song/media/outer/url?id=5285160.mp3"},{"songname":"一路向北","singer":"周杰伦","image":"http://p2.music.126.net/sbw-bwlP3w1oygtuZHEHEQ==/119846767443488.jpg?param=130y130","lyric":"http://music.163.com/api/song/lyric?id=5285160&lv=1&kv=1&tv=-1","special_name":"无名高地3","song_url":"http://music.163.com/song/media/outer/url?id=5285160.mp3"}]}
     * time : 1575712023
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
         * categorys : {"thumbnail":"https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg","title":"经典歌曲","desc":"8090都在听","play_count":5151,"alias":"333"}
         * songs : [{"songname":"晴天","singer":"周杰伦","image":"http://p2.music.126.net/sbw-bwlP3w1oygtuZHEHEQ==/119846767443488.jpg?param=130y130","lyric":"http://music.163.com/api/song/lyric?id=5285160&lv=1&kv=1&tv=-1","special_name":"超级歌单 第五期","song_url":"http://music.163.com/song/media/outer/url?id=5285160.mp3"},{"songname":"一路向北","singer":"周杰伦","image":"http://p2.music.126.net/sbw-bwlP3w1oygtuZHEHEQ==/119846767443488.jpg?param=130y130","lyric":"http://music.163.com/api/song/lyric?id=5285160&lv=1&kv=1&tv=-1","special_name":"无名高地3","song_url":"http://music.163.com/song/media/outer/url?id=5285160.mp3"}]
         */

        private CategorysBean categorys;
        private List<SongBean> songs;

        public CategorysBean getCategorys() {
            return categorys;
        }

        public void setCategorys(CategorysBean categorys) {
            this.categorys = categorys;
        }

        public List<SongBean> getSongs() {
            return songs;
        }

        public void setSongs(List<SongBean> songs) {
            this.songs = songs;
        }
    }
}
