package com.newsuper.t.juejinbao.ui.movie.entity;

import java.util.List;

public class MovieDetailEntity {


    /**
     * code : 0
     * msg : success
     * data : {"vod_id":336515,"vod_name":"少年的你","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/e151428eb9df302c3125d8c0a241111a.jpg","type_id_1":1,"type_id":8,"vod_en":"shaoniandeni","vod_time":1574148137,"vod_remarks":"HC","vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"爱情","vod_hits":47018,"vod_weekday":"","vod_serial":"0","vod_douban_score":"8.6","vod_play_from":["bwm3u8","wlm3u8","kkyun","kkm3u8","soyun","som3u8","kuyun","ckm3u8","subom3u8","suboyun"],"vod_content":"　　一场高考前夕的校园意外，改变了两个少年的命运","vod_blurb":"　　一场高考前夕的校园意外，改变了两个少年的命运","vod_actor":"周冬雨,易烊千玺,尹昉,黄觉,吴越,周也,张耀,张艺凡,赵润南,张歆怡","vod_director":"曾国祥","vod_writer":"","vod_tag":"","review_count":0,"review_score":"0.0","comment_count":0,"reply_count":0,"fabulous_count":0,"vod_play_from_count":10,"vod_play_list_count":14,"play_list":{"云线路1":"http://okjx.cc/?url=","云线路2":"http://www.2gty.com/apiurl/yun.php?url=","云线路3":"https://jexi.a0296.cn/?url=","云线路4":"http://beaacc.com/api.php?url=","云线路5":"https://jiexi.380k.com/?url=","云线路6":"http://api.baiyug.vip/index.php?url=","云线路7":"https://www.58danke.top/jx/xin?url=","云线路8":"https://vip.mpos.ren/v/?url=","云线路9":"11"},"vod_play_list":[[{"title":"TS清晰版","url":"https://k.bwzybf.com/20191026/USpj5obq/index.m3u8","source":"bwm3u8"},{"title":"TS清晰版","url":"https://videos6.jsyunbf.com/20191026/1HJUL3Eb/index.m3u8","source":"bwm3u8"},{"title":"TS清晰版","url":"https://solezy.me/20191026/KGBim6Dn/index.m3u8","source":"bwm3u8"}],[{"title":"HC","url":"https://bilibili.com-h-bilibili.com/20191113/10136_33537416/index.m3u8","source":"ckm3u8"}],[{"title":"HC","url":"https://www.mmicLoud.com:65/20191026/isTDwsjH/index.m3u8","source":"123kum3u8"}],[{"title":"HC枪版","url":"https://www.ziboshishen.com/20191026/tZ2GvBSc/index.m3u8","source":"subom3u8"}],[{"title":"HC清晰版","url":"https://cn6.7639616.com/hls/20191114/1ad108f56d94017bed322a982abe79ec/1573693678/index.m3u8","source":"wlm3u8"}],[{"title":"HD1080P中字","url":"https://solezy.me/20191113/F8iTVIP5/index.m3u8","source":"som3u8"}],[{"title":"HD1080P国语","url":"http://vip888.kuyun99.com/20191113/H77Ad5O1/index.m3u8","source":"kkm3u8"}],[{"title":"HD1080P国语","url":"https://v3.yongjiujiexi.com/20191113/09A5L1LJ/index.m3u8","source":"yjm3u8"}],[{"title":"HD高清","url":"https://v1.8888zy-biubiu.com/20191026/8KxSus1aMygefAY9/index.m3u8","source":"cjm3u8"}],[{"title":"HD1080P中字","url":"https://k.bwzybf.com/share/RYuQxTs6rDqWLdCk","source":"bwm3u8"},{"title":"HD1080P中字","url":"https://videos7.jsyunbf.com/20191113/VIybNbhQ/index.m3u8","source":"bwm3u8"},{"title":"HD1080P中字","url":"https://solezy.me/20191113/F8iTVIP5/index.m3u8","source":"bwm3u8"}],[{"title":"HC","url":"https://bilibili.com-h-bilibili.com/share/291dbc18539ba7e19b8abb7d85aa204e","source":"kuyun"}],[{"title":"HC枪版","url":"https://www.ziboshishen.com/share/T5JVP8KBuxbpXsly","source":"suboyun"}],[{"title":"HD1080P中字","url":"https://solezy.me/share/o0ca4xkb4xXXjkww","source":"soyun"}],[{"title":"HD1080P国语","url":"https://vip888.kuyun99.com/share/69xempeg348NWhTZ","source":"kkyun"}]],"is_fabulous":0,"is_review":0,"is_collection":0,"is_subscription":0,"subscription_id":null}
     * time : 1574148268
     * vsn : 1.8.8.2
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
         * vod_id : 336515
         * vod_name : 少年的你
         * vod_sub :
         * vod_letter : S
         * vod_pic : http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/e151428eb9df302c3125d8c0a241111a.jpg
         * type_id_1 : 1
         * type_id : 8
         * vod_en : shaoniandeni
         * vod_time : 1574148137
         * vod_remarks : HC
         * vod_year : 2019
         * vod_area : 大陆
         * vod_lang :
         * vod_class : 爱情
         * vod_hits : 47018
         * vod_weekday :
         * vod_serial : 0
         * vod_douban_score : 8.6
         * vod_play_from : ["bwm3u8","wlm3u8","kkyun","kkm3u8","soyun","som3u8","kuyun","ckm3u8","subom3u8","suboyun"]
         * vod_content : 　　一场高考前夕的校园意外，改变了两个少年的命运
         * vod_blurb : 　　一场高考前夕的校园意外，改变了两个少年的命运
         * vod_actor : 周冬雨,易烊千玺,尹昉,黄觉,吴越,周也,张耀,张艺凡,赵润南,张歆怡
         * vod_director : 曾国祥
         * vod_writer :
         * vod_tag :
         * review_count : 0
         * review_score : 0.0
         * comment_count : 0
         * reply_count : 0
         * fabulous_count : 0
         * vod_play_from_count : 10
         * vod_play_list_count : 14
         * play_list : {"云线路1":"http://okjx.cc/?url=","云线路2":"http://www.2gty.com/apiurl/yun.php?url=","云线路3":"https://jexi.a0296.cn/?url=","云线路4":"http://beaacc.com/api.php?url=","云线路5":"https://jiexi.380k.com/?url=","云线路6":"http://api.baiyug.vip/index.php?url=","云线路7":"https://www.58danke.top/jx/xin?url=","云线路8":"https://vip.mpos.ren/v/?url=","云线路9":"11"}
         * vod_play_list : [[{"title":"TS清晰版","url":"https://k.bwzybf.com/20191026/USpj5obq/index.m3u8","source":"bwm3u8"},{"title":"TS清晰版","url":"https://videos6.jsyunbf.com/20191026/1HJUL3Eb/index.m3u8","source":"bwm3u8"},{"title":"TS清晰版","url":"https://solezy.me/20191026/KGBim6Dn/index.m3u8","source":"bwm3u8"}],[{"title":"HC","url":"https://bilibili.com-h-bilibili.com/20191113/10136_33537416/index.m3u8","source":"ckm3u8"}],[{"title":"HC","url":"https://www.mmicLoud.com:65/20191026/isTDwsjH/index.m3u8","source":"123kum3u8"}],[{"title":"HC枪版","url":"https://www.ziboshishen.com/20191026/tZ2GvBSc/index.m3u8","source":"subom3u8"}],[{"title":"HC清晰版","url":"https://cn6.7639616.com/hls/20191114/1ad108f56d94017bed322a982abe79ec/1573693678/index.m3u8","source":"wlm3u8"}],[{"title":"HD1080P中字","url":"https://solezy.me/20191113/F8iTVIP5/index.m3u8","source":"som3u8"}],[{"title":"HD1080P国语","url":"http://vip888.kuyun99.com/20191113/H77Ad5O1/index.m3u8","source":"kkm3u8"}],[{"title":"HD1080P国语","url":"https://v3.yongjiujiexi.com/20191113/09A5L1LJ/index.m3u8","source":"yjm3u8"}],[{"title":"HD高清","url":"https://v1.8888zy-biubiu.com/20191026/8KxSus1aMygefAY9/index.m3u8","source":"cjm3u8"}],[{"title":"HD1080P中字","url":"https://k.bwzybf.com/share/RYuQxTs6rDqWLdCk","source":"bwm3u8"},{"title":"HD1080P中字","url":"https://videos7.jsyunbf.com/20191113/VIybNbhQ/index.m3u8","source":"bwm3u8"},{"title":"HD1080P中字","url":"https://solezy.me/20191113/F8iTVIP5/index.m3u8","source":"bwm3u8"}],[{"title":"HC","url":"https://bilibili.com-h-bilibili.com/share/291dbc18539ba7e19b8abb7d85aa204e","source":"kuyun"}],[{"title":"HC枪版","url":"https://www.ziboshishen.com/share/T5JVP8KBuxbpXsly","source":"suboyun"}],[{"title":"HD1080P中字","url":"https://solezy.me/share/o0ca4xkb4xXXjkww","source":"soyun"}],[{"title":"HD1080P国语","url":"https://vip888.kuyun99.com/share/69xempeg348NWhTZ","source":"kkyun"}]]
         * is_fabulous : 0
         * is_review : 0
         * is_collection : 0
         * is_subscription : 0
         * subscription_id : null
         */

        private int vod_id;
        private String vod_name;
        private String vod_sub;
        private String vod_letter;
        private String vod_pic;
        private int type_id_1;
        private int type_id;
        private String vod_en;
        private int vod_time;
        private String vod_remarks;
        private int vod_year;
        private String vod_area;
        private String vod_lang;
        private String vod_class;
        private int vod_hits;
        private String vod_weekday;
        private String vod_serial;
        private String vod_douban_score;
        private String vod_content;
        private String vod_blurb;
        private String vod_actor;
        private String vod_director;
        private String vod_writer;
        private String vod_tag;
        private int review_count;
        private String review_score;
        private int comment_count;
        private int reply_count;
        private int fabulous_count;
        private int vod_play_from_count;
        private int vod_play_list_count;
        private PlayListBean play_list;
        private int is_fabulous;
        private int is_review;
        private int is_collection;
        private int is_subscription;
        private Object subscription_id;
        private List<String> vod_play_from;
        private List<List<VodPlayListBean>> vod_play_list;

        public int getVod_id() {
            return vod_id;
        }

        public void setVod_id(int vod_id) {
            this.vod_id = vod_id;
        }

        public String getVod_name() {
            return vod_name;
        }

        public void setVod_name(String vod_name) {
            this.vod_name = vod_name;
        }

        public String getVod_sub() {
            return vod_sub;
        }

        public void setVod_sub(String vod_sub) {
            this.vod_sub = vod_sub;
        }

        public String getVod_letter() {
            return vod_letter;
        }

        public void setVod_letter(String vod_letter) {
            this.vod_letter = vod_letter;
        }

        public String getVod_pic() {
            return vod_pic;
        }

        public void setVod_pic(String vod_pic) {
            this.vod_pic = vod_pic;
        }

        public int getType_id_1() {
            return type_id_1;
        }

        public void setType_id_1(int type_id_1) {
            this.type_id_1 = type_id_1;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getVod_en() {
            return vod_en;
        }

        public void setVod_en(String vod_en) {
            this.vod_en = vod_en;
        }

        public int getVod_time() {
            return vod_time;
        }

        public void setVod_time(int vod_time) {
            this.vod_time = vod_time;
        }

        public String getVod_remarks() {
            return vod_remarks;
        }

        public void setVod_remarks(String vod_remarks) {
            this.vod_remarks = vod_remarks;
        }

        public int getVod_year() {
            return vod_year;
        }

        public void setVod_year(int vod_year) {
            this.vod_year = vod_year;
        }

        public String getVod_area() {
            return vod_area;
        }

        public void setVod_area(String vod_area) {
            this.vod_area = vod_area;
        }

        public String getVod_lang() {
            return vod_lang;
        }

        public void setVod_lang(String vod_lang) {
            this.vod_lang = vod_lang;
        }

        public String getVod_class() {
            return vod_class;
        }

        public void setVod_class(String vod_class) {
            this.vod_class = vod_class;
        }

        public int getVod_hits() {
            return vod_hits;
        }

        public void setVod_hits(int vod_hits) {
            this.vod_hits = vod_hits;
        }

        public String getVod_weekday() {
            return vod_weekday;
        }

        public void setVod_weekday(String vod_weekday) {
            this.vod_weekday = vod_weekday;
        }

        public String getVod_serial() {
            return vod_serial;
        }

        public void setVod_serial(String vod_serial) {
            this.vod_serial = vod_serial;
        }

        public String getVod_douban_score() {
            return vod_douban_score;
        }

        public void setVod_douban_score(String vod_douban_score) {
            this.vod_douban_score = vod_douban_score;
        }

        public String getVod_content() {
            return vod_content;
        }

        public void setVod_content(String vod_content) {
            this.vod_content = vod_content;
        }

        public String getVod_blurb() {
            return vod_blurb;
        }

        public void setVod_blurb(String vod_blurb) {
            this.vod_blurb = vod_blurb;
        }

        public String getVod_actor() {
            return vod_actor;
        }

        public void setVod_actor(String vod_actor) {
            this.vod_actor = vod_actor;
        }

        public String getVod_director() {
            return vod_director;
        }

        public void setVod_director(String vod_director) {
            this.vod_director = vod_director;
        }

        public String getVod_writer() {
            return vod_writer;
        }

        public void setVod_writer(String vod_writer) {
            this.vod_writer = vod_writer;
        }

        public String getVod_tag() {
            return vod_tag;
        }

        public void setVod_tag(String vod_tag) {
            this.vod_tag = vod_tag;
        }

        public int getReview_count() {
            return review_count;
        }

        public void setReview_count(int review_count) {
            this.review_count = review_count;
        }

        public String getReview_score() {
            return review_score;
        }

        public void setReview_score(String review_score) {
            this.review_score = review_score;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public int getFabulous_count() {
            return fabulous_count;
        }

        public void setFabulous_count(int fabulous_count) {
            this.fabulous_count = fabulous_count;
        }

        public int getVod_play_from_count() {
            return vod_play_from_count;
        }

        public void setVod_play_from_count(int vod_play_from_count) {
            this.vod_play_from_count = vod_play_from_count;
        }

        public int getVod_play_list_count() {
            return vod_play_list_count;
        }

        public void setVod_play_list_count(int vod_play_list_count) {
            this.vod_play_list_count = vod_play_list_count;
        }

        public PlayListBean getPlay_list() {
            return play_list;
        }

        public void setPlay_list(PlayListBean play_list) {
            this.play_list = play_list;
        }

        public int getIs_fabulous() {
            return is_fabulous;
        }

        public void setIs_fabulous(int is_fabulous) {
            this.is_fabulous = is_fabulous;
        }

        public int getIs_review() {
            return is_review;
        }

        public void setIs_review(int is_review) {
            this.is_review = is_review;
        }

        public int getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        public int getIs_subscription() {
            return is_subscription;
        }

        public void setIs_subscription(int is_subscription) {
            this.is_subscription = is_subscription;
        }

        public Object getSubscription_id() {
            return subscription_id;
        }

        public void setSubscription_id(Object subscription_id) {
            this.subscription_id = subscription_id;
        }

        public List<String> getVod_play_from() {
            return vod_play_from;
        }

        public void setVod_play_from(List<String> vod_play_from) {
            this.vod_play_from = vod_play_from;
        }

        public List<List<VodPlayListBean>> getVod_play_list() {
            return vod_play_list;
        }

        public void setVod_play_list(List<List<VodPlayListBean>> vod_play_list) {
            this.vod_play_list = vod_play_list;
        }

        public static class PlayListBean {
            /**
             * 云线路1 : http://okjx.cc/?url=
             * 云线路2 : http://www.2gty.com/apiurl/yun.php?url=
             * 云线路3 : https://jexi.a0296.cn/?url=
             * 云线路4 : http://beaacc.com/api.php?url=
             * 云线路5 : https://jiexi.380k.com/?url=
             * 云线路6 : http://api.baiyug.vip/index.php?url=
             * 云线路7 : https://www.58danke.top/jx/xin?url=
             * 云线路8 : https://vip.mpos.ren/v/?url=
             * 云线路9 : 11
             */

            private String 云线路1;
            private String 云线路2;
            private String 云线路3;
            private String 云线路4;
            private String 云线路5;
            private String 云线路6;
            private String 云线路7;
            private String 云线路8;
            private String 云线路9;

            public String get云线路1() {
                return 云线路1;
            }

            public void set云线路1(String 云线路1) {
                this.云线路1 = 云线路1;
            }

            public String get云线路2() {
                return 云线路2;
            }

            public void set云线路2(String 云线路2) {
                this.云线路2 = 云线路2;
            }

            public String get云线路3() {
                return 云线路3;
            }

            public void set云线路3(String 云线路3) {
                this.云线路3 = 云线路3;
            }

            public String get云线路4() {
                return 云线路4;
            }

            public void set云线路4(String 云线路4) {
                this.云线路4 = 云线路4;
            }

            public String get云线路5() {
                return 云线路5;
            }

            public void set云线路5(String 云线路5) {
                this.云线路5 = 云线路5;
            }

            public String get云线路6() {
                return 云线路6;
            }

            public void set云线路6(String 云线路6) {
                this.云线路6 = 云线路6;
            }

            public String get云线路7() {
                return 云线路7;
            }

            public void set云线路7(String 云线路7) {
                this.云线路7 = 云线路7;
            }

            public String get云线路8() {
                return 云线路8;
            }

            public void set云线路8(String 云线路8) {
                this.云线路8 = 云线路8;
            }

            public String get云线路9() {
                return 云线路9;
            }

            public void set云线路9(String 云线路9) {
                this.云线路9 = 云线路9;
            }
        }

        public static class VodPlayListBean {
            /**
             * title : TS清晰版
             * url : https://k.bwzybf.com/20191026/USpj5obq/index.m3u8
             * source : bwm3u8
             */

            private String title;
            private String url;
            private String source;

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

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }
    }
}
