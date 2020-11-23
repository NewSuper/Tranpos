package com.newsuper.t.juejinbao.ui.movie.entity;

import java.util.List;

public class MovieNewRecommendEntity {


    /**
     * code : 0
     * msg : success
     * data : [{"title":"热播电视","alias":"tv","items":[{"thumbnail":"https://img3.doubanio.com/view/photo/photo/public/p2571152400.webp","title":"误杀正在热映中！ 看肖央如何瞒天过海？","subtitle":"国产动漫崛起","search_content":"国产动漫崛起"}]},{"title":"热门电影推荐","alias":"movie","items":[{"thumbnail":"https://img3.doubanio.com/view/photo/photo/public/p2571152400.webp","title":"误杀正在热映中！ 看肖央如何瞒天过海？","subtitle":"国产动漫崛起","search_content":"国产动漫崛起"}]},{"title":"热门综艺推荐","alias":"show","items":[{"thumbnail":"https://img3.doubanio.com/view/photo/photo/public/p2571152400.webp","title":"误杀正在热映中！ 看肖央如何瞒天过海？","subtitle":"国产动漫崛起","search_content":"国产动漫崛起"}]}]
     * time : 1580959528
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
         * title : 热播电视
         * alias : tv
         * items : [{"thumbnail":"https://img3.doubanio.com/view/photo/photo/public/p2571152400.webp","title":"误杀正在热映中！ 看肖央如何瞒天过海？","subtitle":"国产动漫崛起","search_content":"国产动漫崛起"}]
         */

        private String title;
        private String alias;
        private List<ItemsBean> items;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * thumbnail : https://img3.doubanio.com/view/photo/photo/public/p2571152400.webp
             * title : 误杀正在热映中！ 看肖央如何瞒天过海？
             * subtitle : 国产动漫崛起
             * search_content : 国产动漫崛起
             */

            private String thumbnail;
            private String title;
            private String subtitle;
            private String search_content;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getSearch_content() {
                return search_content;
            }

            public void setSearch_content(String search_content) {
                this.search_content = search_content;
            }
        }
    }
}
