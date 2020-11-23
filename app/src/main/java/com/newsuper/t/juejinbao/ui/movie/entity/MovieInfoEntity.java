package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class MovieInfoEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"code":0,"msg":"success","data":{"movie_info":{"title":"复仇者联盟4：终局之战","cover":"http:\/\/jjmovie.oss-cn-shenzhen.aliyuncs.com\/Douban\/img3p2552058346.jpg","rate":"8.6","location":"美国","ext_class":"动作\/科幻\/奇幻\/冒险","actor":"小罗伯特·唐尼 \/ 克里斯·埃文斯 \/ 马克·鲁弗洛","director":"安东尼·罗素 乔·罗素","duration":181,"vod_hits":19541},"user_info":{"avatar":"http:\/\/jjlmobile.juejinchain.com\/default\/avatar.png","nickname":"掘金宝3439"}},"time":1570882148,"vsn":"1.8.9.10"}
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

    public static class DataBean extends Entity{
        private MovieInfoBean movie_info;
        private UserInfoBean user_info;
        private DownloadInfoBean download_info;

        public DownloadInfoBean getDownload_info() {
            return download_info;
        }

        public void setDownload_info(DownloadInfoBean download_info) {
            this.download_info = download_info;
        }

        public MovieInfoBean getMovie_info() {
            return movie_info;
        }

        public void setMovie_info(MovieInfoBean movie_info) {
            this.movie_info = movie_info;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class DownloadInfoBean extends Entity {
            private String download_url;

            public String getDownload_url() {
                return download_url;
            }

            public void setDownload_url(String download_url) {
                this.download_url = download_url;
            }
        }

        public static class UserInfoBean extends Entity{
            private String avatar;
            private String nickname;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }
        }

        public static class MovieInfoBean extends Entity{
            private int duration;
            private int vod_hits;
            private String title;
            private String cover;
            private String rate;
            private String location;
            private String ext_class;
            private String actor;
            private String director;
            private String release_year;

            public String getRelease_year() {
                return release_year;
            }

            public void setRelease_year(String release_year) {
                this.release_year = release_year;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getVod_hits() {
                return vod_hits;
            }

            public void setVod_hits(int vod_hits) {
                this.vod_hits = vod_hits;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getExt_class() {
                return ext_class;
            }

            public void setExt_class(String ext_class) {
                this.ext_class = ext_class;
            }

            public String getActor() {
                return actor;
            }

            public void setActor(String actor) {
                this.actor = actor;
            }

            public String getDirector() {
                return director;
            }

            public void setDirector(String director) {
                this.director = director;
            }
        }
    }
}
