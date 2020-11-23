package com.newsuper.t.juejinbao.ui.share.entity;

import com.newsuper.t.juejinbao.base.PagerCons;
import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

import java.util.List;

import io.paperdb.Paper;


public class ShareConfigEntity extends Entity {

    /**
     * code : 0
     * msg : success
     * data : {"share_array":[{"name":"微信好友","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png","share_way":1,"tag":"","alias":"wechat"},{"name":"朋友圈","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/friend.png","share_way":1,"tag":"","alias":"friend"},{"name":"QQ","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/qq.png","share_way":1,"tag":"","alias":"qq"},{"name":"新浪微博","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/sinaweibo.png","share_way":1,"tag":"","alias":"sinaweibo"},{"name":"锁粉海报","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/poster.png","share_way":1,"tag":"","alias":"poster"},{"name":"复制链接","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/link.png","share_way":1,"tag":"","alias":"copy_link"},{"name":"分享链接","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/link.png","share_way":1,"tag":"","alias":"share_link"},{"name":"手机锁粉","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/phone.png","share_way":1,"tag":"锁粉","alias":"phone"}],"link":"http://dev.wechat.juejinchain.cn/#/","check_icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/share.gif","share_bg":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/tanchunag_bg.png","share_html":""}
     * time : 1563607184
     * vsn : 1.8.2
     */

    //ture
    public static String getDownLoadUrl() {
        DataBean dataBean = Paper.book().read(PagerCons.SHARE_CONFIG);
        if (dataBean == null) {
            return "";
        } else {
            return dataBean.getDownload_url();
        }
    }

    public static String getYingYongBaoUrl() {
        return "https://a.app.qq.com/o/simple.jsp?pkgname=com.juejinchain.android";
    }

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
         * share_array : [{"name":"微信好友","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png","share_way":1,"tag":"","alias":"wechat"},{"name":"朋友圈","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/friend.png","share_way":1,"tag":"","alias":"friend"},{"name":"QQ","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/qq.png","share_way":1,"tag":"","alias":"qq"},{"name":"新浪微博","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/sinaweibo.png","share_way":1,"tag":"","alias":"sinaweibo"},{"name":"锁粉海报","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/poster.png","share_way":1,"tag":"","alias":"poster"},{"name":"复制链接","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/link.png","share_way":1,"tag":"","alias":"copy_link"},{"name":"分享链接","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/link.png","share_way":1,"tag":"","alias":"share_link"},{"name":"手机锁粉","icon":"http://jjlmobile.juejinchain.com/vsn1_1/share_icon/phone.png","share_way":1,"tag":"锁粉","alias":"phone"}]
         * link : http://dev.wechat.juejinchain.cn/#/
         * check_icon : http://jjlmobile.juejinchain.com/vsn1_1/share_icon/share.gif
         * share_bg : http://jjlmobile.juejinchain.com/vsn1_1/share_icon/tanchunag_bg.png
         * share_html :
         */

        private String link;
        private String check_icon;
        private String share_bg;
        private String share_html;
        private String inner_link;
        private String download_url = "https://a.app.qq.com/o/simple.jsp?pkgname=com.juejinchain.android";

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getInner_link() {
            return inner_link;
        }

        public void setInner_link(String inner_link) {
            this.inner_link = inner_link;
        }

        private List<ShareArrayBean> share_array;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getCheck_icon() {
            return check_icon;
        }

        public void setCheck_icon(String check_icon) {
            this.check_icon = check_icon;
        }

        public String getShare_bg() {
            return share_bg;
        }

        public void setShare_bg(String share_bg) {
            this.share_bg = share_bg;
        }

        public String getShare_html() {
            return share_html;
        }

        public void setShare_html(String share_html) {
            this.share_html = share_html;
        }

        public List<ShareArrayBean> getShare_array() {
            return share_array;
        }

        public void setShare_array(List<ShareArrayBean> share_array) {
            this.share_array = share_array;
        }

        public static class ShareArrayBean {
            /**
             * name : 微信好友
             * icon : http://jjlmobile.juejinchain.com/vsn1_1/share_icon/wechat.png
             * share_way : 1
             * tag :
             * alias : wechat
             */

            private String name;
            private String icon;
            private int share_way;
            private String tag;
            private String alias;
            private String share_type;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getShare_way() {
                return share_way;
            }

            public void setShare_way(int share_way) {
                this.share_way = share_way;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getShare_type() {
                return share_type;
            }

            public void setShare_type(String share_type) {
                this.share_type = share_type;
            }
        }
    }
}
