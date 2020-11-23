package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class RecommendRankingEntity extends Entity {


    /**
     * code : 0
     * msg : success
     * data : {"per_page":"10","total":164,"list":[{"id":76384,"title":"复仇者联盟4：终局之战","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2552058346.jpg","rate":"8.6","location":"美国","ext_class":"动作/科幻/奇幻/冒险","actor":"小罗伯特·唐尼 / 克里斯·埃文斯 / 马克·鲁弗洛","director":"安东尼·罗素 乔·罗素","duration":181,"is_collection":0,"vod_id":324054,"vod_hits":19541},{"id":76385,"title":"何以为家","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2555295759.jpg","rate":"9.1","location":"黎巴嫩","ext_class":"剧情","actor":"赞恩·阿尔·拉菲亚 / 约丹诺斯·希费罗 / 博鲁瓦蒂夫·特雷杰·班科尔","director":"娜丁·拉巴基","duration":117,"is_collection":0,"vod_id":79916,"vod_hits":59454},{"id":76386,"title":"下一任：前任","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554795775.jpg","rate":"2.6","location":"台湾","ext_class":"爱情","actor":"郭采洁 / 郑恺 / 李东学","director":"陈鸿仪","duration":90,"is_collection":0,"vod_id":15920,"vod_hits":46687},{"id":76387,"title":"雪暴","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554545271.jpg","rate":"6.3","location":"中国大陆","ext_class":"动作/悬疑/犯罪","actor":"张震 / 倪妮 / 廖凡","director":"崔斯韦","duration":112,"is_collection":0,"vod_id":15866,"vod_hits":42858},{"id":76388,"title":"反贪风暴4","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2551353482.jpg","rate":"6.3","location":"香港","ext_class":"动作/犯罪","actor":"古天乐 / 郑嘉颖 / 林峯","director":"林德禄","duration":100,"is_collection":0,"vod_id":12336,"vod_hits":5280},{"id":76389,"title":"调音师","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2551995207.jpg","rate":"8.3","location":"印度","ext_class":"喜剧/悬疑/惊悚/犯罪","actor":"阿尤斯曼·库拉纳 / 塔布 / 拉迪卡·艾普特","director":"斯里兰姆·拉格万","duration":139,"is_collection":0,"vod_id":160602,"vod_hits":30648},{"id":76390,"title":"神奇乐园历险记","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2552076937.jpg","rate":"6.5","location":"美国","ext_class":"喜剧/动画/奇幻/冒险","actor":"索菲亚·玛丽 / 詹妮弗·加纳 / 肯·哈德森·坎贝尔","director":"迪兰·布朗","duration":86,"is_collection":0,"vod_id":255150,"vod_hits":29519},{"id":76391,"title":"撞死了一只羊","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554775210.jpg","rate":"7.4","location":"中国大陆","ext_class":"剧情","actor":"金巴 / 更登彭措 / 索朗旺姆","director":"万玛才旦","duration":87,"is_collection":0,"vod_id":15861,"vod_hits":61293},{"id":76392,"title":"天上再见","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554018955.jpg","rate":"8.1","location":"法国","ext_class":"喜剧/战争/犯罪","actor":"纳威尔·佩雷兹·毕斯卡亚特 / 阿尔贝·杜邦泰尔 / 罗兰·拉斐特","director":"阿尔贝·杜邦泰尔","duration":117,"is_collection":0,"vod_id":78424,"vod_hits":38607},{"id":76393,"title":"祈祷落幕时","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2552073598.jpg","rate":"8.0","location":"日本","ext_class":"剧情/悬疑","actor":"阿部宽 / 松岛菜菜子 / 沟端淳平","director":"福泽克雄","duration":119,"is_collection":0,"vod_id":20894,"vod_hits":28444}],"cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2552058346.jpg"}
     * time : 1570867187
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
         * per_page : 10
         * total : 164
         * list : [{"id":76384,"title":"复仇者联盟4：终局之战","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2552058346.jpg","rate":"8.6","location":"美国","ext_class":"动作/科幻/奇幻/冒险","actor":"小罗伯特·唐尼 / 克里斯·埃文斯 / 马克·鲁弗洛","director":"安东尼·罗素 乔·罗素","duration":181,"is_collection":0,"vod_id":324054,"vod_hits":19541},{"id":76385,"title":"何以为家","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2555295759.jpg","rate":"9.1","location":"黎巴嫩","ext_class":"剧情","actor":"赞恩·阿尔·拉菲亚 / 约丹诺斯·希费罗 / 博鲁瓦蒂夫·特雷杰·班科尔","director":"娜丁·拉巴基","duration":117,"is_collection":0,"vod_id":79916,"vod_hits":59454},{"id":76386,"title":"下一任：前任","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554795775.jpg","rate":"2.6","location":"台湾","ext_class":"爱情","actor":"郭采洁 / 郑恺 / 李东学","director":"陈鸿仪","duration":90,"is_collection":0,"vod_id":15920,"vod_hits":46687},{"id":76387,"title":"雪暴","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554545271.jpg","rate":"6.3","location":"中国大陆","ext_class":"动作/悬疑/犯罪","actor":"张震 / 倪妮 / 廖凡","director":"崔斯韦","duration":112,"is_collection":0,"vod_id":15866,"vod_hits":42858},{"id":76388,"title":"反贪风暴4","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2551353482.jpg","rate":"6.3","location":"香港","ext_class":"动作/犯罪","actor":"古天乐 / 郑嘉颖 / 林峯","director":"林德禄","duration":100,"is_collection":0,"vod_id":12336,"vod_hits":5280},{"id":76389,"title":"调音师","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2551995207.jpg","rate":"8.3","location":"印度","ext_class":"喜剧/悬疑/惊悚/犯罪","actor":"阿尤斯曼·库拉纳 / 塔布 / 拉迪卡·艾普特","director":"斯里兰姆·拉格万","duration":139,"is_collection":0,"vod_id":160602,"vod_hits":30648},{"id":76390,"title":"神奇乐园历险记","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2552076937.jpg","rate":"6.5","location":"美国","ext_class":"喜剧/动画/奇幻/冒险","actor":"索菲亚·玛丽 / 詹妮弗·加纳 / 肯·哈德森·坎贝尔","director":"迪兰·布朗","duration":86,"is_collection":0,"vod_id":255150,"vod_hits":29519},{"id":76391,"title":"撞死了一只羊","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554775210.jpg","rate":"7.4","location":"中国大陆","ext_class":"剧情","actor":"金巴 / 更登彭措 / 索朗旺姆","director":"万玛才旦","duration":87,"is_collection":0,"vod_id":15861,"vod_hits":61293},{"id":76392,"title":"天上再见","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2554018955.jpg","rate":"8.1","location":"法国","ext_class":"喜剧/战争/犯罪","actor":"纳威尔·佩雷兹·毕斯卡亚特 / 阿尔贝·杜邦泰尔 / 罗兰·拉斐特","director":"阿尔贝·杜邦泰尔","duration":117,"is_collection":0,"vod_id":78424,"vod_hits":38607},{"id":76393,"title":"祈祷落幕时","cover":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img1p2552073598.jpg","rate":"8.0","location":"日本","ext_class":"剧情/悬疑","actor":"阿部宽 / 松岛菜菜子 / 沟端淳平","director":"福泽克雄","duration":119,"is_collection":0,"vod_id":20894,"vod_hits":28444}]
         * cover : http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2552058346.jpg
         */

        private String per_page;
        private int total;
        private String cover;
        private List<ListBean> list;

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 76384
             * title : 复仇者联盟4：终局之战
             * cover : http://jjmovie.oss-cn-shenzhen.aliyuncs.com/Douban/img3p2552058346.jpg
             * rate : 8.6
             * location : 美国
             * ext_class : 动作/科幻/奇幻/冒险
             * actor : 小罗伯特·唐尼 / 克里斯·埃文斯 / 马克·鲁弗洛
             * director : 安东尼·罗素 乔·罗素
             * duration : 181
             * is_collection : 0
             * vod_id : 324054
             * vod_hits : 19541
             */

            private int id;
            private String title;
            private String cover;
            private String rate;
            private String location;
            private String ext_class;
            private String actor;
            private String director;
            private int duration;
            private int is_collection;
            private int vod_id;
            private int vod_hits;
            private String release_year;

            public String getRelease_year() {
                return release_year;
            }

            public void setRelease_year(String release_year) {
                this.release_year = release_year;
            }

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

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getIs_collection() {
                return is_collection;
            }

            public void setIs_collection(int is_collection) {
                this.is_collection = is_collection;
            }

            public int getVod_id() {
                return vod_id;
            }

            public void setVod_id(int vod_id) {
                this.vod_id = vod_id;
            }

            public int getVod_hits() {
                return vod_hits;
            }

            public void setVod_hits(int vod_hits) {
                this.vod_hits = vod_hits;
            }
        }
    }
}
