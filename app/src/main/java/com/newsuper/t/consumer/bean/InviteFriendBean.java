package com.newsuper.t.consumer.bean;

import java.util.ArrayList;

public class InviteFriendBean extends BaseBean{
    public InviteFriendData data;
    public static class InviteFriendData{
        /* "invite_title": "邀好友，赚取红包",
                 "invite_img": "http://img.lewaimai.com/upload_files/image/20180615/cxOXZ19Y8igkrgRIethj5PXBikSwMDOQ.jpg",
                 "rule": "邀好友，赚取红包邀好友，赚取红包邀好友，赚取红包邀好友，赚取红包",
                 "share_title": 邀好友，赚取红包 ,
                "share_content": "邀好友，赚取红包邀好友，赚取红包邀好友，赚取红包邀好友，赚取红包",
                "share_img": "http://img.lewaimai.com/upload_files/image/20180615/cxOXZ19Y8igkrgRIethj5PXBikSwMDOQ.jpg",
                "come_customer_id": 1,
                "come_customer_nickname": "好好的",
                "come_customer_headimgurl": "http://img.lewaimai.com/upload_files/image/20180615/cxOXZ19Y8igkrgRIethj5PXBikSwMDOQ.jpg",
                //当参数type=1的时候下面的字段才有返回
                "invite_num": "0",*/

        public String invite_title;
        public String invite_img;
        public ArrayList<String> rule;
        public String share_title;
        public String share_content;
        public String share_img;
        public String come_customer_id;
        public String come_customer_nickname;
        public String come_customer_headimgurl;
        public String invite_num;
        public String invite_style_type;

    }
}
