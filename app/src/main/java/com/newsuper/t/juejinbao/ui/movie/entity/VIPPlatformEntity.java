package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;

public class VIPPlatformEntity implements Serializable {


    /**
     * id : 1
     * title : 腾讯视频
     * img_url : http://jjmovie.juejinchain.com/movie_icon/tengxun.png
     * index_url : https://m.v.qq.com/index.html
     * preg_url : https://m.v.qq.com/x/cover/e/eaa7qkjmlecfnp1.html
     * is_resolving : 1
     * status : 1
     * sort : 99
     */

    private int id;
    private String title;
    private String img_url;
    private String index_url;
    private String preg_url;
    private int is_resolving;
    private int status;
    private int sort;
    private String type;
    private String remoteAllUrl;
    private String remoteUrl;

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIndex_url() {
        return index_url;
    }

    public void setIndex_url(String index_url) {
        this.index_url = index_url;
    }

    public String getPreg_url() {
        return preg_url;
    }

    public void setPreg_url(String preg_url) {
        this.preg_url = preg_url;
    }

    public int getIs_resolving() {
        return is_resolving;
    }

    public void setIs_resolving(int is_resolving) {
        this.is_resolving = is_resolving;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemoteAllUrl() {
        return remoteAllUrl;
    }

    public void setRemoteAllUrl(String remoteAllUrl) {
        this.remoteAllUrl = remoteAllUrl;
    }
}
