package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class Book extends Entity {

    /**
     * {
     * 	"novel": {
     * 		"id": "5151",
     * 		"name": "帝逆洪荒",
     * 		"intro": "<b>&#091;帝逆洪荒简介&#093;：<\/b>\n...\n<b>&#091;关键词&#093;：<\/b>帝逆洪荒天子辉\n<b>&#091;本文地址&#093;：<\/b>https:\/\/www.dswx.cc\/html\/65\/65167\/index.html\n<b>&#091;作者有话说&#093;：<\/b>在百度中搜索本书&gt;&gt;&gt;\n<span>最新章节：<\/span>第一千二百四十八章祸福相依，天地意志出手！\n<span>最新更新：<\/span>2019-08-27",
     * 		"cover": "https:\/\/www.dswx.cc\/files\/article\/image\/65\/65167\/65167s.jpg"
     *        },
     * 	"author": {
     * 		"name": "天子辉"
     *    },
     * 	"category": {
     * 		"id": "16",
     * 		"name": "文学"
     *    },
     * 	"last": {
     * 		"id": "1349",
     * 		"name": "第一千三百零五章 帝辛受到了重创！【一更】",
     * 		"time": "1569737659"
     *    }
     * }
     */

    private Novel novel;
    private Author author;
    private Source source;
    private Category category;
    private Last last;
    private Data data;
    private Top top;
    private int hasnew;
    private String keyword;
    private boolean isLocal;
    private boolean isUpdate;
    private String lastRead;
    private List<BookChapterEntity.Chapter> bookChapters;

    public Novel getNovel() {
        return novel;
    }

    public void setNovel(Novel novel) {
        this.novel = novel;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Last getLast() {
        return last;
    }

    public void setLast(Last last) {
        this.last = last;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Top getTop() {
        return top;
    }

    public void setTop(Top top) {
        this.top = top;
    }

    public int getHasnew() {
        return hasnew;
    }

    public void setHasnew(int hasnew) {
        this.hasnew = hasnew;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getLastRead() {
        return lastRead;
    }

    public void setLastRead(String lastRead) {
        this.lastRead = lastRead;
    }

    public List<BookChapterEntity.Chapter> getBookChapters() {
        return bookChapters;
    }

    public void setBookChapters(List<BookChapterEntity.Chapter> bookChapters) {
        this.bookChapters = bookChapters;
    }

    public static class Novel extends Entity {
        private String id;
        private String name;
        private String intro;
        private String cover;
        private String pinyin;
        private String initial;
        private String caption;
        private long postdate;
        private String isgood;
        private String status;
        private int isover;
        private String url_info;
        private String url_chapterlist;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getInitial() {
            return initial;
        }

        public void setInitial(String initial) {
            this.initial = initial;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public long getPostdate() {
            return postdate;
        }

        public void setPostdate(long postdate) {
            this.postdate = postdate;
        }

        public String getIsgood() {
            return isgood;
        }

        public void setIsgood(String isgood) {
            this.isgood = isgood;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getIsover() {
            return isover;
        }

        public void setIsover(int isover) {
            this.isover = isover;
        }

        public String getUrl_info() {
            return url_info;
        }

        public void setUrl_info(String url_info) {
            this.url_info = url_info;
        }

        public String getUrl_chapterlist() {
            return url_chapterlist;
        }

        public void setUrl_chapterlist(String url_chapterlist) {
            this.url_chapterlist = url_chapterlist;
        }
    }

    public static class Author extends Entity {
        private String name;
        private String url;

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
    }

    public static class Source extends Entity {
        private String sitename;

        public String getSitename() {
            return sitename;
        }

        public void setSitename(String sitename) {
            this.sitename = sitename;
        }
    }

    public static class Category extends Entity {

        private String id;
        private String name;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }

    public static class Last extends Entity {
        private String id;
        private String name;
        private long time;
        private String siteid;
        private String sign;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getSiteid() {
            return siteid;
        }

        public void setSiteid(String siteid) {
            this.siteid = siteid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Data extends Entity {
        private String allvisit;
        private String monthvisit;
        private String weekvisit;
        private String dayvisit;
        private String marknum;
        private String votenum;
        private String downnum;

        public String getAllvisit() {
            return allvisit;
        }

        public void setAllvisit(String allvisit) {
            this.allvisit = allvisit;
        }

        public String getMonthvisit() {
            return monthvisit;
        }

        public void setMonthvisit(String monthvisit) {
            this.monthvisit = monthvisit;
        }

        public String getWeekvisit() {
            return weekvisit;
        }

        public void setWeekvisit(String weekvisit) {
            this.weekvisit = weekvisit;
        }

        public String getDayvisit() {
            return dayvisit;
        }

        public void setDayvisit(String dayvisit) {
            this.dayvisit = dayvisit;
        }

        public String getMarknum() {
            return marknum;
        }

        public void setMarknum(String marknum) {
            this.marknum = marknum;
        }

        public String getVotenum() {
            return votenum;
        }

        public void setVotenum(String votenum) {
            this.votenum = votenum;
        }

        public String getDownnum() {
            return downnum;
        }

        public void setDownnum(String downnum) {
            this.downnum = downnum;
        }
    }

    public static class Top extends Entity {
        private String name;
        private String num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }

}
