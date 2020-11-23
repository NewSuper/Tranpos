package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;

public class PushEntity implements Serializable {

    /**
     * action_type : 1
     * action_url : http://dev.wechat.juejinchain.cn/#/
     * show_title : 内部浏览器展示标题
     * desc : 描述
     * imgurl : http://p1-tt.byteimg.com/large/pgc-image/RXrOdURDrloHTt?from=detail
     * show_type : 2
     */

    private String action_type;
    private String action_url;
    private String show_title;
    private String desc;
    private String imgurl;
    private String show_type;
    private String msg_type;

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public String getAction_url() {
        return action_url;
    }

    public void setAction_url(String action_url) {
        this.action_url = action_url;
    }

    public String getShow_title() {
        return show_title;
    }

    public void setShow_title(String show_title) {
        this.show_title = show_title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    @Override
    public String toString() {
        return "PushEntity{" +
                "action_type='" + action_type + '\'' +
                ", action_url='" + action_url + '\'' +
                ", show_title='" + show_title + '\'' +
                ", desc='" + desc + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", show_type='" + show_type + '\'' +
                ", msg_type='" + msg_type + '\'' +
                '}';
    }
}
