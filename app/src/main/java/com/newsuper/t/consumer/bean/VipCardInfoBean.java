package com.newsuper.t.consumer.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VipCardInfoBean extends BaseBean{
    public VipCardData data;
    public class VipCardData implements Serializable{
        public String admin_id;//商家id
        public String member_num;//会员数量
        public String is_userinfo_automember;//是否填写信息后自动成为会员 0：不是 1：是
        public String is_balance;//	是否可以通过充值余额开通会员 0：不是 1：是
        public String balance_value;// 	充值多少可以开通会员
        public String is_onlinepay;// 	是否开通在线充值会员 0 ：没开通 1：开通
        public String card_name;// 	会员卡名称
        public String pic;// 	会员图片
        public String member_privilege;// 	会员特权
        public String open_condition;// 	开通条件
        public String open_upsend;// 	是否开启充值送功能 0 ：没开通 1：开通
        public String upsend;// 	充值送详情
        public String open_zhifubao;// 	是否开启支付宝支付 0：不是 1：是
        public String open_caifutong;//	是否开启财付通 0：不是 1：是
        public String open_self_weixinzhifu;// 	是否开启自己的微信支付 0：不是 1：是
        public String open_weixinzhifu;// 	是否开启乐外卖微信支付 0 ：没开通 1：开通
        public String is_open_membercard;//	是否开启会员卡包 0 ：没开通 1：开通
        public String membercard_id;// 	会员卡id
        public String membercard_info;// 	会员卡信息
    }

}
