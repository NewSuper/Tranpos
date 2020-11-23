package com.newsuper.t.juejinbao.ui.song.entity;

public class SongBean{
    /**
     * songname : 晴天
     * singer : 周杰伦
     * image : http://p2.music.126.net/sbw-bwlP3w1oygtuZHEHEQ==/119846767443488.jpg?param=130y130
     * lyric : http://music.163.com/api/song/lyric?id=5285160&lv=1&kv=1&tv=-1
     * special_name : 超级歌单 第五期
     * song_url : http://music.163.com/song/media/outer/url?id=5285160.mp3
     */
    private String id;
    private String song_id;
    private String song_name;
    private String singer;
    private String special_name;
    private String image;
    private String lyric;
    private String song_url;
    private String playlist_name;
    private String play_count;
    private String create_time;
    private String status;
    private String cate_id;
    private String favourite_id;

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getSpecial_name() {
        return special_name;
    }

    public void setSpecial_name(String special_name) {
        this.special_name = special_name;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getFavourite_id() {
        return favourite_id;
    }

    public void setFavourite_id(String favourite_id) {
        this.favourite_id = favourite_id;
    }
}
