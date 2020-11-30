package com.newsuper.t.consumer.bean;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class VipInfoBean extends BaseBean{
    public VipInfoData data;
    public class VipInfoData{
        public int id;//会员id
        public String admin_id;// 	商家ID
        public String employee_id;//	string 	智能机员工账号id
        public String lewaimai_customer_id;// 	string 	顾客id
        public String level;//	string 	会员等级
        public String num;// 	string 	会员编号
        public String balance;// 	string 	会员余额
        public String point;// 	string 	会员积分
        public String name;// 	string 	会员名
        public String sex;// 	string 	会员性别 0：男 1：女 2：其他
        public String birthday;// 	string 	生日
        public String tel;// 	string 	电话
        public String address;// 	string 	地址
        public String freeze;// 	string 	是否冻结： 1是，0否
        public String is_card;// 	string 	是否开启了实体卡
        public String open_no_card_payment;// 	string 	是否开启手机号码+密码支付，0：否，默认，1：是
        public String is_weixin;// 	string 	是否开启了微信会员
        public String total_recharge;// 	string 	充值总额
        public String total_cost;// 	string 	累计消费金额
        public String init_date;// 	string 	成为会员时间
        public String is_get_membercard;// 	string 	是否已领取微信会员卡券
        public String x_discount_value;// 	string 	第X件优惠金额
        public String user_card_code;// 	string 	对应用户领取卡券的唯一凭证
    }

}
