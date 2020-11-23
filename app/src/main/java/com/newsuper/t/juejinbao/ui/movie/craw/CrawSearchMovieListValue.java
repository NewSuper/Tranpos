package com.newsuper.t.juejinbao.ui.movie.craw;

import java.util.List;

/**
 * 爬取电影搜索列表数据
 */
public class CrawSearchMovieListValue {


    /**
     * url : https://www.douban.com
     * list : [{"type":"select","name":"body","index":-1},{"type":"select","name":"div[id=anony-movie]","index":-1},{"type":"select","name":"div[class=main]","index":-1},{"type":"select","name":"div[class=movie-list list]","index":-1},{"type":"select","name":"ul","index":0}]
     * item : {"img":[{"type":"select","name":"div[class=pic]","index":-1},{"type":"select","name":"a[href]","index":-1},{"type":"select","name":"img[src]","index":-1},{"type":"attr","name":"data-origin","index":-1}],"title":[{"type":"select","name":"div[class=title]","index":-1},{"type":"select","name":"a[href]","index":-1}],"rating":[{"type":"select","name":"div[class=rating]","index":-1},{"type":"element","name":"","index":1}]}
     */

    private int sourceListStartIndex;
    private int sourceListEndIndex;

    private String name;
    private String url;
    private ItemBean item;
    private int level;
    private List<CrawValue> list;
    private List<String> ignore;

    private String requestType;
    private List<List<String>> requestFormData;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public List<List<String>> getRequestFormData() {
        return requestFormData;
    }

    public void setRequestFormData(List<List<String>> requestFormData) {
        this.requestFormData = requestFormData;
    }

    public int getSourceListStartIndex() {
        return sourceListStartIndex;
    }

    public void setSourceListStartIndex(int sourceListStartIndex) {
        this.sourceListStartIndex = sourceListStartIndex;
    }

    public int getSourceListEndIndex() {
        return sourceListEndIndex;
    }

    public void setSourceListEndIndex(int sourceListEndIndex) {
        this.sourceListEndIndex = sourceListEndIndex;
    }

    public List<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ItemBean getItem() {
        return item;
    }

    public void setItem(ItemBean item) {
        this.item = item;
    }

    public List<CrawValue> getList() {
        return list;
    }

    public void setList(List<CrawValue> list) {
        this.list = list;
    }

    public static class ItemBean {
        //图片
        private List<CrawValue> img;
        //标题
        private List<CrawValue> title;
        //评分
        private List<CrawValue> rating;
        //导演
        private List<CrawValue> director;
        //演员
        private List<CrawValue> actor;
        //类型
        private List<CrawValue> form;
        //简介
        private List<CrawValue> intro;
        //清晰度
        private List<CrawValue> limpid;
        //发行日期
        private List<CrawValue> date;
        //地区
        private List<CrawValue> area;
        //现状
        private List<CrawValue> now;
        //链接
        private List<CrawValue> link;

        public List<CrawValue> getImg() {
            return img;
        }

        public void setImg(List<CrawValue> img) {
            this.img = img;
        }

        public List<CrawValue> getTitle() {
            return title;
        }

        public void setTitle(List<CrawValue> title) {
            this.title = title;
        }

        public List<CrawValue> getRating() {
            return rating;
        }

        public void setRating(List<CrawValue> rating) {
            this.rating = rating;
        }

        public List<CrawValue> getDirector() {
            return director;
        }

        public void setDirector(List<CrawValue> director) {
            this.director = director;
        }

        public List<CrawValue> getActor() {
            return actor;
        }

        public void setActor(List<CrawValue> actor) {
            this.actor = actor;
        }

        public List<CrawValue> getForm() {
            return form;
        }

        public void setForm(List<CrawValue> form) {
            this.form = form;
        }

        public List<CrawValue> getIntro() {
            return intro;
        }

        public void setIntro(List<CrawValue> intro) {
            this.intro = intro;
        }

        public List<CrawValue> getLimpid() {
            return limpid;
        }

        public void setLimpid(List<CrawValue> limpid) {
            this.limpid = limpid;
        }

        public List<CrawValue> getDate() {
            return date;
        }

        public void setDate(List<CrawValue> date) {
            this.date = date;
        }

        public List<CrawValue> getArea() {
            return area;
        }

        public void setArea(List<CrawValue> area) {
            this.area = area;
        }

        public List<CrawValue> getNow() {
            return now;
        }

        public void setNow(List<CrawValue> now) {
            this.now = now;
        }

        public List<CrawValue> getLink() {
            return link;
        }

        public void setLink(List<CrawValue> link) {
            this.link = link;
        }

    }


}
