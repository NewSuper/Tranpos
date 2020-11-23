package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 影视模块tab数据种子
 */
public class MovieTabDataEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"nav":[{"name":"电影","type":"电影"},{"name":"电视剧","type":"电视剧"},{"name":"综艺","type":"综艺"},{"name":"动漫","type":"动漫"}],"tab":[{"name":"影院热播","category1":"电影","en":"movie"},{"name":"热播电视剧","category1":"电视剧","en":"tv"},{"name":"热门综艺","category1":"综艺","en":"variety"},{"name":"热门动漫","category1":"动漫","en":"cartoon"}]}
     * time : 1562827555
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
        private List<NavBean> nav;
        private List<TabBean> tab;

        public List<NavBean> getNav() {
            return nav;
        }

        public void setNav(List<NavBean> nav) {
            this.nav = nav;
        }

        public List<TabBean> getTab() {
            return tab;
        }

        public void setTab(List<TabBean> tab) {
            this.tab = tab;
        }

        public static class NavBean {
            /**
             * name : 电影
             * type : 电影
             */

            private String name;
            private String type;
            private String alias;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class TabBean implements Serializable{
            /**
             * name : 影院热播
             * category1 : 电影
             * en : movie
             */

            private String name;
            private String category1;
            private String en;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCategory1() {
                return category1;
            }

            public void setCategory1(String category1) {
                this.category1 = category1;
            }

            public String getEn() {
                return en;
            }

            public void setEn(String en) {
                this.en = en;
            }
        }
    }
}
