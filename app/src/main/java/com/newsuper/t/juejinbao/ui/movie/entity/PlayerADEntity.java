package com.newsuper.t.juejinbao.ui.movie.entity;

import java.util.List;

public class PlayerADEntity {


    /**
     * code : 0
     * msg : success
     * data : [{"title":"影视百度外链","is_show_ad":"1","ad_probability":0.5,"redirect_type":2,"view_type":"1","ad_id":"啊标","ad_control_type":"1","imgs":[{"url":"","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/j6kWfQsApP.png","probability":0},{"url":"","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/GmWWAEcXFS.png","probability":0},{"url":"","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/FBmxMWjG6a.png","probability":0}]},{"title":"http://local.ad","is_show_ad":"0","ad_probability":0.9,"redirect_type":2,"view_type":"2","ad_id":"","ad_control_type":"2","imgs":[{"url":"http://local.ad.juejinchain.cn","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/KFZCQsyfsR.jpg","probability":0.6},{"url":"http://local.ad.juejinchain.cn","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/72cJR7En4W.jpg","probability":0.4}]}]
     * time : 1571923797
     * vsn : 1.8.8
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
         * title : 影视百度外链
         * is_show_ad : 1
         * ad_probability : 0.5
         * redirect_type : 2
         * view_type : 1
         * ad_id : 啊标
         * ad_control_type : 1
         * imgs : [{"url":"","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/j6kWfQsApP.png","probability":0},{"url":"","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/GmWWAEcXFS.png","probability":0},{"url":"","src":"http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/FBmxMWjG6a.png","probability":0}]
         */

        private int id;
        private String title;
        private String is_show_ad;
        private double ad_probability;
        private int redirect_type;
        private String view_type;
        private String ad_id;
        private String ad_control_type;
        private List<ImgsBean> imgs;

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

        public String getIs_show_ad() {
            return is_show_ad;
        }

        public void setIs_show_ad(String is_show_ad) {
            this.is_show_ad = is_show_ad;
        }

        public double getAd_probability() {
            return ad_probability;
        }

        public void setAd_probability(double ad_probability) {
            this.ad_probability = ad_probability;
        }

        public int getRedirect_type() {
            return redirect_type;
        }

        public void setRedirect_type(int redirect_type) {
            this.redirect_type = redirect_type;
        }

        public String getView_type() {
            return view_type;
        }

        public void setView_type(String view_type) {
            this.view_type = view_type;
        }

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public String getAd_control_type() {
            return ad_control_type;
        }

        public void setAd_control_type(String ad_control_type) {
            this.ad_control_type = ad_control_type;
        }

        public List<ImgsBean> getImgs() {
            return imgs;
        }

        public void setImgs(List<ImgsBean> imgs) {
            this.imgs = imgs;
        }

        public static class ImgsBean {
            /**
             * url :
             * src : http://jjbadfile.juejinchain.com/jjb-ad-file/1/2019/10/24/j6kWfQsApP.png
             * probability : 0
             */

            private String url;
            private String src;
            private double probability;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public double getProbability() {
                return probability;
            }

            public void setProbability(double probability) {
                this.probability = probability;
            }
        }
    }
}
