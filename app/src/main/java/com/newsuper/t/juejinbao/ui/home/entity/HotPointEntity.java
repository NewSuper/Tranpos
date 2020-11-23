package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class HotPointEntity extends Entity{
    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private String today;
    private List<HotPoint> data;

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

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public List<HotPoint> getData() {
        return data;
    }

    public void setData(List<HotPoint> data) {
        this.data = data;
    }

    public class HotPoint extends Entity {
        private int id;
        private String title;
        private String author;
        private long publish_time;
        private List<String> img_url;
        private String author_logo;
        private String mark;
        private int showtype;
        private String short_publish_time;
        private int read_count;
        private int comment_count;
        private int digg_count;
        private String type;
        private String description;

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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public long getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(long publish_time) {
            this.publish_time = publish_time;
        }

        public List<String> getImg_url() {
            return img_url;
        }

        public void setImg_url(List<String> img_url) {
            this.img_url = img_url;
        }

        public String getAuthor_logo() {
            return author_logo;
        }

        public void setAuthor_logo(String author_logo) {
            this.author_logo = author_logo;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public int getShowtype() {
            return showtype;
        }

        public void setShowtype(int showtype) {
            this.showtype = showtype;
        }

        public String getShort_publish_time() {
            return short_publish_time;
        }

        public void setShort_publish_time(String short_publish_time) {
            this.short_publish_time = short_publish_time;
        }

        public int getRead_count() {
            return read_count;
        }

        public void setRead_count(int read_count) {
            this.read_count = read_count;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getDigg_count() {
            return digg_count;
        }

        public void setDigg_count(int digg_count) {
            this.digg_count = digg_count;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
