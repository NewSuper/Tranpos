package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;


public class ArticleCollectReplyEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"total":4,"per_page":10,"current_page":1,"last_page":1,"data":[{"rid":64386786,"aid":6074904,"cid":84662148,"content":"上次世锦赛冠军50万英镑！百度一下","create_time":1565583077,"fabulous":0,"uid":0,"nickname":"卖他六百亿","avatar":"http://p9.pstatp.com/thumb/2c6f0009101d3b0c3ee6","is_fabulous":0},{"rid":64386785,"aid":6074904,"cid":84662148,"content":"上赛季世锦赛冠军50万英镑，这个17.5万英镑，，哪个奖金高？？？","create_time":1565582981,"fabulous":0,"uid":0,"nickname":"Jay146112521","avatar":"http://p1.pstatp.com/thumb/dac2000485709ac1c326","is_fabulous":0},{"rid":64386784,"aid":6074904,"cid":84662148,"content":"这就不少了。知足吧。可怜那些陪榜的。也就拔了几根毛回去。看来还得多练功。争取挣大钱。谁知道丁俊晖能拿多少。别叫外国人全搂去。","create_time":1565582884,"fabulous":0,"uid":0,"nickname":"手机用户6630162986","avatar":"http://p1.pstatp.com/thumb/d2770004c78dc363c6af","is_fabulous":0},{"rid":64386783,"aid":6074904,"cid":84662148,"content":"真球迷啊[捂脸]","create_time":1565582779,"fabulous":0,"uid":0,"nickname":"那时花开153821535","avatar":"http://p3.pstatp.com/thumb/289e001bd8c4b18801c3","is_fabulous":0}],"top":[{"rid":64386782,"aid":6074904,"cid":84662148,"content":"世锦赛第一名是35万英镑 差不多 还是全部奖金是35忘了","create_time":1565582742,"fabulous":1,"uid":0,"nickname":"佳啊啊啊啊啊啊2","avatar":"http://p3.pstatp.com/thumb/249c001f16a0503fcbfa","is_fabulous":0}]}
     * time : 1565839737
     * vsn : 1.8.8
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
         * total : 4
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"rid":64386786,"aid":6074904,"cid":84662148,"content":"上次世锦赛冠军50万英镑！百度一下","create_time":1565583077,"fabulous":0,"uid":0,"nickname":"卖他六百亿","avatar":"http://p9.pstatp.com/thumb/2c6f0009101d3b0c3ee6","is_fabulous":0},{"rid":64386785,"aid":6074904,"cid":84662148,"content":"上赛季世锦赛冠军50万英镑，这个17.5万英镑，，哪个奖金高？？？","create_time":1565582981,"fabulous":0,"uid":0,"nickname":"Jay146112521","avatar":"http://p1.pstatp.com/thumb/dac2000485709ac1c326","is_fabulous":0},{"rid":64386784,"aid":6074904,"cid":84662148,"content":"这就不少了。知足吧。可怜那些陪榜的。也就拔了几根毛回去。看来还得多练功。争取挣大钱。谁知道丁俊晖能拿多少。别叫外国人全搂去。","create_time":1565582884,"fabulous":0,"uid":0,"nickname":"手机用户6630162986","avatar":"http://p1.pstatp.com/thumb/d2770004c78dc363c6af","is_fabulous":0},{"rid":64386783,"aid":6074904,"cid":84662148,"content":"真球迷啊[捂脸]","create_time":1565582779,"fabulous":0,"uid":0,"nickname":"那时花开153821535","avatar":"http://p3.pstatp.com/thumb/289e001bd8c4b18801c3","is_fabulous":0}]
         * top : [{"rid":64386782,"aid":6074904,"cid":84662148,"content":"世锦赛第一名是35万英镑 差不多 还是全部奖金是35忘了","create_time":1565582742,"fabulous":1,"uid":0,"nickname":"佳啊啊啊啊啊啊2","avatar":"http://p3.pstatp.com/thumb/249c001f16a0503fcbfa","is_fabulous":0}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;
        private List<DataBean> top;

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

        public List<DataBean> getTop() {
            return top;
        }

        public void setTop(List<DataBean> top) {
            this.top = top;
        }

        public static class DataBean {
            /**
             * rid : 64386786
             * aid : 6074904
             * cid : 84662148
             * content : 上次世锦赛冠军50万英镑！百度一下
             * create_time : 1565583077
             * fabulous : 0
             * uid : 0
             * nickname : 卖他六百亿
             * avatar : http://p9.pstatp.com/thumb/2c6f0009101d3b0c3ee6
             * is_fabulous : 0
             */

            private int vid = 0;
            private int rid = 0;
            private int aid = 0;
            private int cid = 0;
            private String content;
            private int create_time = 0;
            private int fabulous = 0;
            private int uid = 0;
            private String nickname;
            private String avatar;
            private int is_fabulous = 0;

            public int getVid() {
                return vid;
            }

            public void setVid(int vid) {
                this.vid = vid;
            }

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
            }

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getFabulous() {
                return fabulous;
            }

            public void setFabulous(int fabulous) {
                this.fabulous = fabulous;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getIs_fabulous() {
                return is_fabulous;
            }

            public void setIs_fabulous(int is_fabulous) {
                this.is_fabulous = is_fabulous;
            }
        }
    }
}
