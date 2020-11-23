package com.newsuper.t.juejinbao.ui.home.entity;

import java.util.List;

public class HotWordSearchEntity {

    /**
     * code : 0
     * msg : success
     * data : [{"aid":"9510258","hot_word":"香港反对派区议员凌晨被拘捕","title":"暴徒昨晚堵路纵火，港媒：港警拘约60人，包括身为反对派的元朗区议会主席","describe":"【环球网报道】据香港《大公报》\u201c东网\u201d等港媒报道，昨日香港元朗有暴徒非法集结，进行堵路纵火等暴力活动，香港警方进行驱散和拘捕行动。","showType":1,"img_url":["http://p3-tt.byteimg.com/list/190x124/pgc-image/10e582539193451eb334cde9e3324af9"],"author":"中国网直播","author_logo":"http://p3.pstatp.com/medium/ff900000be838fb1bb47","publish_time":"1584859579","comment_count":9,"read_count":3018,"title_blod":[{"start":30,"len":1}],"describe_blod":[{"start":30,"len":1}]},{"aid":"9510184","hot_word":"香港反对派区议员凌晨被拘捕","title":"暴徒昨晚又堵路、纵火！港警拘约60人，元朗区议会主席被捕","describe":"报道称，期间，警方拘捕约60人，反对派区议员、元朗区议会主席黄伟贤亦因涉嫌阻碍警务人员执行职务被捕。","showType":2,"img_url":["http://p9-tt.byteimg.com/list/190x124/pgc-image/RtvbrhrEF2sPgk","http://p3-tt.byteimg.com/list/190x124/pgc-image/Rtvbrj9GCJmB4Q","http://p9-tt.byteimg.com/list/190x124/pgc-image/RtvbrjV4U8v5P1"],"author":"人民日报海外网","author_logo":"http://sf1-ttcdn-tos.pstatp.com/img/pgc-image/6f7b7a71bcf84fd693c1dfe8f2ebd668~120x256.image","publish_time":"1584844944","comment_count":15,"read_count":30739,"title_blod":[{"start":21,"len":1},{"start":26,"len":1}],"describe_blod":[{"start":21,"len":1},{"start":26,"len":1}]}]
     * time : 1585219921
     * vsn : 1.8.8.2
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

    public static class DataBean {
        /**
         * aid : 9510258
         * hot_word : 香港反对派区议员凌晨被拘捕
         * title : 暴徒昨晚堵路纵火，港媒：港警拘约60人，包括身为反对派的元朗区议会主席
         * describe : 【环球网报道】据香港《大公报》“东网”等港媒报道，昨日香港元朗有暴徒非法集结，进行堵路纵火等暴力活动，香港警方进行驱散和拘捕行动。
         * showType : 1
         * img_url : ["http://p3-tt.byteimg.com/list/190x124/pgc-image/10e582539193451eb334cde9e3324af9"]
         * author : 中国网直播
         * author_logo : http://p3.pstatp.com/medium/ff900000be838fb1bb47
         * publish_time : 1584859579
         * comment_count : 9
         * read_count : 3018
         * title_blod : [{"start":30,"len":1}]
         * describe_blod : [{"start":30,"len":1}]
         */

        private String aid;
        private String hot_word;
        private String title;
        private String describe;
        private int showType;
        private String author;
        private String author_logo;
        private long publish_time;
        private int comment_count;
        private int read_count;
        private boolean isSelected;
        private List<String> img_url;
        private List<TitleBlodBean> title_blod;
        private List<DescribeBlodBean> describe_blod;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getHot_word() {
            return hot_word;
        }

        public void setHot_word(String hot_word) {
            this.hot_word = hot_word;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor_logo() {
            return author_logo;
        }

        public void setAuthor_logo(String author_logo) {
            this.author_logo = author_logo;
        }

        public long getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(long publish_time) {
            this.publish_time = publish_time;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getRead_count() {
            return read_count;
        }

        public void setRead_count(int read_count) {
            this.read_count = read_count;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean select) {
            isSelected = select;
        }

        public List<String> getImg_url() {
            return img_url;
        }

        public void setImg_url(List<String> img_url) {
            this.img_url = img_url;
        }

        public List<TitleBlodBean> getTitle_blod() {
            return title_blod;
        }

        public void setTitle_blod(List<TitleBlodBean> title_blod) {
            this.title_blod = title_blod;
        }

        public List<DescribeBlodBean> getDescribe_blod() {
            return describe_blod;
        }

        public void setDescribe_blod(List<DescribeBlodBean> describe_blod) {
            this.describe_blod = describe_blod;
        }

        public static class TitleBlodBean {
            /**
             * start : 30
             * len : 1
             */

            private int start;
            private int len;

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public int getLen() {
                return len;
            }

            public void setLen(int len) {
                this.len = len;
            }
        }

        public static class DescribeBlodBean {
            /**
             * start : 30
             * len : 1
             */

            private int start;
            private int len;

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public int getLen() {
                return len;
            }

            public void setLen(int len) {
                this.len = len;
            }
        }
    }
}
