package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class MovieCinamesEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : [{"id":10,"title":"哆来咪影院网","url":"https://m.haiduomi.cc","sort":0,"status":1,"uid":0,"create_time":1565941246},{"id":18,"title":"龙天下影院网","url":"https://s.lol5s.com","sort":0,"status":1,"uid":0,"create_time":1565941456},{"id":17,"title":"云影影院网","url":"https://m.yunbtv.com","sort":0,"status":1,"uid":0,"create_time":1565941426},{"id":16,"title":"橘子影视网","url":"https://m.jukantv.com","sort":0,"status":1,"uid":0,"create_time":1565941402},{"id":15,"title":"海兔影院","url":"http://www.haitum.cn","sort":0,"status":1,"uid":0,"create_time":1565941381},{"id":14,"title":"豌豆影视网","url":"https://www.wandouys.com","sort":0,"status":1,"uid":0,"create_time":1565941353},{"id":13,"title":"TV6影院","url":"http://www.tv6.com","sort":0,"status":1,"uid":0,"create_time":1565941327},{"id":12,"title":"达达兔影院","url":"https://www.dadatu.co","sort":0,"status":1,"uid":0,"create_time":1565941305},{"id":11,"title":"飞鱼电影网","url":"http://www.feiyudianying.com","sort":0,"status":1,"uid":0,"create_time":1565941276},{"id":1,"title":"薄荷影视网","url":"https://m.benbenji.com","sort":0,"status":1,"uid":0,"create_time":0},{"id":9,"title":"新世界动漫网","url":"https://m.x4jdm.tv","sort":0,"status":1,"uid":0,"create_time":1565941218},{"id":8,"title":"七彩虹影院网","url":"https://www.qiyeok.com","sort":0,"status":1,"uid":0,"create_time":1565941196},{"id":7,"title":"哈密影迷网","url":"https://m.haj8.net","sort":0,"status":1,"uid":0,"create_time":1565941169},{"id":6,"title":"神马影院","url":"https://m.aism.cc","sort":0,"status":1,"uid":0,"create_time":1565941145},{"id":5,"title":"匹诺影院网","url":"https://m.zhudaye.com","sort":0,"status":1,"uid":0,"create_time":1565941126},{"id":4,"title":"看剧库影院网","url":"https://m.kankanwu.com","sort":0,"status":1,"uid":0,"create_time":1565941099},{"id":3,"title":"快看影院网","url":"http://m.kkkkmao.com","sort":0,"status":1,"uid":0,"create_time":1565941027},{"id":2,"title":"木糖醇影院","url":"https://m.micaitu.net","sort":0,"status":1,"uid":0,"create_time":0}]
     * time : 1566193520
     * vsn : 1.8.8
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
         * id : 10
         * title : 哆来咪影院网
         * url : https://m.haiduomi.cc
         * sort : 0
         * status : 1
         * uid : 0
         * create_time : 1565941246
         */

        private int id;
        private String title;
        private String url;
        private String search_url;
        private int sort;
        private int status;
        private int uid;
        private int create_time;
        private int injection_time;
        private int progress;
        public boolean isCheck;

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public int getInjection_time() {
            return injection_time;
        }

        public void setInjection_time(int injection_time) {
            this.injection_time = injection_time;
        }

        public String getSearch_url() {
            return search_url;
        }

        public void setSearch_url(String search_url) {
            this.search_url = search_url;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
