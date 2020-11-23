package com.newsuper.t.juejinbao.ui.share.entity;

 //防封神器获取对应资源

public class ShareInfoByBraekLock {


    /**
     * code : 0
     * msg : success
     * data : {"copywriting":" https://a.app.qq.com/o/simple.jsp?pkgname=com.juejinchain.android&t=3c06c7c7dacb1129be8f8b160a2f319b","img_url":"http://jjlmobile.juejinchain.com/poster/201908/1/poster/2019081018344686599.jpg","title":"灰姑娘被原配逼发疯，老爷把她绑起来折磨，关键时刻救星带她离开","describe":"赚豪车赚洋房，回家过年提车就靠它了，赶紧看看>","url":"https://a.app.qq.com/o/simple.jsp?pkgname=com.juejinchain.android&t=3c06c7c7dacb1129be8f8b160a2f319b"}
     * time : 1565433286
     * vsn : 1.8.8.2
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
         * copywriting :  https://a.app.qq.com/o/simple.jsp?pkgname=com.juejinchain.android&t=3c06c7c7dacb1129be8f8b160a2f319b
         * img_url : http://jjlmobile.juejinchain.com/poster/201908/1/poster/2019081018344686599.jpg
         * title : 灰姑娘被原配逼发疯，老爷把她绑起来折磨，关键时刻救星带她离开
         * describe : 赚豪车赚洋房，回家过年提车就靠它了，赶紧看看>
         * url : https://a.app.qq.com/o/simple.jsp?pkgname=com.juejinchain.android&t=3c06c7c7dacb1129be8f8b160a2f319b
         */

        private String copywriting;
        private String img_url;
        private String title;
        private String describe;
        private String url;
        private int share_type;

        public int getShare_type() {
            return share_type;
        }

        public void setShare_type(int share_type) {
            this.share_type = share_type;
        }

        public String getCopywriting() {
            return copywriting;
        }

        public void setCopywriting(String copywriting) {
            this.copywriting = copywriting;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
