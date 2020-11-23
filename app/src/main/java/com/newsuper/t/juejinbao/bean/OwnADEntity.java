package com.newsuper.t.juejinbao.bean;


import java.util.List;

public class OwnADEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"ad_info":[{"id":343,"uid":1,"username":"yn123","ad_name":"开屏GIF","max_day":10000,"max_all":10000,"category":"","view_count":0,"click_count":0,"state":1,"cost":0,"create_time":1562323625,"update_time":1562323648,"type":100,"pid":0,"ptitle":"","directional_packet":0,"region":"{\"provinces\":[],\"citys\":[]}","sex":0,"device":"[]","online_date_start":1556380800,"online_date_end":1588003199,"online_time_start":0,"online_time_end":86399,"price_type":1,"price":100,"link":"http://www.baidu.com","style_type":17,"images":["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/07/05/R2y7xmeHjs.gif"],"title":"掘金宝","sub_title":"看资讯影视赚车子房子","download":0,"download_type":0,"download_ios":"","download_android":"","ad_position":"1","keywords":"[]","ad_sign":"广告","ad_type":2,"ad_position_json":"[\"1\"]"}],"ad_platform_type":2}
     * time : 1563163381
     * vsn : 1.8.2
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
         * ad_info : [{"id":343,"uid":1,"username":"yn123","ad_name":"开屏GIF","max_day":10000,"max_all":10000,"category":"","view_count":0,"click_count":0,"state":1,"cost":0,"create_time":1562323625,"update_time":1562323648,"type":100,"pid":0,"ptitle":"","directional_packet":0,"region":"{\"provinces\":[],\"citys\":[]}","sex":0,"device":"[]","online_date_start":1556380800,"online_date_end":1588003199,"online_time_start":0,"online_time_end":86399,"price_type":1,"price":100,"link":"http://www.baidu.com","style_type":17,"images":["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/07/05/R2y7xmeHjs.gif"],"title":"掘金宝","sub_title":"看资讯影视赚车子房子","download":0,"download_type":0,"download_ios":"","download_android":"","ad_position":"1","keywords":"[]","ad_sign":"广告","ad_type":2,"ad_position_json":"[\"1\"]"}]
         * ad_platform_type : 2
         */

        private int ad_platform_type;  // 开屏广告类型 ： 1自平台，0广点通，2穿山甲 3 搜狗

        private int reopen_time; // 后台切换至前台广告展示的间隔时间

        private int restart_time; // 重启app需要展示广告的间隔时间

        private int show_senseless_ad;//是否开启无感广告

        private int interval_home_page_ad; //首页信息流广告间隔

        private int interval_smallvideo_ad; //小视频广告间隔

        private String top_bg; // 首頁頭部背景圖

        private String movie_bg; // VIP頭部背景圖

        private int  is_open_app_protect; //是否开启app防护

        private int  advertiser_type = 2; //（1：穿山甲；2：广点通），默认2

        private int share_movie_count; //当天观看影视第n 次需要分享  0 代表不需要

        private int is_close_open_ad;//是否开启关闭开屏广告


        public int getIs_close_open_ad() {
            return is_close_open_ad;
        }

        public void setIs_close_open_ad(int is_close_open_ad) {
            this.is_close_open_ad = is_close_open_ad;
        }

        public int getShare_movie_count() {
            return share_movie_count;
        }

        public void setShare_movie_count(int share_movie_count) {
            this.share_movie_count = share_movie_count;
        }

        public int getAdvertiser_type() {
            return advertiser_type;
        }

        public void setAdvertiser_type(int advertiser_type) {
            this.advertiser_type = advertiser_type;
        }

        public int getIs_open_app_protect() {
            return is_open_app_protect;
        }

        public void setIs_open_app_protect(int is_open_app_protect) {
            this.is_open_app_protect = is_open_app_protect;
        }

        public String getTop_bg() {
            return top_bg;
        }

        public void setTop_bg(String top_bg) {
            this.top_bg = top_bg;
        }

        public String getMovie_bg() {
            return movie_bg;
        }

        public void setMovie_bg(String movie_bg) {
            this.movie_bg = movie_bg;
        }

        public int getInterval_home_page_ad() {
            return interval_home_page_ad;
        }

        public void setInterval_home_page_ad(int interval_home_page_ad) {
            this.interval_home_page_ad = interval_home_page_ad;
        }

        public int getInterval_smallvideo_ad() {
            return interval_smallvideo_ad;
        }

        public void setInterval_smallvideo_ad(int interval_smallvideo_ad) {
            this.interval_smallvideo_ad = interval_smallvideo_ad;
        }

        private List<AdInfoBean> ad_info;

        public int getShow_senseless_ad() {
            return show_senseless_ad;
        }

        public void setShow_senseless_ad(int show_senseless_ad) {
            this.show_senseless_ad = show_senseless_ad;
        }

        public int getReopen_time() {
            return reopen_time;
        }

        public void setReopen_time(int reopen_time) {
            this.reopen_time = reopen_time;
        }

        public int getRestart_time() {
            return restart_time;
        }

        public void setRestart_time(int restart_time) {
            this.restart_time = restart_time;
        }

        public int getAd_platform_type() {
            return ad_platform_type;
        }

        public void setAd_platform_type(int ad_platform_type) {
            this.ad_platform_type = ad_platform_type;
        }

        public List<AdInfoBean> getAd_info() {
            return ad_info;
        }

        public void setAd_info(List<AdInfoBean> ad_info) {
            this.ad_info = ad_info;
        }

        public static class AdInfoBean {
            /**
             * id : 343
             * uid : 1
             * username : yn123
             * ad_name : 开屏GIF
             * max_day : 10000
             * max_all : 10000
             * category :
             * view_count : 0
             * click_count : 0
             * state : 1
             * cost : 0
             * create_time : 1562323625
             * update_time : 1562323648
             * type : 100
             * pid : 0
             * ptitle :
             * directional_packet : 0
             * region : {"provinces":[],"citys":[]}
             * sex : 0
             * device : []
             * online_date_start : 1556380800
             * online_date_end : 1588003199
             * online_time_start : 0
             * online_time_end : 86399
             * price_type : 1
             * price : 100
             * link : http://www.baidu.com
             * style_type : 17
             * images : ["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/07/05/R2y7xmeHjs.gif"]
             * title : 掘金宝
             * sub_title : 看资讯影视赚车子房子
             * download : 0
             * download_type : 0
             * download_ios :
             * download_android :
             * ad_position : 1
             * keywords : []
             * ad_sign : 广告
             * ad_type : 2
             * ad_position_json : ["1"]
             */

            private int id;
            private int uid;
            private String username;
            private String ad_name;
            private int max_day;
            private int max_all;
            private String category;
            private int view_count;
            private int click_count;
            private int state;
            private int cost;
            private int create_time;
            private int update_time;
            private int type;
            private int pid;
            private String ptitle;
            private int directional_packet;
            private String region;
            private int sex;
            private String device;
            private int online_date_start;
            private int online_date_end;
            private int online_time_start;
            private int online_time_end;
            private int price_type;
            private int price;
            private String link;
            private int style_type;
            private String title;
            private String sub_title;
            private int download;
            private int download_type;
            private String download_ios;
            private String download_android;
            private String ad_position;
            private String keywords;
            private String ad_sign;
            private int ad_type;
            private String ad_position_json;
            private List<String> images;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAd_name() {
                return ad_name;
            }

            public void setAd_name(String ad_name) {
                this.ad_name = ad_name;
            }

            public int getMax_day() {
                return max_day;
            }

            public void setMax_day(int max_day) {
                this.max_day = max_day;
            }

            public int getMax_all() {
                return max_all;
            }

            public void setMax_all(int max_all) {
                this.max_all = max_all;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public int getView_count() {
                return view_count;
            }

            public void setView_count(int view_count) {
                this.view_count = view_count;
            }

            public int getClick_count() {
                return click_count;
            }

            public void setClick_count(int click_count) {
                this.click_count = click_count;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getCost() {
                return cost;
            }

            public void setCost(int cost) {
                this.cost = cost;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(int update_time) {
                this.update_time = update_time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public String getPtitle() {
                return ptitle;
            }

            public void setPtitle(String ptitle) {
                this.ptitle = ptitle;
            }

            public int getDirectional_packet() {
                return directional_packet;
            }

            public void setDirectional_packet(int directional_packet) {
                this.directional_packet = directional_packet;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public int getSex() {
                return sex;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public String getDevice() {
                return device;
            }

            public void setDevice(String device) {
                this.device = device;
            }

            public int getOnline_date_start() {
                return online_date_start;
            }

            public void setOnline_date_start(int online_date_start) {
                this.online_date_start = online_date_start;
            }

            public int getOnline_date_end() {
                return online_date_end;
            }

            public void setOnline_date_end(int online_date_end) {
                this.online_date_end = online_date_end;
            }

            public int getOnline_time_start() {
                return online_time_start;
            }

            public void setOnline_time_start(int online_time_start) {
                this.online_time_start = online_time_start;
            }

            public int getOnline_time_end() {
                return online_time_end;
            }

            public void setOnline_time_end(int online_time_end) {
                this.online_time_end = online_time_end;
            }

            public int getPrice_type() {
                return price_type;
            }

            public void setPrice_type(int price_type) {
                this.price_type = price_type;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public int getStyle_type() {
                return style_type;
            }

            public void setStyle_type(int style_type) {
                this.style_type = style_type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSub_title() {
                return sub_title;
            }

            public void setSub_title(String sub_title) {
                this.sub_title = sub_title;
            }

            public int getDownload() {
                return download;
            }

            public void setDownload(int download) {
                this.download = download;
            }

            public int getDownload_type() {
                return download_type;
            }

            public void setDownload_type(int download_type) {
                this.download_type = download_type;
            }

            public String getDownload_ios() {
                return download_ios;
            }

            public void setDownload_ios(String download_ios) {
                this.download_ios = download_ios;
            }

            public String getDownload_android() {
                return download_android;
            }

            public void setDownload_android(String download_android) {
                this.download_android = download_android;
            }

            public String getAd_position() {
                return ad_position;
            }

            public void setAd_position(String ad_position) {
                this.ad_position = ad_position;
            }

            public String getKeywords() {
                return keywords;
            }

            public void setKeywords(String keywords) {
                this.keywords = keywords;
            }

            public String getAd_sign() {
                return ad_sign;
            }

            public void setAd_sign(String ad_sign) {
                this.ad_sign = ad_sign;
            }

            public int getAd_type() {
                return ad_type;
            }

            public void setAd_type(int ad_type) {
                this.ad_type = ad_type;
            }

            public String getAd_position_json() {
                return ad_position_json;
            }

            public void setAd_position_json(String ad_position_json) {
                this.ad_position_json = ad_position_json;
            }

            public List<String> getImages() {
                return images;
            }

            public void setImages(List<String> images) {
                this.images = images;
            }
        }
    }
}
