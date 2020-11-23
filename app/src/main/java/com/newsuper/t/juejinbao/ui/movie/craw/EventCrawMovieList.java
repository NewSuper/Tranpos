package com.newsuper.t.juejinbao.ui.movie.craw;

import java.util.List;

public class EventCrawMovieList {
    private String tag;
    private boolean isEnd;
    private BeanMovieSearchItem item;

    public EventCrawMovieList(String tag, BeanMovieSearchItem item , boolean isEnd) {
        this.tag = tag;
        this.item = item;
        this.isEnd = isEnd;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public BeanMovieSearchItem getItem() {
        return item;
    }

    public void setItem(BeanMovieSearchItem item) {
        this.item = item;
    }
}
