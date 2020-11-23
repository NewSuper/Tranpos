package com.newsuper.t.juejinbao.ui.movie.bean;

public class SelectSniff extends Sniff {
    public String title;
    public SelectSniff(int index, String webUrl) {
        super(index, webUrl);
    }


    public SelectSniff(int index, String webUrl , String title , String videoDownloadUrl) {
        super(index, webUrl);
        this.title = title;
        this.videoDownloadUrl = videoDownloadUrl;
    }
}
