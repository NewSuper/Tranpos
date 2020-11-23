package com.newsuper.t.juejinbao.ui.share.entity;

import java.util.List;

public class ShareDetailEntity {

    /**
     * code : 0
     * msg : success
     * data : {"gid":1003493,"title":"看资讯看电影，180天能赚豪车赚洋房，就差你没来了！","desc":"现在有 229531 人免费领取中，赶紧看看","img_url":"http://car3.autoimg.cn/cardfs/product/g25/M08/DF/6C/640x480_autohomecar__wKgHIlrE4iuABn1SAAoWiQxgggc028.jpg?x-oss-process=image/resize,h_50","copywriting_id":1,"tag":"car_20_25","app_images":["http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-1.png","http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-2.png","http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-3.png","http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-4.png"]}
     * time : 1563779006
     * vsn : 1.8.2
     */

    private int code;
    private String msg;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * gid : 1003493
         * title : 看资讯看电影，180天能赚豪车赚洋房，就差你没来了！
         * desc : 现在有 229531 人免费领取中，赶紧看看
         * img_url : http://car3.autoimg.cn/cardfs/product/g25/M08/DF/6C/640x480_autohomecar__wKgHIlrE4iuABn1SAAoWiQxgggc028.jpg?x-oss-process=image/resize,h_50
         * copywriting_id : 1
         * tag : car_20_25
         * app_images : ["http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-1.png","http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-2.png","http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-3.png","http://jjlmobile.juejinchain.com/share/mobile/img/weibo/720x1280-4.png"]
         */

//        private int gid;
        private String title;
        private String desc;
        private String img_url;
        private int copywriting_id;
        private String tag;
        private List<String> app_images;
        private String poster_bg;
        private String text_id;

        private String mini_program_id;
        private String mini_program_path;

        public String getMini_program_id() {
            return mini_program_id;
        }

        public void setMini_program_id(String mini_program_id) {
            this.mini_program_id = mini_program_id;
        }

        public String getMini_program_path() {
            return mini_program_path;
        }

        public void setMini_program_path(String mini_program_path) {
            this.mini_program_path = mini_program_path;
        }

        public String getText_id() {
            return text_id;
        }

        public void setText_id(String text_id) {
            this.text_id = text_id;
        }

        public String getPoster_bg() {
            return poster_bg;
        }

        public void setPoster_bg(String poster_bg) {
            this.poster_bg = poster_bg;
        }

        //        public int getGid() {
//            return gid;
//        }
//
//        public void setGid(int gid) {
//            this.gid = gid;
//        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getCopywriting_id() {
            return copywriting_id;
        }

        public void setCopywriting_id(int copywriting_id) {
            this.copywriting_id = copywriting_id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public List<String> getApp_images() {
            return app_images;
        }

        public void setApp_images(List<String> app_images) {
            this.app_images = app_images;
        }
    }
}
