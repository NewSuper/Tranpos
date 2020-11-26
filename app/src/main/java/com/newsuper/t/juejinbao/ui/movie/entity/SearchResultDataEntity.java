package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class SearchResultDataEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : [{"vod_id":4042,"vod_name":"另一个地球"},{"vod_id":435900,"vod_name":"从太空看地球"},{"vod_id":408738,"vod_name":"流浪地球"},{"vod_id":420145,"vod_name":"绑架地球人"},{"vod_id":77913,"vod_name":"月球陷阱：地球危机"},{"vod_id":20245,"vod_name":"地球上最后一只僵尸"},{"vod_id":53745,"vod_name":"地球最后的夜晚"},{"vod_id":146568,"vod_name":"地球防卫遗孀"},{"vod_id":65776,"vod_name":"地球回音"},{"vod_id":61451,"vod_name":"重返地球"}]
     * time : 1563163467
     * vsn : 1.8.7
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends EasyAdapter.TypeBean {
        /**
         * vod_id : 4042
         * vod_name : 另一个地球
         */

        private int vod_id;
        private String vod_name;

        public int getVod_id() {
            return vod_id;
        }

        public void setVod_id(int vod_id) {
            this.vod_id = vod_id;
        }

        public String getVod_name() {
            return vod_name;
        }

        public void setVod_name(String vod_name) {
            this.vod_name = vod_name;
        }
    }
}
