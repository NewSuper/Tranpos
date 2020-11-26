package com.newsuper.t.juejinbao.ui.movie.entity;


import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class MovieMovieRecommendDataEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"total":86,"per_page":12,"current_page":"1","last_page":8,"data":[{"id":108605,"title":"狮子王","url":"https://movie.douban.com/subject/26884354/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2559742751.jpg","rate":"7.7","is_new":0,"release_time":1562860800,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922306,"ext_class":"剧情/动画/冒险","watch_num":0,"actor":"唐纳德·格洛弗 / 阿尔法·伍达德 / 詹姆斯·厄尔·琼斯","director":"乔恩·费儒","duration":118,"episode":0,"year":2019,"vod_hits":47871,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108585,"title":"扫毒2天地对决","url":"https://movie.douban.com/subject/30171425/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2561172733.jpg","rate":"6.2","is_new":0,"release_time":1562256000,"location":"中国大陆","category1":"电影","category2":"影院热映","create_time":1562922308,"ext_class":"剧情/动作/悬疑/犯罪","watch_num":0,"actor":"刘德华 / 古天乐 / 苗侨伟","director":"邱礼涛","duration":99,"episode":0,"year":2019,"vod_hits":33970,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108606,"title":"素人特工","url":"https://movie.douban.com/subject/27155276/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2560447448.jpg","rate":"4.2","is_new":0,"release_time":1562860800,"location":"中国大陆","category1":"电影","category2":"影院热映","create_time":1562922309,"ext_class":"喜剧/动作/冒险","watch_num":0,"actor":"王大陆 / 张榕容 / 米拉·乔沃维奇","director":"袁锦麟","duration":113,"episode":0,"year":2019,"vod_hits":39480,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108540,"title":"蜘蛛侠：英雄远征","url":"https://movie.douban.com/subject/26931786/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558293106.jpg","rate":"8.0","is_new":0,"release_time":1561651200,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922311,"ext_class":"动作/科幻/冒险","watch_num":0,"actor":"汤姆·赫兰德 / 赞达亚 / 杰克·吉伦哈尔","director":"乔·沃茨","duration":127,"episode":0,"year":2019,"vod_hits":31767,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108468,"title":"千与千寻","url":"https://movie.douban.com/subject/1291561/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2557573348.jpg","rate":"9.3","is_new":0,"release_time":1561046400,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562922312,"ext_class":"剧情/动画/奇幻","watch_num":0,"actor":"柊瑠美 / 入野自由 / 夏木真理","director":"宫崎骏","duration":125,"episode":0,"year":2019,"vod_hits":31059,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108586,"title":"命运之夜\u2014\u2014天之杯I","url":"https://movie.douban.com/subject/26759819/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2561910374.jpg","rate":"8.2","is_new":0,"release_time":1562860800,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562922314,"ext_class":"剧情/动画/奇幻","watch_num":0,"actor":"杉山纪彰 / 下屋则子 / 神谷浩史","director":"须藤友德","duration":113,"episode":0,"year":2019,"vod_hits":23821,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108527,"title":"爱宠大机密2","url":"https://movie.douban.com/subject/26848167/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2555923582.jpg","rate":"7.1","is_new":0,"release_time":1562256000,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922316,"ext_class":"喜剧/动画/冒险","watch_num":0,"actor":"帕顿·奥斯瓦尔特 / 凯文·哈特 / 哈里森·福特","director":"克里斯·雷纳德","duration":86,"episode":0,"year":2019,"vod_hits":25320,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108506,"title":"玩具总动员4","url":"https://movie.douban.com/subject/6850547/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2557284230.jpg","rate":"8.8","is_new":0,"release_time":1561046400,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922319,"ext_class":"喜剧/动画/奇幻","watch_num":0,"actor":"汤姆·汉克斯 / 蒂姆·艾伦 / 安妮·波茨","director":"乔什·库雷","duration":100,"episode":0,"year":2019,"vod_hits":10945,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108574,"title":"机动战士高达NT","url":"https://movie.douban.com/subject/30201138/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558661806.jpg","rate":"6.0","is_new":0,"release_time":1562860800,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562922320,"ext_class":"科幻/动画/战争","watch_num":0,"actor":"榎木淳弥 / 村中知 / 松浦爱弓","director":"吉泽俊一","duration":90,"episode":0,"year":2019,"vod_hits":15348,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108464,"title":"绝杀慕尼黑","url":"https://movie.douban.com/subject/27024959/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2556883031.jpg","rate":"8.3","is_new":0,"release_time":1560355200,"location":"俄罗斯","category1":"电影","category2":"影院热映","create_time":1562922321,"ext_class":"剧情/运动","watch_num":0,"actor":"弗拉基米尔·马什科夫 / 约翰·萨维奇 / 马拉特·巴沙罗夫","director":"安东·梅格尔季切夫","duration":120,"episode":0,"year":2019,"vod_hits":45447,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108404,"title":"追龙Ⅱ","url":"https://movie.douban.com/subject/30175306/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558294190.jpg","rate":"5.7","is_new":0,"release_time":1559750400,"location":"香港","category1":"电影","category2":"影院热映","create_time":1562922323,"ext_class":"剧情/动作/犯罪","watch_num":0,"actor":"梁家辉 / 古天乐 / 林家栋","director":"王晶 关智耀","duration":103,"episode":0,"year":2019,"vod_hits":24804,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108565,"title":"九龙不败","url":"https://movie.douban.com/subject/26796665/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2560169035.jpg","rate":"3.3","is_new":0,"release_time":1561996800,"location":"香港","category1":"电影","category2":"影院热映","create_time":1562922324,"ext_class":"剧情/动作/犯罪","watch_num":0,"actor":"张晋 / 安德森·席尔瓦 / 郑嘉颖","director":"陈果","duration":100,"episode":0,"year":2019,"vod_hits":19871,"pull_time":1562922300,"is_subscription":0,"subscription_id":0}],"category":["影院热映","即将上映","近期热门"]}
     * time : 1562938816
     * vsn : 1.8.5
     */

    private int code;
    private String msg;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * total : 86
         * per_page : 12
         * current_page : 1
         * last_page : 8
         * data : [{"id":108605,"title":"狮子王","url":"https://movie.douban.com/subject/26884354/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2559742751.jpg","rate":"7.7","is_new":0,"release_time":1562860800,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922306,"ext_class":"剧情/动画/冒险","watch_num":0,"actor":"唐纳德·格洛弗 / 阿尔法·伍达德 / 詹姆斯·厄尔·琼斯","director":"乔恩·费儒","duration":118,"episode":0,"year":2019,"vod_hits":47871,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108585,"title":"扫毒2天地对决","url":"https://movie.douban.com/subject/30171425/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2561172733.jpg","rate":"6.2","is_new":0,"release_time":1562256000,"location":"中国大陆","category1":"电影","category2":"影院热映","create_time":1562922308,"ext_class":"剧情/动作/悬疑/犯罪","watch_num":0,"actor":"刘德华 / 古天乐 / 苗侨伟","director":"邱礼涛","duration":99,"episode":0,"year":2019,"vod_hits":33970,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108606,"title":"素人特工","url":"https://movie.douban.com/subject/27155276/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2560447448.jpg","rate":"4.2","is_new":0,"release_time":1562860800,"location":"中国大陆","category1":"电影","category2":"影院热映","create_time":1562922309,"ext_class":"喜剧/动作/冒险","watch_num":0,"actor":"王大陆 / 张榕容 / 米拉·乔沃维奇","director":"袁锦麟","duration":113,"episode":0,"year":2019,"vod_hits":39480,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108540,"title":"蜘蛛侠：英雄远征","url":"https://movie.douban.com/subject/26931786/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558293106.jpg","rate":"8.0","is_new":0,"release_time":1561651200,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922311,"ext_class":"动作/科幻/冒险","watch_num":0,"actor":"汤姆·赫兰德 / 赞达亚 / 杰克·吉伦哈尔","director":"乔·沃茨","duration":127,"episode":0,"year":2019,"vod_hits":31767,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108468,"title":"千与千寻","url":"https://movie.douban.com/subject/1291561/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2557573348.jpg","rate":"9.3","is_new":0,"release_time":1561046400,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562922312,"ext_class":"剧情/动画/奇幻","watch_num":0,"actor":"柊瑠美 / 入野自由 / 夏木真理","director":"宫崎骏","duration":125,"episode":0,"year":2019,"vod_hits":31059,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108586,"title":"命运之夜\u2014\u2014天之杯I","url":"https://movie.douban.com/subject/26759819/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2561910374.jpg","rate":"8.2","is_new":0,"release_time":1562860800,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562922314,"ext_class":"剧情/动画/奇幻","watch_num":0,"actor":"杉山纪彰 / 下屋则子 / 神谷浩史","director":"须藤友德","duration":113,"episode":0,"year":2019,"vod_hits":23821,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108527,"title":"爱宠大机密2","url":"https://movie.douban.com/subject/26848167/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2555923582.jpg","rate":"7.1","is_new":0,"release_time":1562256000,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922316,"ext_class":"喜剧/动画/冒险","watch_num":0,"actor":"帕顿·奥斯瓦尔特 / 凯文·哈特 / 哈里森·福特","director":"克里斯·雷纳德","duration":86,"episode":0,"year":2019,"vod_hits":25320,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108506,"title":"玩具总动员4","url":"https://movie.douban.com/subject/6850547/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2557284230.jpg","rate":"8.8","is_new":0,"release_time":1561046400,"location":"美国","category1":"电影","category2":"影院热映","create_time":1562922319,"ext_class":"喜剧/动画/奇幻","watch_num":0,"actor":"汤姆·汉克斯 / 蒂姆·艾伦 / 安妮·波茨","director":"乔什·库雷","duration":100,"episode":0,"year":2019,"vod_hits":10945,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108574,"title":"机动战士高达NT","url":"https://movie.douban.com/subject/30201138/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558661806.jpg","rate":"6.0","is_new":0,"release_time":1562860800,"location":"日本","category1":"电影","category2":"影院热映","create_time":1562922320,"ext_class":"科幻/动画/战争","watch_num":0,"actor":"榎木淳弥 / 村中知 / 松浦爱弓","director":"吉泽俊一","duration":90,"episode":0,"year":2019,"vod_hits":15348,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108464,"title":"绝杀慕尼黑","url":"https://movie.douban.com/subject/27024959/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2556883031.jpg","rate":"8.3","is_new":0,"release_time":1560355200,"location":"俄罗斯","category1":"电影","category2":"影院热映","create_time":1562922321,"ext_class":"剧情/运动","watch_num":0,"actor":"弗拉基米尔·马什科夫 / 约翰·萨维奇 / 马拉特·巴沙罗夫","director":"安东·梅格尔季切夫","duration":120,"episode":0,"year":2019,"vod_hits":45447,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108404,"title":"追龙Ⅱ","url":"https://movie.douban.com/subject/30175306/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2558294190.jpg","rate":"5.7","is_new":0,"release_time":1559750400,"location":"香港","category1":"电影","category2":"影院热映","create_time":1562922323,"ext_class":"剧情/动作/犯罪","watch_num":0,"actor":"梁家辉 / 古天乐 / 林家栋","director":"王晶 关智耀","duration":103,"episode":0,"year":2019,"vod_hits":24804,"pull_time":1562922300,"is_subscription":0,"subscription_id":0},{"id":108565,"title":"九龙不败","url":"https://movie.douban.com/subject/26796665/?from=playing_poster","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2560169035.jpg","rate":"3.3","is_new":0,"release_time":1561996800,"location":"香港","category1":"电影","category2":"影院热映","create_time":1562922324,"ext_class":"剧情/动作/犯罪","watch_num":0,"actor":"张晋 / 安德森·席尔瓦 / 郑嘉颖","director":"陈果","duration":100,"episode":0,"year":2019,"vod_hits":19871,"pull_time":1562922300,"is_subscription":0,"subscription_id":0}]
         * category : ["影院热映","即将上映","近期热门"]
         */

        private int total;
        private int per_page;
        private String current_page;
        private int last_page;
        private List<DataBean> data;
        private List<String> category;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public List<String> getCategory() {
            return category;
        }

        public void setCategory(List<String> category) {
            this.category = category;
        }

        public static class DataBean extends EasyAdapter.TypeBean {
            /**
             * id : 108605
             * title : 狮子王
             * url : https://movie.douban.com/subject/26884354/?from=playing_poster
             * cover : http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2559742751.jpg
             * rate : 7.7
             * is_new : 0
             * release_time : 1562860800
             * location : 美国
             * category1 : 电影
             * category2 : 影院热映
             * create_time : 1562922306
             * ext_class : 剧情/动画/冒险
             * watch_num : 0
             * actor : 唐纳德·格洛弗 / 阿尔法·伍达德 / 詹姆斯·厄尔·琼斯
             * director : 乔恩·费儒
             * duration : 118
             * episode : 0
             * year : 2019
             * vod_hits : 47871
             * pull_time : 1562922300
             * is_subscription : 0
             * subscription_id : 0
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
            private int is_subscription;
            private int subscription_id;

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

            public int getIs_subscription() {
                return is_subscription;
            }

            public void setIs_subscription(int is_subscription) {
                this.is_subscription = is_subscription;
            }

            public int getSubscription_id() {
                return subscription_id;
            }

            public void setSubscription_id(int subscription_id) {
                this.subscription_id = subscription_id;
            }
        }
    }
}
