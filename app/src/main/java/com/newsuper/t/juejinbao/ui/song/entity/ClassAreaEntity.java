package com.newsuper.t.juejinbao.ui.song.entity;

import java.util.List;

public class ClassAreaEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"id":1,"en":1,"cn":null,"title":"怀旧经典","thumbnail":null},{"id":2,"en":2,"cn":null,"title":"影视原声","thumbnail":null},{"id":3,"en":3,"cn":null,"title":"经典说唱","thumbnail":null},{"id":4,"en":4,"cn":null,"title":"亲子儿歌","thumbnail":null},{"id":5,"en":5,"cn":null,"title":"夜晚入睡必听","thumbnail":null},{"id":6,"en":6,"cn":null,"title":"70后","thumbnail":null},{"id":7,"en":7,"cn":null,"title":"欧美经典","thumbnail":null},{"id":8,"en":8,"cn":null,"title":"民族特色","thumbnail":null}]
     */

    private int code;
    private String msg;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * en : 1
         * cn : null
         * title : 怀旧经典
         * thumbnail : null
         */

        private int id;
        private int en;
        private Object cn;
        private String title;
        private Object thumbnail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getEn() {
            return en;
        }

        public void setEn(int en) {
            this.en = en;
        }

        public Object getCn() {
            return cn;
        }

        public void setCn(Object cn) {
            this.cn = cn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Object thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
