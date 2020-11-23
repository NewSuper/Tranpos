package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;
import java.util.List;

public class MoviePostDataEntity2 implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : [{"id":77976,"title":"扫毒2天地对决","url":"https://movie.douban.com/subject/30171425/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2561172733.jpg","rate":"6.2","is_new":0,"release_time":1562256000,"location":"中国大陆","category1":"电影","category2":"影院热映","create_time":1562835906,"ext_class":"剧情/动作/悬疑/犯罪","watch_num":0,"actor":"刘德华 / 古天乐 / 苗侨伟","director":"邱礼涛","duration":99,"episode":0,"year":2019,"vod_hits":40141,"pull_time":1562835900},{"id":77931,"title":"蜘蛛侠：英雄远征","url":"https://movie.douban.com/subject/26931786/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558293106.jpg","rate":"8.0","is_new":0,"release_time":1561651200,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562835907,"ext_class":"动作/科幻/冒险","watch_num":0,"actor":"汤姆·赫兰德 / 赞达亚 / 杰克·吉伦哈尔","director":"乔·沃茨","duration":127,"episode":0,"year":2019,"vod_hits":45720,"pull_time":1562835900},{"id":77859,"title":"千与千寻","url":"https://movie.douban.com/subject/1291561/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2557573348.jpg","rate":"9.3","is_new":0,"release_time":1561046400,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562835909,"ext_class":"剧情/动画/奇幻","watch_num":0,"actor":"柊瑠美 / 入野自由 / 夏木真理","director":"宫崎骏","duration":125,"episode":0,"year":2019,"vod_hits":42550,"pull_time":1562835900},{"id":77918,"title":"爱宠大机密2","url":"https://movie.douban.com/subject/26848167/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2555923582.jpg","rate":"7.1","is_new":0,"release_time":1562256000,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562835910,"ext_class":"喜剧/动画/冒险","watch_num":0,"actor":"帕顿·奥斯瓦尔特 / 凯文·哈特 / 哈里森·福特","director":"克里斯·雷纳德","duration":86,"episode":0,"year":2019,"vod_hits":46376,"pull_time":1562835900},{"id":77977,"title":"命运之夜\u2014\u2014天之杯I","url":"https://movie.douban.com/subject/26759819/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2561910374.jpg","rate":"8.2","is_new":0,"release_time":1562860800,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562835912,"ext_class":"剧情/动画/奇幻","watch_num":0,"actor":"杉山纪彰 / 下屋则子 / 神谷浩史","director":"须藤友德","duration":113,"episode":0,"year":2019,"vod_hits":35562,"pull_time":1562835900},{"id":77897,"title":"玩具总动员4","url":"https://movie.douban.com/subject/6850547/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2557284230.jpg","rate":"8.8","is_new":0,"release_time":1561046400,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562835913,"ext_class":"喜剧/动画/奇幻","watch_num":0,"actor":"汤姆·汉克斯 / 蒂姆·艾伦 / 安妮·波茨","director":"乔什·库雷","duration":100,"episode":0,"year":2019,"vod_hits":34727,"pull_time":1562835900},{"id":77965,"title":"机动战士高达NT","url":"https://movie.douban.com/subject/30201138/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558661806.jpg","rate":"5.9","is_new":0,"release_time":1562860800,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562835914,"ext_class":"科幻/动画/战争","watch_num":0,"actor":"榎木淳弥 / 村中知 / 松浦爱弓","director":"吉泽俊一","duration":90,"episode":0,"year":2019,"vod_hits":19282,"pull_time":1562835900},{"id":77855,"title":"绝杀慕尼黑","url":"https://movie.douban.com/subject/27024959/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2556883031.jpg","rate":"8.3","is_new":0,"release_time":1560355200,"location":"俄罗斯","category1":"电影","category2":"影院热映","create_time":1562835915,"ext_class":"剧情/运动","watch_num":0,"actor":"弗拉基米尔·马什科夫 / 约翰·萨维奇 / 马拉特·巴沙罗夫","director":"安东·梅格尔季切夫","duration":120,"episode":0,"year":2019,"vod_hits":49173,"pull_time":1562835900}]
     * time : 1562850683
     * vsn : 1.8.4
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 77976
         * title : 扫毒2天地对决
         * url : https://movie.douban.com/subject/30171425/?from=playing_poster
         * cover : http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2561172733.jpg
         * rate : 6.2
         * is_new : 0
         * release_time : 1562256000
         * location : 中国大陆
         * category1 : 电影
         * category2 : 影院热映
         * create_time : 1562835906
         * ext_class : 剧情/动作/悬疑/犯罪
         * watch_num : 0
         * actor : 刘德华 / 古天乐 / 苗侨伟
         * director : 邱礼涛
         * duration : 99
         * episode : 0
         * year : 2019
         * vod_hits : 40141
         * pull_time : 1562835900
         */

        private int id;
        private String title;
        private String url;
        private String cover;
        private String rate;
        private int is_new;
        private int release_time;
        private String location;
        private String category1;
        private String category2;
        private int create_time;
        private String ext_class;
        private int watch_num;
        private String actor;
        private String director;
        private int duration;
        private int episode;
        private int year;
        private int vod_hits;
        private int pull_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public int getRelease_time() {
            return release_time;
        }

        public void setRelease_time(int release_time) {
            this.release_time = release_time;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCategory1() {
            return category1;
        }

        public void setCategory1(String category1) {
            this.category1 = category1;
        }

        public String getCategory2() {
            return category2;
        }

        public void setCategory2(String category2) {
            this.category2 = category2;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getExt_class() {
            return ext_class;
        }

        public void setExt_class(String ext_class) {
            this.ext_class = ext_class;
        }

        public int getWatch_num() {
            return watch_num;
        }

        public void setWatch_num(int watch_num) {
            this.watch_num = watch_num;
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

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getEpisode() {
            return episode;
        }

        public void setEpisode(int episode) {
            this.episode = episode;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getVod_hits() {
            return vod_hits;
        }

        public void setVod_hits(int vod_hits) {
            this.vod_hits = vod_hits;
        }

        public int getPull_time() {
            return pull_time;
        }

        public void setPull_time(int pull_time) {
            this.pull_time = pull_time;
        }
    }
}
