package com.newsuper.t.juejinbao.ui.movie.entity;


import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class MovieMovieFilterDataEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"total":17932,"per_page":"12","current_page":"1","last_page":1495,"data":[{"vod_id":93441,"vod_name":"我的超级老师","vod_sub":"","vod_letter":"W","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/e910d72043440dcaac4d0b11e659c82a.jpg","type_id_1":"电影","type_id":7,"vod_en":"wodechaojilaoshi","vod_time":1562598787,"vod_remarks":"HD国语","vod_hits":14857,"vod_hits_true":9,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"9.3"},{"vod_id":113922,"vod_name":"上海风云之夺宝金龙","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/fd8f42d371a9892b4bc119fc6e313b34.jpg","type_id_1":"电影","type_id":7,"vod_en":"shanghaifengyunzhiduobaojinlong","vod_time":1562672109,"vod_remarks":"高清","vod_hits":68474,"vod_hits_true":0,"vod_year":2019,"vod_area":"大陆","vod_lang":"国语","vod_class":"喜剧","vod_douban_score":"7.6"},{"vod_id":97027,"vod_name":"水木追爱记","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/24380506d40e6ed1c6bf9d1a97274ff4.jpg","type_id_1":"电影","type_id":7,"vod_en":"shuimuzhuiaiji","vod_time":1562605651,"vod_remarks":"BD国语中字","vod_hits":7227,"vod_hits_true":0,"vod_year":2019,"vod_area":"大陆","vod_lang":"国语","vod_class":"喜剧","vod_douban_score":"6.4"},{"vod_id":168707,"vod_name":"肥仔快跑","vod_sub":"","vod_letter":"F","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/3298fcb5d610deb33eb38090bd9be8ab.jpg","type_id_1":"电影","type_id":7,"vod_en":"feizikuaipao","vod_time":1562756036,"vod_remarks":"HD","vod_hits":24477,"vod_hits_true":0,"vod_year":2019,"vod_area":"英国","vod_lang":"英国","vod_class":"喜剧","vod_douban_score":"6.1"},{"vod_id":174851,"vod_name":"唐砖：地狱花谷","vod_sub":"","vod_letter":"T","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/6b604bbba8eddfee46c79956b268413a.jpg","type_id_1":"电影","type_id":7,"vod_en":"tangzhuandiyuhuagu","vod_time":1562601709,"vod_remarks":"HD","vod_hits":79623,"vod_hits_true":4,"vod_year":2019,"vod_area":"中国大陆","vod_lang":"中国大陆","vod_class":"喜剧","vod_douban_score":"9.6"},{"vod_id":22789,"vod_name":"小乔遇上潘多拉之白日做梦","vod_sub":"","vod_letter":"X","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/c960dea704ac4aeac0a5e6c266a5f33e.jpg","type_id_1":"电影","type_id":7,"vod_en":"xiaoqiaoyushangpanduolazhibairizuomeng","vod_time":1562756601,"vod_remarks":"HD1080P中字","vod_hits":74580,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"国语","vod_class":"喜剧","vod_douban_score":"6.8"},{"vod_id":12550,"vod_name":"人间·喜剧","vod_sub":"","vod_letter":"R","vod_pic":"http://www.8888zy.com/upload/vod/2019-06-28/156173241311.jpg","type_id_1":"电影","type_id":7,"vod_en":"renjianxiju","vod_time":1562806848,"vod_remarks":"正片","vod_hits":97091,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"6.3"},{"vod_id":22790,"vod_name":"我的外星人舅舅","vod_sub":"","vod_letter":"W","vod_pic":"http://www.8888zy.com/upload/vod/2019-06-28/156173394116.jpg","type_id_1":"电影","type_id":7,"vod_en":"wodewaixingrenjiujiu","vod_time":1562677843,"vod_remarks":"HD高清","vod_hits":83989,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"8.6"},{"vod_id":113414,"vod_name":"济公之降龙有悔（粤语）","vod_sub":"","vod_letter":"J","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/a5914e1b5b91ca5b6560e79a7a6ff10d.jpg","type_id_1":"电影","type_id":7,"vod_en":"jigongzhijianglongyouhuiyueyu","vod_time":1562596344,"vod_remarks":"HD","vod_hits":3213,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"8.6"},{"vod_id":160775,"vod_name":"忘情今夜","vod_sub":"","vod_letter":"W","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/fa067f5f192c432ca5c071aa68e0bb04.jpg","type_id_1":"电影","type_id":7,"vod_en":"wangqingjinye","vod_time":1562598274,"vod_remarks":"HD1080P中字","vod_hits":97757,"vod_hits_true":1,"vod_year":2019,"vod_area":"欧美","vod_lang":"英语","vod_class":"喜剧","vod_douban_score":"9.5"},{"vod_id":102407,"vod_name":"威龙兄弟","vod_sub":"","vod_letter":"W","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/21d80416232f67c0a804e1c245387116.jpg","type_id_1":"电影","type_id":7,"vod_en":"weilongxiongdi","vod_time":1562674255,"vod_remarks":"HD高清","vod_hits":92243,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"7.1"},{"vod_id":12296,"vod_name":"北海食神","vod_sub":"","vod_letter":"B","vod_pic":"http://www.8888zy.com/upload/vod/2019-06-28/156173237013.jpg","type_id_1":"电影","type_id":7,"vod_en":"beihaishishen","vod_time":1562806834,"vod_remarks":"正片","vod_hits":8932,"vod_hits_true":0,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"6.5"}],"count":[{"name":"电影","count":127896},{"name":"电视剧","count":32705},{"name":"综艺","count":3862},{"name":"动漫","count":3287},{"name":"其它","count":48031}]}
     * time : 1562925592
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
         * total : 17932
         * per_page : 12
         * current_page : 1
         * last_page : 1495
         * data : [{"vod_id":93441,"vod_name":"我的超级老师","vod_sub":"","vod_letter":"W","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/e910d72043440dcaac4d0b11e659c82a.jpg","type_id_1":"电影","type_id":7,"vod_en":"wodechaojilaoshi","vod_time":1562598787,"vod_remarks":"HD国语","vod_hits":14857,"vod_hits_true":9,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"9.3"},{"vod_id":113922,"vod_name":"上海风云之夺宝金龙","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/fd8f42d371a9892b4bc119fc6e313b34.jpg","type_id_1":"电影","type_id":7,"vod_en":"shanghaifengyunzhiduobaojinlong","vod_time":1562672109,"vod_remarks":"高清","vod_hits":68474,"vod_hits_true":0,"vod_year":2019,"vod_area":"大陆","vod_lang":"国语","vod_class":"喜剧","vod_douban_score":"7.6"},{"vod_id":97027,"vod_name":"水木追爱记","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/24380506d40e6ed1c6bf9d1a97274ff4.jpg","type_id_1":"电影","type_id":7,"vod_en":"shuimuzhuiaiji","vod_time":1562605651,"vod_remarks":"BD国语中字","vod_hits":7227,"vod_hits_true":0,"vod_year":2019,"vod_area":"大陆","vod_lang":"国语","vod_class":"喜剧","vod_douban_score":"6.4"},{"vod_id":168707,"vod_name":"肥仔快跑","vod_sub":"","vod_letter":"F","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/3298fcb5d610deb33eb38090bd9be8ab.jpg","type_id_1":"电影","type_id":7,"vod_en":"feizikuaipao","vod_time":1562756036,"vod_remarks":"HD","vod_hits":24477,"vod_hits_true":0,"vod_year":2019,"vod_area":"英国","vod_lang":"英国","vod_class":"喜剧","vod_douban_score":"6.1"},{"vod_id":174851,"vod_name":"唐砖：地狱花谷","vod_sub":"","vod_letter":"T","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/6b604bbba8eddfee46c79956b268413a.jpg","type_id_1":"电影","type_id":7,"vod_en":"tangzhuandiyuhuagu","vod_time":1562601709,"vod_remarks":"HD","vod_hits":79623,"vod_hits_true":4,"vod_year":2019,"vod_area":"中国大陆","vod_lang":"中国大陆","vod_class":"喜剧","vod_douban_score":"9.6"},{"vod_id":22789,"vod_name":"小乔遇上潘多拉之白日做梦","vod_sub":"","vod_letter":"X","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/c960dea704ac4aeac0a5e6c266a5f33e.jpg","type_id_1":"电影","type_id":7,"vod_en":"xiaoqiaoyushangpanduolazhibairizuomeng","vod_time":1562756601,"vod_remarks":"HD1080P中字","vod_hits":74580,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"国语","vod_class":"喜剧","vod_douban_score":"6.8"},{"vod_id":12550,"vod_name":"人间·喜剧","vod_sub":"","vod_letter":"R","vod_pic":"http://www.8888zy.com/upload/vod/2019-06-28/156173241311.jpg","type_id_1":"电影","type_id":7,"vod_en":"renjianxiju","vod_time":1562806848,"vod_remarks":"正片","vod_hits":97091,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"6.3"},{"vod_id":22790,"vod_name":"我的外星人舅舅","vod_sub":"","vod_letter":"W","vod_pic":"http://www.8888zy.com/upload/vod/2019-06-28/156173394116.jpg","type_id_1":"电影","type_id":7,"vod_en":"wodewaixingrenjiujiu","vod_time":1562677843,"vod_remarks":"HD高清","vod_hits":83989,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"8.6"},{"vod_id":113414,"vod_name":"济公之降龙有悔（粤语）","vod_sub":"","vod_letter":"J","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/a5914e1b5b91ca5b6560e79a7a6ff10d.jpg","type_id_1":"电影","type_id":7,"vod_en":"jigongzhijianglongyouhuiyueyu","vod_time":1562596344,"vod_remarks":"HD","vod_hits":3213,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"8.6"},{"vod_id":160775,"vod_name":"忘情今夜","vod_sub":"","vod_letter":"W","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/fa067f5f192c432ca5c071aa68e0bb04.jpg","type_id_1":"电影","type_id":7,"vod_en":"wangqingjinye","vod_time":1562598274,"vod_remarks":"HD1080P中字","vod_hits":97757,"vod_hits_true":1,"vod_year":2019,"vod_area":"欧美","vod_lang":"英语","vod_class":"喜剧","vod_douban_score":"9.5"},{"vod_id":102407,"vod_name":"威龙兄弟","vod_sub":"","vod_letter":"W","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/21d80416232f67c0a804e1c245387116.jpg","type_id_1":"电影","type_id":7,"vod_en":"weilongxiongdi","vod_time":1562674255,"vod_remarks":"HD高清","vod_hits":92243,"vod_hits_true":1,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"7.1"},{"vod_id":12296,"vod_name":"北海食神","vod_sub":"","vod_letter":"B","vod_pic":"http://www.8888zy.com/upload/vod/2019-06-28/156173237013.jpg","type_id_1":"电影","type_id":7,"vod_en":"beihaishishen","vod_time":1562806834,"vod_remarks":"正片","vod_hits":8932,"vod_hits_true":0,"vod_year":2019,"vod_area":"大陆","vod_lang":"","vod_class":"喜剧","vod_douban_score":"6.5"}]
         * count : [{"name":"电影","count":127896},{"name":"电视剧","count":32705},{"name":"综艺","count":3862},{"name":"动漫","count":3287},{"name":"其它","count":48031}]
         */

        private int total;
        private String per_page;
        private String current_page;
        private int last_page;
        private List<DataBean> data;
        private List<CountBean> count;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
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

        public List<CountBean> getCount() {
            return count;
        }

        public void setCount(List<CountBean> count) {
            this.count = count;
        }

        public static class DataBean extends EasyAdapter.TypeBean {
            /**
             * vod_id : 93441
             * vod_name : 我的超级老师
             * vod_sub :
             * vod_letter : W
             * vod_pic : http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/e910d72043440dcaac4d0b11e659c82a.jpg
             * type_id_1 : 电影
             * type_id : 7
             * vod_en : wodechaojilaoshi
             * vod_time : 1562598787
             * vod_remarks : HD国语
             * vod_hits : 14857
             * vod_hits_true : 9
             * vod_year : 2019
             * vod_area : 大陆
             * vod_lang :
             * vod_class : 喜剧
             * vod_douban_score : 9.3
             */

            private int vod_id;
            private String vod_name;
            private String vod_sub;
            private String vod_letter;
            private String vod_pic;
            private String type_id_1;
            private int type_id;
            private String vod_en;
            private int vod_time;
            private String vod_remarks;
            private int vod_hits;
            private int vod_hits_true;
            private int vod_year;
            private String vod_area;
            private String vod_lang;
            private String vod_class;
            private String vod_douban_score;

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

            public String getType_id_1() {
                return type_id_1;
            }

            public void setType_id_1(String type_id_1) {
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

            public int getVod_hits() {
                return vod_hits;
            }

            public void setVod_hits(int vod_hits) {
                this.vod_hits = vod_hits;
            }

            public int getVod_hits_true() {
                return vod_hits_true;
            }

            public void setVod_hits_true(int vod_hits_true) {
                this.vod_hits_true = vod_hits_true;
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

            public String getVod_douban_score() {
                return vod_douban_score;
            }

            public void setVod_douban_score(String vod_douban_score) {
                this.vod_douban_score = vod_douban_score;
            }
        }

        public static class CountBean {
            /**
             * name : 电影
             * count : 127896
             */

            private String name;
            private int count;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
