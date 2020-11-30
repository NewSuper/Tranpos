package com.newsuper.t.consumer.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VipTopInfoBean extends BaseBean implements Serializable{
    public VipInfoData data;
    public class VipInfoData implements Serializable{
        public String pic;//会员图片url
        public String card_name;// 	会员卡名称
        public String is_userinfo_automember;// 	是否填写信息后自动成为会员 0：不是 1：是
        public String is_balance;// 	是否可以通过充值余额开通会员 0：不是 1：是
        public String is_onlinepay;// 	是否开通在线充值会员 0：不是 1：是
        public String freeze;// 	是否冻结 0：否 1：是
        public String is_member;// 	是否已开通会员 0 ：没开通 1：开通
        public String balance;// 	会员余额
        public String num ;// 	会员卡号
        public String member_id;// 	会员id
        public String is_get_membercard;// 	是否已加入微信卡包 0：否 1：是
        public String hotline;// 	商家联系电话
        public String is_open_membercard;// 	是否开启会员卡包
        public String membercard_id;// 	会员卡id
        public String is_card;// 	是否绑定实体卡 0：否 1：是
        public String open_condition;
        public String grade_nickname;
    }

}
