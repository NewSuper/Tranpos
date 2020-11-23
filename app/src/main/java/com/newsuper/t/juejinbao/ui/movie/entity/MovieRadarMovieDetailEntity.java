package com.newsuper.t.juejinbao.ui.movie.entity;

import java.io.Serializable;
import java.util.List;

public class MovieRadarMovieDetailEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"cinemas":["{ \"name\": \"看看屋\", \"url\": \"https://m.kankanwu.com/vod-search-wd-{key}-p-1.html\", \"list\": [ { \"t\": \"select\", \"n\": \"body\", \"i\": -1 }, { \"t\": \"select\", \"n\": \"div[class=main]\", \"i\": -1 }, { \"t\": \"select\", \"n\": \"div[class=all_tab top]\", \"i\": -1 }, { \"t\": \"select\", \"n\": \"ul[class=new_tab_img]\", \"i\": 0 } ], \"ignore\": [ \"演员：\" ], \"item\": { \"img\": [ { \"t\": \"select\", \"n\": \"a\", \"i\": -1 }, { \"t\": \"select\", \"n\": \"div[class=picsize]\", \"i\": -1 }, { \"t\": \"select\", \"n\": \"img\", \"i\": -1 }, { \"t\": \"attr\", \"n\": \"src\", \"i\": -1 } ], \"title\": [ { \"t\": \"select\", \"n\": \"div[class=list_info]\", \"i\": 0 }, { \"t\": \"element\", \"n\": \"\", \"i\": 0 }, { \"t\": \"select\", \"n\": \"a\", \"i\": -1 }, { \"t\": \"text\", \"n\": \"\", \"i\": -1 } ], \"form\": [ { \"t\": \"select\", \"n\": \"div[class=list_info]\", \"i\": 0 }, { \"t\": \"element\", \"n\": \"\", \"i\": 1 }, { \"t\": \"select\", \"n\": \"a\", \"i\": -1 }, { \"t\": \"attr\", \"n\": \"title\", \"i\": -1 } ], \"actor\": [ { \"t\": \"select\", \"n\": \"div[class=list_info]\", \"i\": 0 }, { \"t\": \"element\", \"n\": \"\", \"i\": 3 }, { \"t\": \"text\", \"n\": \"\", \"i\": -1 } ] } }"]}
     * time : 1575008374
     * vsn : 1.8.8.2
     */

    private int code;
    private String msg;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
