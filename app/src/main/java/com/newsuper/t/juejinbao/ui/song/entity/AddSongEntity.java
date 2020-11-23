package com.newsuper.t.juejinbao.ui.song.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class AddSongEntity extends Entity {

    private int code;
    private String msg;
    private SongBean data;
    private int time;
    private String vsn;

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

    public SongBean getData() {
        return data;
    }

    public void setData(SongBean data) {
        this.data = data;
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
}
