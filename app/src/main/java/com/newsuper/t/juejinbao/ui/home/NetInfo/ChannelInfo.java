package com.newsuper.t.juejinbao.ui.home.NetInfo;

import com.newsuper.t.juejinbao.bean.Entity;
import com.newsuper.t.juejinbao.ui.home.entity.ChannelEntity;

import java.util.List;

import java.util.List;

public class ChannelInfo extends Entity {

    /**
     * code : 0
     * msg : success
     * data : [{"id":101,"name":"掘金宝"},{"id":1,"name":"热门"},{"id":44,"name":"视频"},{"id":50,"name":"小视频"},{"id":3,"name":"社会"},{"id":43,"name":"本地"},{"id":4,"name":"娱乐"},{"id":7,"name":"体育"},{"id":45,"name":"币圈"}]
     * time : 1562594458
     * vsn : 1.8.2
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private List<ChannelEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = vsn;
    }

    public List<ChannelEntity> getData() {
        return data;
    }

    public void setData(List<ChannelEntity> data) {
        this.data = data;
    }

}
