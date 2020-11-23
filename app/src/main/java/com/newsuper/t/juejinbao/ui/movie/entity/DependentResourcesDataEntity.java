package com.newsuper.t.juejinbao.ui.movie.entity;

import com.juejinchain.android.module.movie.adapter.ConditionAdapter;
import com.juejinchain.android.module.movie.adapter.DependentResourceConditionAdapter;
import com.juejinchain.android.module.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class DependentResourcesDataEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"total":80,"per_page":10,"current_page":"1","last_page":8,"data":[{"vod_id":103399,"vod_name":"狮子王","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/dc105f531c036fc0ed0f72d6c0dc63f5.jpg","type_id_1":"其他","type_id":1,"vod_en":"shiziwang","vod_time":1562367694,"vod_remarks":"BD1080P中字","vod_hits":345,"vod_hits_true":141,"vod_year":1994,"vod_area":"美国","vod_lang":"","vod_class":"动画","vod_douban_score":"8.0"},{"vod_id":65148,"vod_name":"狮子王","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/dc105f531c036fc0ed0f72d6c0dc63f5.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwang","vod_time":1562387642,"vod_remarks":"BD高清","vod_hits":508,"vod_hits_true":88,"vod_year":1994,"vod_area":"美国","vod_lang":"","vod_class":"动画","vod_douban_score":"6.0"},{"vod_id":20823,"vod_name":"狮子王强大","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/58fdd263349250168cf9d9367e6901a0.jpg","type_id_1":"电视剧","type_id":14,"vod_en":"shiziwangqiangda","vod_time":1562673611,"vod_remarks":"共25集","vod_hits":280,"vod_hits_true":8,"vod_year":2017,"vod_area":"台湾","vod_lang":"","vod_class":"台剧","vod_douban_score":"7.0"},{"vod_id":67212,"vod_name":"狮子王3","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/7e1e3f4006dd414e1507c6610e76f12f.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwang3","vod_time":1562388726,"vod_remarks":"BD高清中字","vod_hits":881,"vod_hits_true":18,"vod_year":2004,"vod_area":"其它","vod_lang":"","vod_class":"动漫","vod_douban_score":"6.0"},{"vod_id":41004,"vod_name":"狮子王2：辛巴的荣耀","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/c04dd3922c669d6353d3a0ed7756a08d.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwang2xinbaderongyao","vod_time":1562388682,"vod_remarks":"BD高清中字","vod_hits":260,"vod_hits_true":16,"vod_year":1998,"vod_area":"美国","vod_lang":"英语","vod_class":"动漫","vod_douban_score":"9.0"},{"vod_id":303444,"vod_name":"狮子王2：辛巴的荣耀","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/12b3803b08dbabf45270c01c2204ab3d.jpg","type_id_1":"其他","type_id":1,"vod_en":"shiziwang2xinbaderongyao","vod_time":1562367694,"vod_remarks":"BD720P中字","vod_hits":330,"vod_hits_true":6,"vod_year":1998,"vod_area":"欧美","vod_lang":"","vod_class":"电影","vod_douban_score":"6.0"},{"vod_id":103392,"vod_name":"狮子王3","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/7e1e3f4006dd414e1507c6610e76f12f.jpg","type_id_1":"其他","type_id":1,"vod_en":"shiziwang3","vod_time":1562367693,"vod_remarks":"BD720P中字","vod_hits":500,"vod_hits_true":9,"vod_year":2004,"vod_area":"美国","vod_lang":"","vod_class":"动画","vod_douban_score":"6.0"},{"vod_id":413075,"vod_name":"狮子王(普通话版)","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/2823bfa3c2e803aa8da371cb211bf55f.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwangputonghuaban","vod_time":1562312413,"vod_remarks":"BD1280高清中英双字版","vod_hits":81321,"vod_hits_true":16,"vod_year":1994,"vod_area":"美国","vod_lang":"英语","vod_class":"动漫","vod_douban_score":"6.5"},{"vod_id":120006,"vod_name":"狮子王3","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/7e1e3f4006dd414e1507c6610e76f12f.jpg","type_id_1":"电影","type_id":7,"vod_en":"shiziwang3","vod_time":1562307884,"vod_remarks":"超清英语中字","vod_hits":322,"vod_hits_true":5,"vod_year":2004,"vod_area":"美国","vod_lang":"英语","vod_class":"喜剧","vod_douban_score":"7.0"},{"vod_id":120016,"vod_name":"狮子王2：辛巴的荣耀","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/909fa52e077b2f5a7584d182c9818543.jpg","type_id_1":"电影","type_id":8,"vod_en":"shiziwang2xinbaderongyao","vod_time":1562307884,"vod_remarks":"高清英语中字","vod_hits":95,"vod_hits_true":9,"vod_year":1998,"vod_area":"英国","vod_lang":"英语","vod_class":"爱情","vod_douban_score":"6.0"}],"count":[{"name":"电影","count":24},{"name":"电视剧","count":8},{"name":"综艺","count":2},{"name":"动漫","count":9},{"name":"其它","count":38}]}
     * time : 1563006190
     * vsn : 1.8.7
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
         * total : 80
         * per_page : 10
         * current_page : 1
         * last_page : 8
         * data : [{"vod_id":103399,"vod_name":"狮子王","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/dc105f531c036fc0ed0f72d6c0dc63f5.jpg","type_id_1":"其他","type_id":1,"vod_en":"shiziwang","vod_time":1562367694,"vod_remarks":"BD1080P中字","vod_hits":345,"vod_hits_true":141,"vod_year":1994,"vod_area":"美国","vod_lang":"","vod_class":"动画","vod_douban_score":"8.0"},{"vod_id":65148,"vod_name":"狮子王","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/dc105f531c036fc0ed0f72d6c0dc63f5.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwang","vod_time":1562387642,"vod_remarks":"BD高清","vod_hits":508,"vod_hits_true":88,"vod_year":1994,"vod_area":"美国","vod_lang":"","vod_class":"动画","vod_douban_score":"6.0"},{"vod_id":20823,"vod_name":"狮子王强大","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/58fdd263349250168cf9d9367e6901a0.jpg","type_id_1":"电视剧","type_id":14,"vod_en":"shiziwangqiangda","vod_time":1562673611,"vod_remarks":"共25集","vod_hits":280,"vod_hits_true":8,"vod_year":2017,"vod_area":"台湾","vod_lang":"","vod_class":"台剧","vod_douban_score":"7.0"},{"vod_id":67212,"vod_name":"狮子王3","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/7e1e3f4006dd414e1507c6610e76f12f.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwang3","vod_time":1562388726,"vod_remarks":"BD高清中字","vod_hits":881,"vod_hits_true":18,"vod_year":2004,"vod_area":"其它","vod_lang":"","vod_class":"动漫","vod_douban_score":"6.0"},{"vod_id":41004,"vod_name":"狮子王2：辛巴的荣耀","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/c04dd3922c669d6353d3a0ed7756a08d.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwang2xinbaderongyao","vod_time":1562388682,"vod_remarks":"BD高清中字","vod_hits":260,"vod_hits_true":16,"vod_year":1998,"vod_area":"美国","vod_lang":"英语","vod_class":"动漫","vod_douban_score":"9.0"},{"vod_id":303444,"vod_name":"狮子王2：辛巴的荣耀","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/12b3803b08dbabf45270c01c2204ab3d.jpg","type_id_1":"其他","type_id":1,"vod_en":"shiziwang2xinbaderongyao","vod_time":1562367694,"vod_remarks":"BD720P中字","vod_hits":330,"vod_hits_true":6,"vod_year":1998,"vod_area":"欧美","vod_lang":"","vod_class":"电影","vod_douban_score":"6.0"},{"vod_id":103392,"vod_name":"狮子王3","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/7e1e3f4006dd414e1507c6610e76f12f.jpg","type_id_1":"其他","type_id":1,"vod_en":"shiziwang3","vod_time":1562367693,"vod_remarks":"BD720P中字","vod_hits":500,"vod_hits_true":9,"vod_year":2004,"vod_area":"美国","vod_lang":"","vod_class":"动画","vod_douban_score":"6.0"},{"vod_id":413075,"vod_name":"狮子王(普通话版)","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/2823bfa3c2e803aa8da371cb211bf55f.jpg","type_id_1":"其他","type_id":4,"vod_en":"shiziwangputonghuaban","vod_time":1562312413,"vod_remarks":"BD1280高清中英双字版","vod_hits":81321,"vod_hits_true":16,"vod_year":1994,"vod_area":"美国","vod_lang":"英语","vod_class":"动漫","vod_douban_score":"6.5"},{"vod_id":120006,"vod_name":"狮子王3","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/7e1e3f4006dd414e1507c6610e76f12f.jpg","type_id_1":"电影","type_id":7,"vod_en":"shiziwang3","vod_time":1562307884,"vod_remarks":"超清英语中字","vod_hits":322,"vod_hits_true":5,"vod_year":2004,"vod_area":"美国","vod_lang":"英语","vod_class":"喜剧","vod_douban_score":"7.0"},{"vod_id":120016,"vod_name":"狮子王2：辛巴的荣耀","vod_sub":"","vod_letter":"S","vod_pic":"http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/909fa52e077b2f5a7584d182c9818543.jpg","type_id_1":"电影","type_id":8,"vod_en":"shiziwang2xinbaderongyao","vod_time":1562307884,"vod_remarks":"高清英语中字","vod_hits":95,"vod_hits_true":9,"vod_year":1998,"vod_area":"英国","vod_lang":"英语","vod_class":"爱情","vod_douban_score":"6.0"}]
         * count : [{"name":"电影","count":24},{"name":"电视剧","count":8},{"name":"综艺","count":2},{"name":"动漫","count":9},{"name":"其它","count":38}]
         */

        private int total;
        private int per_page;
        private String current_page;
        private int last_page;
        private List<DataBean> data;
        private List<CountBean> count;
        private String ckey;

        public String getCkey() {
            return ckey;
        }

        public void setCkey(String ckey) {
            this.ckey = ckey;
        }

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

        public List<CountBean> getCount() {
            return count;
        }

        public void setCount(List<CountBean> count) {
            this.count = count;
        }

        public static class DataBean extends EasyAdapter.TypeBean {
            /**
             * vod_id : 103399
             * vod_name : 狮子王
             * vod_sub :
             * vod_letter : S
             * vod_pic : http://jjmovie.oss-cn-shenzhen.aliyuncs.com/upload/vod/dc105f531c036fc0ed0f72d6c0dc63f5.jpg
             * type_id_1 : 其他
             * type_id : 1
             * vod_en : shiziwang
             * vod_time : 1562367694
             * vod_remarks : BD1080P中字
             * vod_hits : 345
             * vod_hits_true : 141
             * vod_year : 1994
             * vod_area : 美国
             * vod_lang :
             * vod_class : 动画
             * vod_douban_score : 8.0
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

            public String ckey = "";

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

        public static class CountBean{
            /**
             * name : 电影
             * count : 24
             */

            private String name;
            private int count;

            public boolean isCheck = false;

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
