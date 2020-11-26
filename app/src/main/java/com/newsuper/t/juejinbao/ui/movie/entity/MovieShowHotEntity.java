package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.util.List;

public class MovieShowHotEntity {

    /**
     * code : 0
     * msg : success
     * data : {"total":141,"per_page":12,"current_page":1,"last_page":12,"data":[{"vod_id":"34855021","vod_title":"令人心动的offer","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 陈平 / 何炅 周震南","vod_pub_date":"2019-10-30","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2572320724.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34894594","vod_title":"你怎么这么好看","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 宋梓漪 黄力 / 昆凌 吴昕","vod_pub_date":"2019-12-19","vod_pic":"https://img1.doubanio.com/view/photo/m_ratio_poster/public/p2579436038.webp","vod_score":"4.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"30446494","vod_title":"明星大侦探 第五季","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 何舒 / 何炅 撒贝宁","vod_pub_date":"2019-11-08","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2574618624.webp","vod_score":"8.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34855021","vod_title":"令人心动的offer","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 陈平 / 何炅 周震南","vod_pub_date":"2019-10-30","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2572320724.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"33406300","vod_title":"局部 第三季","vod_subtitle":"2020 / 中国大陆 / 脱口秀 / 谢梦茜 / 陈丹青","vod_pub_date":"2020-01-08","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579391183.webp","vod_score":"9.5","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34909421","vod_title":"詹姆斯·梅：人在日本 第一季","vod_subtitle":"2020 / 英国 / 纪录片 / 詹姆斯·梅","vod_pub_date":"2020-01-03","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2579745635.webp","vod_score":"9.0","vod_category":"show","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"詹姆斯·梅","vod_director":"","av_trend":0},{"vod_id":"34909421","vod_title":"詹姆斯·梅：人在日本 第一季","vod_subtitle":"2020 / 英国 / 纪录片 / 詹姆斯·梅","vod_pub_date":"2020-01-03","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2579745635.webp","vod_score":"9.0","vod_category":"show","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"詹姆斯·梅","vod_director":"","av_trend":0},{"vod_id":"33406300","vod_title":"局部 第三季","vod_subtitle":"2020 / 中国大陆 / 脱口秀 / 谢梦茜 / 陈丹青","vod_pub_date":"2020-01-08","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579391183.webp","vod_score":"9.5","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34937987","vod_title":"圆桌跨越派","vod_subtitle":"2019 / 中国大陆 / 脱口秀 / 苏雷 / 窦文涛 吴晓波","vod_pub_date":"2019-12-30","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579706812.webp","vod_score":"8.7","vod_category":"show","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"窦文涛,吴晓波,周轶君","vod_director":"苏雷","av_trend":0},{"vod_id":"30446494","vod_title":"明星大侦探 第五季","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 何舒 / 何炅 撒贝宁","vod_pub_date":"2019-11-08","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2574618624.webp","vod_score":"8.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34822629","vod_title":"派出所的故事2019","vod_subtitle":"2019 / 中国大陆 / 纪录片 真人秀 / 蔡征 邵英黎","vod_pub_date":"2019-12-19","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2577381806.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34822629","vod_title":"派出所的故事2019","vod_subtitle":"2019 / 中国大陆 / 纪录片 真人秀 / 蔡征 邵英黎","vod_pub_date":"2019-12-19","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2577381806.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0}]}
     * time : 1578722914
     * vsn : 1.8.8.3
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
         * total : 141
         * per_page : 12
         * current_page : 1
         * last_page : 12
         * data : [{"vod_id":"34855021","vod_title":"令人心动的offer","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 陈平 / 何炅 周震南","vod_pub_date":"2019-10-30","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2572320724.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34894594","vod_title":"你怎么这么好看","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 宋梓漪 黄力 / 昆凌 吴昕","vod_pub_date":"2019-12-19","vod_pic":"https://img1.doubanio.com/view/photo/m_ratio_poster/public/p2579436038.webp","vod_score":"4.6","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"30446494","vod_title":"明星大侦探 第五季","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 何舒 / 何炅 撒贝宁","vod_pub_date":"2019-11-08","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2574618624.webp","vod_score":"8.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34855021","vod_title":"令人心动的offer","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 陈平 / 何炅 周震南","vod_pub_date":"2019-10-30","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2572320724.webp","vod_score":"8.0","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"33406300","vod_title":"局部 第三季","vod_subtitle":"2020 / 中国大陆 / 脱口秀 / 谢梦茜 / 陈丹青","vod_pub_date":"2020-01-08","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579391183.webp","vod_score":"9.5","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34909421","vod_title":"詹姆斯·梅：人在日本 第一季","vod_subtitle":"2020 / 英国 / 纪录片 / 詹姆斯·梅","vod_pub_date":"2020-01-03","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2579745635.webp","vod_score":"9.0","vod_category":"show","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"詹姆斯·梅","vod_director":"","av_trend":0},{"vod_id":"34909421","vod_title":"詹姆斯·梅：人在日本 第一季","vod_subtitle":"2020 / 英国 / 纪录片 / 詹姆斯·梅","vod_pub_date":"2020-01-03","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2579745635.webp","vod_score":"9.0","vod_category":"show","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"詹姆斯·梅","vod_director":"","av_trend":0},{"vod_id":"33406300","vod_title":"局部 第三季","vod_subtitle":"2020 / 中国大陆 / 脱口秀 / 谢梦茜 / 陈丹青","vod_pub_date":"2020-01-08","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579391183.webp","vod_score":"9.5","vod_category":"tv","vod_year":"2020","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34937987","vod_title":"圆桌跨越派","vod_subtitle":"2019 / 中国大陆 / 脱口秀 / 苏雷 / 窦文涛 吴晓波","vod_pub_date":"2019-12-30","vod_pic":"https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2579706812.webp","vod_score":"8.7","vod_category":"show","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"窦文涛,吴晓波,周轶君","vod_director":"苏雷","av_trend":0},{"vod_id":"30446494","vod_title":"明星大侦探 第五季","vod_subtitle":"2019 / 中国大陆 / 真人秀 / 何舒 / 何炅 撒贝宁","vod_pub_date":"2019-11-08","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2574618624.webp","vod_score":"8.8","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34822629","vod_title":"派出所的故事2019","vod_subtitle":"2019 / 中国大陆 / 纪录片 真人秀 / 蔡征 邵英黎","vod_pub_date":"2019-12-19","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2577381806.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0},{"vod_id":"34822629","vod_title":"派出所的故事2019","vod_subtitle":"2019 / 中国大陆 / 纪录片 真人秀 / 蔡征 邵英黎","vod_pub_date":"2019-12-19","vod_pic":"https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2577381806.webp","vod_score":"9.2","vod_category":"tv","vod_year":"2019","vod_district":"","vod_star":"0.0","vod_tag":"","vod_actor":"","vod_director":"","av_trend":0}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

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

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
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

        public static class DataBean extends EasyAdapter.TypeBean {
            /**
             * vod_id : 34855021
             * vod_title : 令人心动的offer
             * vod_subtitle : 2019 / 中国大陆 / 真人秀 / 陈平 / 何炅 周震南
             * vod_pub_date : 2019-10-30
             * vod_pic : https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2572320724.webp
             * vod_score : 8.0
             * vod_category : tv
             * vod_year : 2019
             * vod_district :
             * vod_star : 0.0
             * vod_tag :
             * vod_actor :
             * vod_director :
             * av_trend : 0
             */

            private String vod_id;
            private String vod_title;
            private String vod_subtitle;
            private String vod_pub_date;
            private String vod_pic;
            private String vod_score;
            private String vod_category;
            private String vod_year;
            private String vod_district;
            private String vod_star;
            private String vod_tag;
            private String vod_actor;
            private String vod_director;
            private int av_trend;

            public String getVod_id() {
                return vod_id;
            }

            public void setVod_id(String vod_id) {
                this.vod_id = vod_id;
            }

            public String getVod_title() {
                return vod_title;
            }

            public void setVod_title(String vod_title) {
                this.vod_title = vod_title;
            }

            public String getVod_subtitle() {
                return vod_subtitle;
            }

            public void setVod_subtitle(String vod_subtitle) {
                this.vod_subtitle = vod_subtitle;
            }

            public String getVod_pub_date() {
                return vod_pub_date;
            }

            public void setVod_pub_date(String vod_pub_date) {
                this.vod_pub_date = vod_pub_date;
            }

            public String getVod_pic() {
                return vod_pic;
            }

            public void setVod_pic(String vod_pic) {
                this.vod_pic = vod_pic;
            }

            public String getVod_score() {
                return vod_score;
            }

            public void setVod_score(String vod_score) {
                this.vod_score = vod_score;
            }

            public String getVod_category() {
                return vod_category;
            }

            public void setVod_category(String vod_category) {
                this.vod_category = vod_category;
            }

            public String getVod_year() {
                return vod_year;
            }

            public void setVod_year(String vod_year) {
                this.vod_year = vod_year;
            }

            public String getVod_district() {
                return vod_district;
            }

            public void setVod_district(String vod_district) {
                this.vod_district = vod_district;
            }

            public String getVod_star() {
                return vod_star;
            }

            public void setVod_star(String vod_star) {
                this.vod_star = vod_star;
            }

            public String getVod_tag() {
                return vod_tag;
            }

            public void setVod_tag(String vod_tag) {
                this.vod_tag = vod_tag;
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

            public int getAv_trend() {
                return av_trend;
            }

            public void setAv_trend(int av_trend) {
                this.av_trend = av_trend;
            }
        }
    }
}
