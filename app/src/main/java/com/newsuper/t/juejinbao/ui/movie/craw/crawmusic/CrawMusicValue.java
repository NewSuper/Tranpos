package com.newsuper.t.juejinbao.ui.movie.craw.crawmusic;

import com.newsuper.t.juejinbao.ui.movie.craw.CrawValue;

import java.util.List;

public class CrawMusicValue {


    /**
     * sourceListStartIndex : 1
     * sourceListEndIndex : 2
     * name : 音乐助手
     * requestType : get
     * requestFormData : [["wd","key"],["submit","search"]]
     * url : https://music.hwkxk.cn/?kw=龙舌兰&lx=migu
     * list : [{"t":"select","n":"body","i":-1},{"t":"select","n":"div[class=xing_vb]","i":0}]
     * ignore : ["演员："]
     * item : {"name":[{"t":"select","n":"span[class=xing_vb4]","i":0},{"t":"text","n":"","i":-1}],"link":[{"t":"select","n":"span[class=xing_vb4]","i":0},{"t":"attr","n":"href","i":-1}]}
     */

    private int sourceListStartIndex;
    private int sourceListEndIndex;
    private String name;
    private String requestType;
    private String url;
    private ItemBean item;
    private List<List<String>> requestFormData;
    private List<CrawValue> list;
    private List<String> ignore;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
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

    public List<List<String>> getRequestFormData() {
        return requestFormData;
    }

    public void setRequestFormData(List<List<String>> requestFormData) {
        this.requestFormData = requestFormData;
    }

    public List<CrawValue> getList() {
        return list;
    }

    public void setList(List<CrawValue> list) {
        this.list = list;
    }

    public List<String> getIgnore() {
        return ignore;
    }

    public void setIgnore(List<String> ignore) {
        this.ignore = ignore;
    }

    public static class ItemBean {
        private List<CrawValue> songName;
        private List<CrawValue> singer;
        private List<CrawValue> link;

        public List<CrawValue> getSongName() {
            return songName;
        }

        public void setSongName(List<CrawValue> songName) {
            this.songName = songName;
        }

        public List<CrawValue> getSinger() {
            return singer;
        }

        public void setSinger(List<CrawValue> singer) {
            this.singer = singer;
        }

        public List<CrawValue> getLink() {
            return link;
        }

        public void setLink(List<CrawValue> link) {
            this.link = link;
        }


    }


}
