package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class SmallVideoContentEntity extends Entity {


    /**
     * code : 0
     * msg : success
     * data : {"total":2,"per_page":10,"current_page":"1","last_page":1,"data":[{"cid":66839,"content":"资本主义水深火热。","comment_time":1562880390,"fabulous":0,"nickname":"文光翟","avatar":"http://p3.pstatp.com/thumb/2c6b00137f8aee479099","is_fabulous":0},{"cid":66838,"content":"为什么用汉字","comment_time":1562861502,"fabulous":0,"nickname":"鲁班娱乐场","avatar":"http://p1.pstatp.com/thumb/da6f000443a179bb5d67","is_fabulous":0}]}
     * time : 1563003501
     * vsn : 1.8.7
     */

    private int code;
    private String msg;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * total : 2
         * per_page : 10
         * current_page : 1
         * last_page : 1
         * data : [{"cid":66839,"content":"资本主义水深火热。","comment_time":1562880390,"fabulous":0,"nickname":"文光翟","avatar":"http://p3.pstatp.com/thumb/2c6b00137f8aee479099","is_fabulous":0},{"cid":66838,"content":"为什么用汉字","comment_time":1562861502,"fabulous":0,"nickname":"鲁班娱乐场","avatar":"http://p1.pstatp.com/thumb/da6f000443a179bb5d67","is_fabulous":0}]
         */

        private int total;
        private int per_page;
        private String current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * cid : 66839
             * content : 资本主义水深火热。
             * comment_time : 1562880390
             * fabulous : 0
             * nickname : 文光翟
             * avatar : http://p3.pstatp.com/thumb/2c6b00137f8aee479099
             * is_fabulous : 0
             */

            private int cid;
            private String content;
            private long comment_time;
            private int fabulous;
            private String nickname;
            private String avatar;
            private int is_fabulous;
            private int rid;
            private int uid;


            private long create_time;

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public int getRid() {
                return rid;
            }

            public void setRid(int rid) {
                this.rid = rid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getComment_time() {
                return comment_time;
            }

            public void setComment_time(long comment_time) {
                this.comment_time = comment_time;
            }

            public int getFabulous() {
                return fabulous;
            }

            public void setFabulous(int fabulous) {
                this.fabulous = fabulous;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getIs_fabulous() {
                return is_fabulous;
            }

            public void setIs_fabulous(int is_fabulous) {
                this.is_fabulous = is_fabulous;
            }
        }
    }
}

