package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class UnReadEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : [{"id":42844,"from_uid":0,"content":"<a data-url=\"#/replylist\" class=\"link\">查看我的反馈<\/a>","read_time":0,"create_time":1559792576,"image":["https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png"],"from_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png","title":"您的反馈已有答复<\/div>啥子<br/><div>☆☆很高心为您服务，你的满意与支持，是掘金宝发展的动力，感谢您的反馈！祝您生活愉快，万事如意☆☆","url":"#/replylist"},{"id":47220,"from_uid":0,"content":"<a data-url=\"#/replylist\" class=\"link\">查看我的反馈<\/a>","read_time":0,"create_time":1563012703,"image":["https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png"],"from_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png","title":"您的反馈已有答复<\/div><a target=\"_blank\" class=\"link\" data-url=\"http://www.fqxs.org/\" style=\"color: blue;cursor: pointer\">http://www.fqxs.org/<\/a>\" style=\"color: blue;cursor: pointer\"><a target=\"_blank\" class=\"link\" data-url=\"http://www.fqxs.org/\" style=\"color: blue;cursor: pointer\">http://www.fqxs.org/<\/a><br/><div>☆☆很高心为您服务，你的满意与支持，是掘金宝发展的动力，感谢您的反馈！祝您生活愉快，万事如意☆☆","url":"#/replylist"},{"id":47221,"from_uid":0,"content":"<a data-url=\"#/replylist\" class=\"link\">查看我的反馈<\/a>","read_time":0,"create_time":1563012795,"image":["https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png"],"from_avatar":"https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png","title":"您的反馈已有答复<\/div><a target=\"_blank\" class=\"link\" data-url=\"http://www.fqxs.org/\" style=\"color: blue;cursor: pointer\">http://www.fqxs.org/<\/a><br/><div>☆☆很高心为您服务，你的满意与支持，是掘金宝发展的动力，感谢您的反馈！祝您生活愉快，万事如意☆☆","url":"#/replylist"}]
     * time : 1563532735
     * vsn : 1.8.2
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
         * id : 42844
         * from_uid : 0
         * content : <a data-url="#/replylist" class="link">查看我的反馈</a>
         * read_time : 0
         * create_time : 1559792576
         * image : ["https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png"]
         * from_avatar : https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/aboutus_logo.png
         * title : 您的反馈已有答复</div>啥子<br/><div>☆☆很高心为您服务，你的满意与支持，是掘金宝发展的动力，感谢您的反馈！祝您生活愉快，万事如意☆☆
         * url : #/replylist
         */

        private int id;
        private int from_uid;
        private String content;
        private int read_time;
        private int create_time;
        private String from_avatar;
        private String title;
        private String url;
        private List<String> image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFrom_uid() {
            return from_uid;
        }

        public void setFrom_uid(int from_uid) {
            this.from_uid = from_uid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRead_time() {
            return read_time;
        }

        public void setRead_time(int read_time) {
            this.read_time = read_time;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getFrom_avatar() {
            return from_avatar;
        }

        public void setFrom_avatar(String from_avatar) {
            this.from_avatar = from_avatar;
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

        public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }
    }
}
