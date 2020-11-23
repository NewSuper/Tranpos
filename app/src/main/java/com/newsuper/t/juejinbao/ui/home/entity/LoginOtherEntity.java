package com.newsuper.t.juejinbao.ui.home.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;
public class LoginOtherEntity extends Entity {

    /**
     * code : 1
     * msg :
     * data : []
     * time : 1562921360
     * vsn : 1.8.5
     */

    private int code;
    private String msg;
    private int time;
    private String vsn;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends Entity {
        /**
         * user_token : cee32fd1842034106359a39a679b5529
         * nickname : 掘金宝7639
         * avatar : https://jjlmobile.oss-cn-shenzhen.aliyuncs.com/default/avatar.png
         * city :
         * is_new : 1
         * invitation : 979275
         * follow : 0
         * collection : 0
         * first_login : 1
         * unionid :
         * achieved_gift_bag : 0
         * mobile : 18679107639
         */

        private String user_token;
        private String nickname;
        private String avatar;
        private String city;
        private int is_new;
        private String invitation;
        private int follow;
        private int collection;
        private int first_login;
        private String unionid;
        private int achieved_gift_bag;
        private String mobile;

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public String getInvitation() {
            return invitation;
        }

        public void setInvitation(String invitation) {
            this.invitation = invitation;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public int getFirst_login() {
            return first_login;
        }

        public void setFirst_login(int first_login) {
            this.first_login = first_login;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public int getAchieved_gift_bag() {
            return achieved_gift_bag;
        }

        public void setAchieved_gift_bag(int achieved_gift_bag) {
            this.achieved_gift_bag = achieved_gift_bag;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
