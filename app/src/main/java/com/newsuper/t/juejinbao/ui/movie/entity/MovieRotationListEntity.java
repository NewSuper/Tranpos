package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class MovieRotationListEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"movie_list":[{"total":164,"list":[{"title":"境·界","rate":"4.9"},{"title":"撞死了一只羊","rate":"7.4"},{"title":"祈祷落幕时","rate":"8.0"},{"title":"狗眼看人心","rate":"6.3"}],"category":"影院热映","show_category":"一周口碑电影榜"},{"total":129,"list":[{"title":"霸王别姬/再见，我的妾/FarewellMyConcubine","rate":"9.6"},{"title":"千与千寻/千と千尋の神隠し/神隐少女(台)/千与千寻的神隐","rate":"9.3"},{"title":"忠犬八公的故事/Hachi:ADog'sTale/忠犬小八(台)/秋田犬八千(港)","rate":"9.3"},{"title":"三傻大闹宝莱坞/3Idiots/三个傻瓜(台)/作死不离3兄弟(港)","rate":"9.2"}],"category":"TOP250","show_category":"热播电影TOP250"},{"total":318,"list":[{"title":"彼布利亚古书堂事件手帖","rate":"6.4"},{"title":"幸运儿彼尔","rate":"7.6"},{"title":"江湖儿女","rate":"7.6"},{"title":"特工","rate":"8.5"}],"category":"近期热门","show_category":"一周热门电影榜"}],"tv_list":[{"total":207,"list":[{"title":"地下交通站","rate":"9.2"},{"title":"能耐大了 第一季","rate":"7.3"},{"title":"因法之名","rate":"5.5"},{"title":"不思异：辞典","rate":"7.1"}],"category":"国产剧","show_category":"华语口碑剧集榜"},{"total":179,"list":[{"title":"我的老板每天死一次","rate":"7.9"},{"title":"特殊案件专案组TEN2","rate":"8.2"},{"title":"圈套","rate":"7.8"},{"title":"只是相爱的关系","rate":"8.5"}],"category":"韩剧","show_category":"全球口碑剧集榜"}]}
     * time : 1570849007
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

    public static class DataBean extends Entity{
        private List<MovieListBean> movie_list;
        private List<MovieListBean> tv_list;

        public List<MovieListBean> getMovie_list() {
            return movie_list;
        }

        public void setMovie_list(List<MovieListBean> movie_list) {
            this.movie_list = movie_list;
        }

        public List<MovieListBean> getTv_list() {
            return tv_list;
        }

        public void setTv_list(List<MovieListBean> tv_list) {
            this.tv_list = tv_list;
        }

        public static class MovieListBean extends Entity{
            /**
             * total : 164
             * list : [{"title":"境·界","rate":"4.9"},{"title":"撞死了一只羊","rate":"7.4"},{"title":"祈祷落幕时","rate":"8.0"},{"title":"狗眼看人心","rate":"6.3"}]
             * category : 影院热映
             * show_category : 一周口碑电影榜
             */

            private int total;
            private String category;
            private String show_category;
            private String cover;
            private List<ListBean> list;

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getShow_category() {
                return show_category;
            }

            public void setShow_category(String show_category) {
                this.show_category = show_category;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean extends Entity{
                /**
                 * title : 境·界
                 * rate : 4.9
                 */

                private String title;
                private String rate;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getRate() {
                    return rate;
                }

                public void setRate(String rate) {
                    this.rate = rate;
                }
            }
        }

        public static class TvListBean {
            /**
             * total : 207
             * list : [{"title":"地下交通站","rate":"9.2"},{"title":"能耐大了 第一季","rate":"7.3"},{"title":"因法之名","rate":"5.5"},{"title":"不思异：辞典","rate":"7.1"}]
             * category : 国产剧
             * show_category : 华语口碑剧集榜
             */

            private int total;
            private String category;
            private String show_category;
            private List<ListBeanX> list;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getShow_category() {
                return show_category;
            }

            public void setShow_category(String show_category) {
                this.show_category = show_category;
            }

            public List<ListBeanX> getList() {
                return list;
            }

            public void setList(List<ListBeanX> list) {
                this.list = list;
            }

            public static class ListBeanX {
                /**
                 * title : 地下交通站
                 * rate : 9.2
                 */

                private String title;
                private String rate;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getRate() {
                    return rate;
                }

                public void setRate(String rate) {
                    this.rate = rate;
                }
            }
        }
    }
}
