package com.newsuper.t.juejinbao.ui.my.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class UserProfileEntity extends Entity {


    /**
     * code : 0
     * msg : success
     * data : {"nickname":"123","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/bxLdxOKRN6RxD1Xvn1OniarCugHVVpHUxqHcjJkIuRf6BrbyiaOichLZdicJd5Zau9k1dpVIiacawAuLZUmUhZdkQ2Q/132","sex":"未知","birthday":"","personal_sign":"","wechat_number":"","mobile":"13641417391","total_invitee":0,"vcoin":"8.8400","wx_qrcode":"","qq_qrcode":"","show_mobile_to_uplevel":1,"show_wechat_number_to_uplevel":1,"show_wx_qrcode_to_uplevel":1,"show_qq_qrcode_to_uplevel":1}
     * time : 1573352466
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
         * nickname : 123
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/bxLdxOKRN6RxD1Xvn1OniarCugHVVpHUxqHcjJkIuRf6BrbyiaOichLZdicJd5Zau9k1dpVIiacawAuLZUmUhZdkQ2Q/132
         * sex : 未知
         * birthday :
         * personal_sign :
         * wechat_number :
         * mobile : 13641417391
         * total_invitee : 0
         * vcoin : 8.8400
         * wx_qrcode :
         * qq_qrcode :
         * show_mobile_to_uplevel : 1
         * show_wechat_number_to_uplevel : 1
         * show_wx_qrcode_to_uplevel : 1
         * show_qq_qrcode_to_uplevel : 1
         */

        private String nickname;
        private String avatar;
        private String sex;
        private String birthday;
        private String personal_sign;
        private String wechat_number;
        private String mobile;
        private int total_invitee;
        private String vcoin;
        private String wx_qrcode;
        private String qq_qrcode;
        private int show_mobile_to_uplevel;
        private int show_wechat_number_to_uplevel;
        private int show_wx_qrcode_to_uplevel;
        private int show_qq_qrcode_to_uplevel;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPersonal_sign() {
            return personal_sign;
        }

        public void setPersonal_sign(String personal_sign) {
            this.personal_sign = personal_sign;
        }

        public String getWechat_number() {
            return wechat_number;
        }

        public void setWechat_number(String wechat_number) {
            this.wechat_number = wechat_number;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getTotal_invitee() {
            return total_invitee;
        }

        public void setTotal_invitee(int total_invitee) {
            this.total_invitee = total_invitee;
        }

        public String getVcoin() {
            return vcoin;
        }

        public void setVcoin(String vcoin) {
            this.vcoin = vcoin;
        }

        public String getWx_qrcode() {
            return wx_qrcode;
        }

        public void setWx_qrcode(String wx_qrcode) {
            this.wx_qrcode = wx_qrcode;
        }

        public String getQq_qrcode() {
            return qq_qrcode;
        }

        public void setQq_qrcode(String qq_qrcode) {
            this.qq_qrcode = qq_qrcode;
        }

        public int getShow_mobile_to_uplevel() {
            return show_mobile_to_uplevel;
        }

        public void setShow_mobile_to_uplevel(int show_mobile_to_uplevel) {
            this.show_mobile_to_uplevel = show_mobile_to_uplevel;
        }

        public int getShow_wechat_number_to_uplevel() {
            return show_wechat_number_to_uplevel;
        }

        public void setShow_wechat_number_to_uplevel(int show_wechat_number_to_uplevel) {
            this.show_wechat_number_to_uplevel = show_wechat_number_to_uplevel;
        }

        public int getShow_wx_qrcode_to_uplevel() {
            return show_wx_qrcode_to_uplevel;
        }

        public void setShow_wx_qrcode_to_uplevel(int show_wx_qrcode_to_uplevel) {
            this.show_wx_qrcode_to_uplevel = show_wx_qrcode_to_uplevel;
        }

        public int getShow_qq_qrcode_to_uplevel() {
            return show_qq_qrcode_to_uplevel;
        }

        public void setShow_qq_qrcode_to_uplevel(int show_qq_qrcode_to_uplevel) {
            this.show_qq_qrcode_to_uplevel = show_qq_qrcode_to_uplevel;
        }
    }
}
