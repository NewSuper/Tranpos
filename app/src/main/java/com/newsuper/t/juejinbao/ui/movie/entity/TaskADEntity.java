package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;
import java.util.List;

public class TaskADEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"ad_info_9":[{"id":118,"uid":2,"username":"lizi","ad_name":"变现猫-我的-ICOM","max_day":2000000,"max_all":3000000,"category":"","view_count":0,"click_count":0,"state":1,"cost":0,"create_time":1562688376,"update_time":1562059467,"type":100,"pid":0,"ptitle":"","directional_packet":0,"region":"{\"provinces\":[],\"citys\":[]}","sex":0,"device":"[]","online_date_start":1561996800,"online_date_end":1593532800,"online_time_start":0,"online_time_end":86399,"price_type":1,"price":300,"link":"https://i.fawulu.com/activities/?appKey=17d1aa9ffb7340e6a4d44e96790b9ef5&appEntrance=3&business=money","style_type":52,"images":["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/06/25/Ymx8r6eNfs.png"],"title":"天天砸金蛋","sub_title":"","download":0,"download_type":0,"download_ios":"","download_android":"","ad_position":"9","keywords":"[]","ad_sign":"广告","ad_type":1,"ad_position_json":"[\"14\",\"9\"]"},{"id":465,"uid":1,"username":"yn123","ad_name":"试玩领金币","max_day":10000,"max_all":50000,"category":"","view_count":0,"click_count":0,"state":1,"cost":0,"create_time":1562743739,"update_time":1562229855,"type":100,"pid":0,"ptitle":"","directional_packet":0,"region":"{\"provinces\":[],\"citys\":[]}","sex":0,"device":"[]","online_date_start":1560960000,"online_date_end":1592582399,"online_time_start":0,"online_time_end":86399,"price_type":1,"price":100,"link":"https://h5.wangzhuantianxia.com/try/try_list_plus.aspx","style_type":11,"images":["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/06/20/NZzeZfXdhh.jpg"],"title":"嘻趣广告000","sub_title":"","download":1,"download_type":0,"download_ios":"http://dev.wechat.juejinchain.cn/#/","download_android":"http://dev.wechat.juejinchain.cn/#/","ad_position":"9","keywords":"[]","ad_sign":"","ad_type":1,"ad_position_json":"[\"9\",\"10\"]"}],"ad_info_10":[{"id":465,"uid":1,"username":"yn123","ad_name":"试玩领金币","max_day":10000,"max_all":50000,"category":"","view_count":0,"click_count":0,"state":1,"cost":0,"create_time":1562743739,"update_time":1562229855,"type":100,"pid":0,"ptitle":"","directional_packet":0,"region":"{\"provinces\":[],\"citys\":[]}","sex":0,"device":"[]","online_date_start":1560960000,"online_date_end":1592582399,"online_time_start":0,"online_time_end":86399,"price_type":1,"price":100,"link":"https://h5.wangzhuantianxia.com/try/try_list_plus.aspx","style_type":11,"images":["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/06/20/NZzeZfXdhh.jpg"],"title":"嘻趣广告000","sub_title":"","download":1,"download_type":0,"download_ios":"http://dev.wechat.juejinchain.cn/#/","download_android":"http://dev.wechat.juejinchain.cn/#/","ad_position":"10","keywords":"[]","ad_sign":"","ad_type":1,"ad_position_json":"[\"9\",\"10\"]"}]}
     * time : 1563949746
     * vsn : 1.8.4
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
        private List<AdInfo9Bean> ad_info_9;
        private List<AdInfo10Bean> ad_info_10;

        public List<AdInfo9Bean> getAd_info_9() {
            return ad_info_9;
        }

        public void setAd_info_9(List<AdInfo9Bean> ad_info_9) {
            this.ad_info_9 = ad_info_9;
        }

        public List<AdInfo10Bean> getAd_info_10() {
            return ad_info_10;
        }

        public void setAd_info_10(List<AdInfo10Bean> ad_info_10) {
            this.ad_info_10 = ad_info_10;
        }

        public static class AdInfo9Bean {
            /**
             * id : 118
             * uid : 2
             * username : lizi
             * ad_name : 变现猫-我的-ICOM
             * max_day : 2000000
             * max_all : 3000000
             * category :
             * view_count : 0
             * click_count : 0
             * state : 1
             * cost : 0
             * create_time : 1562688376
             * update_time : 1562059467
             * type : 100
             * pid : 0
             * ptitle :
             * directional_packet : 0
             * region : {"provinces":[],"citys":[]}
             * sex : 0
             * device : []
             * online_date_start : 1561996800
             * online_date_end : 1593532800
             * online_time_start : 0
             * online_time_end : 86399
             * price_type : 1
             * price : 300
             * link : https://i.fawulu.com/activities/?appKey=17d1aa9ffb7340e6a4d44e96790b9ef5&appEntrance=3&business=money
             * style_type : 52
             * images : ["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/06/25/Ymx8r6eNfs.png"]
             * title : 天天砸金蛋
             * sub_title :
             * download : 0
             * download_type : 0
             * download_ios :
             * download_android :
             * ad_position : 9
             * keywords : []
             * ad_sign : 广告
             * ad_type : 1
             * ad_position_json : ["14","9"]
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

        public static class AdInfo10Bean {
            /**
             * id : 465
             * uid : 1
             * username : yn123
             * ad_name : 试玩领金币
             * max_day : 10000
             * max_all : 50000
             * category :
             * view_count : 0
             * click_count : 0
             * state : 1
             * cost : 0
             * create_time : 1562743739
             * update_time : 1562229855
             * type : 100
             * pid : 0
             * ptitle :
             * directional_packet : 0
             * region : {"provinces":[],"citys":[]}
             * sex : 0
             * device : []
             * online_date_start : 1560960000
             * online_date_end : 1592582399
             * online_time_start : 0
             * online_time_end : 86399
             * price_type : 1
             * price : 100
             * link : https://h5.wangzhuantianxia.com/try/try_list_plus.aspx
             * style_type : 11
             * images : ["http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/06/20/NZzeZfXdhh.jpg"]
             * title : 嘻趣广告000
             * sub_title :
             * download : 1
             * download_type : 0
             * download_ios : http://dev.wechat.juejinchain.cn/#/
             * download_android : http://dev.wechat.juejinchain.cn/#/
             * ad_position : 10
             * keywords : []
             * ad_sign :
             * ad_type : 1
             * ad_position_json : ["9","10"]
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
