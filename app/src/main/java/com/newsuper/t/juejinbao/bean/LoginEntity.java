package com.newsuper.t.juejinbao.bean;


import com.newsuper.t.juejinbao.base.JJBApplication;
import com.newsuper.t.juejinbao.base.PagerCons;

import io.paperdb.Paper;

public class LoginEntity extends Entity {

    private boolean isLogin = false;

    /**
     * code : 0
     * msg : 登录成功
     * data : {"user_token":"cfedb7af1b5dfddea00c02c1d735e86c","nickname":"有毒快闪","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/myU16RLuxDf67iak1wicovaE0TK7eY7159iboePuqGPCBamFibnwQ4tdsZXuAPIKaljQanzicdzBIpFwncQsBRMuI6g/132","city":"","is_new":1,"invitation":"8639092","follow":0,"collection":0,"first_login":0,"unionid":"obUGU0i8273JhHDLixIvv4V2Oa30","achieved_gift_bag":0,"mobile":"","uid":9412}
     * time : 1563014801
     * vsn : 1.8.7
     */

    private int code;
    private String msg;
    private DataBean data;
    private int time;
    private String channel = JJBApplication.getChannel(); //提供给前端 用于接口请求
    private String vsn;


    //ture
    public static String getUserToken() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return "";
        } else {
            return loginEntity.getData().getUser_token();
        }
    }

    public static boolean getIsLogin() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 邀请码
     *
     * @return
     */
    public static String getInvitation() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return "";
        } else {
            return loginEntity.getData().getInvitation();
        }
    }

    /**
     * uid
     *
     * @return
     */
    public static int getUid() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return -1;
        } else {
            return loginEntity.getData().getUid();
        }
    }

    /**
     * uid
     *
     * @return
     */
    public static String getNickName() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return "";
        } else {
            return loginEntity.getData().getNickname();
        }
    }

    /**
     * 是否领取了大礼包
     *
     * @return
     */
    public static boolean getIsGetGiftPacks() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return false;
        } else {
            return loginEntity.getData().getAchieved_gift_bag() == 1;
        }
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isLogin() {
        return true;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

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

    public static boolean getIsNew() {
        LoginEntity loginEntity = Paper.book().read(PagerCons.USER_DATA);
        if (loginEntity == null) {
            return false;
        } else {
            return loginEntity.getData().getIs_new() == 1;
        }
    }

    public static class DataBean extends Entity{
        /**
         * user_token : cfedb7af1b5dfddea00c02c1d735e86c
         * nickname : 有毒快闪
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/myU16RLuxDf67iak1wicovaE0TK7eY7159iboePuqGPCBamFibnwQ4tdsZXuAPIKaljQanzicdzBIpFwncQsBRMuI6g/132
         * city :
         * is_new : 1
         * invitation : 8639092
         * follow : 0
         * collection : 0
         * first_login : 0
         * unionid : obUGU0i8273JhHDLixIvv4V2Oa30
         * achieved_gift_bag : 0
         * mobile :
         * uid : 9412
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
        private int uid = 0;
        private String password;
        private String inviter_uid;
        private boolean takeToken = false;
        private double coin = 0;
        private int trade_status = 0;

        public int getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(int trade_status) {
            this.trade_status = trade_status;
        }

        //是否显示弹窗
        private int show_invitation_code_alert;
        //"请输入正确邀请码",  邀请码不正确提示
        private String tips;
        //是否允许跳过
        private int request;

        public int getShow_invitation_code_alert() {
            return show_invitation_code_alert;
        }

        public void setShow_invitation_code_alert(int show_invitation_code_alert) {
            this.show_invitation_code_alert = show_invitation_code_alert;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public int getRequest() {
            return request;
        }

        public void setRequest(int request) {
            this.request = request;
        }

        public double getCoin() {
            return coin;
        }

        public void setCoin(double coin) {
            this.coin = coin;
        }

        public boolean isTakeToken() {
            return takeToken;
        }

        public void setTakeToken(boolean takeToken) {
            this.takeToken = takeToken;
        }

        public String getInviter_uid() {
            return inviter_uid;
        }

        public void setInviter_uid(String inviter_uid) {
            this.inviter_uid = inviter_uid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

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

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
