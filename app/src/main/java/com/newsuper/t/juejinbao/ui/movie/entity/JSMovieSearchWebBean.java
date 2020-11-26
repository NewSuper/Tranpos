package com.newsuper.t.juejinbao.ui.movie.entity;

import com.newsuper.t.juejinbao.ui.movie.adapter.EasyAdapter;

import java.io.Serializable;
import java.util.List;

public class JSMovieSearchWebBean implements Serializable {


    private String domain;

    private List<DataBean> data;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends EasyAdapter.TypeBean {
        /**
         * name : 狮子王
         * pic : /mstyle/images/default.png
         * url : /donghuapian/szw1/
         * actor : 唐纳德·格洛弗,阿尔法·伍达德,詹姆斯·厄尔·琼斯,切瓦特·埃加福特,科甘-迈克尔·凯,塞斯·罗根,比利·艾希纳,碧昂丝·诺尔斯,埃里克·安德烈,弗洛伦丝·卡松巴,约翰·卡尼,约翰·奥利弗,艾米·塞德丽丝
         */

        private String name;
        private String pic;
        private String url;
        private String actor;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }
    }
}
