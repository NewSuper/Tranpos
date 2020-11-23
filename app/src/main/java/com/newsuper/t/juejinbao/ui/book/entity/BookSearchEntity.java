package com.newsuper.t.juejinbao.ui.book.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

public class BookSearchEntity extends Entity {

    private int code;
    private String msg;
    private List<Book> data;
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

    public List<Book> getData() {
        return data;
    }

    public void setData(List<Book> data) {
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
