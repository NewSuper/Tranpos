package com.newsuper.t.juejinbao.ui.my.entity;

import com.newsuper.t.juejinbao.bean.Entity;

import java.util.List;

public class UserInfoEntity extends Entity {

    /**
     * {
     * 	"code": 0,
     * 	"msg": "success",
     * 	"data": {
     * 		"id": 10684,
     * 		"avatar": "https:\/\/jjlmobile.oss-cn-shenzhen.aliyuncs.com\/avatar\/1569724894689.jpeg",
     * 		"nickname": "掘金宝3409",
     * 		"birthday": null,
     * 		"sex": 0,
     * 		"mobile": "18503063409",
     * 		"city": "",
     * 		"create_time": 1569408836,
     * 		"password": "2ec45f65b43234dde61cffea16476eb0",
     * 		"wechat_nickname": "",
     * 		"wechat_avatar": "",
     * 		"unionid": "",
     * 		"achieved_gift_bag": 0,
     * 		"is_new": 0,
     * 		"invitation": 3392713,
     * 		"qq_unionid": "",
     * 		"qq_nickname": "",
     * 		"has_set_password": 1,
     * 		"trade_status": 0,
     * 		"j_url": "",
     * 		"wechat_number": "",
     * 		"personal_sign": "",
     * 		"show_mobile_to_uplevel": 1,
     * 		"show_wechat_number_to_uplevel": 1,
     * 		"show_wx_qrcode_to_uplevel": 1,
     * 		"show_qq_qrcode_to_uplevel": 1,
     * 		"wx_qrcode": "",
     * 		"qq_qrcode": ""
     *        },
     * 	"time": 1573122746,
     * 	"vsn": "1.8.8.2"
     * }
     */
    private int code;
    private String msg;
    private int time;
    private String vsn;
    private User data;

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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public static class User{
        private String id;
        private String avatar;
        private String nickname;
        private String birthday;
        private String sex;
        private String mobile;
        private String city;
        private String create_time;
        private String password;
        private String wechat_nickname;
        private String wechat_avatar;
        private String unionid;
        private String achieved_gift_bag;
        private String is_new;
        private String invitation;
        private String qq_unionid;
        private String qq_nickname;
        private String has_set_password;
        private String trade_status;
        private String j_url;
        private String wechat_number;
        private String personal_sign;
        private int show_mobile_to_uplevel;
        private int show_wechat_number_to_uplevel;
        private int show_wx_qrcode_to_uplevel;
        private int show_qq_qrcode_to_uplevel;
        private String wx_qrcode;
        private String qq_qrcode;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getWechat_nickname() {
            return wechat_nickname;
        }

        public void setWechat_nickname(String wechat_nickname) {
            this.wechat_nickname = wechat_nickname;
        }

        public String getWechat_avatar() {
            return wechat_avatar;
        }

        public void setWechat_avatar(String wechat_avatar) {
            this.wechat_avatar = wechat_avatar;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getAchieved_gift_bag() {
            return achieved_gift_bag;
        }

        public void setAchieved_gift_bag(String achieved_gift_bag) {
            this.achieved_gift_bag = achieved_gift_bag;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

        public String getInvitation() {
            return invitation;
        }

        public void setInvitation(String invitation) {
            this.invitation = invitation;
        }

        public String getQq_unionid() {
            return qq_unionid;
        }

        public void setQq_unionid(String qq_unionid) {
            this.qq_unionid = qq_unionid;
        }

        public String getQq_nickname() {
            return qq_nickname;
        }

        public void setQq_nickname(String qq_nickname) {
            this.qq_nickname = qq_nickname;
        }

        public String getHas_set_password() {
            return has_set_password;
        }

        public void setHas_set_password(String has_set_password) {
            this.has_set_password = has_set_password;
        }

        public String getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(String trade_status) {
            this.trade_status = trade_status;
        }

        public String getJ_url() {
            return j_url;
        }

        public void setJ_url(String j_url) {
            this.j_url = j_url;
        }

        public String getWechat_number() {
            return wechat_number;
        }

        public void setWechat_number(String wechat_number) {
            this.wechat_number = wechat_number;
        }

        public String getPersonal_sign() {
            return personal_sign;
        }

        public void setPersonal_sign(String personal_sign) {
            this.personal_sign = personal_sign;
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
    }
}
