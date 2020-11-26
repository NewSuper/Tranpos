package com.newsuper.t.juejinbao.bean;


import com.newsuper.t.juejinbao.ui.share.entity.ShareInfo;

public class ShareEvent {

    public ShareInfo shareModel;

    //分享类型
    //首页
    public static final String TYPE_INDEX = "/";
    //文章页
    public static final String TYPE_ARTICLE = "ArticleDetails";
    //视频的
    public static final String TYPE_VIDEO = "VideoDetail";

    public String type;


    /**
     * 不同类型分享href的path不一样
     * 首页   /
     * 文章页 /ArticleDetails/5130958
     * 视频  /VideoDetail/598401
     */
    public String typePath;


    public ShareEvent(ShareInfo model) {
        this.shareModel = model;
    }

}
