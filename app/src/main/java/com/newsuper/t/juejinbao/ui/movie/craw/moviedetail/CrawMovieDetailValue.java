package com.newsuper.t.juejinbao.ui.movie.craw.moviedetail;


import com.newsuper.t.juejinbao.ui.movie.craw.CrawValue;

import java.util.List;

public class CrawMovieDetailValue {
    private List<String> ignore;
    private List<String> ignoreSource;

    private int sourceListStartIndex;
    private int sourceListEndIndex;
    private int playHttpIndex;
    private int playCrawType;
    private String playCrawEx1;
    private String playCrawEx2;
    private String playCrawEx3;
    private String playCrawExFinal;

    private List<CrawValue> img;
    private List<CrawValue> title;
    private List<CrawValue> now;
    private List<CrawValue> rating;
    private List<CrawValue> director;
    private List<CrawValue> actor;
    private List<CrawValue> form;
    private List<CrawValue> intro;
    private List<CrawValue> date;
    private List<CrawValue> year;
    private List<CrawValue> language;
    private List<CrawValue> area;
    private List<CrawValue> detail;


    private List<CrawValue> sourceList;
    private List<CrawValue> sourceItemText;
    private List<CrawValue> pathList;
    private List<CrawValue> pathItemList;
    private List<CrawValue> pathItemName;
    private List<CrawValue> pathItemLink;

    private List<CrawValue> play;


    public List<String> getIgnoreSource() {
        return ignoreSource;
    }

    public void setIgnoreSource(List<String> ignoreSource) {
        this.ignoreSource = ignoreSource;
    }

    public int getPlayCrawType() {
        return playCrawType;
    }

    public void setPlayCrawType(int playCrawType) {
        this.playCrawType = playCrawType;
    }

    public String getPlayCrawEx1() {
        return playCrawEx1;
    }

    public void setPlayCrawEx1(String playCrawEx1) {
        this.playCrawEx1 = playCrawEx1;
    }

    public String getPlayCrawEx2() {
        return playCrawEx2;
    }

    public void setPlayCrawEx2(String playCrawEx2) {
        this.playCrawEx2 = playCrawEx2;
    }

    public String getPlayCrawEx3() {
        return playCrawEx3;
    }

    public void setPlayCrawEx3(String playCrawEx3) {
        this.playCrawEx3 = playCrawEx3;
    }

    public String getPlayCrawExFinal() {
        return playCrawExFinal;
    }

    public void setPlayCrawExFinal(String playCrawExFinal) {
        this.playCrawExFinal = playCrawExFinal;
    }

    public List<CrawValue> getDetail() {
        return detail;
    }

    public void setDetail(List<CrawValue> detail) {
        this.detail = detail;
    }

    public int getPlayHttpIndex() {
        return playHttpIndex;
    }

    public void setPlayHttpIndex(int playHttpIndex) {
        this.playHttpIndex = playHttpIndex;
    }

    public List<CrawValue> getPlay() {
        return play;
    }

    public void setPlay(List<CrawValue> play) {
        this.play = play;
    }

    public List<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
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

    public List<CrawValue> getNow() {
        return now;
    }

    public void setNow(List<CrawValue> now) {
        this.now = now;
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

    public List<CrawValue> getDate() {
        return date;
    }

    public void setDate(List<CrawValue> date) {
        this.date = date;
    }

    public List<CrawValue> getYear() {
        return year;
    }

    public void setYear(List<CrawValue> year) {
        this.year = year;
    }

    public List<CrawValue> getLanguage() {
        return language;
    }

    public void setLanguage(List<CrawValue> language) {
        this.language = language;
    }

    public List<CrawValue> getArea() {
        return area;
    }

    public void setArea(List<CrawValue> area) {
        this.area = area;
    }

    public List<CrawValue> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<CrawValue> sourceList) {
        this.sourceList = sourceList;
    }

    public List<CrawValue> getSourceItemText() {
        return sourceItemText;
    }

    public void setSourceItemText(List<CrawValue> sourceItemText) {
        this.sourceItemText = sourceItemText;
    }

    public List<CrawValue> getPathList() {
        return pathList;
    }

    public void setPathList(List<CrawValue> pathList) {
        this.pathList = pathList;
    }

    public List<CrawValue> getPathItemList() {
        return pathItemList;
    }

    public void setPathItemList(List<CrawValue> pathItemList) {
        this.pathItemList = pathItemList;
    }

    public List<CrawValue> getPathItemName() {
        return pathItemName;
    }

    public void setPathItemName(List<CrawValue> pathItemName) {
        this.pathItemName = pathItemName;
    }

    public List<CrawValue> getPathItemLink() {
        return pathItemLink;
    }

    public void setPathItemLink(List<CrawValue> pathItemLink) {
        this.pathItemLink = pathItemLink;
    }
}
