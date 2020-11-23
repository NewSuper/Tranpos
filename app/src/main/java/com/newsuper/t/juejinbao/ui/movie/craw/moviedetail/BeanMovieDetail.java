package com.newsuper.t.juejinbao.ui.movie.craw.moviedetail;

import com.juejinchain.android.module.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * 电影详情数据
 */
public class BeanMovieDetail implements Serializable {
    //原始链接
    private String origin;

    //图片
    private String img = "http://jjlmobile.juejinchain.com/vsn1_1/share_logo.png";
    //标题
    private String title = "";
    //评分
    private double rating;
    //导演
    private String director = "";
    //演员
    private String actor = "";

    //类型
    private String form = "";
    //简介
    private String intro = "";
    //发行日期
    private String date = "";
    //地区
    private String area = "";
    //现状
    private String now = "";
    //年份
    private String year = "";
    //语言
    private String language = "";
    //详情
    private String detail = "";
    //影视源集合
    private List<PlaybackSource> playbackSources;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<PlaybackSource> getPlaybackSources() {
        return playbackSources;
    }

    public void setPlaybackSources(List<PlaybackSource> playbackSources) {
        this.playbackSources = playbackSources;
    }

    //影视源
    public static class PlaybackSource implements Serializable {
        //影视源名称
        private String name;
        //剧集集合
        private List<Drama> dramaSeries;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Drama> getDramaSeries() {
            return dramaSeries;
        }

        public void setDramaSeries(List<Drama> dramaSeries) {
            this.dramaSeries = dramaSeries;
        }

        //剧集
        public static class Drama extends EasyAdapter.TypeBean{
            //剧集名称
            private String name;
            //剧集链接
            private String link;


            private String videoDownloadUrl;
            private int selected = 0;
            private int played = 0;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getVideoDownloadUrl() {
                return videoDownloadUrl;
            }

            public void setVideoDownloadUrl(String videoDownloadUrl) {
                this.videoDownloadUrl = videoDownloadUrl;
            }

            public int getSelected() {
                return selected;
            }

            public void setSelected(int selected) {
                this.selected = selected;
            }

            public int getPlayed() {
                return played;
            }

            public void setPlayed(int played) {
                this.played = played;
            }
        }
    }


    @Override
    public String toString() {
        return "BeanMovieDetail{" +
                "origin='" + origin + '\'' +
                ", img='" + img + '\'' +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", director='" + director + '\'' +
                ", actor='" + actor + '\'' +
                ", form='" + form + '\'' +
                ", intro='" + intro + '\'' +
                ", date='" + date + '\'' +
                ", area='" + area + '\'' +
                ", now='" + now + '\'' +
                ", year='" + year + '\'' +
                ", language='" + language + '\'' +
                ", playbackSources=" + playbackSources +
                '}';
    }
}
